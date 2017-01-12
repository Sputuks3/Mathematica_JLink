/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.JLinkClassLoader;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.PacketListener;

public interface KernelLink
extends MathLink {
    public static final String VERSION = "4.9.1";
    public static final double VERSION_NUMBER = 4.9;
    public static final String PACKAGE_CONTEXT = "JLink`";
    public static final int MLTKOBJECT = 100000;
    public static final int TYPE_OBJECT = -14;
    public static final int MLE_BAD_OBJECT = 1100;

    public void evaluate(String var1) throws MathLinkException;

    public void evaluate(Expr var1) throws MathLinkException;

    public String evaluateToInputForm(String var1, int var2);

    public String evaluateToInputForm(Expr var1, int var2);

    public String evaluateToOutputForm(String var1, int var2);

    public String evaluateToOutputForm(Expr var1, int var2);

    public byte[] evaluateToImage(String var1, int var2, int var3);

    public byte[] evaluateToImage(Expr var1, int var2, int var3);

    public byte[] evaluateToImage(String var1, int var2, int var3, int var4, boolean var5);

    public byte[] evaluateToImage(Expr var1, int var2, int var3, int var4, boolean var5);

    public byte[] evaluateToTypeset(String var1, int var2, boolean var3);

    public byte[] evaluateToTypeset(Expr var1, int var2, boolean var3);

    public Throwable getLastError();

    public int waitForAnswer() throws MathLinkException;

    public void discardAnswer() throws MathLinkException;

    public void handlePacket(int var1) throws MathLinkException;

    public void put(Object var1) throws MathLinkException;

    public void putReference(Object var1) throws MathLinkException;

    public Object getObject() throws MathLinkException;

    public int getNext() throws MathLinkException;

    public int getType() throws MathLinkException;

    public Object getArray(int var1, int var2) throws MathLinkException;

    public Object getArray(int var1, int var2, String[] var3) throws MathLinkException;

    public Object getArray(Class var1, int var2) throws MathLinkException;

    public Object getArray(Class var1, int var2, String[] var3) throws MathLinkException;

    public void enableObjectReferences() throws MathLinkException;

    public Expr enableObjectReferences(boolean var1) throws MathLinkException;

    public void addPacketListener(PacketListener var1);

    public void removePacketListener(PacketListener var1);

    public boolean notifyPacketListeners(int var1);

    public JLinkClassLoader getClassLoader();

    public void setClassLoader(JLinkClassLoader var1);

    public void abortEvaluation();

    public void interruptEvaluation();

    public void abandonEvaluation();

    public void terminateKernel();

    public void print(String var1);

    public void message(String var1, String var2);

    public void message(String var1, String[] var2);

    public void beginManual();

    public boolean wasInterrupted();

    public void clearInterrupt();
}

