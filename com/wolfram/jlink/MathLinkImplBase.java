/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.MLFunction;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.Utils;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class MathLinkImplBase
implements MathLink {
    static final int DEBUGLEVEL = 0;
    private static final byte[] decimalPointString = new byte[]{46};
    private static final byte[] expString = new byte[]{42, 94};
    private static final byte[] tickString = new byte[]{96};
    private String[] headHolder = new String[1];

    public synchronized void activate() throws MathLinkException {
        this.connect();
    }

    public synchronized boolean getBoolean() throws MathLinkException {
        return this.getSymbol().equals("True");
    }

    public synchronized void putData(byte[] data) throws MathLinkException {
        this.putData(data, data.length);
    }

    public synchronized boolean[] getBooleanArray1() throws MathLinkException {
        return (boolean[])this.getArray(-1, 1);
    }

    public synchronized boolean[][] getBooleanArray2() throws MathLinkException {
        return (boolean[][])this.getArray(-1, 2);
    }

    public synchronized byte[] getByteArray1() throws MathLinkException {
        return (byte[])this.getArray(-2, 1);
    }

    public synchronized byte[][] getByteArray2() throws MathLinkException {
        return (byte[][])this.getArray(-2, 2);
    }

    public synchronized char[] getCharArray1() throws MathLinkException {
        return (char[])this.getArray(-3, 1);
    }

    public synchronized char[][] getCharArray2() throws MathLinkException {
        return (char[][])this.getArray(-3, 2);
    }

    public synchronized short[] getShortArray1() throws MathLinkException {
        return (short[])this.getArray(-4, 1);
    }

    public synchronized short[][] getShortArray2() throws MathLinkException {
        return (short[][])this.getArray(-4, 2);
    }

    public synchronized int[] getIntArray1() throws MathLinkException {
        return (int[])this.getArray(-5, 1);
    }

    public synchronized int[][] getIntArray2() throws MathLinkException {
        return (int[][])this.getArray(-5, 2);
    }

    public synchronized long[] getLongArray1() throws MathLinkException {
        return (long[])this.getArray(-6, 1);
    }

    public synchronized long[][] getLongArray2() throws MathLinkException {
        return (long[][])this.getArray(-6, 2);
    }

    public synchronized float[] getFloatArray1() throws MathLinkException {
        return (float[])this.getArray(-7, 1);
    }

    public synchronized float[][] getFloatArray2() throws MathLinkException {
        return (float[][])this.getArray(-7, 2);
    }

    public synchronized double[] getDoubleArray1() throws MathLinkException {
        return (double[])this.getArray(-8, 1);
    }

    public synchronized double[][] getDoubleArray2() throws MathLinkException {
        return (double[][])this.getArray(-8, 2);
    }

    public synchronized String[] getStringArray1() throws MathLinkException {
        return (String[])this.getArray(-9, 1);
    }

    public synchronized String[][] getStringArray2() throws MathLinkException {
        return (String[][])this.getArray(-9, 2);
    }

    public synchronized Object[] getComplexArray1() throws MathLinkException {
        return (Object[])this.getArray(-13, 1);
    }

    public synchronized Object[][] getComplexArray2() throws MathLinkException {
        return (Object[][])this.getArray(-13, 2);
    }

    public synchronized Expr getExpr() throws MathLinkException {
        return Expr.createFromLink(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Expr peekExpr() throws MathLinkException {
        long mark = this.createMark();
        try {
            Expr expr = Expr.createFromLink(this);
            return expr;
        }
        finally {
            this.seekMark(mark);
            this.destroyMark(mark);
        }
    }

    public synchronized Object getArray(int type, int depth, String[] heads) throws MathLinkException {
        Object result = null;
        if (depth == 1) {
            MLFunction func = this.getFunction();
            switch (type) {
                case -1: {
                    result = Array.newInstance(Boolean.TYPE, func.argCount);
                    for (int i = 0; i < func.argCount; ++i) {
                        Array.setBoolean(result, i, this.getBoolean());
                    }
                    break;
                }
                case -9: {
                    result = Array.newInstance(String.class, func.argCount);
                    for (int i = 0; i < func.argCount; ++i) {
                        Array.set(result, i, this.getString());
                    }
                    break;
                }
                case -6: {
                    result = Array.newInstance(Long.TYPE, func.argCount);
                    for (int i = 0; i < func.argCount; ++i) {
                        Array.setLong(result, i, this.getLongInteger());
                    }
                    break;
                }
                case -13: {
                    result = Array.newInstance(this.getComplexClass(), func.argCount);
                    for (int i = 0; i < func.argCount; ++i) {
                        Array.set(result, i, this.getComplex());
                    }
                    break;
                }
                case -12: {
                    result = new Expr[func.argCount];
                    for (int i = 0; i < func.argCount; ++i) {
                        Array.set(result, i, this.getExpr());
                    }
                    break;
                }
                case -10: {
                    result = new BigInteger[func.argCount];
                    for (int i = 0; i < func.argCount; ++i) {
                        Array.set(result, i, new BigInteger(this.getString()));
                    }
                    break;
                }
                case -11: {
                    result = new BigDecimal[func.argCount];
                    for (int i = 0; i < func.argCount; ++i) {
                        Array.set(result, i, Utils.bigDecimalFromString(this.getString()));
                    }
                    break;
                }
            }
            if (heads != null) {
                heads[0] = func.name;
            }
        } else {
            result = this.getArraySlices(type, depth, heads, 0, null);
        }
        return result;
    }

    public synchronized Object getArray(int type, int depth) throws MathLinkException {
        return this.getArray(type, depth, null);
    }

    protected Object getArray(Class elementType, int depth, String[] heads) throws MathLinkException {
        throw new UnsupportedOperationException("This method should never be entered; only the superclass version should be called.");
    }

    protected Object getArraySlices(int type, int depth, String[] heads, int headsIndex, Class componentClass) throws MathLinkException {
        Object resArray = null;
        if (depth > 1) {
            if (componentClass == null) {
                ClassLoader loader = this.getClass().getClassLoader();
                String compClassName = "";
                for (int i = 1; i < depth; ++i) {
                    compClassName = compClassName + "[";
                }
                switch (type) {
                    case -1: {
                        compClassName = compClassName + "Z";
                        break;
                    }
                    case -2: {
                        compClassName = compClassName + "B";
                        break;
                    }
                    case -3: {
                        compClassName = compClassName + "C";
                        break;
                    }
                    case -4: {
                        compClassName = compClassName + "S";
                        break;
                    }
                    case -5: {
                        compClassName = compClassName + "I";
                        break;
                    }
                    case -6: {
                        compClassName = compClassName + "J";
                        break;
                    }
                    case -7: {
                        compClassName = compClassName + "F";
                        break;
                    }
                    case -8: {
                        compClassName = compClassName + "D";
                        break;
                    }
                    case -9: {
                        compClassName = compClassName + "Ljava.lang.String;";
                        break;
                    }
                    case -10: {
                        compClassName = compClassName + "Ljava.math.BigInteger;";
                        break;
                    }
                    case -11: {
                        compClassName = compClassName + "Ljava.math.BigDecimal;";
                        break;
                    }
                    case -12: {
                        compClassName = compClassName + "Lcom.wolfram.jlink.Expr;";
                        break;
                    }
                    case -13: {
                        compClassName = compClassName + "L" + this.getComplexClass().getName() + ";";
                        loader = this.getComplexClass().getClassLoader();
                        break;
                    }
                }
                try {
                    componentClass = Class.forName(compClassName, true, loader);
                }
                catch (ClassNotFoundException e) {
                    // empty catch block
                }
            }
            MLFunction func = this.getFunction();
            if (heads != null) {
                heads[headsIndex] = func.name;
            }
            int len = func.argCount;
            resArray = Array.newInstance(componentClass, len);
            func = null;
            Class subComponentClass = componentClass.getComponentType();
            for (int i = 0; i < len; ++i) {
                Array.set(resArray, i, this.getArraySlices(type, depth - 1, heads, headsIndex + 1, subComponentClass));
            }
        } else {
            resArray = type < -13 ? this.getArray(componentClass, 1, heads != null ? this.headHolder : null) : this.getArray(type, 1, heads != null ? this.headHolder : null);
            if (heads != null) {
                heads[headsIndex] = this.headHolder[0];
            }
        }
        return resArray;
    }

    public synchronized void put(Object obj) throws MathLinkException {
        if (obj == null) {
            this.putSymbol("Null");
        } else if (obj instanceof String) {
            this.putString((String)obj);
        } else if (obj.getClass().isArray()) {
            this.putArray(obj, null);
        } else if (obj instanceof Expr) {
            ((Expr)obj).put(this);
        } else if (this.getComplexClass() != null && this.getComplexClass().isInstance(obj)) {
            this.putComplex(obj);
        } else if (obj instanceof Number) {
            if (obj instanceof Integer || obj instanceof Short || obj instanceof Byte) {
                this.put(((Number)obj).intValue());
            } else if (obj instanceof Double || obj instanceof Float) {
                this.put(((Number)obj).doubleValue());
            } else if (obj instanceof Long || obj instanceof BigInteger) {
                byte[] data = obj.toString().getBytes();
                this.putNext(43);
                this.putSize(data.length);
                this.putData(data, data.length);
            } else if (obj instanceof BigDecimal) {
                BigDecimal bd = (BigDecimal)obj;
                String scale = Integer.toString(- bd.scale());
                String unscaledValue = bd.unscaledValue().toString();
                this.putNext(42);
                this.putSize(unscaledValue.length() + 3 + scale.length());
                this.putData(unscaledValue.getBytes());
                this.putData(decimalPointString);
                this.putData(expString);
                this.putData(scale.getBytes());
            } else {
                byte[] data = obj.toString().getBytes();
                boolean hasDecimal = false;
                boolean mustBeConvertedToDouble = false;
                for (char b : data) {
                    char c = b;
                    if (c == '.') {
                        hasDecimal = true;
                        continue;
                    }
                    if (Character.isDigit(c) || c == '-' || c == '+' || c == 'E' || c == 'e') continue;
                    mustBeConvertedToDouble = true;
                    break;
                }
                if (mustBeConvertedToDouble) {
                    this.put(((Number)obj).doubleValue());
                } else {
                    this.putNext(42);
                    this.putSize(data.length + (hasDecimal ? 0 : 1));
                    this.putData(data, data.length);
                    if (!hasDecimal) {
                        this.putData(new byte[]{46}, 1);
                    }
                }
            }
        } else if (obj instanceof Boolean) {
            this.putSymbol((Boolean)obj != false ? "True" : "False");
        } else if (obj instanceof Character) {
            this.put(((Character)obj).charValue());
        } else {
            this.putReference(obj);
        }
    }

    public synchronized void put(Object obj, String[] heads) throws MathLinkException {
        if (obj == null) {
            this.putSymbol("Null");
        } else if (obj.getClass().isArray()) {
            this.putArray(obj, heads);
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected abstract void putArray(Object var1, String[] var2) throws MathLinkException;

    protected void putArrayPiecemeal(Object obj, String[] heads, int headIndex) throws MathLinkException {
        Class<?> cls;
        Class<?> class_ = cls = obj != null ? obj.getClass() : null;
        if (cls != null && cls.isArray()) {
            String thisHead = heads != null && heads.length > headIndex ? heads[headIndex] : "List";
            int len = Array.getLength(obj);
            this.putFunction(thisHead, len);
            ++headIndex;
            for (int i = 0; i < len; ++i) {
                this.putArrayPiecemeal(Array.get(obj, i), heads, headIndex);
            }
        } else {
            this.put(obj);
        }
    }

    public synchronized Object getComplex() throws MathLinkException {
        double re = 0.0;
        double im = 0.0;
        int type = this.getNext();
        switch (type) {
            case 42: 
            case 43: {
                re = this.getDouble();
                break;
            }
            case 70: {
                this.checkFunctionWithArgCount("Complex", 2);
                re = this.getDouble();
                im = this.getDouble();
                break;
            }
            default: {
                throw new MathLinkException(1003);
            }
        }
        if (this.getComplexClass() == null) {
            return null;
        }
        return this.constructComplex(re, im);
    }

    protected synchronized void putComplex(Object obj) throws MathLinkException {
        double re = 0.0;
        double im = 0.0;
        try {
            re = this.getRealPart(obj);
            im = this.getImaginaryPart(obj);
        }
        catch (Exception e) {
            this.putSymbol("$Failed");
            return;
        }
        this.putFunction("Complex", 2);
        this.put(re);
        this.put(im);
    }

    protected abstract Object constructComplex(double var1, double var3);

    protected abstract double getRealPart(Object var1) throws Exception;

    protected abstract double getImaginaryPart(Object var1) throws Exception;

    protected abstract void putString(String var1) throws MathLinkException;

    protected synchronized void putReference(Object obj) throws MathLinkException {
        this.put(obj.toString());
    }
}

