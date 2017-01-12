/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ext;

import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.NativeLink;
import com.wolfram.jlink.ext.RemoteMathLink;

public class NativeRemoteLink
extends NativeLink
implements RemoteMathLink {
    private String cmdLine = null;
    private String[] argv = null;
    private boolean isOpen = false;

    public NativeRemoteLink(String cmdLine) throws MathLinkException {
        super(cmdLine);
        this.cmdLine = cmdLine;
        this.isOpen = true;
    }

    public NativeRemoteLink(String[] argv) throws MathLinkException {
        super(argv);
        this.argv = argv;
        this.isOpen = true;
    }

    public synchronized void close() {
        try {
            this.putMessage(1);
        }
        catch (MathLinkException e) {
            // empty catch block
        }
        super.close();
        this.isOpen = false;
    }

    public synchronized void connect() throws MathLinkException {
        this.reopenIfNecessary();
        super.connect();
    }

    public synchronized long createMark() throws MathLinkException {
        this.reopenIfNecessary();
        return super.createMark();
    }

    public synchronized void putFunction(String f, int argCount) throws MathLinkException {
        this.reopenIfNecessary();
        super.putFunction(f, argCount);
    }

    public synchronized void putNext(int type) throws MathLinkException {
        this.reopenIfNecessary();
        super.putNext(type);
    }

    public synchronized void putSymbol(String s) throws MathLinkException {
        this.reopenIfNecessary();
        super.putSymbol(s);
    }

    public synchronized void sendString(String s) throws MathLinkException {
        this.putString(s);
    }

    protected void reopenIfNecessary() throws MathLinkException {
        if (this.isOpen) {
            return;
        }
        NativeRemoteLink newLink = this.cmdLine != null ? new NativeRemoteLink(this.cmdLine) : new NativeRemoteLink(this.argv);
        this.link = newLink.link;
        newLink.link = 0;
        this.isOpen = true;
    }
}

