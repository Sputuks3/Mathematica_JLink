/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MathComponentListener
extends MathListener
implements ComponentListener {
    public MathComponentListener() {
    }

    public MathComponentListener(KernelLink ml) {
        super(ml);
    }

    public MathComponentListener(String[][] handlers) {
        super(handlers);
    }

    public void componentHidden(ComponentEvent e) {
        this.callVoidMathHandler("componentHidden", new Object[]{e});
    }

    public void componentMoved(ComponentEvent e) {
        this.callVoidMathHandler("componentMoved", new Object[]{e});
    }

    public void componentResized(ComponentEvent e) {
        this.callVoidMathHandler("componentResized", new Object[]{e});
    }

    public void componentShown(ComponentEvent e) {
        this.callVoidMathHandler("componentShown", new Object[]{e});
    }
}

