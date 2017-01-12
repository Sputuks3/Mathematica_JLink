/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.ExprFormatException;
import com.wolfram.jlink.LoopbackLink;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import com.wolfram.jlink.NativeLink;
import com.wolfram.jlink.Utils;
import com.wolfram.jlink.WrappedKernelLink;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class Expr
implements Serializable {
    public static final int INTEGER = 1;
    public static final int REAL = 2;
    public static final int STRING = 3;
    public static final int SYMBOL = 4;
    public static final int RATIONAL = 5;
    public static final int COMPLEX = 6;
    public static final int BIGINTEGER = 7;
    public static final int BIGDECIMAL = 8;
    private static final int NEWBIGDECIMAL = 9;
    private static final int UNKNOWN = 0;
    private static final int FIRST_COMPOSITE = 100;
    private static final int FUNCTION = 100;
    private static final int FIRST_ARRAY_TYPE = 200;
    private static final int INTARRAY1 = 200;
    private static final int REALARRAY1 = 201;
    private static final int INTARRAY2 = 202;
    private static final int REALARRAY2 = 203;
    public static final Expr SYM_SYMBOL = new Expr(4, "Symbol");
    public static final Expr SYM_INTEGER = new Expr(4, "Integer");
    public static final Expr SYM_REAL = new Expr(4, "Real");
    public static final Expr SYM_STRING = new Expr(4, "String");
    public static final Expr SYM_RATIONAL = new Expr(4, "Rational");
    public static final Expr SYM_COMPLEX = new Expr(4, "Complex");
    public static final Expr SYM_LIST = new Expr(4, "List");
    public static final Expr SYM_TRUE = new Expr(4, "True");
    public static final Expr SYM_FALSE = new Expr(4, "False");
    public static final Expr SYM_NULL = new Expr(4, "Null");
    public static final Expr INT_ONE = new Expr(1);
    public static final Expr INT_ZERO = new Expr(0);
    public static final Expr INT_MINUSONE = new Expr(-1);
    private int type;
    private Expr head;
    private Expr[] args;
    private Object val;
    private transient BigDecimal bigDecimalForm;
    private transient LoopbackLink link;
    private volatile int cachedHashCode = 0;
    private static final long serialVersionUID = 469201568023508L;

    private Expr() {
    }

    public Expr(int type, String val) {
        this.type = type;
        switch (type) {
            case 1: {
                this.head = SYM_INTEGER;
                this.val = new Long(val);
                break;
            }
            case 2: {
                this.head = SYM_REAL;
                this.val = new Double(val);
                break;
            }
            case 3: {
                this.head = SYM_STRING;
                this.val = val;
                break;
            }
            case 4: {
                if ("".equals(val)) {
                    throw new IllegalArgumentException("Cannot create a Symbol Expr from an empty string");
                }
                this.head = val.equals("Symbol") ? this : SYM_SYMBOL;
                this.val = val;
                break;
            }
            case 7: {
                this.head = SYM_INTEGER;
                this.val = new BigInteger(val);
                break;
            }
            case 8: {
                this.type = 9;
                this.head = SYM_REAL;
                this.val = val;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported type in Expr(type, string) constructor: " + type);
            }
        }
    }

    public Expr(long val) {
        this.type = 1;
        this.head = SYM_INTEGER;
        this.val = new Long(val);
    }

    public Expr(double val) {
        this.type = 2;
        this.head = SYM_REAL;
        this.val = new Double(val);
    }

    public Expr(String val) {
        this.type = 3;
        this.head = SYM_STRING;
        this.val = val;
    }

    public Expr(int[] val) {
        this.type = 200;
        this.head = SYM_LIST;
        this.val = val.clone();
    }

    public Expr(double[] val) {
        this.type = 201;
        this.head = SYM_LIST;
        this.val = val.clone();
    }

    public Expr(int[][] val) {
        this.type = 202;
        this.head = SYM_LIST;
        this.val = new int[val.length][];
        for (int i = 0; i < val.length; ++i) {
            ((int[][])this.val)[i] = (int[])val[i].clone();
        }
    }

    public Expr(double[][] val) {
        this.type = 203;
        this.head = SYM_LIST;
        this.val = new double[val.length][];
        for (int i = 0; i < val.length; ++i) {
            ((double[][])this.val)[i] = (double[])val[i].clone();
        }
    }

    public Expr(BigInteger val) {
        this.type = 7;
        this.head = SYM_INTEGER;
        this.val = val;
    }

    public Expr(BigDecimal val) {
        this.type = 8;
        this.head = SYM_REAL;
        this.val = val;
    }

    public Expr(Expr head, Expr[] args) {
        this.type = 100;
        this.head = head;
        if (head == null) {
            throw new IllegalArgumentException("The head of an Expr cannot be null. Use Expr.SYM_NULL if you want to represent the Mathematica symbol Null");
        }
        if (args != null) {
            for (int i = 0; i < args.length; ++i) {
                if (args[i] != null) continue;
                throw new IllegalArgumentException("No member of the args array can be null. Use Expr.SYM_NULL if you want to represent the Mathematica symbol Null");
            }
        }
        this.args = args != null ? (Expr[])args.clone() : new Expr[]{};
    }

    public static Expr createFromLink(MathLink ml) throws MathLinkException {
        return Expr.createFromLink(ml, true);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        this.prepareFromLoopback();
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Expr)) {
            return false;
        }
        Expr otherExpr = (Expr)obj;
        if (this.cachedHashCode != 0 && otherExpr.cachedHashCode != 0 && this.cachedHashCode != otherExpr.cachedHashCode) {
            return false;
        }
        otherExpr.prepareFromLoopback();
        this.prepareFromLoopback();
        if (this.type != otherExpr.type) {
            return false;
        }
        if (this.val != null) {
            if (otherExpr.val == null) {
                return false;
            }
            switch (this.type) {
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 7: 
                case 8: 
                case 9: {
                    return this.val.equals(otherExpr.val);
                }
                case 200: {
                    int[] a = (int[])this.val;
                    int[] oa = (int[])otherExpr.val;
                    if (a.length != oa.length) {
                        return false;
                    }
                    for (int i = 0; i < a.length; ++i) {
                        if (a[i] == oa[i]) continue;
                        return false;
                    }
                    return true;
                }
                case 201: {
                    double[] a = (double[])this.val;
                    double[] oa = (double[])otherExpr.val;
                    if (a.length != oa.length) {
                        return false;
                    }
                    for (int i = 0; i < a.length; ++i) {
                        if (a[i] == oa[i]) continue;
                        return false;
                    }
                    return true;
                }
                case 202: {
                    int[][] a = (int[][])this.val;
                    int[][] oa = (int[][])otherExpr.val;
                    if (a.length != oa.length) {
                        return false;
                    }
                    for (int i = 0; i < a.length; ++i) {
                        int[] aPart = a[i];
                        int[] oaPart = oa[i];
                        if (aPart.length != oaPart.length) {
                            return false;
                        }
                        for (int j = 0; j < aPart.length; ++j) {
                            if (aPart[j] == oaPart[j]) continue;
                            return false;
                        }
                    }
                    return true;
                }
                case 203: {
                    double[][] a = (double[][])this.val;
                    double[][] oa = (double[][])otherExpr.val;
                    if (a.length != oa.length) {
                        return false;
                    }
                    for (int i = 0; i < a.length; ++i) {
                        double[] aPart = a[i];
                        double[] oaPart = oa[i];
                        if (aPart.length != oaPart.length) {
                            return false;
                        }
                        for (int j = 0; j < aPart.length; ++j) {
                            if (aPart[j] == oaPart[j]) continue;
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        if (otherExpr.val != null) {
            return false;
        }
        if (!this.head.equals(otherExpr.head)) {
            return false;
        }
        if (this.args.length != otherExpr.args.length) {
            return false;
        }
        for (int i = 0; i < this.args.length; ++i) {
            if (this.args[i].equals(otherExpr.args[i])) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.cachedHashCode != 0) {
            return this.cachedHashCode;
        }
        this.prepareFromLoopback();
        if (this.type != 5 && this.type != 6 && this.atomQ()) {
            return this.val.hashCode();
        }
        int hash = 17;
        hash = 37 * hash + this.type;
        if (this.head != null) {
            hash = 37 * hash + this.head.hashCode();
        }
        if (this.args != null) {
            for (int i = 0; i < this.args.length; ++i) {
                hash = 37 * hash + this.args[i].hashCode();
            }
        }
        if (this.val != null) {
            if (this.type < 200) {
                hash = 37 * hash + this.val.hashCode();
            } else if (this.type == 200) {
                int[] ia = (int[])this.val;
                for (int i = 0; i < ia.length; ++i) {
                    hash += ia[i];
                }
            } else if (this.type == 201) {
                double[] da = (double[])this.val;
                for (int i = 0; i < da.length; ++i) {
                    hash += (int)da[i];
                }
            } else if (this.type == 202) {
                int[][] iaa = (int[][])this.val;
                for (int i = 0; i < iaa.length; ++i) {
                    int[] ia = iaa[i];
                    for (int j = 0; j < ia.length; ++j) {
                        hash += ia[j];
                    }
                }
            } else if (this.type == 203) {
                double[][] daa = (double[][])this.val;
                for (int i = 0; i < daa.length; ++i) {
                    double[] da = daa[i];
                    for (int j = 0; j < da.length; ++j) {
                        hash += (int)da[j];
                    }
                }
            }
        }
        this.cachedHashCode = hash;
        return hash;
    }

    int inheritedHashCode() {
        return super.hashCode();
    }

    public synchronized void dispose() {
        if (this.link != null) {
            this.link.close();
            this.link = null;
        } else if (this.type == 100) {
            if (this.head != null) {
                this.head.dispose();
            }
            if (this.args != null) {
                for (int i = 0; i < this.args.length; ++i) {
                    Expr arg = this.args[i];
                    if (arg == null) continue;
                    arg.dispose();
                }
            }
        }
    }

    public Expr head() {
        this.prepareFromLoopback();
        return this.type < 200 ? this.head : SYM_LIST;
    }

    public synchronized Expr[] args() {
        return (Expr[])this.nonCopyingArgs().clone();
    }

    public int length() {
        this.prepareFromLoopback();
        if (this.type >= 200) {
            return Array.getLength(this.val);
        }
        return this.args != null ? this.args.length : 0;
    }

    public int[] dimensions() {
        this.prepareFromLoopback();
        int[] dims = null;
        if (this.type < 100) {
            dims = new int[]{};
        } else {
            switch (this.type) {
                case 200: 
                case 201: {
                    dims = new int[]{Array.getLength(this.val)};
                    break;
                }
                case 202: {
                    dims = new int[]{Array.getLength(this.val), ((int[][])this.val)[0].length};
                    break;
                }
                case 203: {
                    dims = new int[]{Array.getLength(this.val), ((double[][])this.val)[0].length};
                    break;
                }
                case 100: {
                    if (this.args.length == 0) {
                        dims = new int[]{0};
                        break;
                    }
                    int[] leafDims = this.args[0].dimensions();
                    int[] agreed = new int[leafDims.length + 1];
                    agreed[0] = this.args.length;
                    System.arraycopy(leafDims, 0, agreed, 1, leafDims.length);
                    int depthOK = 1 + leafDims.length;
                    block6 : for (int i = 1; i < this.args.length && depthOK != 1; ++i) {
                        int[] otherLeafDims = this.args[i].dimensions();
                        depthOK = Math.min(depthOK, 1 + otherLeafDims.length);
                        for (int j = 1; j < depthOK; ++j) {
                            if (agreed[j] == otherLeafDims[j - 1]) continue;
                            depthOK = j;
                            continue block6;
                        }
                    }
                    String headStr = this.head().toString();
                    int headsAgreeDepth = this.checkHeads(headStr, 0, depthOK);
                    dims = new int[headsAgreeDepth];
                    System.arraycopy(agreed, 0, dims, 0, headsAgreeDepth);
                    break;
                }
            }
        }
        return dims;
    }

    public Expr part(int i) {
        this.prepareFromLoopback();
        if (Math.abs(i) > this.length()) {
            throw new IllegalArgumentException("Cannot take part " + i + " from this Expr because it has length " + this.length() + ".");
        }
        if (i == 0) {
            return this.head();
        }
        if (i > 0) {
            return this.nonCopyingArgs()[i - 1];
        }
        return this.nonCopyingArgs()[this.length() + i];
    }

    public Expr part(int[] ia) {
        try {
            int len = ia.length;
            if (len == 0) {
                return this;
            }
            if (len == 1) {
                return this.part(ia[0]);
            }
            int[] newia = new int[len - 1];
            System.arraycopy(ia, 0, newia, 0, len - 1);
            return this.part(newia).part(ia[len - 1]);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Part " + new Expr(ia).toString() + " of this Expr does not exist.");
        }
    }

    public double re() throws ExprFormatException {
        this.prepareFromLoopback();
        switch (this.type) {
            case 1: 
            case 2: 
            case 5: 
            case 7: 
            case 8: 
            case 9: {
                return this.asDouble();
            }
            case 6: {
                return this.args[0].asDouble();
            }
        }
        throw new ExprFormatException("This Expr is of type " + this.typeToString() + ", so you cannot call re() on it.");
    }

    public double im() throws ExprFormatException {
        this.prepareFromLoopback();
        switch (this.type) {
            case 1: 
            case 2: 
            case 5: 
            case 7: 
            case 8: 
            case 9: {
                return 0.0;
            }
            case 6: {
                return this.args[1].asDouble();
            }
        }
        throw new ExprFormatException("This Expr is of type " + this.typeToString() + ", so you cannot call im() on it.");
    }

    public String toString() {
        String s = null;
        this.prepareFromLoopback();
        switch (this.type) {
            case 1: 
            case 4: 
            case 7: {
                s = this.val.toString();
                break;
            }
            case 8: {
                s = Expr.bigDecimalToInputFormString((BigDecimal)this.val);
                break;
            }
            case 9: {
                if (this.bigDecimalForm == null) {
                    this.bigDecimalForm = Utils.bigDecimalFromString((String)this.val);
                }
                s = Expr.bigDecimalToInputFormString(this.bigDecimalForm);
                break;
            }
            case 2: {
                s = Expr.doubleToInputFormString((Double)this.val);
                break;
            }
            case 3: {
                s = this.val.toString();
                StringBuffer buf = new StringBuffer(s.length() + 10);
                buf.append('\"');
                int len = s.length();
                for (int i = 0; i < len; ++i) {
                    char c = s.charAt(i);
                    if (c == '\\' || c == '\"') {
                        buf.append('\\');
                    }
                    buf.append(c);
                }
                buf.append('\"');
                s = new String(buf);
                break;
            }
            case 5: {
                s = "Rational[" + this.args[0].toString() + ", " + this.args[1].toString() + "]";
                break;
            }
            case 6: {
                s = "Complex[" + this.args[0].toString() + ", " + this.args[1].toString() + "]";
                break;
            }
            case 100: {
                boolean isList = this.listQ();
                int len = this.length();
                StringBuffer buf = new StringBuffer(len * 2);
                buf.append(isList ? "{" : this.head.toString() + "[");
                for (int i = 0; i < len; ++i) {
                    buf.append(this.args[i].toString());
                    if (i >= len - 1) continue;
                    buf.append(", ");
                }
                buf.append(isList ? '}' : ']');
                s = new String(buf);
                break;
            }
            case 200: 
            case 201: {
                int len = Array.getLength(this.val);
                int[] ia = this.type == 200 ? (int[])this.val : null;
                double[] da = this.type == 201 ? (double[])this.val : null;
                StringBuffer buf = new StringBuffer(len * 2);
                buf.append('{');
                for (int i = 0; i < len; ++i) {
                    buf.append(this.type == 200 ? String.valueOf(ia[i]) : Expr.doubleToInputFormString(da[i]));
                    if (i >= len - 1) continue;
                    buf.append(',');
                }
                buf.append('}');
                s = new String(buf);
                break;
            }
            case 202: 
            case 203: {
                int len1 = Array.getLength(this.val);
                int len2 = Array.getLength(Array.get(this.val, 0));
                int[][] ia = this.type == 202 ? (int[][])this.val : (int[][])null;
                double[][] da = this.type == 203 ? (double[][])this.val : (double[][])null;
                StringBuffer buf = new StringBuffer(len1 * len2 * 2);
                buf.append('{');
                for (int i = 0; i < len1; ++i) {
                    buf.append('{');
                    for (int j = 0; j < len2; ++j) {
                        buf.append(this.type == 202 ? String.valueOf(ia[i][j]) : Expr.doubleToInputFormString(da[i][j]));
                        if (j >= len2 - 1) continue;
                        buf.append(',');
                    }
                    buf.append(i < len1 - 1 ? "}," : "}");
                }
                buf.append('}');
                s = new String(buf);
                break;
            }
        }
        return s;
    }

    public boolean atomQ() {
        this.prepareFromLoopback();
        return this.type < 100;
    }

    public boolean stringQ() {
        this.prepareFromLoopback();
        return this.type == 3;
    }

    public boolean symbolQ() {
        this.prepareFromLoopback();
        return this.type == 4;
    }

    public boolean integerQ() {
        this.prepareFromLoopback();
        return this.type == 1 || this.type == 7;
    }

    public boolean realQ() {
        this.prepareFromLoopback();
        return this.type == 2 || this.type == 8 || this.type == 9;
    }

    public boolean rationalQ() {
        this.prepareFromLoopback();
        return this.type == 5;
    }

    public boolean complexQ() {
        this.prepareFromLoopback();
        return this.type == 6;
    }

    public boolean numberQ() {
        this.prepareFromLoopback();
        return this.type == 2 || this.type == 1 || this.type == 7 || this.type == 8 || this.type == 9 || this.type == 6 || this.type == 5;
    }

    public boolean bigIntegerQ() {
        this.prepareFromLoopback();
        return this.type == 7;
    }

    public boolean bigDecimalQ() {
        this.prepareFromLoopback();
        return this.type == 8 || this.type == 9;
    }

    public boolean trueQ() {
        this.prepareFromLoopback();
        return this.type == 4 && this.val.equals("True");
    }

    public boolean listQ() {
        this.prepareFromLoopback();
        return this.type >= 200 || this.type == 100 && this.head.type == 4 && this.head.val.equals("List");
    }

    public boolean vectorQ() {
        this.prepareFromLoopback();
        if (this.type == 200 || this.type == 201) {
            return true;
        }
        if (this.type == 202 || this.type == 203 || !this.listQ()) {
            return false;
        }
        for (int i = 0; i < this.args.length; ++i) {
            if (!this.args[i].listQ()) continue;
            return false;
        }
        return true;
    }

    public boolean vectorQ(int eType) {
        if (!this.vectorQ()) {
            return false;
        }
        switch (this.type) {
            case 200: {
                return eType == 1;
            }
            case 201: {
                return eType == 2;
            }
            case 202: 
            case 203: {
                return false;
            }
        }
        int len = this.length();
        for (int i = 0; i < len; ++i) {
            if (this.args[i].type() == eType) continue;
            return false;
        }
        return true;
    }

    public boolean matrixQ() {
        this.prepareFromLoopback();
        if (this.type == 202 || this.type == 203) {
            return true;
        }
        if (this.type == 200 || this.type == 201 || !this.listQ()) {
            return false;
        }
        if (this.args.length == 0) {
            return false;
        }
        for (int i = 0; i < this.args.length; ++i) {
            if (this.args[i].vectorQ()) continue;
            return false;
        }
        return this.dimensions().length >= 2;
    }

    public boolean matrixQ(int eType) {
        if (!this.matrixQ()) {
            return false;
        }
        if (eType == 1 && this.type == 202 || eType == 2 && this.type == 203) {
            return true;
        }
        int len = this.length();
        this.nonCopyingArgs();
        for (int i = 0; i < len; ++i) {
            if (this.args[i].vectorQ(eType)) continue;
            return false;
        }
        return true;
    }

    public int asInt() throws ExprFormatException {
        this.prepareFromLoopback();
        switch (this.type) {
            case 1: {
                return ((Long)this.val).intValue();
            }
            case 7: {
                return ((BigInteger)this.val).intValue();
            }
        }
        throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java int");
    }

    public long asLong() throws ExprFormatException {
        this.prepareFromLoopback();
        switch (this.type) {
            case 1: {
                return (Long)this.val;
            }
            case 7: {
                return ((BigInteger)this.val).longValue();
            }
        }
        throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java long");
    }

    public double asDouble() throws ExprFormatException {
        this.prepareFromLoopback();
        switch (this.type) {
            case 1: 
            case 2: {
                return ((Number)this.val).doubleValue();
            }
            case 7: {
                return ((BigInteger)this.val).doubleValue();
            }
            case 8: {
                return ((BigDecimal)this.val).doubleValue();
            }
            case 9: {
                if (this.bigDecimalForm == null) {
                    this.bigDecimalForm = Utils.bigDecimalFromString((String)this.val);
                }
                return this.bigDecimalForm.doubleValue();
            }
            case 5: {
                return this.args[0].asDouble() / this.args[1].asDouble();
            }
        }
        throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java double");
    }

    public String asString() throws ExprFormatException {
        this.prepareFromLoopback();
        if (this.type != 3 && this.type != 4) {
            throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java String");
        }
        return (String)this.val;
    }

    public BigInteger asBigInteger() throws ExprFormatException {
        this.prepareFromLoopback();
        switch (this.type) {
            case 1: 
            case 2: {
                return BigInteger.valueOf(((Number)this.val).longValue());
            }
            case 7: {
                return (BigInteger)this.val;
            }
            case 8: {
                return ((BigDecimal)this.val).toBigInteger();
            }
            case 9: {
                if (this.bigDecimalForm == null) {
                    this.bigDecimalForm = Utils.bigDecimalFromString((String)this.val);
                }
                return this.bigDecimalForm.toBigInteger();
            }
        }
        throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java BigInteger");
    }

    public BigDecimal asBigDecimal() throws ExprFormatException {
        this.prepareFromLoopback();
        switch (this.type) {
            case 2: {
                return new BigDecimal((Double)this.val);
            }
            case 1: {
                return BigDecimal.valueOf((Long)this.val);
            }
            case 7: {
                return new BigDecimal((BigInteger)this.val);
            }
            case 8: {
                return (BigDecimal)this.val;
            }
            case 9: {
                if (this.bigDecimalForm == null) {
                    this.bigDecimalForm = Utils.bigDecimalFromString((String)this.val);
                }
                return this.bigDecimalForm;
            }
        }
        throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java BigInteger");
    }

    public Object asArray(int reqType, int depth) throws ExprFormatException {
        this.prepareFromLoopback();
        if (depth > 2) {
            throw new IllegalArgumentException("Depths > 2 are not supported in Expr.asArray()");
        }
        if (reqType != 1 && reqType != 2) {
            throw new IllegalArgumentException("Unsupported type in Expr.asArray(): " + reqType);
        }
        switch (this.type) {
            case 200: {
                if (depth != 1 || reqType != 1) {
                    throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java array of the requested type and depth");
                }
                return (int[])((int[])this.val).clone();
            }
            case 201: {
                if (depth != 1 || reqType != 2) {
                    throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java array of the requested type and depth");
                }
                return (double[])((double[])this.val).clone();
            }
            case 202: {
                if (depth != 2 || reqType != 1) {
                    throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java array of the requested type and depth");
                }
                int[][] ia = new int[((int[][])this.val).length][];
                for (int i = 0; i < ia.length; ++i) {
                    ia[i] = (int[])((int[][])this.val)[i].clone();
                }
                return ia;
            }
            case 203: {
                if (depth != 2 || reqType != 2) {
                    throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java array of the requested type and depth");
                }
                double[][] da = new double[((double[][])this.val).length][];
                for (int i = 0; i < da.length; ++i) {
                    da[i] = (double[])((double[][])this.val)[i].clone();
                }
                return da;
            }
            case 100: {
                if (depth == 1) {
                    if (reqType == 1) {
                        int[] ia = new int[this.args.length];
                        for (int i = 0; i < this.args.length; ++i) {
                            if (!this.args[i].integerQ()) {
                                throw new ExprFormatException("This Expr cannot be represented as a Java array of ints because some elements are not integers");
                            }
                            ia[i] = this.args[i].asInt();
                        }
                        return ia;
                    }
                    double[] da = new double[this.args.length];
                    for (int i = 0; i < this.args.length; ++i) {
                        if (!this.args[i].realQ() && !this.args[i].integerQ()) {
                            throw new ExprFormatException("This Expr cannot be represented as a Java array of doubles because some elements are not real numbers");
                        }
                        da[i] = this.args[i].asDouble();
                    }
                    return da;
                }
                if (reqType == 1) {
                    int[][] iaa = new int[this.args.length][];
                    for (int i = 0; i < this.args.length; ++i) {
                        iaa[i] = (int[])this.args[i].asArray(reqType, depth - 1);
                    }
                    return iaa;
                }
                double[][] daa = new double[this.args.length][];
                for (int i = 0; i < this.args.length; ++i) {
                    daa[i] = (double[])this.args[i].asArray(reqType, depth - 1);
                }
                return daa;
            }
        }
        throw new ExprFormatException("This Expr is of type " + this.typeToString() + " and cannot be represented as a Java array of the requested type and depth");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void put(MathLink ml) throws MathLinkException {
        if (this.link != null) {
            long mark = this.link.createMark();
            try {
                ml.transferExpression(this.link);
            }
            finally {
                ml.clearError();
                this.link.seekMark(mark);
                this.link.destroyMark(mark);
            }
        }
        if (this.val != null) {
            if (this.type == 4) {
                ml.putSymbol((String)this.val);
            } else if (this.type == 9) {
                ml.putNext(42);
                ml.putSize(((String)this.val).length());
                ml.putData(((String)this.val).getBytes());
            } else {
                ml.put(this.val);
            }
        } else {
            ml.putNext(70);
            ml.putArgCount(this.nonCopyingArgs().length);
            ml.put(this.head());
            for (int i = 0; i < this.args.length; ++i) {
                ml.put(this.args[i]);
            }
        }
    }

    public Expr take(int n) {
        int curLen;
        int num = Math.abs(n);
        if (num > (curLen = this.nonCopyingArgs().length)) {
            throw new IllegalArgumentException("Cannot take " + n + " elements from this Expr because it has length " + curLen + ".");
        }
        Expr[] newArgs = new Expr[num];
        if (n >= 0) {
            System.arraycopy(this.args, 0, newArgs, 0, num);
        } else {
            System.arraycopy(this.args, curLen - num, newArgs, 0, num);
        }
        return new Expr(this.head, newArgs);
    }

    public Expr delete(int n) {
        int curLen = this.nonCopyingArgs().length;
        if (n == 0 || Math.abs(n) > curLen) {
            throw new IllegalArgumentException("" + n + " is an invalid deletion position in this Expr.");
        }
        Expr[] newArgs = new Expr[curLen - 1];
        if (n > 0) {
            System.arraycopy(this.args, 0, newArgs, 0, n - 1);
            System.arraycopy(this.args, n, newArgs, n - 1, curLen - n);
        } else {
            System.arraycopy(this.args, 0, newArgs, 0, curLen + n);
            System.arraycopy(this.args, curLen + n + 1, newArgs, curLen + n, - n - 1);
        }
        return new Expr(this.head, newArgs);
    }

    public Expr insert(Expr e, int n) {
        int curLen = this.nonCopyingArgs().length;
        if (n == 0 || Math.abs(n) > curLen + 1) {
            throw new IllegalArgumentException("" + n + " is an invalid insertion position into this Expr.");
        }
        Expr[] newArgs = new Expr[curLen + 1];
        if (n > 0) {
            System.arraycopy(this.args, 0, newArgs, 0, n - 1);
            newArgs[n - 1] = e;
            System.arraycopy(this.args, n - 1, newArgs, n, curLen - (n - 1));
        } else {
            System.arraycopy(this.args, 0, newArgs, 0, curLen + n + 1);
            newArgs[curLen + n + 1] = e;
            System.arraycopy(this.args, curLen + n + 1, newArgs, curLen + n + 2, - n - 1);
        }
        return new Expr(this.head, newArgs);
    }

    private int type() {
        this.prepareFromLoopback();
        return this.type;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private synchronized void prepareFromLoopback() {
        if (this.link != null) {
            try {
                this.fillFromLink(this.link);
            }
            catch (MathLinkException e) {}
            finally {
                this.link.close();
                this.link = null;
            }
        }
    }

    private synchronized void fillFromLink(MathLink ml) throws MathLinkException {
        int mlType = ml.getType();
        if (mlType == 70) {
            try {
                int argc = ml.getArgCount();
                this.head = Expr.createFromLink(ml, false);
                if (this.head.type == 4 && this.head.val.equals("Rational")) {
                    this.type = 5;
                    this.args = new Expr[2];
                    this.args[0] = Expr.createFromLink(ml, false);
                    this.args[1] = Expr.createFromLink(ml, false);
                }
                if (this.head.type == 4 && this.head.val.equals("Complex")) {
                    this.type = 6;
                    this.args = new Expr[2];
                    this.args[0] = Expr.createFromLink(ml, false);
                    this.args[1] = Expr.createFromLink(ml, false);
                }
                this.type = 100;
                this.args = new Expr[argc];
                for (int i = 0; i < argc; ++i) {
                    this.args[i] = Expr.createFromLink(ml, false);
                }
            }
            catch (MathLinkException e) {
                throw e;
            }
            finally {
                ml.clearError();
            }
        } else if (mlType == 43 || mlType == 42 || mlType == 34 || mlType == 35) {
            // empty if block
        }
    }

    private static Expr createFromLink(MathLink ml, boolean allowLoopback) throws MathLinkException {
        int type = ml.getType();
        if (type == 43 || type == 42 || type == 34 || type == 35) {
            return Expr.createAtomicExpr(ml, type);
        }
        Expr result = new Expr();
        if (allowLoopback && NativeLink.nativeLibraryLoaded && (ml instanceof NativeLink || ml instanceof WrappedKernelLink && ((WrappedKernelLink)ml).getMathLink() instanceof NativeLink)) {
            result.link = MathLinkFactory.createLoopbackLink();
            result.link.transferExpression(ml);
            result.type = 0;
        } else {
            result.fillFromLink(ml);
        }
        return result;
    }

    private static Expr createAtomicExpr(MathLink ml, int type) throws MathLinkException {
        Expr result = null;
        switch (type) {
            case 43: {
                String s = ml.getString();
                if (s.equals("0")) {
                    result = INT_ZERO;
                    break;
                }
                if (s.equals("1")) {
                    result = INT_ONE;
                    break;
                }
                if (s.equals("-1")) {
                    result = INT_MINUSONE;
                    break;
                }
                result = new Expr();
                result.head = SYM_INTEGER;
                try {
                    result.val = new Long(s);
                    result.type = 1;
                }
                catch (NumberFormatException e) {
                    result.val = new BigInteger(s);
                    result.type = 7;
                }
                break;
            }
            case 42: {
                result = new Expr();
                result.head = SYM_REAL;
                String s = ml.getString();
                try {
                    result.val = new Double(s);
                    result.type = 2;
                }
                catch (NumberFormatException e) {
                    result.val = s;
                    result.type = 9;
                }
                break;
            }
            case 34: {
                result = new Expr();
                result.type = 3;
                result.head = SYM_STRING;
                result.val = ml.getString();
                break;
            }
            case 35: {
                String sym = ml.getSymbol();
                if (sym.equals("List")) {
                    result = SYM_LIST;
                    break;
                }
                if (sym.equals("True")) {
                    result = SYM_TRUE;
                    break;
                }
                if (sym.equals("False")) {
                    result = SYM_FALSE;
                    break;
                }
                result = new Expr();
                result.type = 4;
                result.head = SYM_SYMBOL;
                result.val = sym;
                break;
            }
        }
        return result;
    }

    private synchronized Expr[] nonCopyingArgs() {
        this.prepareFromLoopback();
        if (this.args == null) {
            if (this.type < 100) {
                this.args = new Expr[0];
            } else if (this.type >= 200) {
                this.args = new Expr[Array.getLength(this.val)];
                block6 : for (int i = 0; i < this.args.length; ++i) {
                    switch (this.type) {
                        case 200: {
                            this.args[i] = new Expr(((int[])this.val)[i]);
                            continue block6;
                        }
                        case 202: {
                            this.args[i] = new Expr(((int[][])this.val)[i]);
                            continue block6;
                        }
                        case 201: {
                            this.args[i] = new Expr(((double[])this.val)[i]);
                            continue block6;
                        }
                        case 203: {
                            this.args[i] = new Expr(((double[][])this.val)[i]);
                            break;
                        }
                    }
                }
            }
        }
        return this.args;
    }

    private int checkHeads(String head, int curDepth, int maxDepth) {
        if (this.args == null || curDepth > maxDepth || !this.head().toString().equals(head)) {
            return curDepth;
        }
        ++curDepth;
        for (int i = 0; i < this.args.length; ++i) {
            int thisArgDepth = this.args[i].checkHeads(head, curDepth, maxDepth);
            if (thisArgDepth >= maxDepth) continue;
            maxDepth = thisArgDepth;
        }
        return maxDepth;
    }

    private String typeToString() {
        this.prepareFromLoopback();
        switch (this.type) {
            case 1: {
                return "INTEGER";
            }
            case 4: {
                return "SYMBOL";
            }
            case 7: {
                return "BIGINTEGER";
            }
            case 8: 
            case 9: {
                return "BIGDECIMAL";
            }
            case 2: {
                return "REAL";
            }
            case 3: {
                return "STRING";
            }
            case 5: {
                return "RATIONAL";
            }
            case 6: {
                return "COMPLEX";
            }
            case 100: {
                return "FUNCTION";
            }
            case 200: {
                return "INTARRAY1D";
            }
            case 201: {
                return "REALARRAY1D";
            }
            case 202: {
                return "INTARRAY2D";
            }
            case 203: {
                return "REALARRAY2D";
            }
        }
        return "BAD TYPE";
    }

    private static String doubleToInputFormString(double d) {
        String s = Double.toString(d);
        int epos = s.lastIndexOf(69);
        if (epos == -1) {
            return s;
        }
        return s.substring(0, epos) + "*^" + s.substring(epos + 1);
    }

    private static String bigDecimalToInputFormString(BigDecimal bd) {
        String s = bd.toString();
        int ePos = s.indexOf(69);
        if (ePos == -1) {
            return s;
        }
        int decimalPos = s.indexOf(46);
        if (decimalPos == -1 || decimalPos > ePos) {
            return s.replace("E", ".*^");
        }
        return s.replace("E", "*^");
    }
}

