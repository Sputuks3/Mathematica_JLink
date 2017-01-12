/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ext;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.MLFunction;
import com.wolfram.jlink.ext.NativeRemoteLink;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.MarshalException;
import java.rmi.Remote;
import java.rmi.UnmarshalException;
import java.rmi.server.Operation;
import java.rmi.server.RemoteCall;
import java.rmi.server.Skeleton;
import java.rmi.server.SkeletonMismatchException;

public final class NativeRemoteLink_Skel
implements Skeleton {
    private static final Operation[] operations = new Operation[]{new Operation("void activate()"), new Operation("boolean addMessageHandler(java.lang.Class, java.lang.Object, java.lang.String)"), new Operation("int bytesToGet()"), new Operation("int bytesToPut()"), new Operation("int checkFunction(java.lang.String)"), new Operation("void checkFunctionWithArgCount(java.lang.String, int)"), new Operation("boolean clearError()"), new Operation("void close()"), new Operation("void connect()"), new Operation("long createMark()"), new Operation("void destroyMark(long)"), new Operation("void endPacket()"), new Operation("int error()"), new Operation("java.lang.String errorMessage()"), new Operation("void flush()"), new Operation("int getArgCount()"), new Operation("java.lang.Object getArray(int, int, java.lang.String[])"), new Operation("boolean getBooleanArray1()[]"), new Operation("boolean getBooleanArray2()[][]"), new Operation("byte getByteArray1()[]"), new Operation("byte getByteArray2()[][]"), new Operation("byte getByteString(int)[]"), new Operation("char getCharArray1()[]"), new Operation("char getCharArray2()[][]"), new Operation("java.lang.Object getComplex()"), new Operation("java.lang.Object getComplexArray1()[]"), new Operation("java.lang.Object getComplexArray2()[][]"), new Operation("java.lang.Class getComplexClass()"), new Operation("byte getData(int)[]"), new Operation("double getDouble()"), new Operation("double getDoubleArray1()[]"), new Operation("double getDoubleArray2()[][]"), new Operation("com.wolfram.jlink.Expr getExpr()"), new Operation("float getFloatArray1()[]"), new Operation("float getFloatArray2()[][]"), new Operation("com.wolfram.jlink.MLFunction getFunction()"), new Operation("int getIntArray1()[]"), new Operation("int getIntArray2()[][]"), new Operation("int getInteger()"), new Operation("long getLongArray1()[]"), new Operation("long getLongArray2()[][]"), new Operation("long getLongInteger()"), new Operation("int getMessage()"), new Operation("int getNext()"), new Operation("short getShortArray1()[]"), new Operation("short getShortArray2()[][]"), new Operation("java.lang.String getString()"), new Operation("java.lang.String getStringArray1()[]"), new Operation("java.lang.String getStringArray2()[][]"), new Operation("java.lang.String getSymbol()"), new Operation("int getType()"), new Operation("boolean messageReady()"), new Operation("void newPacket()"), new Operation("int nextPacket()"), new Operation("com.wolfram.jlink.Expr peekExpr()"), new Operation("void put(double)"), new Operation("void put(int)"), new Operation("void put(long)"), new Operation("void put(java.lang.Object)"), new Operation("void put(java.lang.Object, java.lang.String[])"), new Operation("void put(boolean)"), new Operation("void putArgCount(int)"), new Operation("void putByteString(byte[])"), new Operation("void putData(byte[])"), new Operation("void putData(byte[], int)"), new Operation("void putFunction(java.lang.String, int)"), new Operation("void putMessage(int)"), new Operation("void putNext(int)"), new Operation("void putSize(int)"), new Operation("void putSymbol(java.lang.String)"), new Operation("boolean ready()"), new Operation("boolean removeMessageHandler(java.lang.String)"), new Operation("void seekMark(long)"), new Operation("void sendString(java.lang.String)"), new Operation("boolean setComplexClass(java.lang.Class)"), new Operation("void setError(int)"), new Operation("boolean setYieldFunction(java.lang.Class, java.lang.Object, java.lang.String)")};
    private static final long interfaceHash = -2864610998920603633L;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void dispatch(Remote remote, RemoteCall remoteCall, int n, long l) throws Exception {
        if (n < 0) {
            if (l == 191707267406792988L) {
                n = 0;
            } else if (l == -2719586101422140918L) {
                n = 1;
            } else if (l == -3522954800231590613L) {
                n = 2;
            } else if (l == -1409200243753533095L) {
                n = 3;
            } else if (l == -6838679377629315437L) {
                n = 4;
            } else if (l == 329426063108410998L) {
                n = 5;
            } else if (l == -6258227538426776421L) {
                n = 6;
            } else if (l == -4742752445160157748L) {
                n = 7;
            } else if (l == -8663404940870220521L) {
                n = 8;
            } else if (l == -8761879688135624552L) {
                n = 9;
            } else if (l == 8486987938091002316L) {
                n = 10;
            } else if (l == -6491048780422358000L) {
                n = 11;
            } else if (l == -7541538629477009245L) {
                n = 12;
            } else if (l == 4503467170055339191L) {
                n = 13;
            } else if (l == 5055409455670303531L) {
                n = 14;
            } else if (l == -3816347137009882276L) {
                n = 15;
            } else if (l == 8585293549185659462L) {
                n = 16;
            } else if (l == 4750082946751214568L) {
                n = 17;
            } else if (l == 4578484375938805770L) {
                n = 18;
            } else if (l == -6796330760611878403L) {
                n = 19;
            } else if (l == -2204492877104724321L) {
                n = 20;
            } else if (l == -2316216654832746041L) {
                n = 21;
            } else if (l == -6357363227755554727L) {
                n = 22;
            } else if (l == -7575336937004015054L) {
                n = 23;
            } else if (l == 5939130357870715756L) {
                n = 24;
            } else if (l == 93753434778583688L) {
                n = 25;
            } else if (l == 2447607313048569366L) {
                n = 26;
            } else if (l == 8834680805976821336L) {
                n = 27;
            } else if (l == -6316819470406046809L) {
                n = 28;
            } else if (l == 1578421879104859276L) {
                n = 29;
            } else if (l == -5693838659026110350L) {
                n = 30;
            } else if (l == 8021302069079763287L) {
                n = 31;
            } else if (l == -7912746306060596821L) {
                n = 32;
            } else if (l == -3659908475389637927L) {
                n = 33;
            } else if (l == -7805701316115320748L) {
                n = 34;
            } else if (l == 5429646895785785041L) {
                n = 35;
            } else if (l == 3588340150270252566L) {
                n = 36;
            } else if (l == -7747156811356899124L) {
                n = 37;
            } else if (l == 7851166811166107966L) {
                n = 38;
            } else if (l == -5543248157965194013L) {
                n = 39;
            } else if (l == -5453072922111353735L) {
                n = 40;
            } else if (l == -1528548931045975830L) {
                n = 41;
            } else if (l == 860505403890833922L) {
                n = 42;
            } else if (l == -5384943886561251895L) {
                n = 43;
            } else if (l == -5869977260901986343L) {
                n = 44;
            } else if (l == -2880462286377360754L) {
                n = 45;
            } else if (l == 3588264124367649661L) {
                n = 46;
            } else if (l == -8412853611685606747L) {
                n = 47;
            } else if (l == -1906284808883095322L) {
                n = 48;
            } else if (l == -2483870871753500737L) {
                n = 49;
            } else if (l == -7030183335631564859L) {
                n = 50;
            } else if (l == 8623214208273510477L) {
                n = 51;
            } else if (l == 105215354685777672L) {
                n = 52;
            } else if (l == -3288618848660966759L) {
                n = 53;
            } else if (l == -9218499979653081330L) {
                n = 54;
            } else if (l == -7671978153085267449L) {
                n = 55;
            } else if (l == -6947845164726441836L) {
                n = 56;
            } else if (l == -5451969808669268034L) {
                n = 57;
            } else if (l == -1635576820600370783L) {
                n = 58;
            } else if (l == -7068267322803028444L) {
                n = 59;
            } else if (l == 367266848888537469L) {
                n = 60;
            } else if (l == 2425626231026243859L) {
                n = 61;
            } else if (l == 4921319707361321904L) {
                n = 62;
            } else if (l == 1707490566199767025L) {
                n = 63;
            } else if (l == -3223044736956303255L) {
                n = 64;
            } else if (l == -5131334581818615839L) {
                n = 65;
            } else if (l == -5516514823284710571L) {
                n = 66;
            } else if (l == 431011167505714520L) {
                n = 67;
            } else if (l == -5154818993544830018L) {
                n = 68;
            } else if (l == -2431489408813159486L) {
                n = 69;
            } else if (l == -6078373644588592097L) {
                n = 70;
            } else if (l == 7494001101339907234L) {
                n = 71;
            } else if (l == -8401822581380247572L) {
                n = 72;
            } else if (l == -1683715236238101498L) {
                n = 73;
            } else if (l == -4664027127687828751L) {
                n = 74;
            } else if (l == -2852454753654284208L) {
                n = 75;
            } else {
                if (l != 6489295443030655416L) throw new UnmarshalException("invalid method hash");
                n = 76;
            }
        } else if (l != -2864610998920603633L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        NativeRemoteLink nativeRemoteLink = (NativeRemoteLink)remote;
        switch (n) {
            case 0: {
                remoteCall.releaseInputStream();
                nativeRemoteLink.activate();
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 1: {
                String string;
                Object object;
                Class class_;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        class_ = (Class)objectInput.readObject();
                        object = objectInput.readObject();
                        string = (String)objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var11_278 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                ObjectOutput objectOutput = null;
                remoteCall.releaseInputStream();
                boolean bl = nativeRemoteLink.addMessageHandler(class_, object, string);
                try {
                    objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeBoolean(bl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 2: {
                remoteCall.releaseInputStream();
                int n2 = nativeRemoteLink.bytesToGet();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n2);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 3: {
                remoteCall.releaseInputStream();
                int n3 = nativeRemoteLink.bytesToPut();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n3);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 4: {
                String string;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        string = (String)objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_215 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                ObjectOutput objectOutput = null;
                remoteCall.releaseInputStream();
                int n4 = nativeRemoteLink.checkFunction(string);
                try {
                    objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n4);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 5: {
                int n5;
                String string;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        string = (String)objectInput.readObject();
                        n5 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var10_312 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var10_311 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.checkFunctionWithArgCount(string, n5);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 6: {
                remoteCall.releaseInputStream();
                boolean bl = nativeRemoteLink.clearError();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeBoolean(bl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 7: {
                remoteCall.releaseInputStream();
                nativeRemoteLink.close();
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 8: {
                remoteCall.releaseInputStream();
                nativeRemoteLink.connect();
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 9: {
                remoteCall.releaseInputStream();
                long l2 = nativeRemoteLink.createMark();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeLong(l2);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 10: {
                long l3;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        l3 = objectInput.readLong();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var10_314 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var10_313 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.destroyMark(l3);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 11: {
                remoteCall.releaseInputStream();
                nativeRemoteLink.endPacket();
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 12: {
                remoteCall.releaseInputStream();
                int n6 = nativeRemoteLink.error();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n6);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 13: {
                remoteCall.releaseInputStream();
                String string = nativeRemoteLink.errorMessage();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(string);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 14: {
                remoteCall.releaseInputStream();
                nativeRemoteLink.flush();
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 15: {
                remoteCall.releaseInputStream();
                int n7 = nativeRemoteLink.getArgCount();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n7);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 16: {
                int n8;
                String[] arrstring;
                int n9;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        n8 = objectInput.readInt();
                        n9 = objectInput.readInt();
                        arrstring = (String[])objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var11_286 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                ObjectOutput objectOutput = null;
                remoteCall.releaseInputStream();
                Object object = nativeRemoteLink.getArray(n8, n9, arrstring);
                try {
                    objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(object);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 17: {
                remoteCall.releaseInputStream();
                boolean[] arrbl = nativeRemoteLink.getBooleanArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrbl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 18: {
                remoteCall.releaseInputStream();
                boolean[][] arrbl = nativeRemoteLink.getBooleanArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrbl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 19: {
                remoteCall.releaseInputStream();
                byte[] arrby = nativeRemoteLink.getByteArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrby);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 20: {
                remoteCall.releaseInputStream();
                byte[][] arrby = nativeRemoteLink.getByteArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrby);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 21: {
                int n10;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        n10 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_225 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                ObjectOutput objectOutput = null;
                remoteCall.releaseInputStream();
                byte[] arrby = nativeRemoteLink.getByteString(n10);
                try {
                    objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrby);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 22: {
                remoteCall.releaseInputStream();
                char[] arrc = nativeRemoteLink.getCharArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrc);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 23: {
                remoteCall.releaseInputStream();
                char[][] arrc = nativeRemoteLink.getCharArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrc);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 24: {
                remoteCall.releaseInputStream();
                Object object = nativeRemoteLink.getComplex();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(object);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 25: {
                remoteCall.releaseInputStream();
                Object[] arrobject = nativeRemoteLink.getComplexArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrobject);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 26: {
                remoteCall.releaseInputStream();
                Object[][] arrobject = nativeRemoteLink.getComplexArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrobject);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 27: {
                remoteCall.releaseInputStream();
                Class class_ = nativeRemoteLink.getComplexClass();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(class_);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 28: {
                int n11;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        n11 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_228 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                ObjectOutput objectOutput = null;
                remoteCall.releaseInputStream();
                byte[] arrby = nativeRemoteLink.getData(n11);
                try {
                    objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrby);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 29: {
                remoteCall.releaseInputStream();
                double d = nativeRemoteLink.getDouble();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeDouble(d);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 30: {
                remoteCall.releaseInputStream();
                double[] arrd = nativeRemoteLink.getDoubleArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrd);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 31: {
                remoteCall.releaseInputStream();
                double[][] arrd = nativeRemoteLink.getDoubleArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrd);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 32: {
                remoteCall.releaseInputStream();
                Expr expr = nativeRemoteLink.getExpr();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(expr);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 33: {
                remoteCall.releaseInputStream();
                float[] arrf = nativeRemoteLink.getFloatArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrf);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 34: {
                remoteCall.releaseInputStream();
                float[][] arrf = nativeRemoteLink.getFloatArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrf);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 35: {
                remoteCall.releaseInputStream();
                MLFunction mLFunction = nativeRemoteLink.getFunction();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(mLFunction);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 36: {
                remoteCall.releaseInputStream();
                int[] arrn = nativeRemoteLink.getIntArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrn);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 37: {
                remoteCall.releaseInputStream();
                int[][] arrn = nativeRemoteLink.getIntArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrn);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 38: {
                remoteCall.releaseInputStream();
                int n12 = nativeRemoteLink.getInteger();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n12);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 39: {
                remoteCall.releaseInputStream();
                long[] arrl = nativeRemoteLink.getLongArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 40: {
                remoteCall.releaseInputStream();
                long[][] arrl = nativeRemoteLink.getLongArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 41: {
                remoteCall.releaseInputStream();
                long l4 = nativeRemoteLink.getLongInteger();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeLong(l4);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 42: {
                remoteCall.releaseInputStream();
                int n13 = nativeRemoteLink.getMessage();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n13);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 43: {
                remoteCall.releaseInputStream();
                int n14 = nativeRemoteLink.getNext();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n14);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 44: {
                remoteCall.releaseInputStream();
                short[] arrs = nativeRemoteLink.getShortArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrs);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 45: {
                remoteCall.releaseInputStream();
                short[][] arrs = nativeRemoteLink.getShortArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrs);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 46: {
                remoteCall.releaseInputStream();
                String string = nativeRemoteLink.getString();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(string);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 47: {
                remoteCall.releaseInputStream();
                String[] arrstring = nativeRemoteLink.getStringArray1();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrstring);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 48: {
                remoteCall.releaseInputStream();
                String[][] arrstring = nativeRemoteLink.getStringArray2();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(arrstring);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 49: {
                remoteCall.releaseInputStream();
                String string = nativeRemoteLink.getSymbol();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(string);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 50: {
                remoteCall.releaseInputStream();
                int n15 = nativeRemoteLink.getType();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n15);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 51: {
                remoteCall.releaseInputStream();
                boolean bl = nativeRemoteLink.messageReady();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeBoolean(bl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 52: {
                remoteCall.releaseInputStream();
                nativeRemoteLink.newPacket();
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 53: {
                remoteCall.releaseInputStream();
                int n16 = nativeRemoteLink.nextPacket();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeInt(n16);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 54: {
                remoteCall.releaseInputStream();
                Expr expr = nativeRemoteLink.peekExpr();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeObject(expr);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 55: {
                double d;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        d = objectInput.readDouble();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var10_322 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var10_321 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.put(d);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 56: {
                int n17;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        n17 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_237 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_236 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.put(n17);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 57: {
                long l5;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        l5 = objectInput.readLong();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var10_326 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var10_325 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.put(l5);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 58: {
                Object object;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        object = objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_241 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_240 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.put(object);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 59: {
                Object object;
                String[] arrstring;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        object = objectInput.readObject();
                        arrstring = (String[])objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var10_331 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var10_330 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.put(object, arrstring);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 60: {
                boolean bl;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        bl = objectInput.readBoolean();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_245 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_244 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.put(bl);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 61: {
                int n18;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        n18 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_247 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_246 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.putArgCount(n18);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 62: {
                byte[] arrby;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        arrby = (byte[])objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_249 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_248 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.putByteString(arrby);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 63: {
                byte[] arrby;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        arrby = (byte[])objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_251 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_250 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.putData(arrby);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 64: {
                byte[] arrby;
                int n19;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        arrby = (byte[])objectInput.readObject();
                        n19 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var10_343 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var10_342 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.putData(arrby, n19);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 65: {
                int n20;
                String string;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        string = (String)objectInput.readObject();
                        n20 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var10_345 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var10_344 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.putFunction(string, n20);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 66: {
                int n21;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        n21 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_257 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_256 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.putMessage(n21);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 67: {
                int n22;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        n22 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_259 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_258 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.putNext(n22);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 68: {
                int n23;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        n23 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_261 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_260 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.putSize(n23);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 69: {
                String string;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        string = (String)objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_263 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_262 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.putSymbol(string);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 70: {
                remoteCall.releaseInputStream();
                boolean bl = nativeRemoteLink.ready();
                try {
                    ObjectOutput objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeBoolean(bl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 71: {
                String string;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        string = (String)objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_265 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                ObjectOutput objectOutput = null;
                remoteCall.releaseInputStream();
                boolean bl = nativeRemoteLink.removeMessageHandler(string);
                try {
                    objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeBoolean(bl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 72: {
                long l6;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        l6 = objectInput.readLong();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var10_359 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var10_358 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.seekMark(l6);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 73: {
                String string;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        string = (String)objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_270 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_269 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.sendString(string);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 74: {
                Class class_;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        class_ = (Class)objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_272 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                ObjectOutput objectOutput = null;
                remoteCall.releaseInputStream();
                boolean bl = nativeRemoteLink.setComplexClass(class_);
                try {
                    objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeBoolean(bl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 75: {
                int n24;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        n24 = objectInput.readInt();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                }
                catch (Throwable throwable) {
                    Object var9_275 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                Object var9_274 = null;
                remoteCall.releaseInputStream();
                nativeRemoteLink.setError(n24);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            case 76: {
                Class class_;
                String string;
                Object object;
                try {
                    try {
                        ObjectInput objectInput = remoteCall.getInputStream();
                        class_ = (Class)objectInput.readObject();
                        object = objectInput.readObject();
                        string = (String)objectInput.readObject();
                    }
                    catch (IOException iOException) {
                        throw new UnmarshalException("error unmarshalling arguments", iOException);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new UnmarshalException("error unmarshalling arguments", classNotFoundException);
                    }
                }
                catch (Throwable throwable) {
                    Object var11_304 = null;
                    remoteCall.releaseInputStream();
                    throw throwable;
                }
                ObjectOutput objectOutput = null;
                remoteCall.releaseInputStream();
                boolean bl = nativeRemoteLink.setYieldFunction(class_, object, string);
                try {
                    objectOutput = remoteCall.getResultStream(true);
                    objectOutput.writeBoolean(bl);
                    return;
                }
                catch (IOException iOException) {
                    throw new MarshalException("error marshalling return", iOException);
                }
            }
            default: {
                throw new UnmarshalException("invalid method number");
            }
        }
    }

    public Operation[] getOperations() {
        return (Operation[])operations.clone();
    }
}

