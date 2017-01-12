/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class MathAdjustmentListener
extends MathListener
implements AdjustmentListener {
    public MathAdjustmentListener() {
    }

    public MathAdjustmentListener(KernelLink ml) {
        super(ml);
    }

    public MathAdjustmentListener(String func) {
        this();
        this.setHandler("adjustmentValueChanged", func);
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        this.callVoidMathHandler("adjustmentValueChanged", new Object[]{e, new Integer(e.getAdjustmentType()), new Integer(e.getValue())});
    }
}

