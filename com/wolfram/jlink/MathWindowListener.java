/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MathWindowListener
extends MathListener
implements WindowListener {
    public MathWindowListener() {
    }

    public MathWindowListener(KernelLink ml) {
        super(ml);
    }

    public MathWindowListener(String[][] handlers) {
        super(handlers);
    }

    public void windowActivated(WindowEvent e) {
        this.callVoidMathHandler("windowActivated", new Object[]{e});
    }

    public void windowClosed(WindowEvent e) {
        this.callVoidMathHandler("windowClosed", new Object[]{e});
    }

    public void windowClosing(WindowEvent e) {
        this.callVoidMathHandler("windowClosing", new Object[]{e});
    }

    public void windowDeactivated(WindowEvent e) {
        this.callVoidMathHandler("windowDeactivated", new Object[]{e});
    }

    public void windowDeiconified(WindowEvent e) {
        this.callVoidMathHandler("windowDeiconified", new Object[]{e});
    }

    public void windowIconified(WindowEvent e) {
        this.callVoidMathHandler("windowIconified", new Object[]{e});
    }

    public void windowOpened(WindowEvent e) {
        this.callVoidMathHandler("windowOpened", new Object[]{e});
    }
}

