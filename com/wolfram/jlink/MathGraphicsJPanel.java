/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathGraphicsDelegate;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;
import javax.swing.JPanel;

public class MathGraphicsJPanel
extends JPanel
implements Serializable {
    private MathGraphicsDelegate delegate;
    public static final int GRAPHICS = 0;
    public static final int TYPESET = 1;

    public MathGraphicsJPanel() {
        this.delegate = new MathGraphicsDelegate(this);
    }

    public MathGraphicsJPanel(KernelLink ml) {
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
        this.paintImmediately(this.getBounds(null));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.delegate.getImage() != null) {
            this.delegate.paintImage(g);
        }
    }
}

