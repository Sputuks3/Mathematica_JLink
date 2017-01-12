/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import java.security.Permission;

public class JLinkSecurityManager
extends SecurityManager {
    protected boolean allowExit = false;

    public static void setAllowExit(boolean allow) {
        SecurityManager sm = System.getSecurityManager();
        if (sm instanceof JLinkSecurityManager) {
            JLinkSecurityManager jsm = (JLinkSecurityManager)sm;
            jsm.allowExit = allow;
        }
    }

    public void checkPermission(Permission perm) {
        if (perm instanceof RuntimePermission && perm.getName().startsWith("exitVM") && !this.allowExit) {
            throw new SecurityException("J/Link does not allow code called from Mathematica to call System.exit().");
        }
    }

    public void checkPermission(Permission perm, Object context) {
        this.checkPermission(perm);
    }
}

