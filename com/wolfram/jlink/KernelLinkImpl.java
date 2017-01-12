/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.Install;
import com.wolfram.jlink.InvalidClassException;
import com.wolfram.jlink.JLinkClassLoader;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MLFunction;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import com.wolfram.jlink.MathLinkImpl;
import com.wolfram.jlink.NumberRangeException;
import com.wolfram.jlink.ObjectHandler;
import com.wolfram.jlink.Reader;
import com.wolfram.jlink.StdLink;
import com.wolfram.jlink.Utils;
import com.wolfram.jlink.ui.ConsoleWindow;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

public abstract class KernelLinkImpl
extends MathLinkImpl
implements KernelLink {
    private ObjectHandler objectHandler = new ObjectHandler();
    private Object msgSync = new Object();
    private volatile int msg;
    protected boolean isManual = false;
    protected Throwable lastError;
    protected Throwable lastExceptionDuringCallPacketHandling;
    private StringBuffer accumulatingPS;
    private boolean lastPktWasMsg = false;
    static final String PACKAGE_PROTECTED_CONTEXT = "JLink`Package`";
    static final String MMA_LOADCLASSANDCREATEINSTANCEDEFS = "JLink`Package`loadClassAndCreateInstanceDefs";
    static final String MMA_CREATEINSTANCEDEFS = "JLink`Package`createInstanceDefs";
    static final String MMA_LOADCLASS = "JLink`Package`loadClassFromJava";
    static final String MMA_PREPAREFORMANUALRETURN = "JLink`Package`prepareForManualReturn";
    static final String MMA_HANDLECLEANEXCEPTION = "JLink`Package`handleCleanException";
    static final String MMA_AUTOEXCEPTION = "JLink`Package`autoException";
    static final String MMA_MANUALEXCEPTION = "JLink`Package`manualException";
    static final int TYPE_FLOATORINT = -15;
    static final int TYPE_DOUBLEORINT = -16;
    static final int TYPE_ARRAY1 = -17;
    static final int TYPE_ARRAY2 = -34;
    static final int TYPE_ARRAY3 = -51;
    static final int TYPE_ARRAY4 = -68;
    static final int TYPE_ARRAY5 = -85;
    static final int TYPE_BAD = -10000;

    protected KernelLinkImpl() {
    }

    public synchronized void evaluate(String s) throws MathLinkException {
        this.putFunction("EvaluatePacket", 1);
        this.putFunction("ToExpression", 1);
        this.put(s);
        this.endPacket();
        this.flush();
    }

    public synchronized void evaluate(Expr e) throws MathLinkException {
        this.putFunction("EvaluatePacket", 1);
        this.put(e);
        this.endPacket();
        this.flush();
    }

    public synchronized String evaluateToOutputForm(String s, int pageWidth) {
        return this.evalToString(s, pageWidth, "OutputForm");
    }

    public synchronized String evaluateToOutputForm(Expr e, int pageWidth) {
        return this.evalToString(e, pageWidth, "OutputForm");
    }

    public synchronized String evaluateToInputForm(String s, int pageWidth) {
        return this.evalToString(s, pageWidth, "InputForm");
    }

    public synchronized String evaluateToInputForm(Expr e, int pageWidth) {
        return this.evalToString(e, pageWidth, "InputForm");
    }

    public synchronized byte[] evaluateToTypeset(String s, int pageWidth, boolean useStdForm) {
        return this.evalToTypeset(s, pageWidth, useStdForm);
    }

    public synchronized byte[] evaluateToTypeset(Expr e, int pageWidth, boolean useStdForm) {
        return this.evalToTypeset(e, pageWidth, useStdForm);
    }

    public synchronized byte[] evaluateToImage(String s, int width, int height) {
        return this.evalToImage(s, width, height, 0, false);
    }

    public synchronized byte[] evaluateToImage(Expr e, int width, int height) {
        return this.evalToImage(e, width, height, 0, false);
    }

    public synchronized byte[] evaluateToImage(String s, int width, int height, int dpi, boolean useFE) {
        return this.evalToImage(s, width, height, dpi, useFE);
    }

    public synchronized byte[] evaluateToImage(Expr e, int width, int height, int dpi, boolean useFE) {
        return this.evalToImage(e, width, height, dpi, useFE);
    }

    public synchronized String evaluateToMathML(String s) {
        return this.evalToString(s, 0, "MathMLForm");
    }

    public synchronized String evaluateToMathML(Expr e) {
        return this.evalToString(e, 0, "MathMLForm");
    }

    public synchronized int waitForAnswer() throws MathLinkException {
        int pkt;
        this.accumulatingPS = null;
        do {
            boolean allowDefaultProcessing;
            if (allowDefaultProcessing = this.notifyPacketListeners(pkt = this.nextPacket())) {
                this.handlePacket(pkt);
            }
            if (pkt == 3 || pkt == 8 || pkt == 4 || pkt == 16) break;
            this.newPacket();
        } while (true);
        return pkt;
    }

    public synchronized void discardAnswer() throws MathLinkException {
        int pkt = this.waitForAnswer();
        this.newPacket();
        while (pkt != 3 && pkt != 8) {
            pkt = this.waitForAnswer();
            this.newPacket();
        }
    }

    public Throwable getLastError() {
        int err = this.error();
        return err != 0 ? new MathLinkException(err, this.errorMessage()) : this.lastError;
    }

    public synchronized void putReference(Object obj) throws MathLinkException {
        this.putReference(obj, null);
    }

    public synchronized void putReference(Object obj, Class upCastCls) throws MathLinkException {
        if (obj == null) {
            this.putSymbol("Null");
        } else {
            this.objectHandler.putReference(this, obj, upCastCls);
        }
    }

    public synchronized Object getObject() throws MathLinkException {
        try {
            return this.objectHandler.getObject(this.getSymbol());
        }
        catch (Exception e) {
            throw new MathLinkException(1100);
        }
    }

    public synchronized void enableObjectReferences() throws MathLinkException {
        this.enableObjectReferences(true);
    }

    public synchronized Expr enableObjectReferences(boolean becomeDefaultJVM) throws MathLinkException {
        this.evaluate("Needs[\"JLink`\"]");
        this.discardAnswer();
        this.evaluate("GetJVM[InstallJava[$ParentLink, Default->" + (becomeDefaultJVM ? "True" : "Automatic") + "]]");
        this.flush();
        Install.install(this);
        this.waitForAnswer();
        Expr jvm = this.getExpr();
        if (StdLink.getLink() == null) {
            StdLink.setLink(this);
        }
        return jvm;
    }

    public JLinkClassLoader getClassLoader() {
        return this.objectHandler.getClassLoader();
    }

    public void setClassLoader(JLinkClassLoader loader) {
        this.objectHandler.setClassLoader(loader);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void handlePacket(int pkt) throws MathLinkException {
        switch (pkt) {
            case 3: {
                ** break;
            }
            case 8: {
                ** break;
            }
            case 4: {
                ** break;
            }
            case 16: {
                ** break;
            }
            case 6: {
                ** break;
            }
            case 5: {
                ** break;
            }
            case 7: {
                type = this.getType();
                if (type == 43) {
                    this.handleCallPacket();
                    ** break;
                }
                if (this.getFEServerLink() == null) ** break;
                feLink = this.getFEServerLink();
                feLink.putFunction("CallPacket", 1);
                feLink.transferExpression(this);
                this.transferExpression(feLink);
                ** break;
            }
            case 1: 
            case 21: {
                if (this.getFEServerLink() == null) ** break;
                fe = this.getFEServerLink();
                fe.putFunction(pkt == 21 ? "InputStringPacket" : "InputPacket", 1);
                fe.put(this.getString());
                fe.flush();
                this.newPacket();
                this.put(fe.getString());
                this.flush();
                ** break;
            }
            case 11: 
            case 12: {
                if (this.getFEServerLink() == null) ** break;
                if (this.accumulatingPS == null) {
                    this.accumulatingPS = new StringBuffer(34000);
                }
                this.accumulatingPS.append(this.getString());
                if (pkt != 12) ** break;
                fe = this.getFEServerLink();
                fe.putFunction("FrontEnd`FrontEndExecute", 1);
                fe.putFunction("FrontEnd`NotebookWrite", 2);
                fe.putFunction("FrontEnd`SelectedNotebook", 0);
                fe.putFunction("Cell", 2);
                fe.putFunction("GraphicsData", 2);
                fe.put("PostScript");
                fe.put(this.accumulatingPS.toString());
                fe.put("Graphics");
                fe.flush();
                this.accumulatingPS = null;
                ** break;
            }
            case 2: 
            case 101: {
                fe = this.getFEServerLink();
                if (fe != null) {
                    fe.putFunction("FrontEnd`FrontEndExecute", 1);
                    fe.putFunction("FrontEnd`NotebookWrite", 2);
                    fe.putFunction("FrontEnd`SelectedNotebook", 0);
                    fe.putFunction("Cell", 2);
                    fe.transferExpression(this);
                    fe.put(this.lastPktWasMsg != false ? "Message" : "Print");
                    fe.flush();
                    ** break;
                }
                if (pkt != 101) ** break;
                this.getFunction();
                ** break;
            }
            case 100: {
                feLink = this.getFEServerLink();
                if (feLink == null) ** GOTO lbl83
                mark = this.createMark();
                try {
                    wrapper = this.getFunction();
                    if (!wrapper.name.equals("FrontEnd`FrontEndExecute")) {
                        feLink.putFunction("FrontEnd`FrontEndExecute", 1);
                    }
                }
                finally {
                    this.seekMark(mark);
                    this.destroyMark(mark);
                }
                feLink.transferExpression(this);
                feLink.flush();
                break;
lbl83: // 1 sources:
                this.getFunction();
                ** break;
            }
lbl85: // 2 sources:
            default: {
                ** GOTO lbl98
            }
        }
        do {
            try {
                Thread.sleep(60);
                continue;
            }
            catch (InterruptedException e) {
                // empty catch block
            }
        } while (!feLink.ready() && !this.ready());
        if (feLink.ready()) {
            this.transferExpression(feLink);
            this.flush();
            ** break;
        }
lbl98: // 21 sources:
        this.lastPktWasMsg = pkt == 5;
    }

    public void interruptEvaluation() {
        try {
            this.putMessage(2);
        }
        catch (MathLinkException e) {
            // empty catch block
        }
    }

    public void abortEvaluation() {
        try {
            this.putMessage(3);
        }
        catch (MathLinkException e) {
            // empty catch block
        }
    }

    public void terminateKernel() {
        try {
            this.putMessage(1);
        }
        catch (MathLinkException e) {
            // empty catch block
        }
    }

    public void abandonEvaluation() {
        this.setYieldFunction(null, this, "bailoutYielder");
    }

    public boolean bailoutYielder() {
        this.setYieldFunction(null, null, null);
        return true;
    }

    public ObjectHandler getObjectHandler() {
        return this.objectHandler;
    }

    public void setObjectHandler(ObjectHandler objh) {
        this.objectHandler = objh;
    }

    public synchronized void print(String s) {
        try {
            this.putFunction("EvaluatePacket", 1);
            this.putFunction("Print", 1);
            this.put(s);
            this.endPacket();
            this.discardAnswer();
        }
        catch (MathLinkException e) {
            this.clearError();
            this.newPacket();
        }
    }

    public synchronized void message(String symtag, String arg) {
        String[] array = new String[]{arg};
        this.message(symtag, array);
    }

    public synchronized void message(String symtag, String[] args) {
        try {
            this.putFunction("EvaluatePacket", 1);
            this.putFunction("Apply", 2);
            this.putFunction("ToExpression", 1);
            this.put("Function[Null, Message[#1, ##2], HoldFirst]");
            this.putFunction("Join", 2);
            this.putFunction("ToHeldExpression", 1);
            this.put(symtag);
            this.putFunction("Hold", args.length);
            for (int i = 0; i < args.length; ++i) {
                this.put(args[i]);
            }
            this.endPacket();
            this.discardAnswer();
        }
        catch (MathLinkException e) {
            this.clearError();
            this.newPacket();
        }
    }

    public synchronized void beginManual() {
        this.setManual(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean wasInterrupted() {
        int theMsg = 0;
        Object object = this.msgSync;
        synchronized (object) {
            theMsg = this.msg;
        }
        return theMsg == 2 || theMsg == 3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearInterrupt() {
        Object object = this.msgSync;
        synchronized (object) {
            this.msg = 0;
        }
    }

    protected void handleCleanException(Throwable t) {
        this.lastExceptionDuringCallPacketHandling = t;
        try {
            this.clearError();
            this.newPacket();
            if (this.wasInterrupted()) {
                this.putFunction("Abort", 0);
            } else {
                String msg = Utils.createExceptionMessage(t);
                this.putFunction("JLink`Package`handleCleanException", 1);
                this.putFunction("JLink`Package`autoException", 1);
                this.put(msg);
            }
            this.endPacket();
            this.flush();
        }
        catch (MathLinkException e) {
            try {
                this.endPacket();
            }
            catch (MathLinkException ee) {
                // empty catch block
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void msgHandler(int msg, int ignore) {
        Object object = this.msgSync;
        synchronized (object) {
            this.msg = msg;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private synchronized void handleCallPacket() {
        int index = 0;
        try {
            index = this.getInteger();
            this.checkFunction("List");
        }
        catch (MathLinkException e) {
            this.handleCleanException(e);
            return;
        }
        if (index != 14) {
            this.lastExceptionDuringCallPacketHandling = null;
        }
        try {
            StdLink.setup(this);
            StdLink.lastPktWasAllowUIComputations(false);
            this.clearInterrupt();
            switch (index) {
                case 1: {
                    this.callJava();
                    return;
                }
                case 2: {
                    this.loadClass();
                    return;
                }
                case 3: {
                    this.throwFromMathematica();
                    return;
                }
                case 4: {
                    this.releaseInstance();
                    return;
                }
                case 5: {
                    this.val();
                    return;
                }
                case 6: {
                    this.callOnLoadClass();
                    return;
                }
                case 7: {
                    this.callOnUnloadClass();
                    return;
                }
                case 8: {
                    this.setComplexCls();
                    return;
                }
                case 9: {
                    this.reflect();
                    return;
                }
                case 10: {
                    this.showInFront();
                    return;
                }
                case 11: {
                    this.sameObjectQ();
                    return;
                }
                case 12: {
                    this.instanceOf();
                    return;
                }
                case 13: {
                    this.allowRaggedArrays();
                    return;
                }
                case 14: {
                    this.getException();
                    return;
                }
                case 15: {
                    this.connectToFEServer();
                    return;
                }
                case 16: {
                    this.disconnectToFEServer();
                    return;
                }
                case 17: {
                    this.peekClasses();
                    return;
                }
                case 18: {
                    this.peekObjects();
                    return;
                }
                case 21: {
                    this.setUserDir();
                    return;
                }
                case 19: {
                    this.getClassPath();
                    return;
                }
                case 20: {
                    this.addToClassPath();
                    return;
                }
                case 23: {
                    this.uiThreadWaiting();
                    return;
                }
                case 22: {
                    this.allowUIComputations();
                    return;
                }
                case 24: {
                    this.yieldTime();
                    return;
                }
                case 25: {
                    this.getConsole();
                    return;
                }
                case 26: {
                    this.extraLinks(true);
                    return;
                }
                case 27: {
                    this.getWindowID();
                    return;
                }
                case 28: {
                    this.addTitleChangeListener();
                    return;
                }
                case 29: {
                    this.setVMName();
                    return;
                }
                case 30: {
                    this.setException();
                    break;
                }
            }
            return;
        }
        catch (Exception e) {
            this.lastExceptionDuringCallPacketHandling = e;
            return;
        }
        finally {
            StdLink.remove();
            this.clearError();
            this.newPacket();
            try {
                this.endPacket();
                this.flush();
            }
            catch (MathLinkException ee) {}
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String evalToString(Object obj, int pageWidth, String format) {
        String res = null;
        this.lastError = null;
        try {
            Utils.writeEvalToStringExpression((MathLink)this, obj, pageWidth, format);
            this.flush();
            this.waitForAnswer();
            res = this.getString();
        }
        catch (MathLinkException e) {
            String dbg = System.getProperty("JLINK_SHOW_INTERNAL_EXCEPTIONS");
            if (dbg != null && dbg.equals("true")) {
                System.err.println("Exception in evaluateTo" + format + ": " + e.toString());
            }
            this.clearError();
            this.lastError = e;
        }
        finally {
            this.newPacket();
        }
        return res;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private byte[] evalToTypeset(Object obj, int pageWidth, boolean useStdForm) {
        byte[] imageData;
        imageData = null;
        this.lastError = null;
        try {
            this.putFunction("EvaluatePacket", 1);
            this.putFunction("Needs", 1);
            this.put("JLink`");
            this.flush();
            this.discardAnswerNoPacketListeners();
            Utils.writeEvalToTypesetExpression(this, obj, pageWidth, useStdForm);
            this.flush();
            this.waitForAnswer();
        }
        catch (MathLinkException e) {
            String dbg = System.getProperty("JLINK_SHOW_INTERNAL_EXCEPTIONS");
            if (dbg != null && dbg.equals("true")) {
                System.err.println("Exception in evaluateToTypeset: " + e.toString());
            }
            this.clearError();
            this.lastError = e;
            this.newPacket();
            return null;
        }
        try {
            if (this.getNext() == 34) {
                imageData = this.getByteString(0);
            }
        }
        catch (Throwable t) {
            String dbg = System.getProperty("JLINK_SHOW_INTERNAL_EXCEPTIONS");
            if (dbg != null && dbg.equals("true")) {
                System.err.println("Exception in evaluateToTypeset: " + t.toString());
            }
            this.clearError();
            this.lastError = t;
        }
        finally {
            this.newPacket();
        }
        return imageData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private byte[] evalToImage(Object obj, int width, int height, int dpi, boolean useFE) {
        byte[] imageData;
        imageData = null;
        this.lastError = null;
        try {
            this.putFunction("EvaluatePacket", 1);
            this.putFunction("Needs", 1);
            this.put("JLink`");
            this.flush();
            this.discardAnswerNoPacketListeners();
            Utils.writeEvalToImageExpression(this, obj, width, height, dpi, useFE);
            this.flush();
            this.waitForAnswer();
        }
        catch (MathLinkException e) {
            String dbg = System.getProperty("JLINK_SHOW_INTERNAL_EXCEPTIONS");
            if (dbg != null && dbg.equals("true")) {
                System.err.println("Exception in evaluateToImage: " + e.toString());
            }
            this.clearError();
            this.lastError = e;
            this.newPacket();
            return null;
        }
        try {
            if (this.getNext() == 34) {
                imageData = this.getByteString(0);
            }
        }
        catch (Throwable t) {
            String dbg = System.getProperty("JLINK_SHOW_INTERNAL_EXCEPTIONS");
            if (dbg != null && dbg.equals("true")) {
                System.err.println("Exception in evaluateToImage: " + t.toString());
            }
            this.clearError();
            this.lastError = t;
        }
        finally {
            this.newPacket();
        }
        return imageData;
    }

    MathLink getFEServerLink() {
        return this.objectHandler.getFEServerLink();
    }

    void setFEServerLink(MathLink feServerLink) {
        this.objectHandler.setFEServerLink(feServerLink);
    }

    protected void setManual(boolean val) {
        if (val && !this.isManual) {
            try {
                this.putFunction("JLink`Package`prepareForManualReturn", 1);
                this.putSymbol("$CurrentLink");
                this.flush();
            }
            catch (MathLinkException e) {
                this.clearError();
            }
        }
        this.isManual = val;
    }

    boolean isManual() {
        return this.isManual;
    }

    protected void throwFromMathematica() throws Exception {
        Exception t = null;
        try {
            if (this.getType() == 100000) {
                Object obj = this.getObject();
                this.getString();
                this.newPacket();
                t = (Exception)obj;
            } else {
                String exc = this.getString();
                String msg = this.getString();
                this.newPacket();
                Class excClass = Class.forName(exc, true, this.objectHandler.getClassLoader());
                Object[] argsArray = null;
                Constructor ctor = null;
                if (msg.length() == 0) {
                    ctor = excClass.getConstructor(new Class[0]);
                    argsArray = new Object[]{};
                } else {
                    ctor = excClass.getConstructor(String.class);
                    argsArray = new Object[]{msg};
                }
                t = (Exception)ctor.newInstance(argsArray);
            }
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
        throw t;
    }

    protected void loadClass() throws MathLinkException {
        int classID;
        Object objSupplyingClassLoader = null;
        try {
            classID = this.getInteger();
            String className = this.getString();
            objSupplyingClassLoader = this.getObject();
            boolean isBeingLoadedAsComplexClass = this.getBoolean();
            this.newPacket();
            this.objectHandler.loadClass(classID, className, objSupplyingClassLoader);
            if (isBeingLoadedAsComplexClass) {
                this.setComplexClass(this.objectHandler.classFromID(classID));
            }
        }
        catch (Throwable t) {
            this.handleCleanException(t);
            return;
        }
        this.objectHandler.putInfo(this, classID, objSupplyingClassLoader);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void callJava() throws MathLinkException {
        int callType;
        boolean byVal;
        int classID;
        Object instance = null;
        int[] indices = null;
        Object[] args = null;
        try {
            this.checkFunction("List");
            classID = this.getInteger();
            callType = this.getInteger();
            instance = this.getObject();
            indices = this.getIntArray1();
            byVal = this.getInteger() != 0;
            int argCount = this.getInteger();
            args = new Object[argCount];
            for (int i = 0; i < argCount; ++i) {
                args[i] = this.getTypeObjectPair();
            }
            this.newPacket();
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        try {
            switch (callType) {
                case 1: {
                    Object obj = null;
                    try {
                        obj = this.objectHandler.callCtor(classID, indices, args);
                    }
                    catch (Throwable t) {
                        if (this.isRecoverableException(t)) {
                            this.handleCleanException(t);
                            break;
                        }
                        throw (Error)t;
                    }
                    if (this.wasInterrupted()) {
                        this.putFunction("Abort", 0);
                        break;
                    }
                    this.putReference(obj);
                    break;
                }
                case 2: {
                    boolean wasManual = this.isManual();
                    this.setManual(false);
                    try {
                        Object res = this.objectHandler.callMethod(classID, instance, indices, args);
                        if (this.isManual()) {
                            this.endPacket();
                            this.putSymbol("Null");
                            break;
                        }
                        if (this.wasInterrupted()) {
                            this.putFunction("Abort", 0);
                            break;
                        }
                        if (byVal) {
                            this.put(res);
                            break;
                        }
                        this.putReference(res);
                    }
                    catch (InvocationTargetException e) {
                        Throwable t = e.getTargetException();
                        if (this.isManual()) {
                            this.lastExceptionDuringCallPacketHandling = t;
                            this.clearError();
                            t.printStackTrace();
                            String msg = Utils.createExceptionMessage(t);
                            this.endPacket();
                            this.flush();
                            this.putFunction("JLink`Package`manualException", 1);
                            this.put(msg);
                        } else {
                            this.handleCleanException(t);
                            if (!this.isRecoverableException(t)) {
                                throw (Error)t;
                            }
                        }
                        break;
                    }
                    catch (Exception t) {
                        this.lastExceptionDuringCallPacketHandling = t;
                        this.handleCleanException(t);
                    }
                    finally {
                        this.setManual(wasManual);
                    }
                }
                case 3: {
                    int fieldIndex = indices[indices.length - 1];
                    try {
                        if (args.length == 0) {
                            Object res = this.objectHandler.getField(classID, instance, fieldIndex);
                            if (byVal) {
                                this.put(res);
                                break;
                            }
                            this.putReference(res);
                            break;
                        }
                        this.objectHandler.setField(classID, instance, fieldIndex, args[0]);
                        this.putSymbol("Null");
                        break;
                    }
                    catch (Exception t) {
                        this.lastExceptionDuringCallPacketHandling = t;
                        this.handleCleanException(t);
                        break;
                    }
                }
            }
        }
        catch (MathLinkException e) {
            System.err.println("Serious error: MathLinkException trying to report results of previous exception.");
            this.clearError();
            try {
                this.endPacket();
            }
            catch (MathLinkException ee) {
                // empty catch block
            }
        }
    }

    protected void releaseInstance() throws MathLinkException {
        try {
            String[] syms = this.getStringArray1();
            this.newPacket();
            this.objectHandler.releaseInstance(syms);
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
    }

    protected void val() throws MathLinkException {
        Object obj = null;
        try {
            obj = this.getObject();
            this.newPacket();
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        if (obj == null) {
            this.put(null);
        } else if (obj instanceof Collection) {
            this.put(((Collection)obj).toArray());
        } else if (obj instanceof Date || obj instanceof Calendar) {
            Calendar cal;
            if (obj instanceof Date) {
                cal = Calendar.getInstance();
                cal.clear();
                cal.setTime((Date)obj);
            } else {
                cal = (Calendar)obj;
            }
            this.putFunction("List", 6);
            this.put(cal.get(1));
            this.put(cal.get(2) + 1);
            this.put(cal.get(5));
            this.put(cal.get(11));
            this.put(cal.get(12));
            this.put((double)cal.get(13) + (double)cal.get(14) / 1000.0);
        } else {
            this.put(obj);
        }
    }

    protected void sameObjectQ() throws MathLinkException {
        Object obj1 = null;
        Object obj2 = null;
        try {
            obj1 = this.getObject();
            obj2 = this.getObject();
            this.newPacket();
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.put(obj1 == obj2);
    }

    protected void instanceOf() throws MathLinkException {
        boolean isInstance;
        try {
            Object obj = this.getObject();
            String clsName = this.getString();
            this.newPacket();
            Class cls = Class.forName(clsName, true, JLinkClassLoader.getInstance());
            isInstance = cls.isInstance(obj);
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.put(isInstance);
    }

    protected void allowRaggedArrays() throws MathLinkException {
        boolean allow = false;
        try {
            allow = this.getBoolean();
            this.newPacket();
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        Utils.setRaggedArrays(allow);
        this.putSymbol("Null");
    }

    protected void getException() throws MathLinkException {
        try {
            this.newPacket();
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putReference(this.lastExceptionDuringCallPacketHandling);
    }

    protected void setException() throws MathLinkException {
        try {
            Object obj = this.getObject();
            this.newPacket();
            this.lastExceptionDuringCallPacketHandling = (Throwable)obj;
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
    }

    protected void setComplexCls() throws MathLinkException {
        String sym;
        try {
            int id = this.getInteger();
            this.newPacket();
            Class cls = this.objectHandler.classFromID(id);
            sym = this.setComplexClass(cls) ? "Null" : "$Failed";
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol(sym);
    }

    protected void callOnLoadClass() throws MathLinkException {
        try {
            int classID = this.getInteger();
            this.newPacket();
            this.objectHandler.callOnLoadClass(this, classID);
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putFunction("ReturnPacket", 1);
        this.putSymbol("Null");
        this.endPacket();
    }

    protected void callOnUnloadClass() throws MathLinkException {
        try {
            int classID = this.getInteger();
            this.newPacket();
            this.objectHandler.callOnUnloadClass(this, classID);
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putFunction("ReturnPacket", 1);
        this.putSymbol("Null");
        this.endPacket();
    }

    protected void reflect() throws MathLinkException {
        int type;
        int classID;
        boolean includeInherited = true;
        int num = 0;
        try {
            classID = this.getInteger();
            type = this.getInteger();
            includeInherited = this.getSymbol().equals("True");
            this.newPacket();
            num = this.objectHandler.reflect(this, classID, type, includeInherited, false);
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putFunction("List", num);
        try {
            this.objectHandler.reflect(this, classID, type, includeInherited, true);
        }
        catch (InvalidClassException e) {
            // empty catch block
        }
    }

    protected void showInFront() throws MathLinkException {
        try {
            Object obj = this.getObject();
            this.newPacket();
            if (Utils.isMacOSX()) {
                try {
                    Class mrjHandlerCls = Class.forName("com.wolfram.jlink.MRJHandlers");
                    Method setupMeth = mrjHandlerCls.getDeclaredMethod("setup", new Class[0]);
                    setupMeth.invoke(null, null);
                }
                catch (Exception e) {
                    // empty catch block
                }
            }
            if (obj instanceof Dialog) {
                Dialog dlg = (Dialog)obj;
                dlg.show();
                if (!dlg.isModal()) {
                    dlg.toFront();
                }
            } else {
                Window windowObj = (Window)obj;
                windowObj.setVisible(true);
                if (windowObj instanceof Frame) {
                    ((Frame)windowObj).setState(0);
                }
                windowObj.toFront();
            }
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
    }

    protected void connectToFEServer() throws MathLinkException {
        MathLink feServerLink;
        boolean result;
        block7 : {
            result = false;
            feServerLink = null;
            try {
                String linkName = this.getString();
                String protocol = this.getString();
                this.newPacket();
                String mlArgs = "-linkmode connect -linkname " + linkName;
                if (!protocol.equals("")) {
                    mlArgs = mlArgs + " -linkprotocol " + protocol;
                }
                if ((feServerLink = MathLinkFactory.createMathLink(mlArgs)) == null) break block7;
                try {
                    feServerLink.connect();
                    feServerLink.putFunction("InputNamePacket", 1);
                    feServerLink.put("In[1]:=");
                    feServerLink.flush();
                    do {
                        MLFunction f = feServerLink.getFunction();
                        feServerLink.newPacket();
                        if (f.name.equals("EnterTextPacket") || f.name.equals("EnterExpressionPacket")) {
                            result = true;
                            break;
                        }
                        if (!f.name.equals("EvaluatePacket")) continue;
                        feServerLink.putFunction("ReturnPacket", 1);
                        feServerLink.putSymbol("Null");
                    } while (true);
                }
                catch (MathLinkException e) {
                    feServerLink.close();
                    feServerLink = null;
                }
            }
            catch (Exception e) {
                this.handleCleanException(e);
                return;
            }
        }
        this.setFEServerLink(feServerLink);
        this.putFunction("ReturnPacket", 1);
        this.put(result);
        this.endPacket();
    }

    protected void disconnectToFEServer() throws MathLinkException {
        this.getFEServerLink().close();
        this.setFEServerLink(null);
        this.putFunction("ReturnPacket", 1);
        this.putSymbol("Null");
        this.endPacket();
    }

    protected void peekClasses() throws MathLinkException {
        this.objectHandler.peekClasses(this);
    }

    protected void peekObjects() throws MathLinkException {
        this.objectHandler.peekObjects(this);
    }

    protected void getClassPath() throws MathLinkException {
        this.put(this.objectHandler.getClassLoader().getClassPath());
    }

    protected void addToClassPath() throws MathLinkException {
        try {
            String[] dirs = (String[])this.getArray(-9, 1);
            boolean searchForJars = this.getBoolean();
            boolean prepend = this.getBoolean();
            this.newPacket();
            this.objectHandler.getClassLoader().addLocations(dirs, searchForJars, prepend);
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
    }

    protected void setUserDir() throws MathLinkException {
        try {
            String userDir = this.getString();
            this.newPacket();
            try {
                System.setProperty("user.dir", userDir);
            }
            catch (Exception ee) {}
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
    }

    protected void uiThreadWaiting() throws MathLinkException {
        this.newPacket();
        this.putSymbol(StdLink.uiThreadWaiting() ? "True" : "False");
    }

    protected void allowUIComputations() throws MathLinkException {
        try {
            boolean allow = this.getSymbol().equals("True");
            boolean enteringModal = this.getSymbol().equals("True");
            this.newPacket();
            StdLink.allowUIComputations(allow, enteringModal);
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
    }

    protected void yieldTime() throws MathLinkException {
        try {
            int millis = this.getInteger();
            this.newPacket();
            try {
                Thread.sleep(millis);
            }
            catch (InterruptedException ee) {}
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
    }

    protected void getConsole() throws MathLinkException {
        this.putReference(ConsoleWindow.getInstance());
    }

    protected void extraLinks(boolean doConnect) throws MathLinkException {
        String linkSnooperCmdLine;
        String prot;
        String uiName;
        String preName;
        try {
            uiName = this.getString();
            preName = this.getString();
            prot = this.getString();
            linkSnooperCmdLine = this.getString();
            this.newPacket();
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        boolean result = true;
        MathLink ui = null;
        try {
            if (linkSnooperCmdLine.length() > 0) {
                linkSnooperCmdLine = linkSnooperCmdLine + " -kernelmode connect -kernelname " + uiName + " -kernelprot " + prot + " -feSide J";
                String[] args = new String[]{"-linkmode", "launch", "-linkname", linkSnooperCmdLine};
                ui = MathLinkFactory.createKernelLink(args);
            } else {
                ui = MathLinkFactory.createKernelLink("-linkname " + uiName + " -linkconnect -linkprotocol " + prot);
            }
            StdLink.setUILink((KernelLink)ui);
            ((KernelLinkImpl)ui).setObjectHandler(this.objectHandler);
        }
        catch (Throwable e) {
            if (ui != null) {
                ui.close();
                ui = null;
            }
            result = false;
        }
        MathLink pre = null;
        try {
            pre = MathLinkFactory.createKernelLink("-linkname " + preName + " -linkconnect -linkprotocol " + prot);
            ((KernelLinkImpl)pre).setObjectHandler(this.objectHandler);
        }
        catch (Throwable e) {
            if (pre != null) {
                pre.close();
                pre = null;
            }
            result = false;
        }
        this.put(result);
        this.flush();
        if (ui != null && doConnect) {
            ui.connect();
        }
        if (pre != null && doConnect) {
            pre.connect();
            new Reader((KernelLink)pre, false, false).start();
        }
    }

    protected void getWindowID() throws MathLinkException {
        long id = -1;
        try {
            Object obj = this.getObject();
            this.newPacket();
            if (obj instanceof Window) {
                id = this.getNativeWindowHandle((Window)obj);
            }
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.put(id);
    }

    protected void addTitleChangeListener() throws MathLinkException {
        try {
            Object obj = this.getObject();
            final String titleChangedFunc = this.getString();
            this.newPacket();
            ((Window)obj).addPropertyChangeListener(new PropertyChangeListener(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals("title")) {
                        KernelLink ml = StdLink.getLink();
                        StdLink.requestTransaction();
                        KernelLink kernelLink = ml;
                        synchronized (kernelLink) {
                            try {
                                ml.putFunction("EvaluatePacket", 1);
                                ml.putFunction(titleChangedFunc, 2);
                                ml.put(evt.getSource());
                                ml.put(evt.getNewValue());
                                ml.endPacket();
                                ml.discardAnswer();
                            }
                            catch (MathLinkException exc) {
                                ml.clearError();
                                ml.newPacket();
                            }
                        }
                    }
                }
            });
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
    }

    protected void setVMName() throws MathLinkException {
        try {
            String name = this.getString();
            this.newPacket();
            this.objectHandler.setVMName(name);
        }
        catch (Exception e) {
            this.handleCleanException(e);
            return;
        }
        this.putSymbol("Null");
    }

    protected abstract long getNativeWindowHandle(Window var1);

    private void discardAnswerNoPacketListeners() throws MathLinkException {
        Vector v = this.packetListeners;
        this.packetListeners = new Vector<E>(0);
        this.discardAnswer();
        this.packetListeners = v;
    }

    public synchronized Object getArray(Class elementType, int depth) throws MathLinkException {
        return this.getArray(elementType, depth, null);
    }

    public synchronized Object getArray(Class elementType, int depth, String[] heads) throws MathLinkException {
        int type = -14;
        if (elementType != null && elementType.isPrimitive()) {
            if (elementType == Integer.TYPE) {
                type = -5;
            } else if (elementType == Double.TYPE) {
                type = -8;
            } else if (elementType == Byte.TYPE) {
                type = -2;
            } else if (elementType == Character.TYPE) {
                type = -3;
            } else if (elementType == Short.TYPE) {
                type = -4;
            } else if (elementType == Long.TYPE) {
                type = -6;
            } else if (elementType == Float.TYPE) {
                type = -7;
            } else if (elementType == Boolean.TYPE) {
                type = -1;
            }
        }
        return this.getArray0(type, depth, heads, elementType);
    }

    public synchronized Object getArray(int type, int depth, String[] heads) throws MathLinkException {
        return this.getArray0(type, depth, heads, null);
    }

    public synchronized Object getArray(int type, int depth) throws MathLinkException {
        return this.getArray0(type, depth, null, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object getArray0(int type, int depth, String[] heads, Class elementType) throws MathLinkException {
        Object resultArray = null;
        if (type == -14) {
            Object firstInstance;
            Class<?> leafClass;
            int actualDepth;
            block15 : {
                firstInstance = null;
                long mark = this.createMark();
                try {
                    int tok;
                    MLFunction mf = this.getFunction();
                    if (mf.argCount == 0) {
                        firstInstance = new Object();
                        break block15;
                    }
                    for (actualDepth = 1; actualDepth < 5 && (tok = this.getNext()) == 70; ++actualDepth) {
                        this.getFunction();
                    }
                    firstInstance = this.getObject();
                }
                finally {
                    this.seekMark(mark);
                    this.destroyMark(mark);
                }
            }
            Class<?> class_ = leafClass = elementType != null ? elementType : firstInstance.getClass();
            if (actualDepth < depth) {
                throw new MathLinkException(1002);
            }
            if (depth == 1) {
                MLFunction func = this.getFunction();
                resultArray = Array.newInstance(leafClass, func.argCount);
                for (int i = 0; i < func.argCount; ++i) {
                    Array.set(resultArray, i, this.getObject());
                }
                if (heads != null) {
                    heads[0] = func.name;
                }
            } else {
                String compClassName = "L" + leafClass.getName() + ";";
                for (int i = 1; i < actualDepth; ++i) {
                    compClassName = "[" + compClassName;
                }
                Class<?> componentClass = null;
                try {
                    componentClass = Class.forName(compClassName, true, leafClass.getClassLoader());
                }
                catch (ClassNotFoundException e) {
                    // empty catch block
                }
                resultArray = this.getArraySlices(type, depth, heads, 0, componentClass);
            }
        } else {
            resultArray = super.getArray(type, depth, heads);
        }
        return resultArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object getTypeObjectPair() throws MathLinkException, NumberRangeException {
        Object result = null;
        int type = this.getInteger();
        if (type % -17 == -15) {
            type = -7 + -17 * (type / -17);
        } else if (type % -17 == -16) {
            type = -8 + -17 * (type / -17);
        }
        switch (type) {
            case -5: {
                result = new Integer(this.getInteger());
                break;
            }
            case -6: {
                result = new Long(this.getLongInteger());
                break;
            }
            case -4: {
                int i = this.getInteger();
                if (i < -32768 || i > 32767) {
                    throw new NumberRangeException(i, "short");
                }
                result = new Short((short)i);
                break;
            }
            case -2: {
                int i = this.getInteger();
                if (i < -128 || i > 127) {
                    throw new NumberRangeException(i, "byte");
                }
                result = new Byte((byte)i);
                break;
            }
            case -3: {
                int i = this.getInteger();
                if (i < 0 || i > 65535) {
                    throw new NumberRangeException(i, "char");
                }
                result = new Character((char)i);
                break;
            }
            case -15: 
            case -7: {
                double d = this.getDouble();
                if (d < -3.4028234663852886E38 || d > 3.4028234663852886E38) {
                    throw new NumberRangeException(d, "float");
                }
                result = new Float((float)d);
                break;
            }
            case -16: 
            case -8: {
                result = new Double(this.getDouble());
                break;
            }
            case -9: {
                int tok = this.getType();
                if (tok == 100000) {
                    result = this.getObject();
                    break;
                }
                result = this.getString();
                if (tok != 35 || !result.equals("Null")) break;
                result = null;
                break;
            }
            case -1: {
                String s = this.getSymbol();
                if (s.equals("True")) {
                    result = Boolean.TRUE;
                    break;
                }
                result = Boolean.FALSE;
                break;
            }
            case -13: {
                long mark = this.createMark();
                try {
                    int tok = this.getNext();
                    if (tok == 100000) {
                        result = this.getObject();
                        break;
                    }
                    if (tok == 35) {
                        result = this.getSymbol();
                        if (result.equals("Null")) {
                            result = null;
                            break;
                        }
                        this.seekMark(mark);
                        result = this.getComplex();
                        break;
                    }
                    this.seekMark(mark);
                    result = this.getComplex();
                }
                finally {
                    this.destroyMark(mark);
                }
            }
            case -10: {
                long mark = this.createMark();
                try {
                    int tok = this.getType();
                    if (tok == 100000) {
                        result = this.getObject();
                        break;
                    }
                    if (tok == 35) {
                        result = this.getSymbol();
                        if (result.equals("Null")) {
                            result = null;
                            break;
                        }
                        result = new BigInteger((String)result);
                        break;
                    }
                    result = new BigInteger(this.getString());
                }
                finally {
                    this.destroyMark(mark);
                }
            }
            case -11: {
                long mark = this.createMark();
                try {
                    int tok = this.getType();
                    if (tok == 100000) {
                        result = this.getObject();
                        break;
                    }
                    if (tok == 35) {
                        result = this.getSymbol();
                        if (result.equals("Null")) {
                            result = null;
                            break;
                        }
                        result = Utils.bigDecimalFromString((String)result);
                        break;
                    }
                    result = Utils.bigDecimalFromString(this.getString());
                }
                finally {
                    this.destroyMark(mark);
                }
            }
            case -12: {
                long mark = this.createMark();
                try {
                    int tok = this.getNext();
                    if (tok == 100000 && (result = this.getObject()) != null) break;
                    this.seekMark(mark);
                    result = this.getExpr();
                }
                finally {
                    this.destroyMark(mark);
                }
            }
            case -14: {
                result = this.getObject();
                break;
            }
            case -10000: {
                break;
            }
            default: {
                int tok = this.getNext();
                result = tok == 100000 || tok == 35 ? this.getObject() : (type > -34 ? this.getArray(type - -17, 1) : (type > -51 ? this.getArray(type - -34, 2) : (type > -68 ? this.getArray(type - -51, 3) : (type > -85 ? this.getArray(type - -68, 4) : this.getArray(type - -85, 5)))));
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean isObject() {
        long mark = 0;
        try {
            mark = this.createMark();
            boolean bl = this.getObject() != null;
            return bl;
        }
        catch (MathLinkException e) {
            this.clearError();
            boolean bl = false;
            return bl;
        }
        finally {
            if (mark != 0) {
                this.seekMark(mark);
                this.destroyMark(mark);
            }
        }
    }

    private boolean isRecoverableException(Throwable t) {
        return t instanceof Exception || t instanceof OutOfMemoryError || t instanceof LinkageError || t instanceof AssertionError;
    }

}

