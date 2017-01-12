/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ext;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.MLFunction;
import com.wolfram.jlink.MathLinkException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteMathLink
extends Remote {
    public void close() throws RemoteException;

    public void connect() throws MathLinkException, RemoteException;

    public void activate() throws MathLinkException, RemoteException;

    public void newPacket() throws RemoteException;

    public int nextPacket() throws MathLinkException, RemoteException;

    public void endPacket() throws MathLinkException, RemoteException;

    public int error() throws RemoteException;

    public boolean clearError() throws RemoteException;

    public String errorMessage() throws RemoteException;

    public void setError(int var1) throws RemoteException;

    public boolean ready() throws MathLinkException, RemoteException;

    public void flush() throws MathLinkException, RemoteException;

    public int getNext() throws MathLinkException, RemoteException;

    public int getType() throws MathLinkException, RemoteException;

    public void putNext(int var1) throws MathLinkException, RemoteException;

    public int getArgCount() throws MathLinkException, RemoteException;

    public void putArgCount(int var1) throws MathLinkException, RemoteException;

    public void putSize(int var1) throws MathLinkException, RemoteException;

    public int bytesToPut() throws MathLinkException, RemoteException;

    public int bytesToGet() throws MathLinkException, RemoteException;

    public void putData(byte[] var1) throws MathLinkException, RemoteException;

    public void putData(byte[] var1, int var2) throws MathLinkException, RemoteException;

    public byte[] getData(int var1) throws MathLinkException, RemoteException;

    public String getString() throws MathLinkException, RemoteException;

    public byte[] getByteString(int var1) throws MathLinkException, RemoteException;

    public void putByteString(byte[] var1) throws MathLinkException, RemoteException;

    public String getSymbol() throws MathLinkException, RemoteException;

    public void putSymbol(String var1) throws MathLinkException, RemoteException;

    public void sendString(String var1) throws MathLinkException, RemoteException;

    public void put(boolean var1) throws MathLinkException, RemoteException;

    public int getInteger() throws MathLinkException, RemoteException;

    public void put(int var1) throws MathLinkException, RemoteException;

    public long getLongInteger() throws MathLinkException, RemoteException;

    public void put(long var1) throws MathLinkException, RemoteException;

    public double getDouble() throws MathLinkException, RemoteException;

    public void put(double var1) throws MathLinkException, RemoteException;

    public boolean[] getBooleanArray1() throws MathLinkException, RemoteException;

    public boolean[][] getBooleanArray2() throws MathLinkException, RemoteException;

    public byte[] getByteArray1() throws MathLinkException, RemoteException;

    public byte[][] getByteArray2() throws MathLinkException, RemoteException;

    public char[] getCharArray1() throws MathLinkException, RemoteException;

    public char[][] getCharArray2() throws MathLinkException, RemoteException;

    public short[] getShortArray1() throws MathLinkException, RemoteException;

    public short[][] getShortArray2() throws MathLinkException, RemoteException;

    public int[] getIntArray1() throws MathLinkException, RemoteException;

    public int[][] getIntArray2() throws MathLinkException, RemoteException;

    public long[] getLongArray1() throws MathLinkException, RemoteException;

    public long[][] getLongArray2() throws MathLinkException, RemoteException;

    public float[] getFloatArray1() throws MathLinkException, RemoteException;

    public float[][] getFloatArray2() throws MathLinkException, RemoteException;

    public double[] getDoubleArray1() throws MathLinkException, RemoteException;

    public double[][] getDoubleArray2() throws MathLinkException, RemoteException;

    public String[] getStringArray1() throws MathLinkException, RemoteException;

    public String[][] getStringArray2() throws MathLinkException, RemoteException;

    public Object[] getComplexArray1() throws MathLinkException, RemoteException;

    public Object[][] getComplexArray2() throws MathLinkException, RemoteException;

    public Object getArray(int var1, int var2, String[] var3) throws MathLinkException, RemoteException;

    public MLFunction getFunction() throws MathLinkException, RemoteException;

    public void putFunction(String var1, int var2) throws MathLinkException, RemoteException;

    public int checkFunction(String var1) throws MathLinkException, RemoteException;

    public void checkFunctionWithArgCount(String var1, int var2) throws MathLinkException, RemoteException;

    public int getMessage() throws MathLinkException, RemoteException;

    public void putMessage(int var1) throws MathLinkException, RemoteException;

    public boolean messageReady() throws MathLinkException, RemoteException;

    public long createMark() throws MathLinkException, RemoteException;

    public void seekMark(long var1) throws RemoteException;

    public void destroyMark(long var1) throws RemoteException;

    public Expr getExpr() throws MathLinkException, RemoteException;

    public Expr peekExpr() throws MathLinkException, RemoteException;

    public Object getComplex() throws MathLinkException, RemoteException;

    public void put(Object var1) throws MathLinkException, RemoteException;

    public void put(Object var1, String[] var2) throws MathLinkException, RemoteException;

    public boolean setComplexClass(Class var1) throws RemoteException;

    public Class getComplexClass() throws RemoteException;

    public boolean setYieldFunction(Class var1, Object var2, String var3) throws RemoteException;

    public boolean addMessageHandler(Class var1, Object var2, String var3) throws RemoteException;

    public boolean removeMessageHandler(String var1) throws RemoteException;
}

