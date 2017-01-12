/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.JLinkClassLoader;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.Utils;
import java.io.PrintStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

class ClassRecord {
    private String vmName;
    private String name;
    private Class cls;
    private Constructor[] ctors;
    private Method[] methods;
    private Field[] fields;
    private Class componentType;
    private int depth;
    private static final int NOT = 0;
    private static final int ASSIGNABLE = 1;
    private static final int EXACTLY = 2;

    ClassRecord(String name, ClassLoader loader, String vmName) throws ClassNotFoundException, SecurityException {
        if (loader == null) {
            loader = JLinkClassLoader.getInstance();
        }
        this.cls = Class.forName(name, false, loader);
        this.vmName = vmName;
        this.name = this.cls.getName();
        this.ctors = this.cls.getConstructors();
        this.methods = this.cls.getMethods();
        this.fields = this.cls.getFields();
        int m = this.cls.getModifiers();
        if (!Modifier.isPublic(m)) {
            try {
                AccessibleObject.setAccessible(this.methods, true);
                AccessibleObject.setAccessible(this.fields, true);
            }
            catch (SecurityException e) {
                System.err.println("Warning: the non-public class " + name + " was loaded by J/Link, and the attempt " + "to make its public methods accessible from Mathematica failed due to a Java " + "security restriction. Objects of this class may generate an IllegalAccessException  " + "when they are used from Mathematica. See the documentation for the class " + "java.lang.reflect.ReflectPermission.");
            }
        }
        this.componentType = Utils.getArrayComponentType(this.cls);
        if (this.componentType != null) {
            this.depth = 0;
            while (name.charAt(this.depth) == '[') {
                ++this.depth;
            }
        }
    }

