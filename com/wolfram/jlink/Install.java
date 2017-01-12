/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.JLinkClassLoader;
import com.wolfram.jlink.JLinkConfigurableSecurityManager;
import com.wolfram.jlink.JLinkSecurityManager;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import com.wolfram.jlink.NativeLink;
import com.wolfram.jlink.Reader;
import com.wolfram.jlink.StdLink;
import com.wolfram.jlink.Utils;
import com.wolfram.jlink.WrappedKernelLink;
import com.wolfram.jlink.ui.ConsoleStream;
import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

public class Install {
    static final int CALLJAVA = 1;
    static final int LOADCLASS = 2;
    static final int THROW = 3;
    static final int RELEASEOBJECT = 4;
    static final int VAL = 5;
    static final int ONLOADCLASS = 6;
    static final int ONUNLOADCLASS = 7;
    static final int SETCOMPLEX = 8;
    static final int REFLECT = 9;
    static final int SHOW = 10;
    static final int SAMEQ = 11;
    static final int INSTANCEOF = 12;
    static final int ALLOWRAGGED = 13;
    static final int GETEXCEPTION = 14;
    static final int CONNECTTOFE = 15;
    static final int DISCONNECTTOFE = 16;
    static final int PEEKCLASSES = 17;
    static final int PEEKOBJECTS = 18;
    static final int CLASSPATH = 19;
    static final int ADDTOCLASSPATH = 20;
    static final int SETUSERDIR = 21;
    static final int ALLOWUICOMPUTATIONS = 22;
    static final int UITHREADWAITING = 23;
    static final int YIELDTIME = 24;
    static final int GETCONSOLE = 25;
    static final int EXTRALINKS = 26;
    static final int GETWINDOWID = 27;
    static final int ADDTITLECHANGELISTENER = 28;
    static final int SETVMNAME = 29;
    static final int SETEXCEPTION = 30;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void main(String[] args) {
        KernelLink ml;
        MathLink impl;
        int timeout;
        block54 : {
            block53 : {
                timeout = 25000;
                boolean isLinked = false;
                for (int i = 0; i < args.length; ++i) {
                    if (!args[i].equals("-mathlink")) continue;
                    isLinked = true;
                    if (!isLinked) {
                        break;
                    }
                    break block53;
                }
                System.out.println("J/Link (tm)");
                System.out.println("Copyright (C) 1999-2016, Wolfram Research, Inc. All Rights Reserved.");
                System.out.println("www.wolfram.com");
                System.out.println("Version 4.9.1");
                System.out.println("");
                System.out.flush();
            }
            ConsoleStream.setSystemStdoutStream(System.out);
            ConsoleStream.setSystemStderrStream(System.err);
            Properties securityProperties = null;
            BufferedReader initFileReader = null;
            for (int i = 0; i < args.length - 1; ++i) {
                if (args[i].equalsIgnoreCase("-init")) {
                    String initFile = args[i + 1];
                    if (initFile.startsWith("\"")) {
                        initFile = initFile.substring(1, initFile.length() - 2);
                    }
                    try {
                        BufferedReader rdr = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(initFile), "UTF-8"));
                        char[] chars = new char[30000];
                        int numChars = rdr.read(chars, 0, 30000);
                        if (numChars > 0) {
                            initFileReader = new BufferedReader(new CharArrayReader(chars, 0, numChars));
                        }
                        rdr.close();
                        new File(initFile).delete();
                        continue;
                    }
                    catch (Exception e) {}
                    continue;
                }
                if (!args[i].equalsIgnoreCase("-jlink.securityFile")) continue;
                String securityDir = System.getProperty("jlink.securityDir");
                if (securityDir == null) {
                    System.err.println("FATAL ERROR: The jlink.securityDir property was not defined");
                    return;
                }
                String securityFileName = args[i + 1];
                if (securityFileName.contains("..")) {
                    System.err.println("FATAL ERROR: The -jlink.securityFile command-line argument has an illegal value");
                    return;
                }
                File securityConfigFile = new File(securityDir, securityFileName);
                if (!securityConfigFile.exists() || !securityConfigFile.canRead()) {
                    System.err.println("FATAL ERROR: The -jlink.securityFile command-line argument does not point to a readable file");
                    return;
                }
                FileInputStream inStr = null;
                securityProperties = new Properties();
                inStr = new FileInputStream(securityConfigFile);
                securityProperties.load(inStr);
                if (inStr == null) continue;
                try {
                    inStr.close();
                    continue;
                }
                catch (IOException ee) {}
                continue;
                catch (Exception e) {
                    System.err.println("FATAL ERROR: Error reading the -securityFile file");
                    return;
                }
                finally {
                    if (inStr != null) {
                        try {
                            inStr.close();
                        }
                        catch (IOException ee) {
                            // empty catch block
                        }
                    }
                }
            }
            try {
                JLinkSecurityManager securityManager2;
                if (securityProperties != null) {
                    JLinkConfigurableSecurityManager securityManager22 = new JLinkConfigurableSecurityManager(securityProperties);
                } else if (System.getProperty("com.wolfram.jlink.security") != null) {
                    String securityManagerClassName = System.getProperty("com.wolfram.jlink.security");
                    Class securityClass = Install.class.getClassLoader().loadClass(securityManagerClassName);
                    SecurityManager securityManager = (SecurityManager)securityClass.newInstance();
                } else {
                    securityManager2 = new JLinkSecurityManager();
                }
                System.setSecurityManager(securityManager2);
            }
            catch (Exception e) {
                System.err.println("FATAL ERROR: attempt to set a SecurityManager failed: " + e);
                return;
            }
            try {
                ml = MathLinkFactory.createKernelLink(args);
            }
            catch (MathLinkException e) {
                System.err.println("FATAL ERROR: link creation failed.");
                return;
            }
            if (initFileReader != null) {
                String line;
                while ((line = initFileReader.readLine()) != null) {
                    try {
                        String addToClassPath;
                        if (line.startsWith("cp ")) {
                            addToClassPath = line.substring(3);
                            ml.getClassLoader().addLocations(new String[]{addToClassPath}, true);
                            continue;
                        }
                        if (line.startsWith("cpf ")) {
                            addToClassPath = line.substring(4);
                            ml.getClassLoader().addLocations(new String[]{addToClassPath}, false);
                            continue;
                        }
                        if (line.startsWith("run ")) continue;
                    }
                    catch (Throwable t) {}
                }
                try {
                    initFileReader.close();
                }
                catch (Exception ee) {}
                break block54;
                catch (Exception e) {
                    try {
                        initFileReader.close();
                    }
                    catch (Exception ee) {}
                    catch (Throwable throwable) {
                        try {
                            initFileReader.close();
                            throw throwable;
                        }
                        catch (Exception ee) {
                            // empty catch block
                        }
                        throw throwable;
                    }
                }
            }
        }
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equalsIgnoreCase("-timeout") && i < args.length - 1) {
                try {
                    timeout = Integer.parseInt(args[i + 1]);
                    continue;
                }
                catch (NumberFormatException e) {}
                continue;
            }
            if (!args[i].equalsIgnoreCase("-linklisten") && (!args[i].equalsIgnoreCase("-linkmode") || i >= args.length - 1 || !args[i + 1].equalsIgnoreCase("listen"))) continue;
            timeout = Integer.MAX_VALUE;
            break;
        }
        if (!Install.install(ml, timeout)) {
            ml.close();
            JLinkSecurityManager.setAllowExit(true);
            System.exit(1);
        }
        if (Utils.isWindows() && ml instanceof WrappedKernelLink && (impl = ((WrappedKernelLink)ml).getMathLink()) instanceof NativeLink) {
            NativeLink.hideJavaWindow();
        }
        Reader.startReader(ml, true, false);
    }

    public static KernelLink getStdLink() {
        return StdLink.getLink();
    }

    public static boolean install(MathLink ml) {
        return Install.install(ml, Integer.MAX_VALUE);
    }

    public static boolean install(MathLink ml, int timeout) {
        try {
            ml.connect(timeout);
            return true;
        }
        catch (MathLinkException e) {
            return false;
        }
    }
}

