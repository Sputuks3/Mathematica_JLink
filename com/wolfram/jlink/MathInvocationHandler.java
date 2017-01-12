/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.StdLink;
import com.wolfram.jlink.Utils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Hashtable;

public class MathInvocationHandler
implements InvocationHandler {
    private Hashtable handlers = new Hashtable(20);
    private KernelLink ml;
    private static Method hashCodeMethod;
    private static Method equalsMethod;
    private static Method toStringMethod;

    public MathInvocationHandler() {
        this((KernelLink)null);
    }

    public MathInvocationHandler(KernelLink ml) {
        this.ml = ml;
    }

    public MathInvocationHandler(String[][] handlers) {
        this(null, handlers);
    }

    public MathInvocationHandler(KernelLink ml, String[][] handlers) {
        this(ml);
        for (int i = 0; i < handlers.length; ++i) {
            this.setHandler(handlers[i][0], handlers[i][1]);
        }
    }

    public void setHandler(String meth, String func) {
        this.handlers.put(meth, func);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mathFunc;
        Object result = null;
        String methName = method.getName();
        Class retType = method.getReturnType();
        if (method.getDeclaringClass() == Object.class) {
            if (method.equals(hashCodeMethod)) {
                return this.proxyHashCode(proxy);
            }
            if (method.equals(equalsMethod)) {
                return this.proxyEquals(proxy, args[0]);
            }
            if (method.equals(toStringMethod)) {
                return this.proxyToString(proxy);
            }
        }
        if ((mathFunc = (String)this.handlers.get(methName)) == null) {
            return null;
        }
        int numArgs = args != null ? args.length : 0;
        KernelLink linkToUse = this.ml != null ? this.ml : StdLink.getLink();
        StdLink.requestTransaction();
        KernelLink kernelLink = linkToUse;
        synchronized (kernelLink) {
            try {
                linkToUse.putFunction("EvaluatePacket", 1);
                linkToUse.putNext(70);
                linkToUse.putArgCount(numArgs);
                linkToUse.putFunction("ToExpression", 1);
                linkToUse.put(mathFunc);
                for (int i = 0; i < numArgs; ++i) {
                    linkToUse.put(args[i]);
                }
                linkToUse.endPacket();
                linkToUse.waitForAnswer();
                if (retType.equals(Expr.class)) {
                    result = linkToUse.getExpr();
                } else {
                    switch (linkToUse.getNext()) {
                        case 43: {
                            result = this.readAsInt(retType, linkToUse);
                            break;
                        }
                        case 42: {
                            result = this.readAsReal(retType, linkToUse);
                            break;
                        }
                        case 34: {
                            result = linkToUse.getString();
                            break;
                        }
                        case 35: {
                            result = linkToUse.getSymbol();
                            if (!result.equals("Null")) break;
                            result = null;
                            break;
                        }
                        case 70: {
                            result = linkToUse.getComplex();
                            break;
                        }
                        case 100000: {
                            result = linkToUse.getObject();
                        }
                    }
                }
            }
            catch (MathLinkException exc) {
                linkToUse.clearError();
                linkToUse.newPacket();
                throw exc;
            }
        }
        return result;
    }

    private Object readAsReal(Class retType, KernelLink ml) throws MathLinkException {
        if (retType.equals(Double.TYPE)) {
            return new Double(ml.getDouble());
        }
        if (retType.equals(Float.TYPE)) {
            return new Float((float)ml.getDouble());
        }
        if (retType.equals(BigDecimal.class)) {
            return Utils.bigDecimalFromString(ml.getString());
        }
        return null;
    }

    private Object readAsInt(Class retType, KernelLink ml) throws MathLinkException {
        if (retType.equals(BigInteger.class)) {
            return new BigInteger(ml.getString());
        }
        long i = ml.getLongInteger();
        if (retType.equals(Character.TYPE)) {
            if (i >= 0 && i <= 65535) {
                return new Character((char)i);
            }
            return new Long(i);
        }
        if (retType.equals(Byte.TYPE)) {
            return new Byte((byte)i);
        }
        if (retType.equals(Short.TYPE)) {
            return new Short((short)i);
        }
        if (retType.equals(Integer.TYPE)) {
            return new Integer((int)i);
        }
        if (retType.equals(Float.TYPE)) {
            return new Float(i);
        }
        if (retType.equals(Double.TYPE)) {
            return new Double(i);
        }
        return new Long(i);
    }

    protected Integer proxyHashCode(Object proxy) {
        return new Integer(System.identityHashCode(proxy));
    }

    protected Boolean proxyEquals(Object proxy, Object other) {
        return proxy == other ? Boolean.TRUE : Boolean.FALSE;
    }

    protected String proxyToString(Object proxy) {
        return proxy.getClass().getName() + '@' + Integer.toHexString(proxy.hashCode());
    }

    static {
        try {
            hashCodeMethod = Object.class.getMethod("hashCode", null);
            equalsMethod = Object.class.getMethod("equals", Object.class);
            toStringMethod = Object.class.getMethod("toString", null);
        }
        catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }
}

