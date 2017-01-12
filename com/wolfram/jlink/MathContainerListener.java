/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

public class MathContainerListener
extends MathListener
implements ContainerListener {
    public MathContainerListener() {
    }

    public MathContainerListener(KernelLink ml) {
        super(ml);
    }

    public MathContainerListener(String[][] handlers) {
        super(handlers);
    }

    public void componentAdded(ContainerEvent e) {
        this.callVoidMathHandler("componentAdded", new Object[]{e});
    }

    public void componentRemoved(ContainerEvent e) {
        this.callVoidMathHandler("componentRemoved", new Object[]{e});
    }
}

