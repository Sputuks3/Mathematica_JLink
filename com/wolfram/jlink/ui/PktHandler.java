/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ui;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.PacketArrivedEvent;
import com.wolfram.jlink.PacketListener;
import com.wolfram.jlink.ui.MathSessionTextPane;
import java.lang.reflect.InvocationTargetException;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

class PktHandler
implements PacketListener {
    private JTextPane comp;
    private MathSessionTextPane.MTDocument doc;
    private boolean lastWasMessage = false;

    PktHandler(JTextPane comp, MathSessionTextPane.MTDocument doc) {
        this.comp = comp;
        this.doc = doc;
    }

    public boolean packetArrived(PacketArrivedEvent evt) {
        KernelLink ml = (KernelLink)evt.getSource();
        try {
            switch (evt.getPktType()) {
                case 2: 
                case 4: 
                case 5: 
                case 8: 
                case 9: {
                    String s = ml.getString();
                    this.handleString(evt.getPktType(), s, this.lastWasMessage);
                    break;
                }
                case 11: {
                    byte[] imageData = ml.getByteString(0);
                    this.handleImage(imageData);
                    break;
                }
            }
        }
        catch (MathLinkException e) {
            e.printStackTrace();
            ml.clearError();
        }
        catch (InvocationTargetException ee) {
            ee.printStackTrace();
        }
        catch (InterruptedException eee) {
            eee.printStackTrace();
        }
        this.lastWasMessage = evt.getPktType() == 5;
        return true;
    }

    private void handleString(final int pkt, final String s, final boolean lastWasMessage) throws InvocationTargetException, InterruptedException {
        if (SwingUtilities.isEventDispatchThread()) {
            this.handleString0(pkt, s, lastWasMessage);
        } else {
            SwingUtilities.invokeAndWait(new Runnable(){

                public void run() {
                    PktHandler.this.handleString0(pkt, s, lastWasMessage);
                }
            });
        }
    }

    private void handleString0(int pkt, String s, boolean lastWasMessage) {
        try {
            switch (pkt) {
                case 8: {
                    this.doc.setLogicalStyle(this.doc.getLength(), this.comp.getStyle("prompt"));
                    if (this.doc.getLength() >= 2 && !this.doc.getText(this.doc.getLength() - 2, 2).equals("\n\n")) {
                        this.doc.insertString(this.doc.getLength(), "\n", null);
                    }
                    this.doc.insertString(this.doc.getLength(), s + "\n", null);
                    this.doc.setFirstEditPos(this.doc.getLength());
                    this.doc.setLogicalStyle(this.doc.getLength(), this.comp.getStyle("input"));
                    break;
                }
                case 9: {
                    this.doc.setLogicalStyle(this.doc.getLength(), this.comp.getStyle("prompt"));
                    this.doc.insertString(this.doc.getLength(), s + "\n", null);
                    break;
                }
                case 4: {
                    this.doc.setLogicalStyle(this.doc.getLength(), this.comp.getStyle("output"));
                    this.doc.insertString(this.doc.getLength(), s + "\n\n", null);
                    break;
                }
                case 2: {
                    String msg = s;
                    if (lastWasMessage) {
                        if (!msg.endsWith("\n")) {
                            msg = msg + "\n";
                        }
                        if (!msg.endsWith("\n\n")) {
                            msg = msg + "\n";
                        }
                    } else {
                        this.doc.setLogicalStyle(this.doc.getLength(), this.comp.getStyle("print"));
                    }
                    this.doc.insertString(this.doc.getLength(), msg, null);
                    break;
                }
                case 5: {
                    this.doc.setLogicalStyle(this.doc.getLength(), this.comp.getStyle("message"));
                }
            }
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void handleImage(final byte[] imageData) throws InvocationTargetException, InterruptedException {
        if (SwingUtilities.isEventDispatchThread()) {
            this.handleImage0(imageData);
        } else {
            SwingUtilities.invokeAndWait(new Runnable(){

                public void run() {
                    PktHandler.this.handleImage0(imageData);
                }
            });
        }
    }

    private void handleImage0(byte[] imageData) {
        try {
            this.doc.setLogicalStyle(this.doc.getLength(), this.comp.getStyle("graphics"));
            MutableAttributeSet inputAttributes = this.comp.getInputAttributes();
            inputAttributes.removeAttributes(inputAttributes);
            StyleConstants.setIcon(inputAttributes, new ImageIcon(imageData));
            this.doc.insertString(this.doc.getLength(), " ", this.comp.getInputAttributes());
            inputAttributes.removeAttributes(inputAttributes);
            this.doc.insertString(this.doc.getLength(), "\n", null);
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}

