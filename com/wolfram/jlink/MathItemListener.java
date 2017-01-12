/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MathItemListener
extends MathListener
implements ItemListener {
    public MathItemListener() {
    }

    public MathItemListener(KernelLink ml) {
        super(ml);
    }

    public MathItemListener(String func) {
        this();
        this.setHandler("itemStateChanged", func);
    }

    public void itemStateChanged(ItemEvent e) {
        this.callVoidMathHandler("itemStateChanged", new Object[]{e, new Integer(e.getStateChange())});
    }
}