    Class getCls() {
        return this.cls;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void putInfo(KernelLink kl, String symbolNameOfObjSupplyingClassLoader) throws MathLinkException {
        Error err;
        block28 : {
            Expr classNameArg = null;
            Expr loadClassArg = null;
            Expr isInterfaceArg = null;
            Expr ctorsArg = null;
            Expr methodsArg = null;
            Expr fieldsArg = null;
            err = null;
            Class complexClass = kl.getComplexClass();
            try {
                classNameArg = new Expr(this.name);
                loadClassArg = new Expr(new Expr(4, "JLink`Package`loadClassFromJava"), new Expr[0]);
                loadClassArg = loadClassArg.insert(new Expr(this.vmName), 1);
                Class sup = this.cls.getSuperclass();
                loadClassArg = loadClassArg.insert(sup == null ? Expr.SYM_NULL : new Expr(sup.getName()), 2);
                loadClassArg = loadClassArg.insert(new Expr(4, symbolNameOfObjSupplyingClassLoader), 3);
                Expr expr = isInterfaceArg = this.cls.isInterface() ? Expr.SYM_TRUE : Expr.SYM_FALSE;
                if (this.componentType != null) {
                    ctorsArg = new Expr(Expr.SYM_LIST, new Expr[]{new Expr(Expr.SYM_LIST, new Expr[]{new Expr(0), new Expr(""), new Expr(ClassRecord.classToMathLinkConstant(int[].class, complexClass, true))})});
                    if (this.depth == 1) {
                        ctorsArg = ctorsArg.insert(new Expr(Expr.SYM_LIST, new Expr[]{new Expr(1), new Expr(""), new Expr(ClassRecord.classToMathLinkConstant(Integer.TYPE, complexClass, true))}), 2);
                    }
                } else {
                    Expr[] ctorExprs = new Expr[this.ctors.length];
                    for (int i = 0; i < this.ctors.length; ++i) {
                        Class[] params = this.ctors[i].getParameterTypes();
                        boolean hasAmbiguity = ClassRecord.hasRealIntAmbiguity(i, params, this.ctors);
                        Expr[] thisCtorArgs = new Expr[params.length + 2];
                        thisCtorArgs[0] = new Expr(i);
                        thisCtorArgs[1] = new Expr(this.ctors[i].toString());
                        for (int j = 0; j < params.length; ++j) {
                            thisCtorArgs[j + 2] = new Expr(ClassRecord.classToMathLinkConstant(params[j], complexClass, hasAmbiguity));
                        }
                        ctorExprs[i] = new Expr(Expr.SYM_LIST, thisCtorArgs);
                    }
                    ctorsArg = new Expr(Expr.SYM_LIST, ctorExprs);
                }
                Method[] declMethods = this.cls.getDeclaredMethods();
                String[] names = new String[declMethods.length];
                for (int i = 0; i < declMethods.length; ++i) {
                    names[i] = declMethods[i].getName();
                }
                int numMethods = this.methods.length;
                int sentMethodCount = 0;
                ArrayList<Expr> methodExprs = new ArrayList<Expr>(numMethods);
                for (int i = 0; i < numMethods; ++i) {
                    boolean isStatic;
                    Method meth;
                    boolean sendThisMethod;
                    block27 : {
                        String name;
                        meth = this.methods[i];
                        isStatic = Modifier.isStatic(meth.getModifiers());
                        sendThisMethod = false;
                        if (meth.getDeclaringClass() == this.cls) {
                            sendThisMethod = true;
                        } else if (isStatic) {
                            sendThisMethod = true;
                            name = meth.getName();
                            Class<?>[] params = meth.getParameterTypes();
                            for (int j = 0; j < declMethods.length; ++j) {
                                Class<?>[] otherParams;
                                if (!name.equals(names[j]) || (otherParams = declMethods[j].getParameterTypes()).length != params.length) continue;
                                sendThisMethod = false;
                                for (int k = 0; k < params.length; ++k) {
                                    if (params[k] == otherParams[k]) continue;
                                    sendThisMethod = true;
                                    break block27;
                                }
                            }
                        } else if (!meth.isBridge()) {
                            name = meth.getName();
                            for (int j = 0; j < declMethods.length; ++j) {
                                if (!name.equals(names[j])) continue;
                                sendThisMethod = true;
                                break;
                            }
                        }
                    }
                    if (!sendThisMethod) continue;
                    ++sentMethodCount;
                    Class[] params = meth.getParameterTypes();
                    boolean hasAmbiguity = ClassRecord.hasRealIntAmbiguity(i, params, this.methods);
                    Expr[] thisMethodArgs = new Expr[params.length + 4];
                    thisMethodArgs[0] = new Expr(i);
                    thisMethodArgs[1] = new Expr(meth.toString());
                    thisMethodArgs[2] = isStatic ? Expr.SYM_TRUE : Expr.SYM_FALSE;
                    thisMethodArgs[3] = new Expr(meth.getName());
                    for (int j = 0; j < params.length; ++j) {
                        thisMethodArgs[j + 4] = new Expr(ClassRecord.classToMathLinkConstant(params[j], complexClass, hasAmbiguity));
                    }
                    methodExprs.add(new Expr(Expr.SYM_LIST, thisMethodArgs));
                }
                methodsArg = new Expr(Expr.SYM_LIST, methodExprs.toArray(new Expr[sentMethodCount]));
                Field[] declFields = this.cls.getDeclaredFields();
                names = new String[declFields.length];
                for (int j = 0; j < declFields.length; ++j) {
                    names[j] = declFields[j].getName();
                }
                int numFields = this.fields.length;
                int sentFieldCount = 0;
                ArrayList<Expr> fieldExprs = new ArrayList<Expr>(numFields);
                for (int i = 0; i < numFields; ++i) {
                    boolean sendThisField = true;
                    Field fld = this.fields[i];
                    boolean isStatic = Modifier.isStatic(fld.getModifiers());
                    if (isStatic) {
                        if (fld.getDeclaringClass() != this.cls) {
                            String name = fld.getName();
                            for (int j = 0; j < declFields.length; ++j) {
                                if (!name.equals(names[j])) continue;
                                sendThisField = false;
                                break;
                            }
                        }
                        if (!sendThisField) continue;
                    }
                    ++sentFieldCount;
                    Expr[] thisFieldArgs = new Expr[5];
                    thisFieldArgs[0] = new Expr(i);
                    thisFieldArgs[1] = isStatic ? Expr.SYM_TRUE : Expr.SYM_FALSE;
                    thisFieldArgs[2] = new Expr(fld.getType().toString());
                    thisFieldArgs[3] = new Expr(fld.getName());
                    thisFieldArgs[4] = new Expr(ClassRecord.classToMathLinkConstant(fld.getType(), complexClass, false));
                    fieldExprs.add(new Expr(Expr.SYM_LIST, thisFieldArgs));
                }
                fieldsArg = new Expr(Expr.SYM_LIST, fieldExprs.toArray(new Expr[sentFieldCount]));
                if (err != null) break block28;
            }
            catch (Error e) {
                block29 : {
                    try {
                        err = e;
                        if (err != null) break block29;
                    }
                    catch (Throwable throwable) {
                        if (err == null) {
                            Expr classInfo = new Expr(Expr.SYM_LIST, new Expr[]{classNameArg, loadClassArg, isInterfaceArg, ctorsArg, methodsArg, fieldsArg});
                            kl.put(classInfo);
                            throw throwable;
                        } else {
                            kl.message("Java::excptn", err.toString());
                            kl.putSymbol("$Failed");
                        }
                        throw throwable;
                    }
                    Expr classInfo = new Expr(Expr.SYM_LIST, new Expr[]{classNameArg, loadClassArg, isInterfaceArg, ctorsArg, methodsArg, fieldsArg});
                    kl.put(classInfo);
                    return;
                }
                kl.message("Java::excptn", err.toString());
                kl.putSymbol("$Failed");
                return;
            }
            Expr classInfo = new Expr(Expr.SYM_LIST, new Expr[]{classNameArg, loadClassArg, isInterfaceArg, ctorsArg, methodsArg, fieldsArg});
            kl.put(classInfo);
            return;
        }
        kl.message("Java::excptn", err.toString());
        kl.putSymbol("$Failed");
        return;
    }

    private static int classToMathLinkConstant(Class cls, Class complexClass, boolean hasRealIntAmbiguity) {
        int res = 0;
        if (cls.isPrimitive()) {
            if (cls == Integer.TYPE) {
                res = -5;
            } else if (cls == Double.TYPE) {
                res = hasRealIntAmbiguity ? -8 : -16;
            } else if (cls == Boolean.TYPE) {
                res = -1;
            } else if (cls == Byte.TYPE) {
                res = -2;
            } else if (cls == Character.TYPE) {
                res = -3;
            } else if (cls == Short.TYPE) {
                res = -4;
            } else if (cls == Long.TYPE) {
                res = -6;
            } else if (cls == Float.TYPE) {
                res = hasRealIntAmbiguity ? -7 : -15;
            }
        } else {
            res = cls == String.class ? -9 : (cls == complexClass ? -13 : (cls.isArray() ? -17 + ClassRecord.classToMathLinkConstant(cls.getComponentType(), complexClass, hasRealIntAmbiguity) : (cls == BigInteger.class ? -10 : (cls == BigDecimal.class ? -11 : (cls == Expr.class ? -12 : -14)))));
        }
        return res;
    }

    private static boolean hasRealIntAmbiguity(int thisIndex, Class[] params, Member[] members) {
        boolean isMethod;
        String thisName = members[thisIndex].getName();
        boolean hasRealParam = false;
        for (int j = 0; j < params.length; ++j) {
            Class c = params[j];
            if (c.isArray()) {
                c = Utils.getArrayComponentType(c);
            }
            if (c != Float.TYPE && c != Double.TYPE) continue;
            hasRealParam = true;
            break;
        }
        boolean bl = isMethod = members.length > 0 && members[0] instanceof Method;
        if (hasRealParam) {
            for (int j = 0; j < members.length; ++j) {
                Class<?>[] otherParams;
                Member otherMember = members[j];
                if (j == thisIndex || !otherMember.getName().equals(thisName)) continue;
                Class<?>[] arrclass = otherParams = isMethod ? ((Method)otherMember).getParameterTypes() : ((Constructor)otherMember).getParameterTypes();
                if (otherParams.length != params.length) continue;
                for (int k = 0; k < params.length; ++k) {
                    Class p = params[k];
                    Class op = otherParams[k];
                    if (p.isArray() && op.isArray()) {
                        p = Utils.getArrayComponentType(p);
                        op = Utils.getArrayComponentType(op);
                    }
                    if (p != Float.TYPE && p != Double.TYPE || op != Integer.TYPE && op != Long.TYPE && op != Short.TYPE && op != Character.TYPE && op != Byte.TYPE) continue;
                    return true;
                }
            }
        }
        return false;
    }

    synchronized Object callField(boolean isSet, Object instance, int fieldIndex, Object val) throws IllegalAccessException, NoSuchMethodException {
        Field f = this.fields[fieldIndex];
        if (isSet) {
            f.set(instance, val);
            return null;
        }
        return f.get(instance);
    }

    Object callBestCtor(int[] indices, Object[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object res = null;
        if (this.componentType != null) {
            int[] dims = indices[0] == 0 ? (int[])args[0] : new int[]{(Integer)args[0]};
            res = Array.newInstance(this.componentType, dims);
        } else {
            Constructor ctor = (Constructor)this.bestMember(indices, args, false);
            res = ctor.newInstance(args);
        }
        return res;
    }

    Object callBestMethod(int[] indices, Object instance, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method meth = (Method)this.bestMember(indices, args, true);
        return meth.invoke(instance, args);
    }

    private Object bestMember(int[] indices, Object[] args, boolean isMethod) {
        int i;
        int len = indices.length;
        if (len == 1) {
            return isMethod ? this.methods[indices[0]] : this.ctors[indices[0]];
        }
        Class[] argClasses = new Class[args.length];
        for (int i2 = 0; i2 < args.length; ++i2) {
            argClasses[i2] = args[i2].getClass();
        }
        boolean[] objsAssignable = new boolean[len];
        boolean[] primitivesMatch = new boolean[len];
        boolean atLeastOneMethodMatchesObjects = false;
        for (i = 0; i < len; ++i) {
            Class[] paramClasses = isMethod ? this.methods[indices[i]].getParameterTypes() : this.ctors[indices[i]].getParameterTypes();
            int objsMatch = ClassRecord.objectClassesMatch(argClasses, paramClasses);
            boolean primsMatch = ClassRecord.primitiveClassesMatch(argClasses, paramClasses);
            if (objsMatch == 2 && primsMatch) {
                return isMethod ? this.methods[indices[i]] : this.ctors[indices[i]];
            }
            if (objsMatch == 2 || objsMatch == 1) {
                objsAssignable[i] = true;
                atLeastOneMethodMatchesObjects = true;
            }
            if (!primsMatch) continue;
            primitivesMatch[i] = true;
        }
        if (!atLeastOneMethodMatchesObjects) {
            return isMethod ? this.methods[indices[0]] : this.ctors[indices[0]];
        }
        for (i = 0; i < len; ++i) {
            if (!objsAssignable[i] || !primitivesMatch[i]) continue;
            return isMethod ? this.methods[indices[i]] : this.ctors[indices[i]];
        }
        Object[] newArgs = new Object[args.length];
        for (int i3 = 0; i3 < len; ++i3) {
            Class[] paramClasses;
            if (!objsAssignable[i3]) continue;
            System.arraycopy(args, 0, newArgs, 0, args.length);
            Class[] arrclass = paramClasses = isMethod ? this.methods[indices[i3]].getParameterTypes() : this.ctors[indices[i3]].getParameterTypes();
            if (!ClassRecord.massagePrimitives(argClasses, paramClasses, newArgs)) continue;
            System.arraycopy(newArgs, 0, args, 0, args.length);
            return isMethod ? this.methods[indices[i3]] : this.ctors[indices[i3]];
        }
        return isMethod ? this.methods[indices[0]] : this.ctors[indices[0]];
    }

    private static int objectClassesMatch(Class[] argClasses, Class[] paramClasses) {
        int len = argClasses.length;
        int match = 2;
        for (int i = 0; i < len; ++i) {
            Class argCls = argClasses[i];
            Class paramCls = paramClasses[i];
            if (paramCls.isPrimitive() || paramCls == argCls) continue;
            if (paramCls.isAssignableFrom(argCls)) {
                match = 1;
                continue;
            }
            return 0;
        }
        return match;
    }

    private static boolean primitiveClassesMatch(Class[] argClasses, Class[] paramClasses) {
        int len = argClasses.length;
        for (int i = 0; i < len; ++i) {
            boolean argsMatch = true;
            Class argCls = argClasses[i];
            Class paramCls = paramClasses[i];
            if (paramCls.isPrimitive()) {
                if (paramCls == Integer.TYPE) {
                    argsMatch = argCls == Integer.class;
                } else if (paramCls == Double.TYPE) {
                    argsMatch = argCls == Double.class;
                } else if (paramCls == Boolean.TYPE) {
                    argsMatch = argCls == Boolean.class;
                } else if (paramCls == Byte.TYPE) {
                    argsMatch = argCls == Byte.class;
                } else if (paramCls == Long.TYPE) {
                    argsMatch = argCls == Long.class;
                } else if (paramCls == Float.TYPE) {
                    argsMatch = argCls == Float.class;
                } else if (paramCls == Short.TYPE) {
                    argsMatch = argCls == Short.class;
                } else if (paramCls == Character.TYPE) {
                    boolean bl = argsMatch = argCls == Character.class;
                }
            }
            if (argsMatch) continue;
            return false;
        }
        return true;
    }

    private static boolean massagePrimitives(Class[] argClasses, Class[] paramClasses, Object[] newArgs) {
        for (int i = 0; i < newArgs.length; ++i) {
            long val;
            Class paramCls = paramClasses[i];
            Class argCls = argClasses[i];
            if (!paramCls.isPrimitive()) continue;
            if (paramCls == Integer.TYPE) {
                if (argCls != Long.class) continue;
                val = ((Number)newArgs[i]).longValue();
                if (val <= Integer.MAX_VALUE && val >= Integer.MIN_VALUE) {
                    newArgs[i] = new Integer((int)val);
                    continue;
                }
                return false;
            }
            if (paramCls == Short.TYPE) {
                if (argCls != Long.class && argCls != Integer.class) continue;
                val = ((Number)newArgs[i]).longValue();
                if (val <= 32767 && val >= -32768) {
                    newArgs[i] = new Short((short)val);
                    continue;
                }
                return false;
            }
            if (paramCls == Character.TYPE) {
                if (argCls != Long.class && argCls != Integer.class && argCls != Short.class) continue;
                val = ((Number)newArgs[i]).longValue();
                if (val <= 65535 && val >= 0) {
                    newArgs[i] = new Character((char)val);
                    continue;
                }
                return false;
            }
            if (paramCls == Byte.TYPE) {
                if (argCls != Long.class && argCls != Integer.class && argCls != Short.class && argCls != Character.class) continue;
                long l = val = argCls == Character.class ? (long)((Character)newArgs[i]).charValue() : ((Number)newArgs[i]).longValue();
                if (val <= 127 && val >= -128) {
                    newArgs[i] = new Byte((byte)val);
                    continue;
                }
                return false;
            }
            if (paramCls != Float.TYPE || argCls != Double.class) continue;
            double d = Math.abs((Double)newArgs[i]);
            if (d <= 3.4028234663852886E38 && d >= 1.401298464324817E-45) {
                newArgs[i] = new Float((float)d);
                continue;
            }
            return false;
        }
        return true;
    }

    void callOnLoadClass(KernelLink ml) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        for (int i = 0; i < this.methods.length; ++i) {
            Class<?>[] params;
            String name = this.methods[i].getName();
            if (!name.equals("onLoadClass") || (params = this.methods[i].getParameterTypes()).length != 1 || !params[0].isInstance(ml)) continue;
            Object[] args = new Object[]{ml};
            this.methods[i].invoke(null, args);
        }
    }

    private void fillObjectArrayFromCtor(Object array, Constructor compCtor, int[] dims, int depth) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        if (depth == dims.length - 1) {
            for (int i = 0; i < dims[depth]; ++i) {
                Array.set(array, i, compCtor.newInstance(null));
            }
        } else {
            for (int i = 0; i < dims[depth]; ++i) {
                this.fillObjectArrayFromCtor(((Object[])array)[i], compCtor, dims, depth + 1);
            }
        }
    }

