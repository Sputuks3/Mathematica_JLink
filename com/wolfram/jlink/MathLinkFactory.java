/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.JLinkClassLoader;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.LoopbackLink;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.NativeLink;
import com.wolfram.jlink.NativeLoopbackLink;
import com.wolfram.jlink.StdLink;
import com.wolfram.jlink.Utils;
import com.wolfram.jlink.WrappedKernelLink;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.StringTokenizer;

public class MathLinkFactory {
    public static KernelLink createKernelLink(String cmdLine) throws MathLinkException {
        return MathLinkFactory.createKernelLink0(cmdLine, null);
    }

    public static KernelLink createKernelLink(String[] argv) throws MathLinkException {
        return MathLinkFactory.createKernelLink0(null, argv);
    }

    public static KernelLink createKernelLink(MathLink ml) throws MathLinkException {
        return new WrappedKernelLink(ml);
    }

    private static KernelLink createKernelLink0(String cmdLine, String[] argv) throws MathLinkException {
        String protocol;
        if (cmdLine == null && argv == null) {
            throw new MathLinkException(1004, "Null argument to KernelLink constructor");
        }
        boolean usingCmdLine = cmdLine != null;
        String string = protocol = usingCmdLine ? MathLinkFactory.determineProtocol(cmdLine) : MathLinkFactory.determineProtocol(argv);
        if (!protocol.equals("native")) {
            String implClassName = null;
            try {
                implClassName = System.getProperty("KernelLink." + protocol);
            }
            catch (SecurityException e) {
                // empty catch block
            }
            if (implClassName == null) {
                Properties props = MathLinkFactory.loadProperties();
                implClassName = props.getProperty("KernelLink." + protocol);
            }
            if (implClassName != null) {
                Class implementingClass = null;
                try {
                    KernelLink stdLink = StdLink.getLink();
                    ClassLoader cl = stdLink != null ? JLinkClassLoader.getInstance() : MathLinkFactory.class.getClassLoader();
                    implementingClass = cl.loadClass(implClassName);
                }
                catch (ClassNotFoundException e) {
                    // empty catch block
                }
                if (implementingClass != null) {
                    try {
                        Object[] arrobject;
                        Class<String> argsClass = usingCmdLine ? String.class : String[].class;
                        Constructor ctor = implementingClass.getConstructor(argsClass);
                        if (usingCmdLine) {
                            Object[] arrobject2 = new Object[1];
                            arrobject = arrobject2;
                            arrobject2[0] = cmdLine;
                        } else {
                            Object[] arrobject3 = new Object[1];
                            arrobject = arrobject3;
                            arrobject3[0] = argv;
                        }
                        return (KernelLink)ctor.newInstance(arrobject);
                    }
                    catch (Exception e) {
                        if (e instanceof InvocationTargetException) {
                            throw new MathLinkException(((InvocationTargetException)e).getTargetException(), "Error instantiating link object of class " + implementingClass.getName());
                        }
                        throw new MathLinkException(e, "Error instantiating link object of class " + implementingClass.getName());
                    }
                }
            }
        }
        return new WrappedKernelLink(usingCmdLine ? MathLinkFactory.createMathLink(cmdLine) : MathLinkFactory.createMathLink(argv));
    }

    public static MathLink createMathLink(String cmdLine) throws MathLinkException {
        return MathLinkFactory.createMathLink0(cmdLine, null);
    }

    public static MathLink createMathLink(String[] argv) throws MathLinkException {
        return MathLinkFactory.createMathLink0(null, argv);
    }

