/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.util;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import com.wolfram.jlink.NativeLink;
import com.wolfram.jlink.Utils;
import com.wolfram.jlink.WrappedKernelLink;
import com.wolfram.jlink.ui.ConsoleWindow;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ItemSelectable;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class LinkSnooper
extends Thread {
    private PrintStream strm;
    private PrintStream logFileStrm;
    private KernelLink kernelMain = null;
    private KernelLink feMain = null;
    private KernelLink kernelService = null;
    private KernelLink feService = null;
    private KernelLink kernelPreemptive = null;
    private KernelLink fePreemptive = null;
    private String feSideName = "FE";
    private String kernelSideName = "K";
    private String toKernelArrow;
    private String toFEArrow;
    private String preemptiveLinkPrefix = "   -Pre-  ";
    private String serviceLinkPrefix = "      -Serv-  ";
    private boolean skipInitTraffic = false;
    private boolean doPrint = true;
    private boolean timestamps = false;
    private SimpleDateFormat timestampFormatter = new SimpleDateFormat("HH:mm:ss.SSS");
    private volatile boolean captureMain = true;
    private volatile boolean capturePre = true;
    private volatile boolean captureServ = true;
    private int pollInterval = 10;
    private boolean useWindow = true;

    public static void main(String[] argv) throws MathLinkException {
        LinkSnooper snooper = new LinkSnooper(argv);
        snooper.start();
    }

    public LinkSnooper(String[] argv) throws MathLinkException {
        this(argv, null);
    }

    public LinkSnooper(String[] argv, PrintStream pstrm) throws MathLinkException {
        String linkedEnvId;
        MathLink ml;
        int i;
        this.strm = pstrm;
        for (i = 0; i < argv.length - 1; ++i) {
            if (argv[i].equalsIgnoreCase("-logfile")) {
                try {
                    this.logFileStrm = new PrintStream(new FileOutputStream(argv[i + 1]));
                }
                catch (Exception e) {
                    System.err.println("Could not open file " + argv[i] + " for writing.");
                }
                continue;
            }
            if (argv[i].equalsIgnoreCase("-timestamps")) {
                this.timestamps = true;
                continue;
            }
            if (argv[i].equalsIgnoreCase("-pollinterval")) {
                try {
                    this.pollInterval = Integer.parseInt(argv[i + 1]);
                }
                catch (Exception e) {}
                continue;
            }
            if (!argv[i].equalsIgnoreCase("-links")) continue;
            String links = argv[i + 1].toLowerCase();
            this.captureMain = links.contains("main");
            this.capturePre = links.contains("pre");
            this.captureServ = links.contains("serv");
        }
        if (this.strm == null) {
            for (i = 0; i < argv.length; ++i) {
                if (!argv[i].equalsIgnoreCase("-nowindow")) continue;
                this.useWindow = false;
            }
            if (this.useWindow) {
                ConsoleWindow cw = ConsoleWindow.getInstance();
                cw.setMaxLines(15000);
                cw.setCapture(3);
                cw.setTitle("LinkSnooper");
                cw.setSize(700, 600);
                cw.setVisible(true);
            }
        }
        this.output("LinkSnooper command-line params:");
        for (i = 0; i < argv.length; ++i) {
            this.output("   " + argv[i]);
        }
        this.feMain = MathLinkFactory.createKernelLink(argv);
        this.feMain.connect();
        if (this.feMain instanceof WrappedKernelLink && (ml = ((WrappedKernelLink)this.feMain).getMathLink()) instanceof NativeLink && (linkedEnvId = ((NativeLink)ml).getLinkedEnvID()) != null) {
            NativeLink.setEnvID(linkedEnvId);
        }
        ArrayList<String> kernelLinkArgs = new ArrayList<String>();
        for (int i2 = 0; i2 < argv.length; ++i2) {
            if (argv[i2].equalsIgnoreCase("-kernelname")) {
                kernelLinkArgs.add("-linkname");
                String kname = argv[i2 + 1];
                if (Utils.isWindows() && kname.startsWith("'") && kname.endsWith("'")) {
                    kname = kname.substring(1, kname.length() - 1);
                }
                kernelLinkArgs.add(kname);
                continue;
            }
            if (argv[i2].equalsIgnoreCase("-kernelmode")) {
                kernelLinkArgs.add("-linkmode");
                kernelLinkArgs.add(argv[++i2]);
                continue;
            }
            if (argv[i2].equalsIgnoreCase("-kernelprot")) {
                kernelLinkArgs.add("-linkprotocol");
                kernelLinkArgs.add(argv[++i2]);
                continue;
            }
            if (argv[i2].equalsIgnoreCase("-kernelhost")) {
                kernelLinkArgs.add("-linkhost");
                kernelLinkArgs.add(argv[++i2]);
                continue;
            }
            if (argv[i2].equalsIgnoreCase("-kernelopts")) {
                kernelLinkArgs.add("-linkoptions");
                kernelLinkArgs.add(argv[++i2]);
                continue;
            }
            if (argv[i2].equalsIgnoreCase("-kernelside")) {
                this.kernelSideName = argv[++i2];
                continue;
            }
            if (argv[i2].equalsIgnoreCase("-feside")) {
                this.feSideName = argv[++i2];
                continue;
            }
            if (!argv[i2].equalsIgnoreCase("-noinit")) continue;
            this.skipInitTraffic = true;
        }
        if (!kernelLinkArgs.contains("-linkmode")) {
            kernelLinkArgs.add("-linkmode");
            kernelLinkArgs.add("launch");
        }
        this.kernelMain = MathLinkFactory.createKernelLink(kernelLinkArgs.toArray(new String[0]));
        this.kernelMain.connect();
        this.toKernelArrow = this.feSideName + " ---> " + this.kernelSideName + ": ";
        this.toFEArrow = this.feSideName + " <--- " + this.kernelSideName + ": ";
        this.feMain.addMessageHandler(LinkSnooper.class, this, "feMainMessageHandler");
        this.kernelMain.addMessageHandler(LinkSnooper.class, this, "kernelMainMessageHandler");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void run() {
        if (this.feMain == null || this.kernelMain == null) {
            this.output("Broken Link. Cannot Relay.");
            return;
        }
        this.output("Start Monitoring...");
        this.doPrint = !this.skipInitTraffic;
        Expr expr = null;
        block4 : do {
            try {
                do {
                    String head;
                    if (this.feMain.ready()) {
                        expr = this.feMain.peekExpr();
                        if (this.skipInitTraffic && !this.doPrint && ((head = LinkSnooper.exprToSafeString(expr.head())).equals("EnterTextPacket") || head.equals("EnterExpressionPacket"))) {
                            this.doPrint = true;
                        }
                        if (this.captureMain) {
                            this.output(this.toKernelArrow + LinkSnooper.exprToSafeString(expr));
                        }
                        this.kernelMain.transferExpression(this.feMain);
                        this.kernelMain.flush();
                        expr.dispose();
                    }
                    if (this.kernelMain.ready()) {
                        String func;
                        expr = this.kernelMain.peekExpr();
                        head = LinkSnooper.exprToSafeString(expr.head());
                        String exprToPrint = head.equals("CallPacket") ? ((func = LinkSnooper.exprToSafeString(expr.part(1).head())).equals("FrontEnd`SetKernelSymbolContexts") || func.equals("FrontEnd`AddFunctionTemplateInformationToFunctions") || func.equals("FrontEnd`SetFunctionInformation") ? "CallPacket[" + LinkSnooper.exprToSafeString(expr.part(1).head()) + "[ -- large contents not printed -- ]]" : LinkSnooper.exprToSafeString(expr)) : LinkSnooper.exprToSafeString(expr);
                        if (this.captureMain) {
                            this.output(this.toFEArrow + exprToPrint);
                        }
                        if (this.fePreemptive == null && head.equals("CallPacket") && LinkSnooper.exprToSafeString(expr.part(1).head()).equals("FrontEnd`OpenParallelLinksPacket")) {
                            this.kernelMain.getExpr().dispose();
                            this.output(" --- Opening special FE links (no further output will appear for this transaction, including the final ReturnPacket) --- ");
                            Expr links = expr.part(new int[]{1, 1});
                            String serviceName = links.part(1).toString();
                            String preemptiveName = links.part(2).toString();
                            String protocol = links.part(3).toString();
                            String protString = protocol.equals("Automatic") ? "" : " -linkprotocol " + protocol;
                            this.kernelService = MathLinkFactory.createKernelLink("-linkmode connect -linkname " + serviceName + protString);
                            this.kernelPreemptive = MathLinkFactory.createKernelLink("-linkmode connect -linkname " + preemptiveName + protString);
                            this.kernelService.addMessageHandler(LinkSnooper.class, this, "kernelServiceMessageHandler");
                            this.kernelPreemptive.addMessageHandler(LinkSnooper.class, this, "kernelPreemptiveMessageHandler");
                            this.kernelMain.putFunction("EvaluatePacket", 1);
                            this.kernelMain.putFunction("LinkConnect", 1);
                            this.kernelMain.putSymbol("MathLink`$ServiceLink");
                            this.kernelMain.flush();
                            this.kernelService.connect();
                            this.kernelMain.discardAnswer();
                            this.kernelMain.putFunction("EvaluatePacket", 1);
                            this.kernelMain.putFunction("LinkConnect", 1);
                            this.kernelMain.putSymbol("MathLink`$PreemptiveLink");
                            this.kernelMain.flush();
                            this.kernelPreemptive.connect();
                            this.kernelMain.discardAnswer();
                            this.kernelMain.putFunction("ReturnPacket", 1);
                            this.kernelMain.putSymbol("Null");
                            this.kernelMain.flush();
                            this.feService = MathLinkFactory.createKernelLink("-linkmode listen " + protString + " -linkoptions MLDontInteract");
                            this.fePreemptive = MathLinkFactory.createKernelLink("-linkmode listen " + protString + " -linkoptions MLDontInteract");
                            this.feService.addMessageHandler(LinkSnooper.class, this, "feServiceMessageHandler");
                            this.fePreemptive.addMessageHandler(LinkSnooper.class, this, "fePreemptiveMessageHandler");
                            expr = new Expr(new Expr(4, "List"), new Expr[]{new Expr(this.feService.name()), new Expr(this.fePreemptive.name()), links.part(3)});
                            expr = new Expr(new Expr(4, "FrontEnd`OpenParallelLinksPacket"), new Expr[]{expr});
                            expr = new Expr(new Expr(4, "CallPacket"), new Expr[]{expr});
                            this.feMain.put(expr);
                            this.feMain.flush();
                            expr.dispose();
                            this.feService.connect();
                            this.feMain.nextPacket();
                            this.feMain.newPacket();
                            this.feMain.putFunction("ReturnPacket", 1);
                            this.feMain.putSymbol("Null");
                            this.feMain.flush();
                            this.fePreemptive.connect();
                            this.feMain.nextPacket();
                            this.feMain.newPacket();
                            this.feMain.putFunction("ReturnPacket", 1);
                            this.feMain.putSymbol("Null");
                            this.feMain.discardAnswer();
                            this.addLinkCheckboxes();
                        } else {
                            this.feMain.transferExpression(this.kernelMain);
                            this.feMain.flush();
                            expr.dispose();
                        }
                    }
                    if (this.fePreemptive != null && this.fePreemptive.ready()) {
                        if (this.capturePre) {
                            expr = this.fePreemptive.peekExpr();
                            this.output(this.preemptiveLinkPrefix + this.toKernelArrow + LinkSnooper.exprToSafeString(expr));
                            expr.dispose();
                        }
                        this.kernelPreemptive.transferExpression(this.fePreemptive);
                        this.kernelPreemptive.flush();
                    }
                    if (this.kernelPreemptive != null && this.kernelPreemptive.ready()) {
                        if (this.capturePre) {
                            expr = this.kernelPreemptive.peekExpr();
                            this.output(this.preemptiveLinkPrefix + this.toFEArrow + LinkSnooper.exprToSafeString(expr));
                            expr.dispose();
                        }
                        this.fePreemptive.transferExpression(this.kernelPreemptive);
                        this.fePreemptive.flush();
                    }
                    if (this.feService != null && this.feService.ready()) {
                        if (this.captureServ) {
                            expr = this.feService.peekExpr();
                            this.output(this.serviceLinkPrefix + this.toKernelArrow + LinkSnooper.exprToSafeString(expr));
                            expr.dispose();
                        }
                        this.kernelService.transferExpression(this.feService);
                        this.kernelService.flush();
                    }
                    if (this.kernelService != null && this.kernelService.ready()) {
                        if (this.captureServ) {
                            expr = this.kernelService.peekExpr();
                            this.output(this.serviceLinkPrefix + this.toFEArrow + LinkSnooper.exprToSafeString(expr));
                            expr.dispose();
                        }
                        this.feService.transferExpression(this.kernelService);
                        this.feService.flush();
                    }
                    try {
                        Thread.sleep(this.pollInterval);
                        continue block4;
                    }
                    catch (InterruptedException err1) {
                        continue;
                    }
                    break;
                } while (true);
            }
            catch (MathLinkException err) {
                int errCode = err.getErrCode();
                this.output("MathLinkException: Code " + errCode + " : " + err.getMessage());
                if (errCode == this.kernelMain.error()) {
                    this.output("Exception was from the " + this.kernelSideName + " side.");
                } else if (errCode == this.feMain.error()) {
                    this.output("Exception was from the " + this.feSideName + " side.");
                } else if (this.kernelPreemptive != null && errCode == this.kernelPreemptive.error()) {
                    this.output("Exception was from the " + this.kernelSideName + " side, on the Preemptive link.");
                } else if (this.fePreemptive != null && errCode == this.fePreemptive.error()) {
                    this.output("Exception was from the " + this.feSideName + " side, on the Preemptive link.");
                } else if (this.kernelService != null && errCode == this.kernelService.error()) {
                    this.output("Exception was from the " + this.kernelSideName + " side, on the Service link.");
                } else if (this.feService != null && errCode == this.feService.error()) {
                    this.output("Exception was from the " + this.feSideName + " side, on the Service link.");
                }
                if (this.logFileStrm != null) {
                    this.logFileStrm.close();
                }
                this.feMain.close();
                this.kernelMain.close();
                if (this.kernelService != null) {
                    this.kernelService.close();
                }
                if (this.feService != null) {
                    this.feService.close();
                }
                if (this.kernelPreemptive != null) {
                    this.kernelPreemptive.close();
                }
                if (this.fePreemptive == null) return;
                this.fePreemptive.close();
                return;
                continue;
            }
            break;
        } while (true);
    }

    private void output(String s) {
        if (this.doPrint) {
            String outputLine = this.timestamps ? "[" + this.timestampFormatter.format(new Date()) + "] " + s : s;
            PrintStream p = this.strm != null ? this.strm : System.out;
            p.println(outputLine);
            if (this.logFileStrm != null) {
                this.logFileStrm.println(outputLine);
            }
        }
    }

    private void addLinkCheckboxes() {
        if (this.useWindow) {
            ConsoleWindow cw = ConsoleWindow.getInstance();
            JPanel p = new JPanel();
            p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
            Checkbox mainBox = new Checkbox("Main", this.captureMain);
            Checkbox preBox = new Checkbox("Preemptive", this.capturePre);
            Checkbox servBox = new Checkbox("Service", this.captureServ);
            p.setLayout(new FlowLayout(0, 20, 4));
            p.add(new Label(""));
            p.add(new Label("Monitor Front End links:"));
            p.add(mainBox);
            p.add(preBox);
            p.add(servBox);
            mainBox.addItemListener(new ItemListener(){

                public void itemStateChanged(ItemEvent e) {
                    Checkbox b = (Checkbox)e.getItemSelectable();
                    LinkSnooper.this.captureMain = b.getState();
                }
            });
            preBox.addItemListener(new ItemListener(){

                public void itemStateChanged(ItemEvent e) {
                    Checkbox b = (Checkbox)e.getItemSelectable();
                    LinkSnooper.this.capturePre = b.getState();
                }
            });
            servBox.addItemListener(new ItemListener(){

                public void itemStateChanged(ItemEvent e) {
                    Checkbox b = (Checkbox)e.getItemSelectable();
                    LinkSnooper.this.captureServ = b.getState();
                }
            });
            GridBagLayout gbl = (GridBagLayout)cw.getContentPane().getLayout();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = 1;
            gbc.gridx = 0;
            gbc.gridy = -1;
            gbc.gridwidth = 0;
            gbc.weighty = 0.0;
            gbl.setConstraints(p, gbc);
            cw.getContentPane().add(p);
            cw.setSize(700, 610);
        }
    }

    public void feMainMessageHandler(int msgType, int ignore) throws MathLinkException {
        this.output("****** Message " + this.toKernelArrow + " on Main: " + msgType);
        this.kernelMain.putMessage(msgType);
    }

    public void kernelMainMessageHandler(int msgType, int ignore) throws MathLinkException {
        this.output("****** Message " + this.toFEArrow + " on Main: " + msgType);
        this.feMain.putMessage(msgType);
    }

    public void fePreemptiveMessageHandler(int msgType, int ignore) throws MathLinkException {
        this.output(this.preemptiveLinkPrefix + "****** Message " + this.toKernelArrow + " on Preemptive: " + msgType);
        this.kernelPreemptive.putMessage(msgType);
    }

    public void kernelPreemptiveMessageHandler(int msgType, int ignore) throws MathLinkException {
        this.output(this.preemptiveLinkPrefix + "****** Message " + this.toFEArrow + " on Preemptive: " + msgType);
        this.fePreemptive.putMessage(msgType);
    }

    public void feServiceMessageHandler(int msgType, int ignore) throws MathLinkException {
        this.output(this.serviceLinkPrefix + "****** Message " + this.toKernelArrow + " on Service: " + msgType);
        this.kernelService.putMessage(msgType);
    }

    public void kernelServiceMessageHandler(int msgType, int ignore) throws MathLinkException {
        this.output(this.serviceLinkPrefix + "****** Message " + this.toFEArrow + " on Service: " + msgType);
        this.feService.putMessage(msgType);
    }

    private static String exprToSafeString(Expr e) {
        try {
            return e.toString();
        }
        catch (Exception exc) {
            return "COULD NOT CONVERT TO JAVA STRING";
        }
    }

}

