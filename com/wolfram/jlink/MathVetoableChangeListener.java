/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class MathVetoableChangeListener
extends MathListener
implements VetoableChangeListener {
    public MathVetoableChangeListener() {
    }

    public MathVetoableChangeListener(KernelLink ml) {
        super(ml);
    }

    public MathVetoableChangeListener(String func) {
        this();
        this.setHandler("vetoableChange", func);
    }

    public void vetoableChange(PropertyChangeEvent e) throws PropertyVetoException {
        this.callVoidMathHandler("vetoableChange", new Object[]{e});
    }
}

