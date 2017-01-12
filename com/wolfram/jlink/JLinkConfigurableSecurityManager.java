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
import java.util.Collection;
import java.util.Properties;
import java.util.Vector;

public class JLinkConfigurableSecurityManager
extends JLinkSecurityManager {
    private Vector<String> allowedReadDirs = new Vector(0);
    private Vector<String> allowedWriteDirs = new Vector(0);
    private Vector<String> allowedLibraryNames = new Vector(0);
    private Vector<SocketPermission> allowedIPs = new Vector(0);
    private String mathematicaInstallationDir;
    private boolean allowedLibsHasWildcard = false;
    private boolean useSecurity = true;
    private static boolean hasNIO = false;
    private static boolean hasCheckedNIO = false;

    public JLinkConfigurableSecurityManager(Properties props) throws IOException {
        if ("false".equals(props.getProperty("useSecurity"))) {
            this.useSecurity = false;
        }
        String javaHome = System.getProperty("java.home");
        this.allowedReadDirs.add(new File(javaHome).getCanonicalPath());
        String tempDir = System.getProperty("java.io.tmpdir");
        if (tempDir != null) {
            String tempDirPath = new File(tempDir).getCanonicalPath();
            this.allowedReadDirs.add(tempDirPath);
            this.allowedWriteDirs.add(tempDirPath);
        }
        this.addAllowedReadDirectories(props.getProperty("allowedReadDirs"));
        this.addAllowedWriteDirectories(props.getProperty("allowedWriteDirs"));
        this.addAllowedIPs(props.getProperty("allowedIPs"));
        this.addAllowedLibs(props.getProperty("allowedLibs"));
    }

    public static String[] getSecurityData() {
        JLinkConfigurableSecurityManager sm = (JLinkConfigurableSecurityManager)System.getSecurityManager();
        Vector<String> vals = new Vector<String>(0);
        vals.add("usesecurity");
        vals.add(sm.useSecurity ? "true" : "false");
        vals.add("readdirs");
        vals.addAll(sm.allowedReadDirs);
        vals.add("writedirs");
        vals.addAll(sm.allowedWriteDirs);
        vals.add("libs");
        vals.addAll(sm.allowedLibraryNames);
        vals.add("ips");
        for (SocketPermission perm : sm.allowedIPs) {
            vals.add(perm.getName());
        }
        return vals.toArray(new String[0]);
    }

    private void addAllowedIPs(String ipSpec) {
        String[] specs;
        if (ipSpec == null || ipSpec.equals("")) {
            return;
        }
        for (String spec : specs = ipSpec.split(",")) {
            this.allowedIPs.add(new SocketPermission(spec.trim(), "connect,resolve"));
        }
    }

    private void addAllowedReadDirectories(String readDirs) {
        if (readDirs == null || readDirs.equals("")) {
            return;
        }
        String[] dirs = readDirs.split(",");
        this.mathematicaInstallationDir = dirs[0].trim();
        for (String dir : dirs) {
            try {
                String d = new File(dir.trim()).getAbsolutePath();
                this.allowedReadDirs.add(d);
                continue;
            }
            catch (Exception e) {
                // empty catch block
            }
        }
    }

    private void addAllowedWriteDirectories(String writeDirs) {
        String[] dirs;
        if (writeDirs == null || writeDirs.equals("")) {
            return;
        }
        for (String dir : dirs = writeDirs.split(",")) {
            try {
                String d = new File(dir.trim()).getAbsolutePath();
                this.allowedWriteDirs.add(d);
                continue;
            }
            catch (Exception e) {
                // empty catch block
            }
        }
    }

    private void addAllowedLibs(String loadLibraryNames) {
        String[] libs;
        if (loadLibraryNames == null || loadLibraryNames.equals("")) {
            return;
        }
        for (String lib : libs = loadLibraryNames.split(",")) {
            if (lib.trim().equals("*")) {
                this.allowedLibsHasWildcard = true;
                continue;
            }
            this.allowedLibraryNames.add(lib.trim());
        }
    }

    public void checkPermission(Permission perm) {
        super.checkPermission(perm);
        if (!this.useSecurity) {
            return;
        }
        String name = perm.getName();
        String actions = perm.getActions();
        if (perm instanceof RuntimePermission) {
            if ("accessDeclaredMembers".equals(name)) {
                return;
            }
            if (name.startsWith("loadLibrary.")) {
                String libName = name.substring(12);
                if (this.allowedLibsHasWildcard || this.allowedLibraryNames.contains(libName)) {
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
            if (JLinkConfigurableSecurityManager.hasNIOClasses() && perm instanceof LinkPermission) {
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

