/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MathMouseListener
extends MathListener
implements MouseListener {
    public MathMouseListener() {
    }

    public MathMouseListener(KernelLink ml) {
        super(ml);
    }

    public MathMouseListener(String[][] handlers) {
        super(handlers);
    }

    public void mouseClicked(MouseEvent e) {
        this.callVoidMathHandler("mouseClicked", this.prepareArgs(e));
    }

    public void mouseEntered(MouseEvent e) {
        this.callVoidMathHandler("mouseEntered", this.prepareArgs(e));
    }

    public void mouseExited(MouseEvent e) {
        this.callVoidMathHandler("mouseExited", this.prepareArgs(e));
    }

    public void mousePressed(MouseEvent e) {
        this.callVoidMathHandler("mousePressed", this.prepareArgs(e));
    }

    public void mouseReleased(MouseEvent e) {
        this.callVoidMathHandler("mouseReleased", this.prepareArgs(e));
    }

    private Object[] prepareArgs(MouseEvent e) {
        return new Object[]{e, new Integer(e.getX()), new Integer(e.getY()), new Integer(e.getClickCount())};
    }
}

