/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import java.util.Hashtable;
import java.util.Stack;

public class StdLink {
    private static final int NONE = 0;
    private static final int JUST_ONE = 1;
    private static final int MODAL = 2;
    private static KernelLink mainLink;
    private static KernelLink uiLink;
    private static boolean mainLinkHasReader;
    private static int allowUIComputations;
    private static boolean lastPktWasAllowUIComps;
    private static boolean uiPermissionRequested;
    private static Hashtable stdLinkHash;
    private static Object stdLinkLock;
    private static Object uiLock;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static KernelLink getLink() {
        Object object = stdLinkLock;
        synchronized (object) {
            Thread key = Thread.currentThread();
            Stack s = (Stack)stdLinkHash.get(key);
            if (s != null && !s.empty()) {
                return (KernelLink)s.peek();
            }
            if (uiLink == null) {
                return mainLink;
            }
            Object object2 = uiLock;
            synchronized (object2) {
                return allowUIComputations == 2 ? mainLink : uiLink;
            }
        }
    }

    public static void setLink(KernelLink ml) {
        mainLink = ml;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void requestTransaction() {
        if (!mainLinkHasReader) {
            return;
        }
        KernelLink linkThatWillBeUsed = StdLink.getLink();
        if (linkThatWillBeUsed == null) {
            return;
        }
        if (linkThatWillBeUsed == uiLink) {
            return;
        }
        Object object = stdLinkLock;
        synchronized (object) {
            Thread key = Thread.currentThread();
            Stack s = (Stack)stdLinkHash.get(key);
            if (s != null && !s.empty()) {
                return;
            }
        }
        object = uiLock;
        synchronized (object) {
            uiPermissionRequested = true;
            while (allowUIComputations == 0) {
                try {
                    uiLock.wait();
                }
                catch (Exception e) {}
            }
            if (allowUIComputations == 1) {
                allowUIComputations = 0;
            }
            uiPermissionRequested = false;
        }
    }

    public static void setUILink(KernelLink uiLink) {
        StdLink.uiLink = uiLink;
    }

    public static KernelLink getUILink() {
        return uiLink;
    }

    public static KernelLink getMainLink() {
        return mainLink;
    }

    static void setHasReader(boolean hasReader) {
        mainLinkHasReader = hasReader;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void setup(KernelLink ml) {
        Object object = stdLinkLock;
        synchronized (object) {
            Thread key = Thread.currentThread();
            Stack<KernelLink> s = (Stack<KernelLink>)stdLinkHash.get(key);
            if (s == null) {
                s = new Stack<KernelLink>();
                stdLinkHash.put(key, s);
            }
            s.push(ml);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void remove() {
        Object object = stdLinkLock;
        synchronized (object) {
            Thread key = Thread.currentThread();
            Stack s = (Stack)stdLinkHash.get(key);
            s.pop();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void allowUIComputations(boolean allow, boolean enteringModal) {
        Object object = uiLock;
        synchronized (object) {
            if (allow) {
                StdLink.lastPktWasAllowUIComputations(true);
                if (enteringModal || uiPermissionRequested) {
                    allowUIComputations = enteringModal ? 2 : 1;
                    uiLock.notify();
                }
            } else {
                allowUIComputations = 0;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static boolean uiThreadWaiting() {
        Object object = uiLock;
        synchronized (object) {
            return uiPermissionRequested;
        }
    }

    static void lastPktWasAllowUIComputations(boolean b) {
        lastPktWasAllowUIComps = b;
    }

    static boolean mustPoll() {
        return lastPktWasAllowUIComps || allowUIComputations == 2;
    }

    static {
        mainLinkHasReader = false;
        allowUIComputations = 0;
        lastPktWasAllowUIComps = false;
        uiPermissionRequested = false;
        stdLinkHash = new Hashtable(4, 0.75f);
        stdLinkLock = new Object();
        uiLock = new Object();
    }
}

