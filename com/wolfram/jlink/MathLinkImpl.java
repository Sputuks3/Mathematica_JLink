/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkImplBase;
import com.wolfram.jlink.MsgHandlerRecord;
import com.wolfram.jlink.PacketArrivedEvent;
import com.wolfram.jlink.PacketListener;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Vector;

public abstract class MathLinkImpl
extends MathLinkImplBase {
    protected Method userYielder;
    protected Object yielderObject;
    protected Vector userMsgHandlers = new Vector(2, 1);
    private long timeoutMillis = 0;
    private long startConnectTime = 0;
    private boolean connectTimeoutExpired;
    protected Vector packetListeners = new Vector(2, 2);
    private Object packetListenerLock = new Object();
    protected Object yieldFunctionLock = new Object();
    protected Class complexClass;
    protected Constructor complexCtor;
    protected Method complexReMethod;
    protected Method complexImMethod;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void connect(long timeoutMillis) throws MathLinkException {
        this.setYieldFunction(null, this, "connectTimeoutYielder");
        this.timeoutMillis = timeoutMillis;
        this.connectTimeoutExpired = false;
        this.startConnectTime = System.currentTimeMillis();
        try {
            this.connect();
        }
        finally {
            this.setYieldFunction(null, null, null);
        }
        if (this.connectTimeoutExpired) {
            throw new MathLinkException(1005);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean setYieldFunction(Class cls, Object target, String methName) {
        Object object = this.yieldFunctionLock;
        synchronized (object) {
            this.userYielder = null;
            this.yielderObject = null;
            if (methName != null) {
                Method meth = null;
                Class[] paramTypes = new Class[]{};
                Class targetClass = cls != null ? cls : target.getClass();
                try {
                    meth = targetClass.getMethod(methName, paramTypes);
                }
                catch (Exception e) {
                    return false;
                }
                this.userYielder = meth;
                this.yielderObject = target;
                if (!this.userYielder.isAccessible()) {
                    try {
                        this.userYielder.setAccessible(true);
                    }
                    catch (SecurityException e) {
                        System.err.println("J/Link warning: The yield function " + methName + " might not be called due to a security restriction. " + "See the documentation for the class java.lang.reflect.ReflectPermission. This problem might go away " + "if JLink.jar is loaded from the classpath instead of the jre/lib/ext directory.");
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    public synchronized boolean addMessageHandler(Class cls, Object target, String methName) {
        Method meth = null;
        Class[] paramTypes = new Class[]{Integer.TYPE, Integer.TYPE};
        Class targetClass = cls != null ? cls : target.getClass();
        try {
            meth = targetClass.getMethod(methName, paramTypes);
        }
        catch (Exception e) {
            return false;
        }
        Enumeration e = this.userMsgHandlers.elements();
        while (e.hasMoreElements()) {
            MsgHandlerRecord msgHandlerRec = (MsgHandlerRecord)e.nextElement();
            if (!msgHandlerRec.meth.equals(meth)) continue;
            return true;
        }
        this.userMsgHandlers.addElement(new MsgHandlerRecord(target, meth, methName));
        if (!meth.isAccessible()) {
            try {
                meth.setAccessible(true);
            }
            catch (SecurityException e2) {
                System.err.println("J/Link warning: The message handler " + methName + " might not be called due to a security restriction. " + "See the documentation for the class java.lang.reflect.ReflectPermission. This problem might go away " + "if JLink.jar is loaded from the classpath instead of the jre/lib/ext directory.");
                e2.printStackTrace();
            }
        }
        return true;
    }

    public synchronized boolean removeMessageHandler(String methName) {
        for (int i = 0; i < this.userMsgHandlers.size(); ++i) {
            if (!((MsgHandlerRecord)this.userMsgHandlers.elementAt((int)i)).methName.equals(methName)) continue;
            this.userMsgHandlers.removeElementAt(i);
            return true;
        }
        return false;
    }

    protected void messageCallback(int message, int n) {
        Object[] args = new Object[]{new Integer(message), new Integer(n)};
        for (int i = 0; i < this.userMsgHandlers.size(); ++i) {
            try {
                MsgHandlerRecord rec = (MsgHandlerRecord)this.userMsgHandlers.elementAt(i);
                rec.meth.invoke(rec.target, args);
                continue;
            }
            catch (Throwable t) {
                // empty catch block
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean yielderCallback() {
        Object object = this.yieldFunctionLock;
        synchronized (object) {
            Object res = null;
            if (this.userYielder != null) {
                try {
                    res = this.userYielder.invoke(this.yielderObject, null);
                }
                catch (Throwable t) {
                    // empty catch block
                }
            }
            if (res instanceof Boolean) {
                return (Boolean)res;
            }
            return false;
        }
    }

    public boolean connectTimeoutYielder() {
        this.connectTimeoutExpired = System.currentTimeMillis() > this.startConnectTime + this.timeoutMillis;
        return this.connectTimeoutExpired;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addPacketListener(PacketListener listener) {
        Object object = this.packetListenerLock;
        synchronized (object) {
            if (!this.packetListeners.contains(listener)) {
                this.packetListeners.addElement(listener);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removePacketListener(PacketListener listener) {
        Object object = this.packetListenerLock;
        synchronized (object) {
            if (this.packetListeners.contains(listener)) {
                this.packetListeners.removeElement(listener);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean notifyPacketListeners(int pkt) {
        boolean allowFurtherProcessing;
        Vector v;
        if (this.packetListeners.size() == 0) {
            return true;
        }
        allowFurtherProcessing = true;
        PacketArrivedEvent evt = new PacketArrivedEvent((KernelLink)((Object)this), pkt);
        Object object = this.packetListenerLock;
        synchronized (object) {
            v = (Vector)this.packetListeners.clone();
        }
        long mark = 0;
        try {
            boolean listenerResult = true;
            mark = this.createMark();
            int len = v.size();
            for (int i = 0; i < len && allowFurtherProcessing; ++i) {
                try {
                    listenerResult = ((PacketListener)v.elementAt(i)).packetArrived(evt);
                    allowFurtherProcessing = allowFurtherProcessing && listenerResult;
                }
                catch (MathLinkException e) {
                    this.clearError();
                }
                finally {
                    this.seekMark(mark);
                }
            }
        }
        catch (MathLinkException e) {
            this.clearError();
        }
        finally {
            if (mark != 0) {
                this.destroyMark(mark);
            }
        }
        return allowFurtherProcessing;
    }

    public synchronized boolean setComplexClass(Class cls) {
        Constructor newComplexCtor = null;
        Method newComplexReMethod = null;
        Method newComplexImMethod = null;
        if (cls != null) {
            try {
                Class[] argTypes = new Class[]{Double.TYPE, Double.TYPE};
                newComplexCtor = cls.getDeclaredConstructor(argTypes);
                newComplexReMethod = cls.getDeclaredMethod("re", null);
                newComplexImMethod = cls.getDeclaredMethod("im", null);
            }
            catch (Exception e) {
                return false;
            }
        }
        this.complexClass = cls;
        this.complexCtor = newComplexCtor;
        this.complexReMethod = newComplexReMethod;
        this.complexImMethod = newComplexImMethod;
        return true;
    }

    public synchronized Class getComplexClass() {
        return this.complexClass;
    }

    protected synchronized Object constructComplex(double re, double im) {
        Object[] args = new Object[]{new Double(re), new Double(im)};
        try {
            return this.complexCtor.newInstance(args);
        }
        catch (Exception e) {
            return null;
        }
    }

    protected synchronized double getRealPart(Object complex) throws Exception {
        return (Double)this.complexReMethod.invoke(complex, null);
    }

    protected synchronized double getImaginaryPart(Object complex) throws Exception {
        return (Double)this.complexImMethod.invoke(complex, null);
    }
}

