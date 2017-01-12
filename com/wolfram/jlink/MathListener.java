/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.StdLink;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.Hashtable;

public abstract class MathListener
implements EventListener {
    private Hashtable handlers;
    private KernelLink usersLink;

    public MathListener() {
        this((KernelLink)null);
    }

    public MathListener(KernelLink ml) {
        this.usersLink = ml;
        this.setupEvents();
    }

    public MathListener(String[][] handlers) {
        this();
        for (int i = 0; i < handlers.length; ++i) {
            this.setHandler(handlers[i][0], handlers[i][1]);
        }
    }

    public boolean setHandler(String meth, String func) {
        if (this.handlers.containsKey(meth)) {
            this.handlers.put(meth, func);
            return true;
        }
        if (this.getLink() != null) {
            this.getLink().message("Java::nohndlr", new String[]{meth, this.getClass().getName()});
        }
        return false;
    }

    protected Expr callMathHandler(String meth, Object[] args) {
        return this.callMathHandler0(true, meth, args);
    }

    protected void callVoidMathHandler(String meth, Object[] args) {
        this.callMathHandler0(false, meth, args);
    }

    protected KernelLink getLink() {
        return this.usersLink != null ? this.usersLink : StdLink.getLink();
    }

    protected String getHandler(String methName) {
        return (String)this.handlers.get(methName);
    }

    private void setupEvents() {
        this.handlers = new Hashtable(10);
        try {
            int i;
            Method[] meths = this.getClass().getMethods();
            Method[] objectMethods = Object.class.getMethods();
            String[] objectMethodNames = new String[objectMethods.length];
            for (i = 0; i < objectMethods.length; ++i) {
                objectMethodNames[i] = objectMethods[i].getName();
            }
            for (i = 0; i < meths.length; ++i) {
                boolean belongs = true;
                String name = meths[i].getName();
                for (int j = 0; j < objectMethodNames.length; ++j) {
                    if (!name.equals(objectMethodNames[j])) continue;
                    belongs = false;
                    break;
                }
                if (!belongs) continue;
                this.handlers.put(name, "");
            }
        }
        catch (SecurityException e) {
            System.err.println("Warning: MathListener cannot establish event handler callbacks: " + e.toString());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Expr callMathHandler0(boolean wantResult, String meth, Object[] args) {
        KernelLink ml = this.getLink();
        if (ml == null) {
            return null;
        }
        String func = (String)this.handlers.get(meth);
        if (func == null) {
            System.err.println("Warning: calling MathListener.callMathHandler() with a method name that does not exist in the class. Method name is " + meth + ". Class is " + this.getClass().getName());
            return null;
        }
        Expr result = null;
        if (!func.equals("")) {
            int numArgs;
            int n = numArgs = args != null ? args.length : 0;
            if (ml.equals(StdLink.getLink())) {
                StdLink.requestTransaction();
            }
            KernelLink kernelLink = ml;
            synchronized (kernelLink) {
                try {
                    ml.putFunction("EvaluatePacket", 1);
                    ml.putNext(70);
                    ml.putArgCount(numArgs);
                    ml.putFunction("ToExpression", 1);
                    ml.put(func);
                    for (int i = 0; i < numArgs; ++i) {
                        ml.put(args[i]);
                    }
                    ml.endPacket();
                    if (wantResult) {
                        ml.waitForAnswer();
                        result = ml.getExpr();
                    } else {
                        ml.discardAnswer();
                    }
                }
                catch (MathLinkException exc) {
                    ml.clearError();
                    ml.newPacket();
                }
            }
        }
        return result;
    }
}

