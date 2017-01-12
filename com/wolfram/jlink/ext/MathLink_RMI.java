/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ext;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.LoopbackLink;
import com.wolfram.jlink.MLFunction;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkImpl;
import com.wolfram.jlink.Utils;
import com.wolfram.jlink.ext.RemoteMathLink;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;

public class MathLink_RMI
extends MathLinkImpl {
    private RemoteMathLink remoteLink;

    public MathLink_RMI(String cmdLine) throws NotBoundException, MalformedURLException, UnknownHostException, RemoteException {
        this.remoteLink = (RemoteMathLink)Naming.lookup(Utils.determineLinkname(cmdLine));
    }

    public MathLink_RMI(String[] argv) throws NotBoundException, MalformedURLException, UnknownHostException, RemoteException {
        this.remoteLink = (RemoteMathLink)Naming.lookup(Utils.determineLinkname(argv));
    }

    public synchronized void close() {
        try {
            this.remoteLink.close();
        }
        catch (RemoteException e) {
            // empty catch block
        }
    }

    public synchronized void connect() throws MathLinkException {
        try {
            this.remoteLink.connect();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void activate() throws MathLinkException {
        this.connect();
    }

    public String name() {
        return "";
    }

    public synchronized void newPacket() {
        try {
            this.remoteLink.newPacket();
        }
        catch (RemoteException e) {
            // empty catch block
        }
    }

    public synchronized int nextPacket() throws MathLinkException {
        try {
            return this.remoteLink.nextPacket();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void endPacket() throws MathLinkException {
        try {
            this.remoteLink.endPacket();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized int error() {
        try {
            return this.remoteLink.error();
        }
        catch (RemoteException e) {
            return 1000;
        }
    }

    public synchronized boolean clearError() {
        try {
            return this.remoteLink.clearError();
        }
        catch (RemoteException e) {
            return false;
        }
    }

    public synchronized String errorMessage() {
        try {
            return this.remoteLink.errorMessage();
        }
        catch (RemoteException e) {
            return "RMI RemoteException occurred: " + e.toString();
        }
    }

    public synchronized void setError(int err) {
        try {
            this.remoteLink.setError(err);
        }
        catch (RemoteException e) {
            // empty catch block
        }
    }

    public synchronized boolean ready() throws MathLinkException {
        try {
            return this.remoteLink.ready();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void flush() throws MathLinkException {
        try {
            this.remoteLink.flush();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized int getNext() throws MathLinkException {
        try {
            return this.remoteLink.getNext();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized int getType() throws MathLinkException {
        try {
            return this.remoteLink.getType();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void putNext(int type) throws MathLinkException {
        try {
            this.remoteLink.putNext(type);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized int getArgCount() throws MathLinkException {
        try {
            return this.remoteLink.getArgCount();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void putArgCount(int argCount) throws MathLinkException {
        try {
            this.remoteLink.putArgCount(argCount);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void putSize(int size) throws MathLinkException {
        try {
            this.remoteLink.putSize(size);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized int bytesToPut() throws MathLinkException {
        try {
            return this.remoteLink.bytesToPut();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized int bytesToGet() throws MathLinkException {
        try {
            return this.remoteLink.bytesToGet();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void putData(byte[] data, int len) throws MathLinkException {
        try {
            this.remoteLink.putData(data, len);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized byte[] getData(int len) throws MathLinkException {
        try {
            return this.remoteLink.getData(len);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized String getString() throws MathLinkException {
        try {
            return this.remoteLink.getString();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized byte[] getByteString(int missing) throws MathLinkException {
        try {
            return this.remoteLink.getByteString(missing);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void putByteString(byte[] data) throws MathLinkException {
        try {
            this.remoteLink.putByteString(data);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized String getSymbol() throws MathLinkException {
        try {
            return this.remoteLink.getSymbol();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void putSymbol(String s) throws MathLinkException {
        try {
            this.remoteLink.putSymbol(s);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void put(boolean b) throws MathLinkException {
        try {
            this.remoteLink.put(b);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized int getInteger() throws MathLinkException {
        try {
            return this.remoteLink.getInteger();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void put(int i) throws MathLinkException {
        try {
            this.remoteLink.put(i);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized long getLongInteger() throws MathLinkException {
        try {
            return this.remoteLink.getLongInteger();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void put(long i) throws MathLinkException {
        try {
            this.remoteLink.put(i);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized double getDouble() throws MathLinkException {
        try {
            return this.remoteLink.getDouble();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void put(double d) throws MathLinkException {
        try {
            this.remoteLink.put(d);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized MLFunction getFunction() throws MathLinkException {
        try {
            return this.remoteLink.getFunction();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void putFunction(String f, int argCount) throws MathLinkException {
        try {
            this.remoteLink.putFunction(f, argCount);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized int checkFunction(String f) throws MathLinkException {
        try {
            return this.remoteLink.checkFunction(f);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void checkFunctionWithArgCount(String f, int argCount) throws MathLinkException {
        try {
            this.remoteLink.checkFunctionWithArgCount(f, argCount);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void transferExpression(MathLink source) throws MathLinkException {
        try {
            this.remoteLink.put(source.getExpr());
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void transferToEndOfLoopbackLink(LoopbackLink source) throws MathLinkException {
        try {
            while (source.ready()) {
                this.remoteLink.put(source.getExpr());
            }
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized Expr getExpr() throws MathLinkException {
        try {
            return this.remoteLink.getExpr();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized Expr peekExpr() throws MathLinkException {
        try {
            return this.remoteLink.peekExpr();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public int getMessage() throws MathLinkException {
        try {
            return this.remoteLink.getMessage();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public void putMessage(int msg) throws MathLinkException {
        try {
            this.remoteLink.putMessage(msg);
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public boolean messageReady() throws MathLinkException {
        try {
            return this.remoteLink.messageReady();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized long createMark() throws MathLinkException {
        try {
            return this.remoteLink.createMark();
        }
        catch (RemoteException e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void seekMark(long mark) {
        try {
            this.remoteLink.seekMark(mark);
        }
        catch (RemoteException e) {
            // empty catch block
        }
    }

    public synchronized void destroyMark(long mark) {
        try {
            this.remoteLink.destroyMark(mark);
        }
        catch (RemoteException e) {
            // empty catch block
        }
    }

    public synchronized void put(Object obj) throws MathLinkException {
        try {
            this.remoteLink.put(obj);
        }
        catch (Exception e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized void put(Object obj, String[] heads) throws MathLinkException {
        try {
            this.remoteLink.put(obj, heads);
        }
        catch (Exception e) {
            throw new MathLinkException(e);
        }
    }

    public synchronized boolean setYieldFunction(Class cls, Object target, String methName) {
        boolean res = super.setYieldFunction(cls, target, methName);
        if (res) {
            // empty if block
        }
        return res;
    }

    public synchronized boolean addMessageHandler(Class cls, Object target, String methName) {
        boolean result = super.addMessageHandler(cls, target, methName);
        if (result) {
            // empty if block
        }
        return result;
    }

    public synchronized Object getArray(int type, int depth) throws MathLinkException {
        return this.getArray(type, depth, null);
    }

    public synchronized Object getArray(int type, int depth, String[] heads) throws MathLinkException {
        try {
            return this.remoteLink.getArray(type, depth, heads);
        }
        catch (Exception e) {
            throw new MathLinkException(e);
        }
    }

    protected void finalize() throws Throwable {
        Object.super.finalize();
        this.close();
    }

    protected void putArray(Object obj, String[] heads) throws MathLinkException {
    }

    protected void putString(String s) throws MathLinkException {
        try {
            this.remoteLink.sendString(s);
        }
        catch (Exception e) {
            throw new MathLinkException(e);
        }
    }
}

