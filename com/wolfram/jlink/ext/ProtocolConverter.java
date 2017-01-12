/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ext;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.ExprFormatException;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import java.io.PrintStream;

public class ProtocolConverter {
    private KernelLink fe2k;
    private KernelLink k2fe;

    public static void main(String[] argv) {
        KernelLink fe2me = null;
        KernelLink me2k = null;
        try {
            fe2me = MathLinkFactory.createKernelLink(argv);
            fe2me.connect();
            fe2me.putFunction("InputNamePacket", 1);
            fe2me.put("In[1]:= ");
            fe2me.flush();
        }
        catch (MathLinkException e) {
            System.out.println("Error establishing link back to the launching program.");
            return;
        }
        String specialProtocol = ProtocolConverter.extractProtocol(argv);
        String specialName = ProtocolConverter.extractName(argv);
        if (specialName == null || specialProtocol == null) {
            fe2me.close();
            System.out.println("ProtocolConverter: Invalid specification of -name or -prot options.");
            return;
        }
        try {
            me2k = MathLinkFactory.createKernelLink("-linkmode launch -linkname " + specialName + " -linkprotocol " + specialProtocol);
            me2k.connect();
        }
        catch (MathLinkException e) {
            System.out.println("ProtocolConverter: Error establishing link to the kernel. " + e.toString());
            try {
                Thread.sleep(4000);
            }
            catch (InterruptedException exc) {
                // empty catch block
            }
            return;
        }
        try {
            boolean firstTime = true;
            do {
                if (fe2me.ready()) {
                    me2k.transferExpression(fe2me);
                    me2k.flush();
                } else if (me2k.ready()) {
                    Expr e = me2k.getExpr();
                    boolean isFirstINP = false;
                    if (firstTime) {
                        try {
                            isFirstINP = e.part(1).stringQ() && e.part(1).asString().startsWith("In[1]:=");
                        }
                        catch (ExprFormatException ee) {
                            // empty catch block
                        }
                    }
                    firstTime = false;
                    if (!isFirstINP) {
                        fe2me.put(e);
                        fe2me.flush();
                    }
                }
                if (fe2me.error() != 0 || me2k.error() != 0) {
                    throw new MathLinkException(fe2me.error(), fe2me.errorMessage());
                }
                try {
                    Thread.sleep(20);
                }
                catch (InterruptedException e) {}
            } while (true);
        }
        catch (MathLinkException e) {
            System.out.println("ProtocolConverter: Error in main loop: " + e.toString());
            fe2me.close();
            me2k.close();
            return;
        }
    }

    private static String extractProtocol(String[] argv) {
        String prot = null;
        for (int i = 0; i < argv.length - 1; ++i) {
            if (!argv[i].toLowerCase().equals("-prot")) continue;
            prot = argv[i + 1].toUpperCase();
            break;
        }
        return prot;
    }

    private static String extractName(String[] argv) {
        String name = null;
        for (int i = 0; i < argv.length - 1; ++i) {
            if (!argv[i].toLowerCase().equals("-name")) continue;
            name = argv[i + 1];
            break;
        }
        return name;
    }
}

