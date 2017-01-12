/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathGraphicsDelegate;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;

public class MathCanvas
extends Canvas
implements Serializable {
    private MathGraphicsDelegate delegate;
    public static final int GRAPHICS = 0;
    public static final int TYPESET = 1;

    public MathCanvas() {
        this.delegate = new MathGraphicsDelegate(this);
    }

    public MathCanvas(KernelLink ml) {
        this.delegate = new MathGraphicsDelegate(this, ml);
    }

    public void setLink(KernelLink ml) {
        this.delegate.setLink(ml);
    }

    public void setImageType(int type) {
        this.delegate.setImageType(type);
    }

    public int getImageType() {
        return this.delegate.getImageType();
    }

    public void setUsesFE(boolean useFE) {
        this.delegate.setUsesFE(useFE);
    }

    public boolean getUsesFE() {
        return this.delegate.getUsesFE();
    }

    public void setUsesTraditionalForm(boolean useTradForm) {
        this.delegate.setUsesTraditionalForm(useTradForm);
    }

    public boolean getUsesTraditionalForm() {
        return this.delegate.getUsesTraditionalForm();
    }

    public void setMathCommand(String cmd) {
        this.delegate.setMathCommand(cmd);
    }

    public String getMathCommand() {
        return this.delegate.getMathCommand();
    }

    public void setImage(Image im) {
        this.delegate.setImage(im);
    }

    public Image getImage() {
        return this.delegate.getImage();
    }

    public void recompute() {
        this.delegate.recompute();
    }

    public void repaintNow() {
        this.delegate.ensureImageReady();
        Graphics g = this.getGraphics();
        if (g != null) {
            this.update(g);
            g.dispose();
        }
        this.repaint();
    }

    public void update(Graphics g) {
        this.paint(g);
    }

    public void paint(Graphics g) {
        if (this.delegate.getImage() != null) {
            this.delegate.paintImage(g);
        } else {
            super.paint(g);
        }
    }
}

