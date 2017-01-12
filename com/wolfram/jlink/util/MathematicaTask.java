/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 *  org.apache.tools.ant.Location
 *  org.apache.tools.ant.Project
 *  org.apache.tools.ant.Task
 */
package com.wolfram.jlink.util;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import com.wolfram.jlink.PacketArrivedEvent;
import com.wolfram.jlink.PacketListener;
import com.wolfram.jlink.Utils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class MathematicaTask
extends Task
implements PacketListener {
    static KernelLink ml = null;
    String exe = null;
    String cmdLine = null;
    boolean freshKernel = false;
    boolean quit = false;
    String runFile = null;
    int timeout = Integer.MAX_VALUE;
    String timeoutProperty = null;
    boolean failOnError = true;
    String code = "";
    String failMsg = null;
    private static String startupCode = "Ant[obj_String] :=                                                        Switch[ToLowerCase[obj],                                                   \"project\",                                                               Ant[\"target\"]@getProject[],                                      \"target\",                                                                $this@getOwningTarget[],                                           \"task\",                                                                  $this,                                                             \"location\",                                                              $this@getLocation[],                                               _,                                                                         AntLog[\"Unknown object type in Ant function: \" <> obj];              $Failed                                                        ];                                                                  AntTask[name_String, args___?OptionQ] :=                                  JavaBlock[                                                                Module[{task, attrNames, attrVals},                                        task = Ant[\"project\"]@createTask[name];                              attrNames =                                                               StringReplace[#, a_ ~~ b___  :> ToUpperCase[a] <> b]& /@                   First /@ Flatten[{args}];                                       attrVals = Last /@ Flatten[{args}];                                    With[{meth = ToExpression[\"set\" <> #1]},                                 task@meth[#2]                                                      ]& @@@ Thread[{attrNames, attrVals}];                                  task@perform[]                                                     ]                                                                   ];                                                                  AntLog[msg_String] := Ant[\"project\"]@log[msg];                       AntLog[e_] := AntLog[ToString[e, FormatType->InputForm]];              AntProperty[p_String] := Ant[\"project\"]@getProperty[p];              AntSetProperty[p_String, val_String] := Ant[\"project\"]@setProperty[p, val];AntReference[ref_String] := Ant[\"project\"]@getReference[ref];        AntFail[msg_String] := ($this@setFail[msg]; Abort[])";

    public void setExe(String exe) {
        this.exe = exe;
    }

    public void setCmdline(String cmdLine) {
        this.cmdLine = cmdLine;
    }

    public void setFresh(boolean fresh) {
        this.freshKernel = fresh;
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public void setRunfile(String runFile) {
        this.runFile = runFile;
    }

    public void setTimeout(int seconds) {
        this.timeout = seconds;
    }

    public void setTimeoutproperty(String timeoutProperty) {
        this.timeoutProperty = timeoutProperty;
    }

    public void setFailonerror(boolean failOnError) {
        this.failOnError = failOnError;
    }

    public void addText(String code) {
        this.code = code;
    }

    public void setFail(String msg) {
        this.failMsg = msg;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute() throws BuildException {
        if (this.freshKernel) {
            this.closeKernel();
        }
        if (ml == null) {
            ml = this.initKernel();
        }
        TimeoutThread timeoutThread = null;
        try {
            timeoutThread = this.startTimeoutThread();
            ml.putFunction("EvaluatePacket", 1);
            ml.putFunction("Set", 2);
            ml.putSymbol("$this");
            ml.put(this);
            ml.discardAnswer();
            if (this.runFile != null) {
                ml.putFunction("EvaluatePacket", 1);
                ml.putFunction("Get", 1);
                ml.put(this.runFile);
                ml.discardAnswer();
            }
            if (this.code != null && !this.code.equals("")) {
                ml.evaluate(this.code);
                ml.discardAnswer();
            }
            if (this.failMsg != null) {
                throw new BuildException(this.failMsg, this.getLocation());
            }
        }
        catch (MathLinkException e) {
            String msg;
            boolean wasTimeout;
            boolean bl = wasTimeout = timeoutThread != null && timeoutThread.timeExpired;
            if (wasTimeout && this.timeoutProperty != null) {
                this.getProject().setNewProperty(this.timeoutProperty, "true");
            }
            String string = msg = wasTimeout ? "Mathematica task killed on expired timeout of " + this.timeout + " seconds." : "Mathematica task had a link error: " + ml.errorMessage();
            if (this.failOnError) {
                throw new BuildException(msg, (Throwable)e, this.getLocation());
            }
            this.log(msg);
        }
        finally {
            this.killTimeoutThread(timeoutThread);
            this.failMsg = null;
            if (this.quit || ml.error() != 0) {
                this.closeKernel();
            }
        }
    }

    protected KernelLink initKernel() {
        KernelLink ml = null;
        String[] args = new String[]{"-linkmode", "launch", "-linkname", ""};
        boolean useArray = false;
        if (this.exe != null) {
            useArray = true;
            String quoteChar = Utils.isWindows() ? "" : "'";
            args[3] = quoteChar + this.exe + quoteChar + " -mathlink";
        } else if (this.cmdLine == null) {
            throw new BuildException("Must specify exe or cmdline attribute to control kernel launch.", this.getLocation());
        }
        try {
            if (useArray) {
                ml = MathLinkFactory.createKernelLink(args);
                ml.discardAnswer();
            } else {
                ml = MathLinkFactory.createKernelLink(this.cmdLine);
                ml.connect();
            }
            ml.enableObjectReferences();
            ml.evaluateToInputForm(startupCode, 0);
            ml.evaluateToInputForm("MathLink`SetTerminating[$ParentLink, True]", 0);
            ml.addPacketListener(this);
        }
        catch (MathLinkException e) {
            throw new BuildException("Failed to launch or connect to Mathematica kernel: " + e.getMessage(), (Throwable)e, this.getLocation());
        }
        return ml;
    }

    protected void closeKernel() {
        if (ml != null) {
            ml.terminateKernel();
            ml.close();
            ml = null;
        }
    }

    protected TimeoutThread startTimeoutThread() {
        if (this.timeout < Integer.MAX_VALUE) {
            long startTime = System.currentTimeMillis();
            TimeoutThread timeoutThread = new TimeoutThread(ml, startTime + (long)(this.timeout * 1000));
            timeoutThread.start();
            return timeoutThread;
        }
        return null;
    }

    protected void killTimeoutThread(TimeoutThread timeoutThread) {
        if (timeoutThread != null) {
            timeoutThread.kill = true;
            try {
                timeoutThread.join();
            }
            catch (InterruptedException e) {
                // empty catch block
            }
        }
    }

    public boolean packetArrived(PacketArrivedEvent evt) throws MathLinkException {
        if (evt.getPktType() == 2) {
            KernelLink ml = (KernelLink)evt.getSource();
            this.log(ml.getString());
        }
        return true;
    }

    private static class TimeoutThread
    extends Thread {
        private KernelLink ml;
        private long endTime;
        volatile boolean kill = false;
        volatile boolean timeExpired = false;

        TimeoutThread(KernelLink ml, long endTime) {
            super("MathematicaTask Kernel-Killer Thread");
            this.setDaemon(true);
            this.ml = ml;
            this.endTime = endTime;
        }

        public void run() {
            while (!this.kill && System.currentTimeMillis() < this.endTime) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {}
            }
            if (!this.kill) {
                this.timeExpired = true;
                this.ml.terminateKernel();
            }
        }
    }

}

