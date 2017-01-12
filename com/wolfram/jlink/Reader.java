/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.JLinkClassLoader;
import com.wolfram.jlink.JLinkSecurityManager;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.StdLink;

public class Reader
extends Thread {
    private KernelLink ml;
    private boolean quitWhenLinkEnds = true;
    private boolean requestedStop = false;
    private boolean isMainLink;
    private static Reader reader;
    private static int sleepInterval;

    public static Thread startReader(KernelLink ml, boolean quitWhenLinkEnds, boolean alwaysPoll) {
        reader = new Reader(ml, quitWhenLinkEnds, true);
        reader.start();
        StdLink.setLink(ml);
        StdLink.setHasReader(true);
        return reader;
    }

    public static Thread stopReader() {
        Reader.reader.requestedStop = true;
        StdLink.setHasReader(false);
        return reader;
    }

    public Reader(KernelLink ml, boolean quitWhenLinkEnds, boolean isMainLink) {
        super("J/Link reader" + (isMainLink ? "" : "2"));
        this.setContextClassLoader(ml.getClassLoader());
        this.ml = ml;
        this.quitWhenLinkEnds = quitWhenLinkEnds;
        this.isMainLink = isMainLink;
        ml.addMessageHandler(null, this, "terminateMsgHandler");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void run() {
        loopsAgo = 0;
        mustPoll = false;
        do {
            block28 : {
                while (this.requestedStop == false) {
                    if (!this.isMainLink || !mustPoll) break block28;
                    isReady = false;
                    try {
                        isReady = this.ml.ready();
                        if (!isReady) {
                            Reader.sleep((long)Reader.sleepInterval + Math.min(loopsAgo++, 20));
                        }
                    }
                    catch (Exception e) {
                        // empty catch block
                    }
                    e = this.ml;
                    // MONITORENTER : e
                    try {
                        if (this.ml.error() != 0) {
                            throw new MathLinkException(this.ml.error(), this.ml.errorMessage());
                        }
                        if (isReady) {
                            loopsAgo = 0;
                            pkt = this.ml.nextPacket();
                            this.ml.handlePacket(pkt);
                            this.ml.newPacket();
                            mustPoll = StdLink.mustPoll();
                        }
                    }
                    catch (MathLinkException e) {
                        if (e.getErrCode() == 11 || !this.ml.clearError()) {
                            // MONITOREXIT : e
                            if (this.quitWhenLinkEnds == false) return;
                            this.ml.close();
                            this.ml = null;
                            if (StdLink.getUILink() != null) {
                                StdLink.getUILink().close();
                                StdLink.setUILink(null);
                            }
                            StdLink.setLink(null);
                            JLinkSecurityManager.setAllowExit(true);
                            System.exit(0);
                            return;
                        }
                        this.ml.newPacket();
                    }
                }
                return;
            }
            isReady = this.ml;
            // MONITORENTER : isReady
            try {
                pkt = this.ml.nextPacket();
                this.ml.handlePacket(pkt);
                this.ml.newPacket();
                mustPoll = StdLink.mustPoll();
                ** GOTO lbl68
            }
            catch (MathLinkException e) {
                if (e.getErrCode() == 11 || !this.ml.clearError()) {
                    // MONITOREXIT : isReady
                    if (this.quitWhenLinkEnds == false) return;
                    this.ml.close();
                    this.ml = null;
                    if (StdLink.getUILink() != null) {
                        StdLink.getUILink().close();
                        StdLink.setUILink(null);
                    }
                    StdLink.setLink(null);
                    JLinkSecurityManager.setAllowExit(true);
                    System.exit(0);
                    return;
                }
                this.ml.newPacket();
lbl68: // 2 sources:
                // MONITOREXIT : isReady
                continue;
            }
            break;
        } while (true);
        finally {
            if (this.quitWhenLinkEnds) {
                this.ml.close();
                this.ml = null;
                if (StdLink.getUILink() != null) {
                    StdLink.getUILink().close();
                    StdLink.setUILink(null);
                }
                StdLink.setLink(null);
                JLinkSecurityManager.setAllowExit(true);
                System.exit(0);
            }
        }
    }

    public void terminateMsgHandler(int msg, int ignore) {
        if (msg == 1) {
            this.stop();
            this.ml.setYieldFunction(null, this, "terminateYielder");
            this.requestedStop = true;
        }
    }

    boolean terminateYielder() {
        return true;
    }

    static {
        sleepInterval = 2;
    }
}

