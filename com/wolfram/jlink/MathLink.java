/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.LoopbackLink;
import com.wolfram.jlink.MLFunction;
import com.wolfram.jlink.MathLinkException;

public interface MathLink {
    public static final int ILLEGALPKT = 0;
    public static final int CALLPKT = 7;
    public static final int EVALUATEPKT = 13;
    public static final int RETURNPKT = 3;
    public static final int INPUTNAMEPKT = 8;
    public static final int ENTERTEXTPKT = 14;
    public static final int ENTEREXPRPKT = 15;
    public static final int OUTPUTNAMEPKT = 9;
    public static final int RETURNTEXTPKT = 4;
    public static final int RETURNEXPRPKT = 16;
    public static final int DISPLAYPKT = 11;
    public static final int DISPLAYENDPKT = 12;
    public static final int MESSAGEPKT = 5;
    public static final int TEXTPKT = 2;
    public static final int INPUTPKT = 1;
    public static final int INPUTSTRPKT = 21;
    public static final int MENUPKT = 6;
    public static final int SYNTAXPKT = 10;
    public static final int SUSPENDPKT = 17;
    public static final int RESUMEPKT = 18;
    public static final int BEGINDLGPKT = 19;
    public static final int ENDDLGPKT = 20;
    public static final int FIRSTUSERPKT = 128;
    public static final int LASTUSERPKT = 255;
    public static final int FEPKT = 100;
    public static final int EXPRESSIONPKT = 101;
    public static final int MLTERMINATEMESSAGE = 1;
    public static final int MLINTERRUPTMESSAGE = 2;
    public static final int MLABORTMESSAGE = 3;
    public static final int MLAUTHENTICATEFAILURE = 10;
    public static final int MLTKFUNC = 70;
    public static final int MLTKSTR = 34;
    public static final int MLTKSYM = 35;
    public static final int MLTKREAL = 42;
    public static final int MLTKINT = 43;
    public static final int MLTKERR = 0;
    public static final int MLEOK = 0;
    public static final int MLEUSER = 1000;
    public static final int MLE_NON_ML_ERROR = 1000;
    public static final int MLE_LINK_IS_NULL = 1000;
    public static final int MLE_OUT_OF_MEMORY = 1001;
    public static final int MLE_ARRAY_TOO_SHALLOW = 1002;
    public static final int MLE_BAD_COMPLEX = 1003;
    public static final int MLE_CREATION_FAILED = 1004;
    public static final int MLE_CONNECT_TIMEOUT = 1005;
    public static final int MLE_WRAPPED_EXCEPTION = 1006;
    public static final int MLE_FIRST_USER_EXCEPTION = 2000;
    public static final int TYPE_BOOLEAN = -1;
    public static final int TYPE_BYTE = -2;
    public static final int TYPE_CHAR = -3;
    public static final int TYPE_SHORT = -4;
    public static final int TYPE_INT = -5;
    public static final int TYPE_LONG = -6;
    public static final int TYPE_FLOAT = -7;
    public static final int TYPE_DOUBLE = -8;
    public static final int TYPE_STRING = -9;
    public static final int TYPE_BIGINTEGER = -10;
    public static final int TYPE_BIGDECIMAL = -11;
    public static final int TYPE_EXPR = -12;
    public static final int TYPE_COMPLEX = -13;

    public void close();

    public void connect() throws MathLinkException;

    public void connect(long var1) throws MathLinkException;

    public void activate() throws MathLinkException;

    public String name() throws MathLinkException;

    public void newPacket();

    public int nextPacket() throws MathLinkException;

    public void endPacket() throws MathLinkException;

    public int error();

    public boolean clearError();

    public String errorMessage();

    public void setError(int var1);

    public boolean ready() throws MathLinkException;

    public void flush() throws MathLinkException;

    public int getNext() throws MathLinkException;

    public int getType() throws MathLinkException;

    public void putNext(int var1) throws MathLinkException;

    public int getArgCount() throws MathLinkException;

    public void putArgCount(int var1) throws MathLinkException;

    public void putSize(int var1) throws MathLinkException;

    public int bytesToPut() throws MathLinkException;

    public int bytesToGet() throws MathLinkException;

    public void putData(byte[] var1) throws MathLinkException;

    public void putData(byte[] var1, int var2) throws MathLinkException;

    public byte[] getData(int var1) throws MathLinkException;

    public String getString() throws MathLinkException;

    public byte[] getByteString(int var1) throws MathLinkException;

    public void putByteString(byte[] var1) throws MathLinkException;

    public String getSymbol() throws MathLinkException;

    public void putSymbol(String var1) throws MathLinkException;

    public boolean getBoolean() throws MathLinkException;

    public void put(boolean var1) throws MathLinkException;

    public int getInteger() throws MathLinkException;

    public void put(int var1) throws MathLinkException;

    public long getLongInteger() throws MathLinkException;

    public void put(long var1) throws MathLinkException;

    public double getDouble() throws MathLinkException;

    public void put(double var1) throws MathLinkException;

    public boolean[] getBooleanArray1() throws MathLinkException;

    public boolean[][] getBooleanArray2() throws MathLinkException;

    public byte[] getByteArray1() throws MathLinkException;

    public byte[][] getByteArray2() throws MathLinkException;

    public char[] getCharArray1() throws MathLinkException;

    public char[][] getCharArray2() throws MathLinkException;

    public short[] getShortArray1() throws MathLinkException;

    public short[][] getShortArray2() throws MathLinkException;

    public int[] getIntArray1() throws MathLinkException;

    public int[][] getIntArray2() throws MathLinkException;

    public long[] getLongArray1() throws MathLinkException;

    public long[][] getLongArray2() throws MathLinkException;

    public float[] getFloatArray1() throws MathLinkException;

    public float[][] getFloatArray2() throws MathLinkException;

    public double[] getDoubleArray1() throws MathLinkException;

    public double[][] getDoubleArray2() throws MathLinkException;

    public String[] getStringArray1() throws MathLinkException;

    public String[][] getStringArray2() throws MathLinkException;

    public Object[] getComplexArray1() throws MathLinkException;

    public Object[][] getComplexArray2() throws MathLinkException;

    public Object getArray(int var1, int var2) throws MathLinkException;

    public Object getArray(int var1, int var2, String[] var3) throws MathLinkException;

    public MLFunction getFunction() throws MathLinkException;

    public void putFunction(String var1, int var2) throws MathLinkException;

    public int checkFunction(String var1) throws MathLinkException;

    public void checkFunctionWithArgCount(String var1, int var2) throws MathLinkException;

    public Object getComplex() throws MathLinkException;

    public void transferExpression(MathLink var1) throws MathLinkException;

    public void transferToEndOfLoopbackLink(LoopbackLink var1) throws MathLinkException;

    public Expr getExpr() throws MathLinkException;

    public Expr peekExpr() throws MathLinkException;

    public int getMessage() throws MathLinkException;

    public void putMessage(int var1) throws MathLinkException;

    public boolean messageReady() throws MathLinkException;

    public long createMark() throws MathLinkException;

    public void seekMark(long var1);

    public void destroyMark(long var1);

    public void put(Object var1) throws MathLinkException;

    public void put(Object var1, String[] var2) throws MathLinkException;

    public boolean setComplexClass(Class var1);

    public Class getComplexClass();

    public boolean setYieldFunction(Class var1, Object var2, String var3);

    public boolean addMessageHandler(Class var1, Object var2, String var3);

    public boolean removeMessageHandler(String var1);
}

