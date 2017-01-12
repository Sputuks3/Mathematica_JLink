/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MathPropertyChangeListener
extends MathListener
implements PropertyChangeListener {
    public MathPropertyChangeListener() {
    }

    public MathPropertyChangeListener(KernelLink ml) {
        super(ml);
    }

    public MathPropertyChangeListener(String func) {
        this();
        this.setHandler("propertyChange", func);
    }

    public void propertyChange(PropertyChangeEvent e) {
        this.callVoidMathHandler("propertyChange", new Object[]{e});
    }
}