    private static MathLink createMathLink0(String cmdLine, String[] argv) throws MathLinkException {
        String protocol;
        if (cmdLine == null && argv == null) {
            throw new MathLinkException(1004, "Null argument to MathLink constructor");
        }
        boolean usingCmdLine = cmdLine != null;
        String string = protocol = usingCmdLine ? MathLinkFactory.determineProtocol(cmdLine) : MathLinkFactory.determineProtocol(argv);
        if (!protocol.equals("native")) {
            String implClassName = null;
            try {
                implClassName = System.getProperty("MathLink." + protocol);
            }
            catch (SecurityException e) {
                // empty catch block
            }
            if (implClassName == null) {
                Properties props = MathLinkFactory.loadProperties();
                implClassName = props.getProperty("MathLink." + protocol);
            }
            if (implClassName == null) {
                if (protocol.equals("rmi")) {
                    implClassName = "com.wolfram.jlink.ext.MathLink_RMI";
                } else if (protocol.equals("remoteservices")) {
                    implClassName = "com.wolfram.remoteservices.jlink.RemoteServicesLink";
                }
            }
            if (implClassName != null) {
                Class implementingClass = null;
                try {
                    KernelLink stdLink = StdLink.getLink();
                    ClassLoader cl = stdLink != null ? JLinkClassLoader.getInstance() : MathLinkFactory.class.getClassLoader();
                    implementingClass = cl.loadClass(implClassName);
                }
                catch (ClassNotFoundException e) {
                    // empty catch block
                }
                if (implementingClass != null) {
                    try {
                        Object[] arrobject;
                        Class<String> argsClass = usingCmdLine ? String.class : String[].class;
                        Constructor ctor = implementingClass.getConstructor(argsClass);
                        if (usingCmdLine) {
                            Object[] arrobject2 = new Object[1];
                            arrobject = arrobject2;
                            arrobject2[0] = cmdLine;
                        } else {
                            Object[] arrobject3 = new Object[1];
                            arrobject = arrobject3;
                            arrobject3[0] = argv;
                        }
                        return (MathLink)ctor.newInstance(arrobject);
                    }
                    catch (Exception e) {
                        if (e instanceof InvocationTargetException) {
                            throw new MathLinkException(((InvocationTargetException)e).getTargetException(), "Error instantiating link object of class " + implementingClass.getName());
                        }
                        throw new MathLinkException(e, "Error instantiating link object of class " + implementingClass.getName());
                    }
                }
                System.err.println("J/Link Warning: could not find any Java class that implements the requested " + protocol + " protocol. This protocol name will be passed to the MathLink library to " + "see if it has a native implementation.");
            }
        }
        return usingCmdLine ? new NativeLink(cmdLine) : new NativeLink(argv);
    }

    public static LoopbackLink createLoopbackLink() throws MathLinkException {
        return new NativeLoopbackLink();
    }

    private static String determineProtocol(String cmdLine) {
        StringTokenizer st = new StringTokenizer(cmdLine);
        String prot = "native";
        while (st != null && st.hasMoreTokens()) {
            if (!st.nextToken().toLowerCase().equals("-linkprotocol") || !st.hasMoreTokens()) continue;
            prot = st.nextToken().toLowerCase();
            break;
        }
        return MathLinkFactory.isNative(prot) ? "native" : prot;
    }

    private static String determineProtocol(String[] argv) {
        String prot = "native";
        if (argv != null) {
            for (int i = 0; i < argv.length - 1; ++i) {
                if (!argv[i].toLowerCase().equals("-linkprotocol")) continue;
                prot = argv[i + 1].toLowerCase();
                break;
            }
        }
        return MathLinkFactory.isNative(prot) ? "native" : prot;
    }

    private static boolean isNative(String prot) {
        return prot.equals("native") || prot.equals("local") || prot.equals("filemap") || prot.equals("fm") || prot.equals("ppc") || prot.equals("tcp") || prot.equals("tcpip") || prot.equals("pipes") || prot.equals("sharedmemory") || prot.equals("");
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try {
            InputStream in = null;
            String jarDir = Utils.getJLinkJarDir();
            if (jarDir != null) {
                try {
                    in = new FileInputStream(jarDir + "JLink.properties");
                }
                catch (Exception e) {
                    // empty catch block
                }
            }
            if (in == null) {
                KernelLink stdLink = StdLink.getLink();
                ClassLoader cl = stdLink != null ? JLinkClassLoader.getInstance() : MathLinkFactory.class.getClassLoader();
                in = cl.getResourceAsStream("JLink.properties");
            }
            if (in != null) {
                props.load(in);
                in.close();
            }
        }
        catch (Exception e) {
            // empty catch block
        }
        return props;
    }
}

