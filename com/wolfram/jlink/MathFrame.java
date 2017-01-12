/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.StdLink;
import java.awt.Frame;
import java.awt.event.WindowEvent;

public class MathFrame
extends Frame {
    protected boolean isModal;
    protected String onCloseCode;

    public MathFrame() {
        this.enableEvents(64);
    }

    public MathFrame(String title) {
        super(title);
        this.enableEvents(64);
    }

    public void setModal() {
        this.isModal = true;
    }

    public void onClose(String code) {
        this.onCloseCode = code + ";";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == 201) {
            KernelLink ml = StdLink.getLink();
            if (ml != null) {
                KernelLink kernelLink;
                if (this.onCloseCode != null) {
                    StdLink.requestTransaction();
                    kernelLink = ml;
                    synchronized (kernelLink) {
                        try {
                            ml.putFunction("EnterTextPacket", 1);
                            ml.put(this.onCloseCode);
                            ml.discardAnswer();
                        }
                        catch (MathLinkException ee) {
                            ml.clearError();
                            ml.newPacket();
                        }
                    }
                }
                if (this.isModal) {
                    if (ml.equals(StdLink.getLink())) {
                        StdLink.requestTransaction();
                    }
                    kernelLink = ml;
                    synchronized (kernelLink) {
                        try {
                            ml.evaluate("EndModal[]");
                            ml.discardAnswer();
                        }
                        catch (MathLinkException ee) {
                            ml.clearError();
                            ml.newPacket();
                        }
                    }
                }
            }
            this.dispose();
        }
    }
}

