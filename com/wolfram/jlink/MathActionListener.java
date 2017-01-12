/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MathActionListener
extends MathListener
implements ActionListener {
    public MathActionListener() {
    }

    public MathActionListener(KernelLink ml) {
        super(ml);
    }

    public MathActionListener(String func) {
        this();
        this.setHandler("actionPerformed", func);
    }

    public void actionPerformed(ActionEvent e) {
        this.callVoidMathHandler("actionPerformed", new Object[]{e, e.getActionCommand()});
    }
}

