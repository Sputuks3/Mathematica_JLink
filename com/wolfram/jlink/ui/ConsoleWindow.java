/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ui;

import com.wolfram.jlink.MathJFrame;
import com.wolfram.jlink.ui.ConsoleStream;
import com.wolfram.jlink.ui.TextAreaOutputStream;
import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ItemSelectable;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

public class ConsoleWindow
extends MathJFrame {
    public static final int NONE = 0;
    public static final int STDOUT = 1;
    public static final int STDERR = 2;
    private static ConsoleWindow theConsoleWindow;
    private boolean isFirstTime = true;
    private final TextAreaOutputStream taos;
    private PrintStream strm;
    private OutputStream oldOut = System.out;
    private OutputStream oldErr = System.err;
    private boolean oldOutWasWrapped;
    private boolean oldErrWasWrapped;
    private boolean isCapturingOut = false;
    private boolean isCapturingErr = false;
    private Checkbox stdoutButton;
    private Checkbox stderrButton;

    public static synchronized ConsoleWindow getInstance() {
        if (theConsoleWindow == null) {
            theConsoleWindow = new ConsoleWindow();
        }
        return theConsoleWindow;
    }

    public synchronized void setMaxLines(int maxLines) {
        this.taos.maxLines = maxLines;
    }

    public synchronized void setCapture(int strmsToCapture) {
        if ((strmsToCapture & 1) != 0) {
            ConsoleStream.setSystemStdoutStream(this.strm);
            this.isCapturingOut = true;
        } else {
            if (this.oldOutWasWrapped) {
                ConsoleStream.setSystemStdoutStream(this.oldOut);
            } else {
                System.setOut((PrintStream)this.oldOut);
            }
            this.isCapturingOut = false;
        }
        if ((strmsToCapture & 2) != 0) {
            ConsoleStream.setSystemStderrStream(this.strm);
            this.isCapturingErr = true;
        } else {
            if (this.oldErrWasWrapped) {
                ConsoleStream.setSystemStderrStream(this.oldErr);
            } else {
                System.setErr((PrintStream)this.oldErr);
            }
            this.isCapturingErr = false;
        }
        this.updateButtons();
    }

    public boolean isFirstTime() {
        return this.isFirstTime;
    }

    public void setFirstTime(boolean first) {
        this.isFirstTime = first;
    }

    private ConsoleWindow() {
        if (this.oldOut instanceof ConsoleStream.ConsolePrintStream) {
            this.oldOut = ConsoleStream.getStdoutStream().getWrappedStream();
            this.oldOutWasWrapped = true;
        }
        if (this.oldErr instanceof ConsoleStream.ConsolePrintStream) {
            this.oldErr = ConsoleStream.getStderrStream().getWrappedStream();
            this.oldErrWasWrapped = true;
        }
        this.setTitle("J/Link Java Console");
        this.setBackground(SystemColor.control);
        this.setResizable(true);
        Button clearButton = new Button("Clear");
        Button closeButton = new Button("Close");
        Panel checkboxPanel = new Panel();
        this.stdoutButton = new Checkbox("System.out", this.isCapturingOut);
        this.stderrButton = new Checkbox("System.err", this.isCapturingErr);
        JTextArea ta = new JTextArea();
        this.taos = new TextAreaOutputStream(ta, 1000);
        this.strm = new PrintStream(this.taos, true);
        this.strm.println("J/Link version 4.9.1");
        try {
            String javaVersion = System.getProperty("java.version");
            String vmName = System.getProperty("java.vm.name");
            this.strm.println("Java version " + javaVersion + "  " + (vmName != null ? vmName : ""));
        }
        catch (Exception e) {
            // empty catch block
        }
        this.strm.println("-------------------------");
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", 0, 12));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(ta);
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.getContentPane().setLayout(gridbag);
        gbc.fill = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.95;
        gbc.gridwidth = 0;
        gridbag.setConstraints(scrollPane, gbc);
        this.getContentPane().add(scrollPane);
        gbc.fill = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.05;
        Panel p = new Panel();
        gridbag.setConstraints(p, gbc);
        this.getContentPane().add(p);
        gbc.insets = new Insets(4, 10, 1, 10);
        gridbag.setConstraints(clearButton, gbc);
        this.getContentPane().add(clearButton);
        gridbag.setConstraints(closeButton, gbc);
        this.getContentPane().add(closeButton);
        gridbag = new GridBagLayout();
        gbc = new GridBagConstraints();
        p.setLayout(gridbag);
        Label lbl = new Label("Capture:");
        gbc.gridheight = 2;
        gridbag.setConstraints(lbl, gbc);
        p.add(lbl);
        gbc.gridheight = 1;
        gbc.gridwidth = 0;
        gridbag.setConstraints(this.stdoutButton, gbc);
        p.add(this.stdoutButton);
        gridbag.setConstraints(this.stderrButton, gbc);
        p.add(this.stderrButton);
        clearButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                if (ConsoleWindow.this.taos != null) {
                    ConsoleWindow.this.taos.reset();
                }
            }
        });
        closeButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                ConsoleWindow.this.dispatchEvent(new WindowEvent(ConsoleWindow.this, 201));
            }
        });
        this.stdoutButton.addItemListener(new CheckboxItemListener());
        this.stderrButton.addItemListener(new CheckboxItemListener());
    }

    private void updateButtons() {
        this.stdoutButton.setState(this.isCapturingOut);
        this.stderrButton.setState(this.isCapturingErr);
    }

    private class CheckboxItemListener
    implements ItemListener {
        private CheckboxItemListener() {
        }

        public void itemStateChanged(ItemEvent e) {
            Checkbox b = (Checkbox)e.getItemSelectable();
            if (b.equals(ConsoleWindow.this.stdoutButton) || b.equals(ConsoleWindow.this.stderrButton)) {
                int strmsToCapture = (ConsoleWindow.this.stdoutButton.getState() ? 1 : 0) | (ConsoleWindow.this.stderrButton.getState() ? 2 : 0);
                ConsoleWindow.this.setCapture(strmsToCapture);
            }
        }
    }

}

