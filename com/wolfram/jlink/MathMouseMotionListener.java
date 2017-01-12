/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MathMouseMotionListener
extends MathListener
implements MouseMotionListener {
    public MathMouseMotionListener() {
    }

    public MathMouseMotionListener(KernelLink ml) {
        super(ml);
    }

    public MathMouseMotionListener(String[][] handlers) {
        super(handlers);
    }

    public void mouseDragged(MouseEvent e) {
        this.callVoidMathHandler("mouseDragged", this.prepareArgs(e));
    }

    public void mouseMoved(MouseEvent e) {
        this.callVoidMathHandler("mouseMoved", this.prepareArgs(e));
    }

    private Object[] prepareArgs(MouseEvent e) {
        return new Object[]{e, new Integer(e.getX()), new Integer(e.getY()), new Integer(e.getClickCount())};
    }
}

