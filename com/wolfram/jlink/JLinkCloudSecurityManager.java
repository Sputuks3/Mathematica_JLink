/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.JLinkSecurityManager;
import com.wolfram.jlink.Utils;
import java.awt.AWTPermission;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.net.NetPermission;
import java.net.SocketPermission;
import java.nio.file.LinkPermission;
import java.security.Permission;
import java.security.SecurityPermission;
import java.util.Vector;

public class JLinkCloudSecurityManager
extends JLinkSecurityManager {
    private boolean useSecurity = true;
    private boolean hasSetUseSecurity = false;
    private boolean hasSetAllowedDirs = false;
    private boolean hasSetAllowedLibs = false;
    private boolean hasSetAllowedIPs = false;
    private Vector<String> allowedReadDirs = new Vector(0);
    private Vector<String> allowedWriteDirs = new Vector(0);
    private Vector<String> allowedLibraryNames = new Vector(0);
    private Vector<SocketPermission> allowedIPs = new Vector(0);
    private String mathematicaInstallationDir;
    private static boolean hasNIO = false;
    private static boolean hasCheckedNIO = false;

    public JLinkCloudSecurityManager() throws IOException {
        String javaHome = System.getProperty("java.home");
        this.allowedReadDirs.add(new File(javaHome).getCanonicalPath());
        this.mathematicaInstallationDir = new File(javaHome).getParentFile().getParentFile().getParentFile().getCanonicalPath();
        this.allowedReadDirs.add(this.mathematicaInstallationDir);
        String tempDir = System.getProperty("java.io.tmpdir");
        if (tempDir != null) {
            String tempDirPath = new File(tempDir).getCanonicalPath();
            this.allowedReadDirs.add(tempDirPath);
            this.allowedWriteDirs.add(tempDirPath);
        }
    }

    public static void setUseSecurity(boolean use) {
        JLinkCloudSecurityManager sm = (JLinkCloudSecurityManager)System.getSecurityManager();
        if (sm.hasSetUseSecurity) {
            return;
        }
        sm.hasSetUseSecurity = true;
        sm.useSecurity = use;
    }

    public static void setAllowedIPs(String ipSpec) {
        String[] specs;
        JLinkCloudSecurityManager sm = (JLinkCloudSecurityManager)System.getSecurityManager();
        if (sm.hasSetAllowedIPs) {
            return;
        }
        sm.hasSetAllowedIPs = true;
        if ("".equals(ipSpec)) {
            return;
        }
        for (String spec : specs = ipSpec.split(",")) {
            sm.allowedIPs.add(new SocketPermission(spec, "connect,resolve"));
        }
    }

    public static void setAllowedDirectories(String[] readDirs, String[] writeDirs) {
        int i;
        String d;
        JLinkCloudSecurityManager sm = (JLinkCloudSecurityManager)System.getSecurityManager();
        if (sm.hasSetAllowedDirs) {
            return;
        }
        sm.hasSetAllowedDirs = true;
        for (i = 0; i < readDirs.length; ++i) {
            try {
                d = new File(readDirs[i]).getAbsolutePath();
                sm.allowedReadDirs.add(d);
                continue;
            }
            catch (Exception e) {
                // empty catch block
            }
        }
        for (i = 0; i < writeDirs.length; ++i) {
            try {
                d = new File(writeDirs[i]).getAbsolutePath();
                sm.allowedWriteDirs.add(d);
                continue;
            }
            catch (Exception e) {
                // empty catch block
            }
        }
    }

    public static void setAllowedLibs(String[] loadLibraryNames) {
        JLinkCloudSecurityManager sm = (JLinkCloudSecurityManager)System.getSecurityManager();
        if (sm.hasSetAllowedLibs) {
            return;
        }
        sm.hasSetAllowedLibs = true;
        for (int i = 0; i < loadLibraryNames.length; ++i) {
            sm.allowedLibraryNames.add(loadLibraryNames[i]);
        }
    }

    public void checkPermission(Permission perm) {
        String name = perm.getName();
        String actions = perm.getActions();
        if (!this.useSecurity) {
            return;
        }
        if (perm instanceof RuntimePermission) {
            if ("accessDeclaredMembers".equals(name)) {
                return;
            }
            if (name.startsWith("loadLibrary.")) {
                String libName = name.substring(12);
                if (this.allowedLibraryNames.contains(libName)) {
                    return;
                }
                String path = libName.replace('\\', '/');
                if (Utils.isWindows() && path.startsWith("/")) {
                    path = path.substring(1);
                }
                if (!path.startsWith(this.mathematicaInstallationDir.replace('\\', '/'))) {
                    throw new SecurityException("Java code called from Wolfram Cloud cannot load the native library " + path);
                }
            } else {
                if ("setSecurityManager".equals(name) || "createSecurityManager".equals(name)) {
                    throw new SecurityException("Code called from Wolfram Cloud cannot set a SecurityManager.");
                }
                if (name.startsWith("exitVM") && !this.allowExit) {
                    throw new SecurityException("Java code called from the Wolfram Language cannot call System.exit().");
                }
            }
        } else if (perm instanceof FilePermission) {
            if (actions.contains("write") || actions.contains("delete")) {
                try {
                    String p = new File(name).getCanonicalPath();
                    for (String dir : this.allowedWriteDirs) {
                        if (!p.startsWith(dir)) continue;
                        return;
                    }
                    throw new SecurityException("Writing is not permitted to file " + name);
                }
                catch (Exception e) {
                    throw new SecurityException("Writing is not permitted to file " + name);
                }
            }
            if (actions.contains("read")) {
                try {
                    String p = new File(name).getCanonicalPath();
                    for (String dir : this.allowedReadDirs) {
                        if (!p.startsWith(dir)) continue;
                        return;
                    }
                    throw new SecurityException("Reading is not permitted from file " + name);
                }
                catch (Exception e) {
                    throw new SecurityException("Reading is not permitted from file " + name);
                }
            }
            if (actions.contains("execute")) {
                throw new SecurityException("Java code called from Wolfram Cloud cannot call exec(). File: " + name);
            }
        } else if (perm instanceof SocketPermission) {
            if (actions.contains("connect")) {
                String ip = name;
                if (ip.startsWith("127.0.0.1")) {
                    return;
                }
                for (SocketPermission ipPerm : this.allowedIPs) {
                    if (!ipPerm.implies(perm)) continue;
                    return;
                }
                throw new SecurityException("Java code called from Wolfram Cloud cannot use TCP.");
            }
            if (actions.contains("listen") || actions.contains("accept")) {
                throw new SecurityException("Java code called from Wolfram Cloud cannot listen on network ports.");
            }
        } else if (perm instanceof NetPermission) {
            if (name.equals("setProxySelector")) {
                throw new SecurityException("Java code called from Wolfram Cloud cannot set a ProxySelector.");
            }
        } else if (perm instanceof SecurityPermission) {
            String foo = name;
        } else {
            if (perm instanceof AWTPermission) {
                throw new SecurityException("Code called from Wolfram Cloud cannot use AWT features.");
            }
            if (JLinkCloudSecurityManager.hasNIOClasses() && perm instanceof LinkPermission) {
                throw new SecurityException("Code called from Wolfram Cloud cannot create hard or symbolic links.");
            }
        }
    }

    public void checkPermission(Permission perm, Object context) {
        this.checkPermission(perm);
    }

    private static boolean hasNIOClasses() {
        if (hasCheckedNIO) {
            return hasNIO;
        }
        hasCheckedNIO = true;
        try {
            Class proxySelectorClass = Class.forName("java.nio.file.LinkPermission");
            hasNIO = true;
            return true;
        }
        catch (Exception e) {
            hasNIO = false;
            return false;
        }
    }
}

