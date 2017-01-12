/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ext;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.MLFunction;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.ext.RemoteMathLink;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.MarshalException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.UnmarshalException;
import java.rmi.server.Operation;
import java.rmi.server.RemoteCall;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;

public final class NativeRemoteLink_Stub
extends RemoteStub
implements RemoteMathLink {
    private static final Operation[] operations = new Operation[]{new Operation("void activate()"), new Operation("boolean addMessageHandler(java.lang.Class, java.lang.Object, java.lang.String)"), new Operation("int bytesToGet()"), new Operation("int bytesToPut()"), new Operation("int checkFunction(java.lang.String)"), new Operation("void checkFunctionWithArgCount(java.lang.String, int)"), new Operation("boolean clearError()"), new Operation("void close()"), new Operation("void connect()"), new Operation("long createMark()"), new Operation("void destroyMark(long)"), new Operation("void endPacket()"), new Operation("int error()"), new Operation("java.lang.String errorMessage()"), new Operation("void flush()"), new Operation("int getArgCount()"), new Operation("java.lang.Object getArray(int, int, java.lang.String[])"), new Operation("boolean getBooleanArray1()[]"), new Operation("boolean getBooleanArray2()[][]"), new Operation("byte getByteArray1()[]"), new Operation("byte getByteArray2()[][]"), new Operation("byte getByteString(int)[]"), new Operation("char getCharArray1()[]"), new Operation("char getCharArray2()[][]"), new Operation("java.lang.Object getComplex()"), new Operation("java.lang.Object getComplexArray1()[]"), new Operation("java.lang.Object getComplexArray2()[][]"), new Operation("java.lang.Class getComplexClass()"), new Operation("byte getData(int)[]"), new Operation("double getDouble()"), new Operation("double getDoubleArray1()[]"), new Operation("double getDoubleArray2()[][]"), new Operation("com.wolfram.jlink.Expr getExpr()"), new Operation("float getFloatArray1()[]"), new Operation("float getFloatArray2()[][]"), new Operation("com.wolfram.jlink.MLFunction getFunction()"), new Operation("int getIntArray1()[]"), new Operation("int getIntArray2()[][]"), new Operation("int getInteger()"), new Operation("long getLongArray1()[]"), new Operation("long getLongArray2()[][]"), new Operation("long getLongInteger()"), new Operation("int getMessage()"), new Operation("int getNext()"), new Operation("short getShortArray1()[]"), new Operation("short getShortArray2()[][]"), new Operation("java.lang.String getString()"), new Operation("java.lang.String getStringArray1()[]"), new Operation("java.lang.String getStringArray2()[][]"), new Operation("java.lang.String getSymbol()"), new Operation("int getType()"), new Operation("boolean messageReady()"), new Operation("void newPacket()"), new Operation("int nextPacket()"), new Operation("com.wolfram.jlink.Expr peekExpr()"), new Operation("void put(double)"), new Operation("void put(int)"), new Operation("void put(long)"), new Operation("void put(java.lang.Object)"), new Operation("void put(java.lang.Object, java.lang.String[])"), new Operation("void put(boolean)"), new Operation("void putArgCount(int)"), new Operation("void putByteString(byte[])"), new Operation("void putData(byte[])"), new Operation("void putData(byte[], int)"), new Operation("void putFunction(java.lang.String, int)"), new Operation("void putMessage(int)"), new Operation("void putNext(int)"), new Operation("void putSize(int)"), new Operation("void putSymbol(java.lang.String)"), new Operation("boolean ready()"), new Operation("boolean removeMessageHandler(java.lang.String)"), new Operation("void seekMark(long)"), new Operation("void sendString(java.lang.String)"), new Operation("boolean setComplexClass(java.lang.Class)"), new Operation("void setError(int)"), new Operation("boolean setYieldFunction(java.lang.Class, java.lang.Object, java.lang.String)")};
    private static final long interfaceHash = -2864610998920603633L;
    private static final long serialVersionUID = 2;
    private static boolean useNewInvoke;
    private static Method $method_activate_0;
    private static Method $method_addMessageHandler_1;
    private static Method $method_bytesToGet_2;
    private static Method $method_bytesToPut_3;
    private static Method $method_checkFunction_4;
    private static Method $method_checkFunctionWithArgCount_5;
    private static Method $method_clearError_6;
    private static Method $method_close_7;
    private static Method $method_connect_8;
    private static Method $method_createMark_9;
    private static Method $method_destroyMark_10;
    private static Method $method_endPacket_11;
    private static Method $method_error_12;
    private static Method $method_errorMessage_13;
    private static Method $method_flush_14;
    private static Method $method_getArgCount_15;
    private static Method $method_getArray_16;
    private static Method $method_getBooleanArray1_17;
    private static Method $method_getBooleanArray2_18;
    private static Method $method_getByteArray1_19;
    private static Method $method_getByteArray2_20;
    private static Method $method_getByteString_21;
    private static Method $method_getCharArray1_22;
    private static Method $method_getCharArray2_23;
    private static Method $method_getComplex_24;
    private static Method $method_getComplexArray1_25;
    private static Method $method_getComplexArray2_26;
    private static Method $method_getComplexClass_27;
    private static Method $method_getData_28;
    private static Method $method_getDouble_29;
    private static Method $method_getDoubleArray1_30;
    private static Method $method_getDoubleArray2_31;
    private static Method $method_getExpr_32;
    private static Method $method_getFloatArray1_33;
    private static Method $method_getFloatArray2_34;
    private static Method $method_getFunction_35;
    private static Method $method_getIntArray1_36;
    private static Method $method_getIntArray2_37;
    private static Method $method_getInteger_38;
    private static Method $method_getLongArray1_39;
    private static Method $method_getLongArray2_40;
    private static Method $method_getLongInteger_41;
    private static Method $method_getMessage_42;
    private static Method $method_getNext_43;
    private static Method $method_getShortArray1_44;
    private static Method $method_getShortArray2_45;
    private static Method $method_getString_46;
    private static Method $method_getStringArray1_47;
    private static Method $method_getStringArray2_48;
    private static Method $method_getSymbol_49;
    private static Method $method_getType_50;
    private static Method $method_messageReady_51;
    private static Method $method_newPacket_52;
    private static Method $method_nextPacket_53;
    private static Method $method_peekExpr_54;
    private static Method $method_put_55;
    private static Method $method_put_56;
    private static Method $method_put_57;
    private static Method $method_put_58;
    private static Method $method_put_59;
    private static Method $method_put_60;
    private static Method $method_putArgCount_61;
    private static Method $method_putByteString_62;
    private static Method $method_putData_63;
    private static Method $method_putData_64;
    private static Method $method_putFunction_65;
    private static Method $method_putMessage_66;
    private static Method $method_putNext_67;
    private static Method $method_putSize_68;
    private static Method $method_putSymbol_69;
    private static Method $method_ready_70;
    private static Method $method_removeMessageHandler_71;
    private static Method $method_seekMark_72;
    private static Method $method_sendString_73;
    private static Method $method_setComplexClass_74;
    private static Method $method_setError_75;
    private static Method $method_setYieldFunction_76;
    static /* synthetic */ Class class$java$rmi$server$RemoteRef;
    static /* synthetic */ Class class$java$rmi$Remote;
    static /* synthetic */ Class class$java$lang$reflect$Method;
    static /* synthetic */ Class array$Ljava$lang$Object;
    static /* synthetic */ Class class$com$wolfram$jlink$ext$RemoteMathLink;
    static /* synthetic */ Class class$java$lang$Class;
    static /* synthetic */ Class class$java$lang$Object;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class array$Ljava$lang$String;
    static /* synthetic */ Class array$B;

    static {
        try {
            Class class_ = class$java$rmi$server$RemoteRef != null ? class$java$rmi$server$RemoteRef : (NativeRemoteLink_Stub.class$java$rmi$server$RemoteRef = NativeRemoteLink_Stub.class$("java.rmi.server.RemoteRef"));
            Class[] arrclass = new Class[4];
            Class class_2 = class$java$rmi$Remote != null ? class$java$rmi$Remote : (NativeRemoteLink_Stub.class$java$rmi$Remote = NativeRemoteLink_Stub.class$("java.rmi.Remote"));
            arrclass[0] = class_2;
            Class class_3 = class$java$lang$reflect$Method != null ? class$java$lang$reflect$Method : (NativeRemoteLink_Stub.class$java$lang$reflect$Method = NativeRemoteLink_Stub.class$("java.lang.reflect.Method"));
            arrclass[1] = class_3;
            Class class_4 = array$Ljava$lang$Object != null ? array$Ljava$lang$Object : (NativeRemoteLink_Stub.array$Ljava$lang$Object = NativeRemoteLink_Stub.class$("[Ljava.lang.Object;"));
            arrclass[2] = class_4;
            arrclass[3] = Long.TYPE;
            class_.getMethod("invoke", arrclass);
            useNewInvoke = true;
            Class class_5 = class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"));
            $method_activate_0 = class_5.getMethod("activate", new Class[0]);
            Class[] arrclass2 = new Class[3];
            Class class_6 = class$java$lang$Class != null ? class$java$lang$Class : (NativeRemoteLink_Stub.class$java$lang$Class = NativeRemoteLink_Stub.class$("java.lang.Class"));
            arrclass2[0] = class_6;
            Class class_7 = class$java$lang$Object != null ? class$java$lang$Object : (NativeRemoteLink_Stub.class$java$lang$Object = NativeRemoteLink_Stub.class$("java.lang.Object"));
            arrclass2[1] = class_7;
            Class class_8 = class$java$lang$String != null ? class$java$lang$String : (NativeRemoteLink_Stub.class$java$lang$String = NativeRemoteLink_Stub.class$("java.lang.String"));
            arrclass2[2] = class_8;
            $method_addMessageHandler_1 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("addMessageHandler", arrclass2);
            $method_bytesToGet_2 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("bytesToGet", new Class[0]);
            $method_bytesToPut_3 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("bytesToPut", new Class[0]);
            Class[] arrclass3 = new Class[1];
            arrclass3[0] = class$java$lang$String != null ? class$java$lang$String : (NativeRemoteLink_Stub.class$java$lang$String = NativeRemoteLink_Stub.class$("java.lang.String"));
            $method_checkFunction_4 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("checkFunction", arrclass3);
            Class[] arrclass4 = new Class[2];
            arrclass4[0] = class$java$lang$String != null ? class$java$lang$String : (NativeRemoteLink_Stub.class$java$lang$String = NativeRemoteLink_Stub.class$("java.lang.String"));
            arrclass4[1] = Integer.TYPE;
            $method_checkFunctionWithArgCount_5 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("checkFunctionWithArgCount", arrclass4);
            $method_clearError_6 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("clearError", new Class[0]);
            $method_close_7 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("close", new Class[0]);
            $method_connect_8 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("connect", new Class[0]);
            $method_createMark_9 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("createMark", new Class[0]);
            $method_destroyMark_10 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("destroyMark", Long.TYPE);
            $method_endPacket_11 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("endPacket", new Class[0]);
            $method_error_12 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("error", new Class[0]);
            $method_errorMessage_13 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("errorMessage", new Class[0]);
            $method_flush_14 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("flush", new Class[0]);
            $method_getArgCount_15 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getArgCount", new Class[0]);
            Class[] arrclass5 = new Class[3];
            arrclass5[0] = Integer.TYPE;
            arrclass5[1] = Integer.TYPE;
            Class class_9 = array$Ljava$lang$String != null ? array$Ljava$lang$String : (NativeRemoteLink_Stub.array$Ljava$lang$String = NativeRemoteLink_Stub.class$("[Ljava.lang.String;"));
            arrclass5[2] = class_9;
            $method_getArray_16 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getArray", arrclass5);
            $method_getBooleanArray1_17 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getBooleanArray1", new Class[0]);
            $method_getBooleanArray2_18 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getBooleanArray2", new Class[0]);
            $method_getByteArray1_19 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getByteArray1", new Class[0]);
            $method_getByteArray2_20 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getByteArray2", new Class[0]);
            $method_getByteString_21 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getByteString", Integer.TYPE);
            $method_getCharArray1_22 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getCharArray1", new Class[0]);
            $method_getCharArray2_23 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getCharArray2", new Class[0]);
            $method_getComplex_24 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getComplex", new Class[0]);
            $method_getComplexArray1_25 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getComplexArray1", new Class[0]);
            $method_getComplexArray2_26 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getComplexArray2", new Class[0]);
            $method_getComplexClass_27 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getComplexClass", new Class[0]);
            $method_getData_28 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getData", Integer.TYPE);
            $method_getDouble_29 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getDouble", new Class[0]);
            $method_getDoubleArray1_30 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getDoubleArray1", new Class[0]);
            $method_getDoubleArray2_31 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getDoubleArray2", new Class[0]);
            $method_getExpr_32 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getExpr", new Class[0]);
            $method_getFloatArray1_33 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getFloatArray1", new Class[0]);
            $method_getFloatArray2_34 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getFloatArray2", new Class[0]);
            $method_getFunction_35 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getFunction", new Class[0]);
            $method_getIntArray1_36 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getIntArray1", new Class[0]);
            $method_getIntArray2_37 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getIntArray2", new Class[0]);
            $method_getInteger_38 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getInteger", new Class[0]);
            $method_getLongArray1_39 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getLongArray1", new Class[0]);
            $method_getLongArray2_40 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getLongArray2", new Class[0]);
            $method_getLongInteger_41 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getLongInteger", new Class[0]);
            $method_getMessage_42 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getMessage", new Class[0]);
            $method_getNext_43 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getNext", new Class[0]);
            $method_getShortArray1_44 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getShortArray1", new Class[0]);
            $method_getShortArray2_45 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getShortArray2", new Class[0]);
            $method_getString_46 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getString", new Class[0]);
            $method_getStringArray1_47 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getStringArray1", new Class[0]);
            $method_getStringArray2_48 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getStringArray2", new Class[0]);
            $method_getSymbol_49 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getSymbol", new Class[0]);
            $method_getType_50 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("getType", new Class[0]);
            $method_messageReady_51 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("messageReady", new Class[0]);
            $method_newPacket_52 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("newPacket", new Class[0]);
            $method_nextPacket_53 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("nextPacket", new Class[0]);
            $method_peekExpr_54 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("peekExpr", new Class[0]);
            $method_put_55 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("put", Double.TYPE);
            $method_put_56 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("put", Integer.TYPE);
            $method_put_57 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("put", Long.TYPE);
            Class[] arrclass6 = new Class[1];
            arrclass6[0] = class$java$lang$Object != null ? class$java$lang$Object : (NativeRemoteLink_Stub.class$java$lang$Object = NativeRemoteLink_Stub.class$("java.lang.Object"));
            $method_put_58 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("put", arrclass6);
            Class[] arrclass7 = new Class[2];
            arrclass7[0] = class$java$lang$Object != null ? class$java$lang$Object : (NativeRemoteLink_Stub.class$java$lang$Object = NativeRemoteLink_Stub.class$("java.lang.Object"));
            arrclass7[1] = array$Ljava$lang$String != null ? array$Ljava$lang$String : (NativeRemoteLink_Stub.array$Ljava$lang$String = NativeRemoteLink_Stub.class$("[Ljava.lang.String;"));
            $method_put_59 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("put", arrclass7);
            $method_put_60 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("put", Boolean.TYPE);
            $method_putArgCount_61 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("putArgCount", Integer.TYPE);
            Class[] arrclass8 = new Class[1];
            Class class_10 = array$B != null ? array$B : (NativeRemoteLink_Stub.array$B = NativeRemoteLink_Stub.class$("[B"));
            arrclass8[0] = class_10;
            $method_putByteString_62 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("putByteString", arrclass8);
            Class[] arrclass9 = new Class[1];
            arrclass9[0] = array$B != null ? array$B : (NativeRemoteLink_Stub.array$B = NativeRemoteLink_Stub.class$("[B"));
            $method_putData_63 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("putData", arrclass9);
            Class[] arrclass10 = new Class[2];
            arrclass10[0] = array$B != null ? array$B : (NativeRemoteLink_Stub.array$B = NativeRemoteLink_Stub.class$("[B"));
            arrclass10[1] = Integer.TYPE;
            $method_putData_64 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("putData", arrclass10);
            Class[] arrclass11 = new Class[2];
            arrclass11[0] = class$java$lang$String != null ? class$java$lang$String : (NativeRemoteLink_Stub.class$java$lang$String = NativeRemoteLink_Stub.class$("java.lang.String"));
            arrclass11[1] = Integer.TYPE;
            $method_putFunction_65 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("putFunction", arrclass11);
            $method_putMessage_66 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("putMessage", Integer.TYPE);
            $method_putNext_67 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("putNext", Integer.TYPE);
            $method_putSize_68 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("putSize", Integer.TYPE);
            Class[] arrclass12 = new Class[1];
            arrclass12[0] = class$java$lang$String != null ? class$java$lang$String : (NativeRemoteLink_Stub.class$java$lang$String = NativeRemoteLink_Stub.class$("java.lang.String"));
            $method_putSymbol_69 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("putSymbol", arrclass12);
            $method_ready_70 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("ready", new Class[0]);
            Class[] arrclass13 = new Class[1];
            arrclass13[0] = class$java$lang$String != null ? class$java$lang$String : (NativeRemoteLink_Stub.class$java$lang$String = NativeRemoteLink_Stub.class$("java.lang.String"));
            $method_removeMessageHandler_71 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("removeMessageHandler", arrclass13);
            $method_seekMark_72 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("seekMark", Long.TYPE);
            Class[] arrclass14 = new Class[1];
            arrclass14[0] = class$java$lang$String != null ? class$java$lang$String : (NativeRemoteLink_Stub.class$java$lang$String = NativeRemoteLink_Stub.class$("java.lang.String"));
            $method_sendString_73 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("sendString", arrclass14);
            Class[] arrclass15 = new Class[1];
            arrclass15[0] = class$java$lang$Class != null ? class$java$lang$Class : (NativeRemoteLink_Stub.class$java$lang$Class = NativeRemoteLink_Stub.class$("java.lang.Class"));
            $method_setComplexClass_74 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("setComplexClass", arrclass15);
            $method_setError_75 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("setError", Integer.TYPE);
            Class[] arrclass16 = new Class[3];
            arrclass16[0] = class$java$lang$Class != null ? class$java$lang$Class : (NativeRemoteLink_Stub.class$java$lang$Class = NativeRemoteLink_Stub.class$("java.lang.Class"));
            arrclass16[1] = class$java$lang$Object != null ? class$java$lang$Object : (NativeRemoteLink_Stub.class$java$lang$Object = NativeRemoteLink_Stub.class$("java.lang.Object"));
            arrclass16[2] = class$java$lang$String != null ? class$java$lang$String : (NativeRemoteLink_Stub.class$java$lang$String = NativeRemoteLink_Stub.class$("java.lang.String"));
            $method_setYieldFunction_76 = (class$com$wolfram$jlink$ext$RemoteMathLink != null ? class$com$wolfram$jlink$ext$RemoteMathLink : (NativeRemoteLink_Stub.class$com$wolfram$jlink$ext$RemoteMathLink = NativeRemoteLink_Stub.class$("com.wolfram.jlink.ext.RemoteMathLink"))).getMethod("setYieldFunction", arrclass16);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            useNewInvoke = false;
        }
    }

    public NativeRemoteLink_Stub() {
    }

    public NativeRemoteLink_Stub(RemoteRef remoteRef) {
        super(remoteRef);
    }

    public void activate() throws MathLinkException, RemoteException {
        try {
            if (useNewInvoke) {
                this.ref.invoke(this, $method_activate_0, null, 191707267406792988L);
            } else {
                RemoteCall remoteCall = this.ref.newCall(this, operations, 0, -2864610998920603633L);
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean addMessageHandler(Class class_, Object object, String string) throws RemoteException {
        try {
            RemoteCall remoteCall;
            boolean bl;
            if (useNewInvoke) {
                Object object2 = this.ref.invoke(this, $method_addMessageHandler_1, new Object[]{class_, object, string}, -2719586101422140918L);
                return (Boolean)object2;
            }
            remoteCall = this.ref.newCall(this, operations, 1, -2864610998920603633L);
            try {
                ObjectOutput objectOutput = remoteCall.getOutputStream();
                objectOutput.writeObject(class_);
                objectOutput.writeObject(object);
                objectOutput.writeObject(string);
            }
            catch (IOException iOException) {
                throw new MarshalException("error marshalling arguments", iOException);
            }
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    bl = objectInput.readBoolean();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var7_15 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var7_14 = null;
            this.ref.done(remoteCall);
            return bl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int bytesToGet() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int n;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_bytesToGet_2, null, -3522954800231590613L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 2, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int bytesToPut() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int n;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_bytesToPut_3, null, -1409200243753533095L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 3, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int checkFunction(String string) throws MathLinkException, RemoteException {
        try {
            int n;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_checkFunction_4, new Object[]{string}, -6838679377629315437L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 4, -2864610998920603633L);
            try {
                ObjectOutput objectOutput = remoteCall.getOutputStream();
                objectOutput.writeObject(string);
            }
            catch (IOException iOException) {
                throw new MarshalException("error marshalling arguments", iOException);
            }
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var5_14 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var5_13 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void checkFunctionWithArgCount(String string, int n) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_checkFunctionWithArgCount_5, new Object[]{string, new Integer(n)}, 329426063108410998L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 5, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeObject(string);
                    objectOutput.writeInt(n);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    static /* synthetic */ Class class$(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    public boolean clearError() throws RemoteException {
        try {
            boolean bl;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_clearError_6, null, -6258227538426776421L);
                return (Boolean)object;
            }
            remoteCall = this.ref.newCall(this, operations, 6, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    bl = objectInput.readBoolean();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_10 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_9 = null;
            this.ref.done(remoteCall);
            return bl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void close() throws RemoteException {
        try {
            if (useNewInvoke) {
                this.ref.invoke(this, $method_close_7, null, -4742752445160157748L);
            } else {
                RemoteCall remoteCall = this.ref.newCall(this, operations, 7, -2864610998920603633L);
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void connect() throws MathLinkException, RemoteException {
        try {
            if (useNewInvoke) {
                this.ref.invoke(this, $method_connect_8, null, -8663404940870220521L);
            } else {
                RemoteCall remoteCall = this.ref.newCall(this, operations, 8, -2864610998920603633L);
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public long createMark() throws MathLinkException, RemoteException {
        try {
            long l;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_createMark_9, null, -8761879688135624552L);
                return (Long)object;
            }
            remoteCall = this.ref.newCall(this, operations, 9, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    l = objectInput.readLong();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var5_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var5_10 = null;
            this.ref.done(remoteCall);
            return l;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void destroyMark(long l) throws RemoteException {
        block7 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_destroyMark_10, new Object[]{new Long(l)}, 8486987938091002316L);
                    break block7;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 10, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeLong(l);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void endPacket() throws MathLinkException, RemoteException {
        try {
            if (useNewInvoke) {
                this.ref.invoke(this, $method_endPacket_11, null, -6491048780422358000L);
            } else {
                RemoteCall remoteCall = this.ref.newCall(this, operations, 11, -2864610998920603633L);
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int error() throws RemoteException {
        try {
            RemoteCall remoteCall;
            int n;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_error_12, null, -7541538629477009245L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 12, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_10 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_9 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String errorMessage() throws RemoteException {
        try {
            RemoteCall remoteCall;
            String string;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_errorMessage_13, null, 4503467170055339191L);
                return (String)object;
            }
            remoteCall = this.ref.newCall(this, operations, 13, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    string = (String)objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return string;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void flush() throws MathLinkException, RemoteException {
        try {
            if (useNewInvoke) {
                this.ref.invoke(this, $method_flush_14, null, 5055409455670303531L);
            } else {
                RemoteCall remoteCall = this.ref.newCall(this, operations, 14, -2864610998920603633L);
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int getArgCount() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int n;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getArgCount_15, null, -3816347137009882276L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 15, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Object getArray(int n, int n2, String[] arrstring) throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            Object object;
            if (useNewInvoke) {
                Object object2 = this.ref.invoke(this, $method_getArray_16, new Object[]{new Integer(n), new Integer(n2), arrstring}, 8585293549185659462L);
                return object2;
            }
            remoteCall = this.ref.newCall(this, operations, 16, -2864610998920603633L);
            try {
                object = remoteCall.getOutputStream();
                object.writeInt(n);
                object.writeInt(n2);
                object.writeObject(arrstring);
            }
            catch (IOException iOException) {
                throw new MarshalException("error marshalling arguments", iOException);
            }
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    object = objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var7_16 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var7_15 = null;
            this.ref.done(remoteCall);
            return object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean[] getBooleanArray1() throws MathLinkException, RemoteException {
        try {
            boolean[] arrbl;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getBooleanArray1_17, null, 4750082946751214568L);
                return (boolean[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 17, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrbl = (boolean[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrbl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean[][] getBooleanArray2() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            boolean[][] arrbl;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getBooleanArray2_18, null, 4578484375938805770L);
                return (boolean[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 18, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrbl = (boolean[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrbl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public byte[] getByteArray1() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            byte[] arrby;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getByteArray1_19, null, -6796330760611878403L);
                return (byte[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 19, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrby = (byte[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrby;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public byte[][] getByteArray2() throws MathLinkException, RemoteException {
        try {
            byte[][] arrby;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getByteArray2_20, null, -2204492877104724321L);
                return (byte[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 20, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrby = (byte[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrby;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public byte[] getByteString(int n) throws MathLinkException, RemoteException {
        try {
            byte[] arrby;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getByteString_21, new Object[]{new Integer(n)}, -2316216654832746041L);
                return (byte[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 21, -2864610998920603633L);
            try {
                arrby = remoteCall.getOutputStream();
                arrby.writeInt(n);
            }
            catch (IOException iOException) {
                throw new MarshalException("error marshalling arguments", iOException);
            }
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrby = (byte[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var5_14 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var5_13 = null;
            this.ref.done(remoteCall);
            return arrby;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public char[] getCharArray1() throws MathLinkException, RemoteException {
        try {
            char[] arrc;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getCharArray1_22, null, -6357363227755554727L);
                return (char[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 22, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrc = (char[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrc;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public char[][] getCharArray2() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            char[][] arrc;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getCharArray2_23, null, -7575336937004015054L);
                return (char[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 23, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrc = (char[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrc;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Object getComplex() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            Object object;
            if (useNewInvoke) {
                Object object2 = this.ref.invoke(this, $method_getComplex_24, null, 5939130357870715756L);
                return object2;
            }
            remoteCall = this.ref.newCall(this, operations, 24, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    object = objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return object;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Object[] getComplexArray1() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            Object[] arrobject;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getComplexArray1_25, null, 93753434778583688L);
                return (Object[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 25, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrobject = (Object[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrobject;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Object[][] getComplexArray2() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            Object[][] arrobject;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getComplexArray2_26, null, 2447607313048569366L);
                return (Object[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 26, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrobject = (Object[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrobject;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Class getComplexClass() throws RemoteException {
        try {
            RemoteCall remoteCall;
            Class class_;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getComplexClass_27, null, 8834680805976821336L);
                return (Class)object;
            }
            remoteCall = this.ref.newCall(this, operations, 27, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    class_ = (Class)objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return class_;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public byte[] getData(int n) throws MathLinkException, RemoteException {
        try {
            byte[] arrby;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getData_28, new Object[]{new Integer(n)}, -6316819470406046809L);
                return (byte[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 28, -2864610998920603633L);
            try {
                arrby = remoteCall.getOutputStream();
                arrby.writeInt(n);
            }
            catch (IOException iOException) {
                throw new MarshalException("error marshalling arguments", iOException);
            }
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrby = (byte[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var5_14 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var5_13 = null;
            this.ref.done(remoteCall);
            return arrby;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public double getDouble() throws MathLinkException, RemoteException {
        try {
            double d;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getDouble_29, null, 1578421879104859276L);
                return (Double)object;
            }
            remoteCall = this.ref.newCall(this, operations, 29, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    d = objectInput.readDouble();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var5_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var5_10 = null;
            this.ref.done(remoteCall);
            return d;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public double[] getDoubleArray1() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            double[] arrd;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getDoubleArray1_30, null, -5693838659026110350L);
                return (double[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 30, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrd = (double[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrd;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public double[][] getDoubleArray2() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            double[][] arrd;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getDoubleArray2_31, null, 8021302069079763287L);
                return (double[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 31, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrd = (double[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrd;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Expr getExpr() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            Expr expr;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getExpr_32, null, -7912746306060596821L);
                return (Expr)object;
            }
            remoteCall = this.ref.newCall(this, operations, 32, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    expr = (Expr)objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return expr;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public float[] getFloatArray1() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            float[] arrf;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getFloatArray1_33, null, -3659908475389637927L);
                return (float[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 33, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrf = (float[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrf;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public float[][] getFloatArray2() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            float[][] arrf;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getFloatArray2_34, null, -7805701316115320748L);
                return (float[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 34, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrf = (float[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrf;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public MLFunction getFunction() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            MLFunction mLFunction;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getFunction_35, null, 5429646895785785041L);
                return (MLFunction)object;
            }
            remoteCall = this.ref.newCall(this, operations, 35, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    mLFunction = (MLFunction)objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return mLFunction;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int[] getIntArray1() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int[] arrn;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getIntArray1_36, null, 3588340150270252566L);
                return (int[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 36, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrn = (int[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrn;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int[][] getIntArray2() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int[][] arrn;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getIntArray2_37, null, -7747156811356899124L);
                return (int[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 37, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrn = (int[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrn;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int getInteger() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int n;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getInteger_38, null, 7851166811166107966L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 38, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public long[] getLongArray1() throws MathLinkException, RemoteException {
        try {
            long[] arrl;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getLongArray1_39, null, -5543248157965194013L);
                return (long[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 39, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrl = (long[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public long[][] getLongArray2() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            long[][] arrl;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getLongArray2_40, null, -5453072922111353735L);
                return (long[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 40, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrl = (long[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public long getLongInteger() throws MathLinkException, RemoteException {
        try {
            long l;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getLongInteger_41, null, -1528548931045975830L);
                return (Long)object;
            }
            remoteCall = this.ref.newCall(this, operations, 41, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    l = objectInput.readLong();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var5_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var5_10 = null;
            this.ref.done(remoteCall);
            return l;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int getMessage() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int n;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getMessage_42, null, 860505403890833922L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 42, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int getNext() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int n;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getNext_43, null, -5384943886561251895L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 43, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public short[] getShortArray1() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            short[] arrs;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getShortArray1_44, null, -5869977260901986343L);
                return (short[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 44, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrs = (short[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrs;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public short[][] getShortArray2() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            short[][] arrs;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getShortArray2_45, null, -2880462286377360754L);
                return (short[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 45, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrs = (short[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrs;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String getString() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            String string;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getString_46, null, 3588264124367649661L);
                return (String)object;
            }
            remoteCall = this.ref.newCall(this, operations, 46, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    string = (String)objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return string;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String[] getStringArray1() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            String[] arrstring;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getStringArray1_47, null, -8412853611685606747L);
                return (String[])object;
            }
            remoteCall = this.ref.newCall(this, operations, 47, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrstring = (String[])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrstring;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String[][] getStringArray2() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            String[][] arrstring;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getStringArray2_48, null, -1906284808883095322L);
                return (String[][])object;
            }
            remoteCall = this.ref.newCall(this, operations, 48, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    arrstring = (String[][])objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return arrstring;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public String getSymbol() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            String string;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getSymbol_49, null, -2483870871753500737L);
                return (String)object;
            }
            remoteCall = this.ref.newCall(this, operations, 49, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    string = (String)objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return string;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int getType() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int n;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_getType_50, null, -7030183335631564859L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 50, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean messageReady() throws MathLinkException, RemoteException {
        try {
            boolean bl;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_messageReady_51, null, 8623214208273510477L);
                return (Boolean)object;
            }
            remoteCall = this.ref.newCall(this, operations, 51, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    bl = objectInput.readBoolean();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return bl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void newPacket() throws RemoteException {
        try {
            if (useNewInvoke) {
                this.ref.invoke(this, $method_newPacket_52, null, 105215354685777672L);
            } else {
                RemoteCall remoteCall = this.ref.newCall(this, operations, 52, -2864610998920603633L);
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public int nextPacket() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            int n;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_nextPacket_53, null, -3288618848660966759L);
                return (Integer)object;
            }
            remoteCall = this.ref.newCall(this, operations, 53, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    n = objectInput.readInt();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return n;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public Expr peekExpr() throws MathLinkException, RemoteException {
        try {
            RemoteCall remoteCall;
            Expr expr;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_peekExpr_54, null, -9218499979653081330L);
                return (Expr)object;
            }
            remoteCall = this.ref.newCall(this, operations, 54, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    expr = (Expr)objectInput.readObject();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new UnmarshalException("error unmarshalling return", classNotFoundException);
                }
            }
            catch (Throwable throwable) {
                Object var4_12 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_11 = null;
            this.ref.done(remoteCall);
            return expr;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void put(double d) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_put_55, new Object[]{new Double(d)}, -7671978153085267449L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 55, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeDouble(d);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void put(int n) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_put_56, new Object[]{new Integer(n)}, -6947845164726441836L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 56, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeInt(n);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void put(long l) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_put_57, new Object[]{new Long(l)}, -5451969808669268034L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 57, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeLong(l);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void put(Object object) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_put_58, new Object[]{object}, -1635576820600370783L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 58, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeObject(object);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void put(Object object, String[] arrstring) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_put_59, new Object[]{object, arrstring}, -7068267322803028444L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 59, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeObject(object);
                    objectOutput.writeObject(arrstring);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void put(boolean bl) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    Object[] arrobject = new Object[1];
                    arrobject[0] = bl ? Boolean.TRUE : Boolean.FALSE;
                    this.ref.invoke(this, $method_put_60, arrobject, 367266848888537469L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 60, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeBoolean(bl);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void putArgCount(int n) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_putArgCount_61, new Object[]{new Integer(n)}, 2425626231026243859L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 61, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeInt(n);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void putByteString(byte[] arrby) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_putByteString_62, new Object[]{arrby}, 4921319707361321904L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 62, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeObject(arrby);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void putData(byte[] arrby) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_putData_63, new Object[]{arrby}, 1707490566199767025L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 63, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeObject(arrby);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void putData(byte[] arrby, int n) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_putData_64, new Object[]{arrby, new Integer(n)}, -3223044736956303255L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 64, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeObject(arrby);
                    objectOutput.writeInt(n);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void putFunction(String string, int n) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_putFunction_65, new Object[]{string, new Integer(n)}, -5131334581818615839L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 65, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeObject(string);
                    objectOutput.writeInt(n);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void putMessage(int n) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_putMessage_66, new Object[]{new Integer(n)}, -5516514823284710571L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 66, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeInt(n);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void putNext(int n) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_putNext_67, new Object[]{new Integer(n)}, 431011167505714520L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 67, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeInt(n);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void putSize(int n) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_putSize_68, new Object[]{new Integer(n)}, -5154818993544830018L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 68, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeInt(n);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void putSymbol(String string) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_putSymbol_69, new Object[]{string}, -2431489408813159486L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 69, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeObject(string);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public boolean ready() throws MathLinkException, RemoteException {
        try {
            boolean bl;
            RemoteCall remoteCall;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_ready_70, null, -6078373644588592097L);
                return (Boolean)object;
            }
            remoteCall = this.ref.newCall(this, operations, 70, -2864610998920603633L);
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    bl = objectInput.readBoolean();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var4_11 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var4_10 = null;
            this.ref.done(remoteCall);
            return bl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (MathLinkException mathLinkException) {
            throw mathLinkException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public boolean removeMessageHandler(String string) throws RemoteException {
        try {
            RemoteCall remoteCall;
            boolean bl;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_removeMessageHandler_71, new Object[]{string}, 7494001101339907234L);
                return (Boolean)object;
            }
            remoteCall = this.ref.newCall(this, operations, 71, -2864610998920603633L);
            try {
                ObjectOutput objectOutput = remoteCall.getOutputStream();
                objectOutput.writeObject(string);
            }
            catch (IOException iOException) {
                throw new MarshalException("error marshalling arguments", iOException);
            }
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    bl = objectInput.readBoolean();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var5_13 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var5_12 = null;
            this.ref.done(remoteCall);
            return bl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void seekMark(long l) throws RemoteException {
        block7 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_seekMark_72, new Object[]{new Long(l)}, -8401822581380247572L);
                    break block7;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 72, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeLong(l);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public void sendString(String string) throws MathLinkException, RemoteException {
        block8 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_sendString_73, new Object[]{string}, -1683715236238101498L);
                    break block8;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 73, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeObject(string);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (MathLinkException mathLinkException) {
                throw mathLinkException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public boolean setComplexClass(Class class_) throws RemoteException {
        try {
            RemoteCall remoteCall;
            boolean bl;
            if (useNewInvoke) {
                Object object = this.ref.invoke(this, $method_setComplexClass_74, new Object[]{class_}, -4664027127687828751L);
                return (Boolean)object;
            }
            remoteCall = this.ref.newCall(this, operations, 74, -2864610998920603633L);
            try {
                ObjectOutput objectOutput = remoteCall.getOutputStream();
                objectOutput.writeObject(class_);
            }
            catch (IOException iOException) {
                throw new MarshalException("error marshalling arguments", iOException);
            }
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    bl = objectInput.readBoolean();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var5_13 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var5_12 = null;
            this.ref.done(remoteCall);
            return bl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }

    public void setError(int n) throws RemoteException {
        block7 : {
            try {
                if (useNewInvoke) {
                    this.ref.invoke(this, $method_setError_75, new Object[]{new Integer(n)}, -2852454753654284208L);
                    break block7;
                }
                RemoteCall remoteCall = this.ref.newCall(this, operations, 75, -2864610998920603633L);
                try {
                    ObjectOutput objectOutput = remoteCall.getOutputStream();
                    objectOutput.writeInt(n);
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling arguments", iOException);
                }
                this.ref.invoke(remoteCall);
                this.ref.done(remoteCall);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
            catch (RemoteException remoteException) {
                throw remoteException;
            }
            catch (Exception exception) {
                throw new UnexpectedException("undeclared checked exception", exception);
            }
        }
    }

    public boolean setYieldFunction(Class class_, Object object, String string) throws RemoteException {
        try {
            RemoteCall remoteCall;
            boolean bl;
            if (useNewInvoke) {
                Object object2 = this.ref.invoke(this, $method_setYieldFunction_76, new Object[]{class_, object, string}, 6489295443030655416L);
                return (Boolean)object2;
            }
            remoteCall = this.ref.newCall(this, operations, 76, -2864610998920603633L);
            try {
                ObjectOutput objectOutput = remoteCall.getOutputStream();
                objectOutput.writeObject(class_);
                objectOutput.writeObject(object);
                objectOutput.writeObject(string);
            }
            catch (IOException iOException) {
                throw new MarshalException("error marshalling arguments", iOException);
            }
            this.ref.invoke(remoteCall);
            try {
                try {
                    ObjectInput objectInput = remoteCall.getInputStream();
                    bl = objectInput.readBoolean();
                }
                catch (IOException iOException) {
                    throw new UnmarshalException("error unmarshalling return", iOException);
                }
            }
            catch (Throwable throwable) {
                Object var7_15 = null;
                this.ref.done(remoteCall);
                throw throwable;
            }
            Object var7_14 = null;
            this.ref.done(remoteCall);
            return bl;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (RemoteException remoteException) {
            throw remoteException;
        }
        catch (Exception exception) {
            throw new UnexpectedException("undeclared checked exception", exception);
        }
    }
}