    void callOnUnloadClass(KernelLink ml) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        for (int i = 0; i < this.methods.length; ++i) {
            Class<?>[] params;
            String name = this.methods[i].getName();
            if (!name.equals("onUnloadClass") || (params = this.methods[i].getParameterTypes()).length != 1 || !params[0].isInstance(ml)) continue;
            Object[] args = new Object[]{ml};
            this.methods[i].invoke(null, args);
        }
    }

    int reflect(MathLink ml, int type, boolean includeInherited, boolean sendData) throws MathLinkException {
        int num = 0;
        switch (type) {
            case 1: {
                num = this.ctors.length;
                if (!sendData) break;
                for (int i = 0; i < num; ++i) {
                    ml.put(this.ctors[i].toString());
                }
                break;
            }
            case 2: {
                for (int i = 0; i < this.methods.length; ++i) {
                    if (!includeInherited && this.methods[i].getDeclaringClass() != this.cls) continue;
                    ++num;
                    if (!sendData) continue;
                    ml.put(this.methods[i].toString());
                }
                break;
            }
            case 3: {
                for (int i = 0; i < this.fields.length; ++i) {
                    if (!includeInherited && this.fields[i].getDeclaringClass() != this.cls) continue;
                    ++num;
                    if (!sendData) continue;
                    boolean isStatic = Modifier.isStatic(this.fields[i].getModifiers());
                    boolean isFinal = Modifier.isFinal(this.fields[i].getModifiers());
                    String typeStr = this.fields[i].getType().toString();
                    if (typeStr.startsWith("class ")) {
                        typeStr = typeStr.substring(6);
                    }
                    ml.put((isStatic ? "static " : "") + (isFinal ? "final " : "") + typeStr + " " + this.fields[i].getName());
                }
                break;
            }
        }
        return num;
    }
}

