/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MathFocusListener
extends MathListener
implements FocusListener {
    public MathFocusListener() {
    }

    public MathFocusListener(KernelLink ml) {
        super(ml);
    }

    public MathFocusListener(String[][] handlers) {
        super(handlers);
    }

    public void focusGained(FocusEvent e) {
        this.callVoidMathHandler("focusGained", new Object[]{e});
    }

    public void focusLost(FocusEvent e) {
        this.callVoidMathHandler("focusLost", new Object[]{e});
    }
}

