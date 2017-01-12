/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.KernelLinkImpl;
import com.wolfram.jlink.LoopbackLink;
import com.wolfram.jlink.MLFunction;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.NativeLink;
import com.wolfram.jlink.Utils;
import java.awt.Window;

public class WrappedKernelLink
extends KernelLinkImpl
implements KernelLink {
    protected MathLink impl;
    private boolean linkConnected = false;
    private static long nextWindowID = 1;

    public WrappedKernelLink() {
        this(null);
    }

    public WrappedKernelLink(MathLink ml) {
        this.setMathLink(ml);
        if (ml != null) {
            this.addMessageHandler(null, this, "msgHandler");
        }
    }

    public MathLink getMathLink() {
        return this.impl;
    }

    public void setMathLink(MathLink ml) {
        this.impl = ml;
    }

    public synchronized void close() {
        this.impl.close();
    }

    public synchronized void connect() throws MathLinkException {
        this.impl.connect();
    }

    public synchronized String name() throws MathLinkException {
        return this.impl.name();
    }

    public synchronized void newPacket() {
        this.impl.newPacket();
    }

    public synchronized void endPacket() throws MathLinkException {
        this.impl.endPacket();
    }

    public synchronized int error() {
        return this.impl.error();
    }

    public synchronized boolean clearError() {
        return this.impl.clearError();
    }

    public synchronized String errorMessage() {
        return this.impl.errorMessage();
    }

    public synchronized void setError(int err) {
        this.impl.setError(err);
    }

    public synchronized boolean ready() throws MathLinkException {
        return this.impl.ready();
    }

    public synchronized void flush() throws MathLinkException {
        this.impl.flush();
    }

    public synchronized void putNext(int type) throws MathLinkException {
        this.impl.putNext(type);
    }

    public synchronized int getArgCount() throws MathLinkException {
        return this.impl.getArgCount();
    }

    public synchronized void putArgCount(int argCount) throws MathLinkException {
        this.impl.putArgCount(argCount);
    }

    public synchronized void putSize(int size) throws MathLinkException {
        this.impl.putSize(size);
    }

    public synchronized int bytesToPut() throws MathLinkException {
        return this.impl.bytesToPut();
    }

    public synchronized int bytesToGet() throws MathLinkException {
        return this.impl.bytesToGet();
    }

    public synchronized void putData(byte[] data, int len) throws MathLinkException {
        this.impl.putData(data, len);
    }

    public synchronized byte[] getData(int len) throws MathLinkException {
        return this.impl.getData(len);
    }

    public synchronized String getString() throws MathLinkException {
        return this.impl.getString();
    }

    public synchronized byte[] getByteString(int missing) throws MathLinkException {
        return this.impl.getByteString(missing);
    }

    public synchronized void putByteString(byte[] data) throws MathLinkException {
        this.impl.putByteString(data);
    }

    public synchronized String getSymbol() throws MathLinkException {
        return this.impl.getSymbol();
    }

    public synchronized void putSymbol(String s) throws MathLinkException {
        this.impl.putSymbol(s);
    }

    public synchronized void put(boolean b) throws MathLinkException {
        this.impl.put(b);
    }

    public synchronized int getInteger() throws MathLinkException {
        return this.impl.getInteger();
    }

    public synchronized void put(int i) throws MathLinkException {
        this.impl.put(i);
    }

    public synchronized long getLongInteger() throws MathLinkException {
        return this.impl.getLongInteger();
    }

    public synchronized void put(long i) throws MathLinkException {
        this.impl.put(i);
    }

    public synchronized double getDouble() throws MathLinkException {
        return this.impl.getDouble();
    }

    public synchronized void put(double d) throws MathLinkException {
        this.impl.put(d);
    }

    public synchronized MLFunction getFunction() throws MathLinkException {
        return this.impl.getFunction();
    }

    public synchronized void putFunction(String f, int argCount) throws MathLinkException {
        this.impl.putFunction(f, argCount);
    }

    public synchronized int checkFunction(String f) throws MathLinkException {
        return this.impl.checkFunction(f);
    }

    public synchronized void checkFunctionWithArgCount(String f, int argCount) throws MathLinkException {
        this.impl.checkFunctionWithArgCount(f, argCount);
    }

    public synchronized void transferExpression(MathLink source) throws MathLinkException {
        this.impl.transferExpression(source);
    }

    public synchronized void transferToEndOfLoopbackLink(LoopbackLink source) throws MathLinkException {
        this.impl.transferToEndOfLoopbackLink(source);
    }

    public synchronized Expr getExpr() throws MathLinkException {
        return this.impl.getExpr();
    }

    public synchronized Expr peekExpr() throws MathLinkException {
        return this.impl.peekExpr();
    }

    public int getMessage() throws MathLinkException {
        return this.impl.getMessage();
    }

    public void putMessage(int msg) throws MathLinkException {
        this.impl.putMessage(msg);
    }

    public boolean messageReady() throws MathLinkException {
        return this.impl.messageReady();
    }

    public synchronized long createMark() throws MathLinkException {
        return this.impl.createMark();
    }

    public synchronized void seekMark(long mark) {
        this.impl.seekMark(mark);
    }

    public synchronized void destroyMark(long mark) {
        this.impl.destroyMark(mark);
    }

    public boolean setYieldFunction(Class cls, Object target, String methName) {
        return this.impl.setYieldFunction(cls, target, methName);
    }

    public synchronized boolean addMessageHandler(Class cls, Object target, String methName) {
        return this.impl.addMessageHandler(cls, target, methName);
    }

    public synchronized boolean removeMessageHandler(String methName) {
        return this.impl.removeMessageHandler(methName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized int nextPacket() throws MathLinkException {
        int pkt;
        block11 : {
            if (!this.linkConnected) {
                this.connect();
                this.linkConnected = true;
            }
            long mark = this.createMark();
            try {
                pkt = this.impl.nextPacket();
            }
            catch (MathLinkException e) {
                if (e.getErrCode() == 23) {
                    this.clearError();
                    this.seekMark(mark);
                    MLFunction f = this.getFunction();
                    if (f.name.equals("ExpressionPacket")) {
                        pkt = 101;
                    } else if (f.name.equals("BoxData")) {
                        this.seekMark(mark);
                        pkt = 101;
                    } else {
                        this.seekMark(mark);
                        pkt = 100;
                    }
                    break block11;
                }
                throw e;
            }
            finally {
                this.destroyMark(mark);
            }
        }
        return pkt;
    }

    public synchronized int getNext() throws MathLinkException {
        int result = this.impl.getNext();
        if (result == 35 && this.isObject()) {
            result = 100000;
        }
        return result;
    }

    public synchronized int getType() throws MathLinkException {
        int result = this.impl.getType();
        if (result == 35 && this.isObject()) {
            result = 100000;
        }
        return result;
    }

    public synchronized Object getArray(int type, int depth) throws MathLinkException {
        return this.getArray(type, depth, null);
    }

    public synchronized Object getArray(int type, int depth, String[] heads) throws MathLinkException {
        if (type == -13 || type == -14) {
            return super.getArray(type, depth, heads);
        }
        return this.impl.getArray(type, depth, heads);
    }

    protected void putString(String s) throws MathLinkException {
        this.impl.put(s);
    }

    protected void putArray(Object obj, String[] heads) throws MathLinkException {
        Class elementClass = Utils.getArrayComponentType(obj.getClass());
        if (elementClass.isPrimitive() || elementClass.equals(String.class)) {
            this.impl.put(obj, heads);
        } else {
            this.putArrayPiecemeal(obj, heads, 0);
        }
    }

    protected void showInFront() throws MathLinkException {
        if (this.impl instanceof NativeLink) {
            NativeLink.winJavaLayerToFront(true);
            NativeLink.macJavaLayerToFront();
        }
        super.showInFront();
        if (this.impl instanceof NativeLink) {
            NativeLink.winJavaLayerToFront(false);
        }
    }

    protected long getNativeWindowHandle(Window obj) {
        if (this.impl instanceof NativeLink && Utils.isWindows()) {
            return NativeLink.getNativeWindowHandle(obj, System.getProperty("java.home"));
        }
        return nextWindowID++;
    }
}

