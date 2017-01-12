/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.StdLink;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class MathJFrame
extends JFrame {
    protected boolean isModal;
    protected String onCloseCode;

    public MathJFrame() {
        this.setDefaultCloseOperation(2);
        this.enableEvents(64);
    }

    public MathJFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(2);
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
        KernelLink ml;
        super.processWindowEvent(e);
        if (e.getID() == 201 && (ml = StdLink.getLink()) != null) {
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
    }
}

