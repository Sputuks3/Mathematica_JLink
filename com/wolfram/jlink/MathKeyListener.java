/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MathKeyListener
extends MathListener
implements KeyListener {
    public MathKeyListener() {
    }

    public MathKeyListener(KernelLink ml) {
        super(ml);
    }

    public MathKeyListener(String[][] handlers) {
        super(handlers);
    }

    public void keyPressed(KeyEvent e) {
        this.callVoidMathHandler("keyPressed", this.prepareArgs(e));
    }

    public void keyReleased(KeyEvent e) {
        this.callVoidMathHandler("keyReleased", this.prepareArgs(e));
    }

    public void keyTyped(KeyEvent e) {
        this.callVoidMathHandler("keyTyped", this.prepareArgs(e));
    }

    private Object[] prepareArgs(KeyEvent e) {
        return new Object[]{e, new Integer(e.getKeyChar()), new Integer(e.getKeyCode())};
    }
}

