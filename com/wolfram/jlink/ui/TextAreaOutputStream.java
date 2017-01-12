/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ui;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
import javax.swing.text.Document;

class TextAreaOutputStream
extends OutputStream {
    protected JTextArea ta;
    public int maxLines;
    public int numLines;
    protected char[] buf = new char[8192];
    protected int count;
    private boolean lastWasCR;

    public TextAreaOutputStream(JTextArea ta, int maxLines) {
        this.ta = ta;
        this.maxLines = maxLines;
        this.reset();
    }

    public synchronized void write(int b) throws IOException {
        boolean addedALine = false;
        this.buf[this.count++] = (char)b;
        if (b == 13 || b == 10 && !this.lastWasCR) {
            ++this.numLines;
            addedALine = true;
        }
        if (b == 10 && this.lastWasCR && this.count > 1) {
            this.buf[this.count - 2] = 10;
            --this.count;
        }
        if (this.count == this.buf.length || addedALine) {
            this.flush();
        }
        this.lastWasCR = b == 13;
    }

    public synchronized void flush() throws IOException {
        int excessLines = this.numLines - this.maxLines;
        if (excessLines > 0) {
            String text = this.ta.getText();
            int deleteUpTo = 0;
            for (int i = 0; i < text.length() && excessLines > 0; ++i) {
                char c = text.charAt(i);
                if (c == '\r') {
                    if (i < text.length() - 1 && text.charAt(i + 1) == '\n') {
                        --excessLines;
                        ++i;
                    }
                } else if (c == '\n') {
                    --excessLines;
                }
                if (excessLines != 0) continue;
                deleteUpTo = i + 1;
            }
            if (deleteUpTo != 0) {
                this.ta.replaceRange("", 0, deleteUpTo);
            }
            this.numLines = this.maxLines;
        }
        this.ta.append(new String(this.buf, 0, this.count));
        try {
            char c;
            if (this.count > 0 && ((c = this.buf[this.count - 1]) == '\n' || c == '\r')) {
                Rectangle r = this.ta.modelToView(this.ta.getDocument().getLength());
                this.ta.scrollRectToVisible(r);
            }
        }
        catch (Exception e) {
            // empty catch block
        }
        this.count = 0;
    }

    public synchronized void reset() {
        this.count = 0;
        this.numLines = 0;
        this.lastWasCR = false;
        this.ta.setText("");
    }
}

