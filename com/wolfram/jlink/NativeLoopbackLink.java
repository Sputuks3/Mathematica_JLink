/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.LoopbackLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.NativeLink;

public class NativeLoopbackLink
extends NativeLink
implements LoopbackLink {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public NativeLoopbackLink() throws MathLinkException {
        NativeLoopbackLink.loadNativeLibrary();
        String[] errMsgOut = new String[1];
        Object object = environmentLock;
        synchronized (object) {
            this.link = NativeLoopbackLink.MLLoopbackOpen(errMsgOut);
        }
        if (this.link == 0) {
            String msg = errMsgOut[0] != null ? errMsgOut[0] : "Link failed to open.";
            throw new MathLinkException(1004, msg);
        }
    }

    public NativeLoopbackLink(long mlinkPtr) {
        super(mlinkPtr);
    }

    public boolean setYieldFunction(Class cls, Object obj, String methName) {
        return false;
    }

    public boolean addMessageHandler(Class cls, Object obj, String methName) {
        return false;
    }
}

