/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.LoopbackLink;
import com.wolfram.jlink.MLFunction;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkImpl;
import com.wolfram.jlink.NativeLoopbackLink;
import com.wolfram.jlink.Utils;
import com.wolfram.jlink.WrappedKernelLink;
import java.awt.Window;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Array;

public class NativeLink
extends MathLinkImpl {
    protected long link;
    protected static final Object environmentLock = Void.class;
    static final String LINK_NULL_MESSAGE = "Link is not open.";
    static final String CREATE_FAILED_MESSAGE = "Link failed to open.";
    static volatile boolean nativeLibraryLoaded = false;
    static volatile boolean jvmIsShuttingDown = false;
    public static Throwable loadLibraryException = null;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void loadNativeLibrary() {
        Object object = environmentLock;
        synchronized (object) {
            if (nativeLibraryLoaded) {
                return;
            }
            String libName = "JLinkNativeLibrary";
            try {
                String jarDir;
                String libDir = null;
                try {
                    libDir = System.getenv("JLINK_LIB_DIR");
                }
                catch (Throwable t) {
                    // empty catch block
                }
                if (libDir != null) {
                    nativeLibraryLoaded = NativeLink.loadNativeLib(libName, libDir);
                }
                if (!nativeLibraryLoaded && (libDir = System.getProperty("com.wolfram.jlink.libdir")) != null) {
                    nativeLibraryLoaded = NativeLink.loadNativeLib(libName, libDir);
                }
                if (!nativeLibraryLoaded && (jarDir = Utils.getJLinkJarDir()) != null) {
                    nativeLibraryLoaded = NativeLink.loadNativeLib(libName, jarDir);
                }
                if (!nativeLibraryLoaded) {
                    try {
                        System.loadLibrary(libName);
                        nativeLibraryLoaded = true;
                    }
                    catch (UnsatisfiedLinkError e) {
                        loadLibraryException = e;
                        System.err.println("Fatal error: cannot find the required native library named " + libName + ".");
                    }
                }
            }
            catch (SecurityException e) {
                loadLibraryException = e;
                System.err.println("Fatal error: security exception trying to load " + libName + ". This thread does not have permission to load native libraries. Message is: " + e.getMessage());
            }
            if (nativeLibraryLoaded) {
                loadLibraryException = null;
                NativeLink.MLInitialize();
                Runtime.getRuntime().addShutdownHook(new JLinkShutdownThread());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public NativeLink(String cmdLine) throws MathLinkException {
        NativeLink.loadNativeLibrary();
        String[] errMsgOut = new String[1];
        String mode = Utils.determineLinkmode(cmdLine);
        if (mode != null && mode.equals("exec")) {
            this.doExecMode(cmdLine, null);
        } else {
            Object object = environmentLock;
            synchronized (object) {
                this.link = this.MLOpenString(cmdLine + " -linkoptions MLForceYield", errMsgOut);
            }
        }
        if (this.link == 0) {
            String msg = errMsgOut[0] != null ? errMsgOut[0] : "Link failed to open.";
            throw new MathLinkException(1004, msg);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public NativeLink(String[] argv) throws MathLinkException {
        NativeLink.loadNativeLibrary();
        String[] errMsgOut = new String[1];
        String mode = Utils.determineLinkmode(argv);
        String[] newArgv = new String[argv.length + 2];
        System.arraycopy(argv, 0, newArgv, 0, argv.length);
        newArgv[newArgv.length - 2] = "-linkoptions";
        newArgv[newArgv.length - 1] = "MLForceYield";
        if (mode != null && mode.equals("exec")) {
            this.doExecMode(null, newArgv);
        } else {
            Object object = environmentLock;
            synchronized (object) {
                this.link = this.MLOpen(newArgv.length, newArgv, errMsgOut);
            }
        }
        if (this.link == 0) {
            String msg = errMsgOut[0] != null ? errMsgOut[0] : "Link failed to open.";
            throw new MathLinkException(1004, msg);
        }
    }

    public NativeLink() {
        this(0);
    }

    public NativeLink(long mlinkPtr) {
        NativeLink.loadNativeLibrary();
        this.link = mlinkPtr;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doExecMode(String cmdLine, String[] argv) throws MathLinkException {
        String[] errMsgOut = new String[1];
        Object object = environmentLock;
        synchronized (object) {
            this.link = this.MLOpenString("-linkmode listen -linkprotocol tcp -linkoptions MLDontInteract", errMsgOut);
        }
        if (this.link == 0) {
            throw new MathLinkException(1004, "Link open failed for exec mode.");
        }
        try {
            String name = NativeLink.MLName(this.link);
            Process p = null;
            if (cmdLine != null) {
                int execPos = cmdLine.indexOf("-linkmode exec");
                String newCmdLine = cmdLine.substring(0, execPos) + cmdLine.substring(execPos + 14) + " -linkmode connect -linkprotocol tcp -linkname " + name;
                p = Runtime.getRuntime().exec(newCmdLine);
            } else {
                String[] newArgv = new String[argv.length + 4];
                System.arraycopy(argv, 0, newArgv, 0, argv.length);
                for (int i = 0; i < argv.length; ++i) {
                    if (!newArgv[i].equals("exec")) continue;
                    newArgv[i] = "connect";
                }
                newArgv[newArgv.length - 4] = "-linkprotocol";
                newArgv[newArgv.length - 3] = "tcp";
                newArgv[newArgv.length - 2] = "-linkname";
                newArgv[newArgv.length - 1] = name;
                p = Runtime.getRuntime().exec(newArgv);
            }
            if (p == null) {
                throw new MathLinkException(1004, "Process was null");
            }
        }
        catch (Exception e) {
            Object p = environmentLock;
            synchronized (p) {
                NativeLink.MLClose(this.link);
            }
            throw new MathLinkException(1004, e.toString());
        }
    }

    public synchronized long getLink() {
        return this.link;
    }

    public static void setEnvID(String idStr) {
        NativeLink.MLSetEnvIDString(idStr);
    }

    public synchronized String getLinkedEnvID() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        String idStr = NativeLink.MLGetLinkedEnvIDString(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
        return idStr;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void close() {
        if (this.link != 0) {
            Object object = environmentLock;
            synchronized (object) {
                NativeLink.MLClose(this.link);
            }
            this.link = 0;
        }
    }

    public synchronized void connect() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLConnect(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized String name() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        String res = NativeLink.MLName(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
        return res;
    }

    public synchronized void newPacket() {
        NativeLink.MLNewPacket(this.link);
    }

    public synchronized int nextPacket() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int pkt = NativeLink.MLNextPacket(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return pkt;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void endPacket() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLEndPacket(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized int error() {
        if (this.link == 0) {
            return 1000;
        }
        return NativeLink.MLError(this.link);
    }

    public synchronized boolean clearError() {
        if (this.link == 0) {
            return false;
        }
        return NativeLink.MLClearError(this.link);
    }

    public synchronized String errorMessage() {
        if (this.link == 0) {
            return "Link is not open.";
        }
        if (this.error() >= 1000) {
            return MathLinkException.lookupMessageText(this.error());
        }
        return NativeLink.MLErrorMessage(this.link);
    }

    public synchronized void setError(int err) {
        NativeLink.MLSetError(this.link, err);
    }

    public synchronized boolean ready() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        return NativeLink.MLReady(this.link);
    }

    public synchronized void flush() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLFlush(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized int getNext() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int type = NativeLink.MLGetNext(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return type;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized int getType() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int type = NativeLink.MLGetType(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return type;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void putNext(int type) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutNext(this.link, type);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized int getArgCount() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int argc = NativeLink.MLGetArgCount(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return argc;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void putArgCount(int argCount) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutArgCount(this.link, argCount);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized void putSize(int size) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutSize(this.link, size);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized int bytesToPut() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int res = NativeLink.MLBytesToPut(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
        return res;
    }

    public synchronized int bytesToGet() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int res = NativeLink.MLBytesToGet(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
        return res;
    }

    public synchronized void putData(byte[] data, int len) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutData(this.link, data, len);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized byte[] getData(int len) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        byte[] res = NativeLink.MLGetData(this.link, len);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
        return res;
    }

    public synchronized String getString() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        String s = NativeLink.MLGetString(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return s;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized byte[] getByteString(int missing) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        byte[] data = NativeLink.MLGetByteString(this.link, (byte)missing);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return data;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void putByteString(byte[] data) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutByteString(this.link, data, data.length);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized String getSymbol() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        String s = NativeLink.MLGetSymbol(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return s;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void putSymbol(String s) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutSymbol(this.link, s);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized void put(boolean b) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutSymbol(this.link, b ? "True" : "False");
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized int getInteger() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int i = NativeLink.MLGetInteger(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return i;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void put(int i) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutInteger(this.link, i);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized long getLongInteger() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        String s = NativeLink.MLGetString(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return Long.parseLong(s);
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void put(long i) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutNext(this.link, 43);
        byte[] bytes = String.valueOf(i).getBytes();
        NativeLink.MLPutSize(this.link, bytes.length);
        NativeLink.MLPutData(this.link, bytes, bytes.length);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized double getDouble() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        double d = NativeLink.MLGetDouble(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return d;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void put(double d) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        if (d == Double.POSITIVE_INFINITY) {
            NativeLink.MLPutSymbol(this.link, "Infinity");
        } else if (d == Double.NEGATIVE_INFINITY) {
            this.putFunction("DirectedInfinity", 1);
            NativeLink.MLPutInteger(this.link, -1);
        } else if (Double.isNaN(d)) {
            NativeLink.MLPutSymbol(this.link, "Indeterminate");
        } else {
            NativeLink.MLPutDouble(this.link, d);
        }
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized MLFunction getFunction() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int type = NativeLink.MLGetType(this.link);
        if (type == 0) {
            throw new MathLinkException(NativeLink.MLError(this.link), NativeLink.MLErrorMessage(this.link));
        }
        if (type != 70) {
            NativeLink.MLSetError(this.link, 3);
            throw new MathLinkException(NativeLink.MLError(this.link), NativeLink.MLErrorMessage(this.link));
        }
        int argc = NativeLink.MLGetArgCount(this.link);
        String head = NativeLink.MLGetSymbol(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return new MLFunction(head, argc);
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void putFunction(String f, int argCount) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutNext(this.link, 70);
        NativeLink.MLPutArgCount(this.link, argCount);
        NativeLink.MLPutSymbol(this.link, f);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized int checkFunction(String f) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int argCount = NativeLink.MLCheckFunction(this.link, f);
        int errCode = NativeLink.MLError(this.link);
        if (errCode == 0) {
            return argCount;
        }
        throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
    }

    public synchronized void checkFunctionWithArgCount(String f, int argCount) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        int res = NativeLink.MLCheckFunctionWithArgCount(this.link, f, argCount);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    public synchronized void transferExpression(MathLink source) throws MathLinkException {
        if (this.link == 0 || source == null) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        if (source instanceof NativeLink) {
            NativeLink.MLTransferExpression(this.link, ((NativeLink)source).link);
        } else if (source instanceof WrappedKernelLink) {
            this.transferExpression(((WrappedKernelLink)source).getMathLink());
        } else {
            this.put(source.getExpr());
        }
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
        errCode = source.error();
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, source.errorMessage());
        }
    }

    public synchronized void transferToEndOfLoopbackLink(LoopbackLink source) throws MathLinkException {
        if (this.link == 0 || source == null) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        if (source instanceof NativeLoopbackLink) {
            NativeLink.MLTransferToEndOfLoopbackLink(this.link, ((NativeLoopbackLink)source).getLink());
        } else {
            while (source.ready()) {
                this.transferExpression(source);
            }
        }
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
        errCode = source.error();
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, source.errorMessage());
        }
    }

    public int getMessage() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        return NativeLink.MLGetMessage(this.link);
    }

    public void putMessage(int msg) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutMessage(this.link, msg);
    }

    public boolean messageReady() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        return NativeLink.MLMessageReady(this.link);
    }

    public synchronized long createMark() throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        long mark = NativeLink.MLCreateMark(this.link);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
        if (mark == 0) {
            throw new MathLinkException(8, "Not enough memory to create Mark");
        }
        return mark;
    }

    public synchronized void seekMark(long mark) {
        if (this.link == 0) {
            return;
        }
        NativeLink.MLSeekMark(this.link, mark);
    }

    public synchronized void destroyMark(long mark) {
        if (this.link == 0) {
            return;
        }
        NativeLink.MLDestroyMark(this.link, mark);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean setYieldFunction(Class cls, Object target, String methName) {
        Object object = this.yieldFunctionLock;
        synchronized (object) {
            boolean res = super.setYieldFunction(cls, target, methName);
            boolean destroyYielder = methName == null || !res;
            NativeLink.MLSetYieldFunction(this.link, destroyYielder);
            return res;
        }
    }

    public synchronized boolean addMessageHandler(Class cls, Object target, String methName) {
        boolean result = super.addMessageHandler(cls, target, methName);
        if (result) {
            NativeLink.MLSetMessageHandler(this.link);
        }
        return result;
    }

    public synchronized Object getArray(int type, int depth) throws MathLinkException {
        return this.getArray(type, depth, null);
    }

    public synchronized Object getArray(int type, int depth, String[] heads) throws MathLinkException {
        Object resArray = null;
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        if (!(type != -5 && type != -8 && type != -2 && type != -3 && type != -4 && type != -7 || depth != 1 && Utils.isRaggedArrays())) {
            resArray = NativeLink.MLGetArray(this.link, type, depth, heads);
            int errCode = NativeLink.MLError(this.link);
            if (errCode > 1000) {
                throw new MathLinkException(errCode);
            }
            if (errCode != 0) {
                throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
            }
        } else {
            resArray = super.getArray(type, depth, heads);
        }
        return resArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void finalize() throws Throwable {
        try {
            Object.super.finalize();
        }
        finally {
            if (!jvmIsShuttingDown) {
                this.close();
            }
        }
    }

    protected static final boolean isException(int errCode) {
        return errCode != 0 && errCode != 10;
    }

    protected void putString(String s) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        NativeLink.MLPutString(this.link, s);
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    protected void putArray(Object obj, String[] heads) throws MathLinkException {
        if (this.link == 0) {
            throw new MathLinkException(1000, "Link is not open.");
        }
        Class objClass = obj.getClass();
        Class elementClass = Utils.getArrayComponentType(objClass);
        int type = 0;
        if (elementClass.isPrimitive()) {
            if (elementClass.equals(Integer.TYPE)) {
                type = -5;
            } else if (elementClass.equals(Double.TYPE)) {
                type = -8;
            } else if (elementClass.equals(Byte.TYPE)) {
                type = -2;
            } else if (elementClass.equals(Character.TYPE)) {
                type = -3;
            } else if (elementClass.equals(Short.TYPE)) {
                type = -4;
            } else if (elementClass.equals(Long.TYPE)) {
                type = -6;
            } else if (elementClass.equals(Float.TYPE)) {
                type = -7;
            } else if (elementClass.equals(Boolean.TYPE)) {
                type = -1;
            }
        } else if (elementClass.equals(String.class)) {
            type = -9;
        }
        boolean anyDimsZero = false;
        int[] dims = Utils.getArrayDims(obj);
        for (int i = 0; i < dims.length; ++i) {
            if (dims[i] != 0) continue;
            anyDimsZero = true;
        }
        if (type != 0) {
            boolean sent = false;
            int depth = objClass.getName().lastIndexOf(91) + 1;
            if (depth == 1) {
                NativeLink.MLPutArray(this.link, type, obj, heads != null ? heads[0] : "List");
                sent = true;
            } else if (depth > 1 && type != -9 && type != -1 && NativeLink.nativeSizesMatch(type) && !anyDimsZero) {
                int flatLen = 1;
                for (int i = 0; i < dims.length; ++i) {
                    flatLen *= dims[i];
                }
                Object flatArray = type == -2 ? Array.newInstance(Short.TYPE, flatLen) : (type == -3 ? Array.newInstance(Integer.TYPE, flatLen) : Array.newInstance(elementClass, flatLen));
                try {
                    NativeLink.flattenInto(obj, dims, 0, flatArray, 0, type);
                    NativeLink.MLPutArrayFlat(this.link, type, flatArray, heads, dims);
                    sent = true;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    // empty catch block
                }
            }
            if (!sent) {
                String[] explicitHeads;
                if (heads != null && heads.length == depth) {
                    explicitHeads = heads;
                } else {
                    explicitHeads = new String[depth];
                    for (int i = 0; i < depth; ++i) {
                        explicitHeads[i] = heads != null && i < heads.length ? heads[i] : "List";
                    }
                }
                this.putArraySlices(obj, type, explicitHeads, 0);
            }
        } else {
            this.putArrayPiecemeal(obj, heads, 0);
        }
        int errCode = NativeLink.MLError(this.link);
        if (NativeLink.isException(errCode)) {
            throw new MathLinkException(errCode, NativeLink.MLErrorMessage(this.link));
        }
    }

    private static void flattenInto(Object source, int[] sourceDims, int sourceDimsIndex, Object dest, int destIndex, int type) {
        int numElements = sourceDims[sourceDimsIndex];
        if (Array.getLength(source) != numElements) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (sourceDimsIndex == sourceDims.length - 1) {
            if (type == -2) {
                byte[] ba = (byte[])source;
                for (int i = 0; i < numElements; ++i) {
                    Array.setShort(dest, destIndex++, ba[i]);
                }
            } else if (type == -3) {
                char[] ca = (char[])source;
                for (int i = 0; i < numElements; ++i) {
                    Array.setInt(dest, destIndex++, ca[i]);
                }
            } else {
                System.arraycopy(source, 0, dest, destIndex, numElements);
            }
        } else {
            int i;
            int destIndexIncrement = 1;
            for (i = sourceDimsIndex + 1; i < sourceDims.length; ++i) {
                destIndexIncrement *= sourceDims[i];
            }
            for (i = 0; i < numElements; ++i) {
                NativeLink.flattenInto(Array.get(source, i), sourceDims, sourceDimsIndex + 1, dest, destIndex + destIndexIncrement * i, type);
            }
        }
    }

    private void putArraySlices(Object obj, int type, String[] heads, int headIndex) throws MathLinkException {
        if (headIndex == heads.length - 1) {
            NativeLink.MLPutArray(this.link, type, obj, heads[headIndex]);
        } else {
            int len = Array.getLength(obj);
            this.putFunction(heads[headIndex], len);
            for (int i = 0; i < len; ++i) {
                this.putArraySlices(Array.get(obj, i), type, heads, headIndex + 1);
            }
        }
    }

    private static boolean loadNativeLib(String libName, String libDir) throws SecurityException {
        if (!libDir.endsWith(File.separator)) {
            libDir = libDir + File.separator;
        }
        String actualLibName = System.mapLibraryName(libName);
        String fullLibPath = libDir + actualLibName;
        try {
            System.load(fullLibPath);
            return true;
        }
        catch (UnsatisfiedLinkError e) {
            String[] systemIDs = Utils.getSystemID();
            for (int i = 0; i < systemIDs.length; ++i) {
                fullLibPath = libDir + "SystemFiles" + File.separator + "Libraries" + File.separator + systemIDs[i] + File.separator + actualLibName;
                try {
                    System.load(fullLibPath);
                    return true;
                }
                catch (UnsatisfiedLinkError ee) {
                    continue;
                }
            }
            return false;
        }
    }

    private boolean nativeYielderCallback(boolean ignore) {
        boolean backOut = this.yielderCallback();
        return backOut;
    }

    private void nativeMessageCallback(int message, int n) {
        this.messageCallback(message, n);
    }

    protected static native void MLInitialize();

    protected native long MLOpenString(String var1, String[] var2);

    protected native long MLOpen(int var1, String[] var2, String[] var3);

    protected static native long MLLoopbackOpen(String[] var0);

    protected static native void MLSetEnvIDString(String var0);

    protected static native String MLGetLinkedEnvIDString(long var0);

    protected static native void MLConnect(long var0);

    protected static native void MLClose(long var0);

    protected static native String MLName(long var0);

    protected static native void MLNewPacket(long var0);

    protected static native int MLNextPacket(long var0);

    protected static native void MLEndPacket(long var0);

    protected static native int MLError(long var0);

    protected static native boolean MLClearError(long var0);

    protected static native String MLErrorMessage(long var0);

    protected static native void MLSetError(long var0, int var2);

    protected static native boolean MLReady(long var0);

    protected static native void MLFlush(long var0);

    protected static native int MLGetNext(long var0);

    protected static native int MLGetType(long var0);

    protected static native void MLPutNext(long var0, int var2);

    protected static native int MLGetArgCount(long var0);

    protected static native void MLPutArgCount(long var0, int var2);

    protected static native void MLPutData(long var0, byte[] var2, int var3);

    protected static native void MLPutSize(long var0, int var2);

    protected static native byte[] MLGetData(long var0, int var2);

    protected static native int MLBytesToGet(long var0);

    protected static native int MLBytesToPut(long var0);

    protected static native String MLGetString(long var0);

    protected static native void MLPutString(long var0, String var2);

    protected static native byte[] MLGetByteString(long var0, byte var2);

    protected static native void MLPutByteString(long var0, byte[] var2, int var3);

    protected static native String MLGetSymbol(long var0);

    protected static native void MLPutSymbol(long var0, String var2);

    protected static native int MLGetInteger(long var0);

    protected static native void MLPutInteger(long var0, int var2);

    protected static native double MLGetDouble(long var0);

    protected static native void MLPutDouble(long var0, double var2);

    protected static native void MLPutArray(long var0, int var2, Object var3, String var4);

    protected static native void MLPutArrayFlat(long var0, int var2, Object var3, String[] var4, int[] var5);

    protected static native Object MLGetArray(long var0, int var2, int var3, String[] var4);

    protected static native int MLCheckFunction(long var0, String var2);

    protected static native int MLCheckFunctionWithArgCount(long var0, String var2, int var3);

    protected static native void MLTransferExpression(long var0, long var2);

    protected static native void MLTransferToEndOfLoopbackLink(long var0, long var2);

    protected static native int MLGetMessage(long var0);

    protected static native void MLPutMessage(long var0, int var2);

    protected static native boolean MLMessageReady(long var0);

    protected static native long MLCreateMark(long var0);

    protected static native void MLSeekMark(long var0, long var2);

    protected static native void MLDestroyMark(long var0, long var2);

    protected static native void MLSetYieldFunction(long var0, boolean var2);

    protected static native void MLSetMessageHandler(long var0);

    protected static native boolean nativeSizesMatch(int var0);

    static native void hideJavaWindow();

    static native void macJavaLayerToFront();

    static native void winJavaLayerToFront(boolean var0);

    static native void mathematicaToFront();

    static native long getNativeWindowHandle(Window var0, String var1);

    public static native int killProcess(long var0);

    public static native void appToFront(long var0);

    private static class JLinkShutdownThread
    extends Thread {
        public JLinkShutdownThread() {
            this.setName("J/Link Shutdown Hook Thread");
        }

        public void run() {
            NativeLink.jvmIsShuttingDown = true;
        }
    }

}

