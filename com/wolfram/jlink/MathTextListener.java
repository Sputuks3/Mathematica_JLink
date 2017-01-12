/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class MathTextListener
extends MathListener
implements TextListener {
    public MathTextListener() {
    }

    public MathTextListener(KernelLink ml) {
        super(ml);
    }

    public MathTextListener(String func) {
        this();
        this.setHandler("textValueChanged", func);
    }

    public void textValueChanged(TextEvent e) {
        this.callVoidMathHandler("textValueChanged", new Object[]{e});
    }
}

