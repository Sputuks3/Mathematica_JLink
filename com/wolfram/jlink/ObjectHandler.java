/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.ClassRecord;
import com.wolfram.jlink.InstanceCollection;
import com.wolfram.jlink.InvalidClassException;
import com.wolfram.jlink.JLinkClassLoader;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.Utils;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ObjectHandler {
    protected JLinkClassLoader jlinkLoader = new JLinkClassLoader();
    protected InstanceCollection instanceCollection = new InstanceCollection();
    protected Map classCollection = Collections.synchronizedMap(new HashMap());
    protected String vmName;
    protected MathLink feServerLink = null;
    private static final boolean RAW_JAVA_OBJECTS = false;

    MathLink getFEServerLink() {
        return this.feServerLink;
    }

    void setFEServerLink(MathLink feServerLink) {
        this.feServerLink = feServerLink;
    }

    public JLinkClassLoader getClassLoader() {
        return this.jlinkLoader;
    }

    public void setClassLoader(JLinkClassLoader jlinkLoader) {
        this.jlinkLoader = jlinkLoader;
    }

    public Class classFromID(int id) {
        Object obj = this.classCollection.get(new Integer(id));
        return obj != null ? ((ClassRecord)obj).getCls() : null;
    }

    public void putReference(MathLink ml, Object obj, Class upCastCls) throws MathLinkException {
        this.putRef(ml, obj, upCastCls, this.instanceCollection.keyOf(obj));
    }

    public Object getObject(String objSymbol) {
        long key;
        Object result = null;
        if (!objSymbol.equals("Null") && (result = this.instanceCollection.get(key = ObjectHandler.keyFromMmaSymbol(objSymbol))) == null) {
            throw new IllegalArgumentException("Symbol in getObject() does not reference a Java object");
        }
        return result;
    }

    public int loadClass(int classID, String className, Object objSupplyingClassLoader) throws ClassNotFoundException, SecurityException {
        ClassLoader cl = objSupplyingClassLoader == null ? this.jlinkLoader : objSupplyingClassLoader.getClass().getClassLoader();
        ClassRecord classRec = new ClassRecord(className, cl, this.vmName);
        this.classCollection.put(new Integer(classID), classRec);
        return classID;
    }

    public void putInfo(KernelLink ml, int classID, Object objSupplyingClassLoader) throws MathLinkException {
        ClassRecord clsRec = (ClassRecord)this.classCollection.get(new Integer(classID));
        long key = objSupplyingClassLoader == null ? 0 : this.instanceCollection.keyOf(objSupplyingClassLoader);
        clsRec.putInfo(ml, key != 0 ? this.mmaSymbolFromKey(key) : "Null");
    }

    public Object callCtor(int classID, int[] indices, Object[] args) throws NoSuchMethodException, InvalidClassException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ClassRecord clsRec = (ClassRecord)this.classCollection.get(new Integer(classID));
        if (clsRec == null) {
            throw new InvalidClassException();
        }
        return clsRec.callBestCtor(indices, args);
    }

    public Object callMethod(int classID, Object instance, int[] indices, Object[] args) throws IllegalAccessException, InvalidClassException, InvocationTargetException {
        ClassRecord clsRec = (ClassRecord)this.classCollection.get(new Integer(classID));
        if (clsRec == null) {
            throw new InvalidClassException();
        }
        return clsRec.callBestMethod(indices, instance, args);
    }

    public Object getField(int classID, Object instance, int index) throws IllegalAccessException, InvalidClassException, IllegalArgumentException, NoSuchMethodException {
        ClassRecord clsRec = (ClassRecord)this.classCollection.get(new Integer(classID));
        if (clsRec == null) {
            throw new InvalidClassException();
        }
        return clsRec.callField(false, instance, index, null);
    }

    public void setField(int classID, Object instance, int index, Object val) throws IllegalAccessException, InvalidClassException, IllegalArgumentException, NoSuchMethodException {
        ClassRecord clsRec = (ClassRecord)this.classCollection.get(new Integer(classID));
        if (clsRec == null) {
            throw new InvalidClassException();
        }
        clsRec.callField(true, instance, index, val);
    }

    public void releaseInstance(String[] objectSyms) {
        for (int i = 0; i < objectSyms.length; ++i) {
            long key = ObjectHandler.keyFromMmaSymbol(objectSyms[i]);
            this.instanceCollection.remove(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void releaseAllInstances(int classID) {
        String clsName = ((ClassRecord)this.classCollection.get(new Integer(classID))).getCls().getName();
        InstanceCollection instanceCollection = this.instanceCollection;
        synchronized (instanceCollection) {
            Long[] keyArray = new Long[this.instanceCollection.size()];
            int i = 0;
            Enumeration keys = this.instanceCollection.keys();
            while (keys.hasMoreElements()) {
                keyArray[i++] = (Long)keys.nextElement();
            }
            while (--i >= 0) {
                Object val = this.instanceCollection.get(keyArray[i]);
                if (!val.getClass().getName().equals(clsName)) continue;
                this.instanceCollection.remove(keyArray[i]);
            }
        }
    }

    public void unloadClass(int classID) {
        this.classCollection.remove(new Integer(classID));
    }

    public void callOnLoadClass(KernelLink ml, int classID) throws IllegalArgumentException, IllegalAccessException, InvalidClassException, InvocationTargetException {
        ClassRecord clsRec = (ClassRecord)this.classCollection.get(new Integer(classID));
        if (clsRec == null) {
            throw new InvalidClassException();
        }
        clsRec.callOnLoadClass(ml);
    }

    public void callOnUnloadClass(KernelLink ml, int classID) throws IllegalArgumentException, IllegalAccessException, InvalidClassException, InvocationTargetException {
        ClassRecord clsRec = (ClassRecord)this.classCollection.get(new Integer(classID));
        if (clsRec == null) {
            throw new InvalidClassException();
        }
        clsRec.callOnUnloadClass(ml);
    }

    public int reflect(MathLink ml, int classID, int type, boolean includeInherited, boolean sendData) throws InvalidClassException, MathLinkException {
        ClassRecord clsRec = (ClassRecord)this.classCollection.get(new Integer(classID));
        if (clsRec == null) {
            throw new InvalidClassException();
        }
        return clsRec.reflect(ml, type, includeInherited, sendData);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void peekObjects(MathLink ml) throws MathLinkException {
        InstanceCollection instanceCollection = this.instanceCollection;
        synchronized (instanceCollection) {
            ml.putFunction("List", this.instanceCollection.size());
            Enumeration keys = this.instanceCollection.keys();
            while (keys.hasMoreElements()) {
                Long key = (Long)keys.nextElement();
                Object inst = this.instanceCollection.get(key);
                this.putRef(ml, inst, null, key);
            }
        }
        ml.endPacket();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void peekClasses(MathLink ml) throws MathLinkException {
        Map map = this.classCollection;
        synchronized (map) {
            Set values = this.classCollection.keySet();
            ml.putFunction("List", values.size());
            Iterator iter = values.iterator();
            while (iter.hasNext()) {
                ml.put(iter.next());
            }
        }
        ml.endPacket();
    }

    public void setVMName(String name) {
        this.vmName = name;
    }

    public String getVMName() {
        return this.vmName;
    }

    public String getComponentTypeName(int classID) {
        ClassRecord clsRec = (ClassRecord)this.classCollection.get(new Integer(classID));
        return Utils.getArrayComponentType(clsRec.getCls()).getName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void putRef(MathLink ml, Object obj, Class upCastCls, long key) throws MathLinkException {
        if (key != 0) {
            ml.putSymbol(this.mmaSymbolFromKey(key));
            return;
        }
        key = this.instanceCollection.put(obj);
        Class cls = upCastCls == null ? obj.getClass() : upCastCls;
        int classID = -1;
        Map map = this.classCollection;
        synchronized (map) {
            Set entries = this.classCollection.entrySet();
            for (Map.Entry entry : entries) {
                if (!cls.equals(((ClassRecord)entry.getValue()).getCls())) continue;
                classID = (Integer)entry.getKey();
                break;
            }
        }
        if (classID == -1) {
            String name = cls.getName();
            ml.putFunction("JLink`Package`loadClassAndCreateInstanceDefs", 3);
            ml.put(this.vmName);
            ml.put(name);
            ml.putSymbol(this.mmaSymbolFromKey(key));
        } else {
            ml.putFunction("JLink`Package`createInstanceDefs", 3);
            ml.put(this.vmName);
            ml.put(classID);
            ml.putSymbol(this.mmaSymbolFromKey(key));
        }
    }

    private static long keyFromMmaSymbol(String sym) {
        String keyString = sym.substring(sym.lastIndexOf(116) + 1);
        try {
            return Long.parseLong(keyString, 10);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }

    private String mmaSymbolFromKey(long key) {
        return "JLink`Objects`" + this.vmName + "`JavaObject" + Long.toString(key);
    }
}

