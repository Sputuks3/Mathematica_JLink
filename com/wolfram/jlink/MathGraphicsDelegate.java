/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.StdLink;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Serializable;

public class MathGraphicsDelegate
implements Serializable {
    private transient KernelLink ml;
    private boolean useStdLink;
    private Component comp;
    private boolean useFE;
    private boolean useTradForm;
    private int imageType = 0;
    private String mathCommand;
    private Image im;
    private MediaTracker tracker;
    public static final int GRAPHICS = 0;
    public static final int TYPESET = 1;

    public MathGraphicsDelegate(Component comp) {
        this(comp, null);
        this.useStdLink = true;
    }

    public MathGraphicsDelegate(Component comp, KernelLink ml) {
        this.comp = comp;
        this.ml = ml;
        this.useStdLink = false;
        this.tracker = new MediaTracker(comp);
    }

    public void setLink(KernelLink ml) {
        this.ml = ml;
        this.useStdLink = false;
    }

    public void setImageType(int type) {
        this.imageType = type;
    }

    public int getImageType() {
        return this.imageType;
    }

    public void setUsesFE(boolean useFE) {
        this.useFE = useFE;
    }

    public boolean getUsesFE() {
        return this.useFE;
    }

    public void setUsesTraditionalForm(boolean useTradForm) {
        this.useTradForm = useTradForm;
    }

    public boolean getUsesTraditionalForm() {
        return this.useTradForm;
    }

    public String getMathCommand() {
        return this.mathCommand;
    }

    public void setMathCommand(String cmd) {
        KernelLink link;
        this.mathCommand = cmd;
        byte[] gifData = null;
        KernelLink kernelLink = link = this.useStdLink ? StdLink.getLink() : this.ml;
        if (link != null) {
            if (link.equals(StdLink.getLink())) {
                StdLink.requestTransaction();
            }
            gifData = this.imageType == 0 ? link.evaluateToImage(this.mathCommand, this.comp.getSize().width - 6, this.comp.getSize().height - 6, 0, this.useFE) : link.evaluateToTypeset(this.mathCommand, this.comp.getSize().width - 4, !this.useTradForm);
        }
        Image image = this.im = gifData != null ? this.comp.getToolkit().createImage(gifData) : null;
        if (!this.ensureImageReady()) {
            this.im = null;
        }
        this.comp.repaint();
    }

    public void setImage(Image im) {
        this.im = im;
        this.mathCommand = null;
        if (im instanceof BufferedImage && !this.ensureImageReady()) {
            im = null;
        }
        this.comp.repaint();
    }

    public Image getImage() {
        return this.im;
    }

    public void recompute() {
        if (this.mathCommand != null) {
            this.setMathCommand(this.mathCommand);
        }
    }

    public boolean ensureImageReady() {
        this.tracker.addImage(this.im, 0);
        try {
            this.tracker.waitForID(0);
        }
        catch (Exception e) {
            // empty catch block
        }
        boolean wasOK = !this.tracker.isErrorID(0);
        this.tracker.removeImage(this.im, 0);
        return wasOK;
    }

    public void paintImage(Graphics g) {
        int imageHeight = this.im.getHeight(this.comp);
        int imageWidth = this.im.getWidth(this.comp);
        if (imageWidth != -1 && imageHeight != -1) {
            Dimension d = this.comp.getSize();
            int compWidth = d.width;
            int compHeight = d.height;
            Insets insets = this.comp instanceof Container ? ((Container)this.comp).getInsets() : new Insets(0, 0, 0, 0);
            int paintWidth = compWidth - insets.left - insets.right;
            int paintHeight = compHeight - insets.top - insets.bottom;
            int left = (compWidth - imageWidth) / 2;
            int top = (compHeight - imageHeight) / 2;
            g.drawImage(this.im, left, top, this.comp);
            g.clearRect(insets.left, insets.top, paintWidth, top - insets.top);
            g.clearRect(insets.left, top, left - insets.left, imageHeight);
            g.clearRect(left + imageWidth, top, compWidth - imageWidth - left - insets.right, imageHeight);
            g.clearRect(insets.left, top + imageHeight, paintWidth, compHeight - imageHeight - top - insets.bottom);
        }
    }
}

