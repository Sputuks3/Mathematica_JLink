/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.PacketArrivedEvent;
import com.wolfram.jlink.PacketListener;
import java.io.PrintStream;

public class PacketPrinter
implements PacketListener {
    private PrintStream strm;

    public PacketPrinter() {
        this(System.out);
    }

    public PacketPrinter(PrintStream strm) {
        this.strm = strm;
    }

    public boolean packetArrived(PacketArrivedEvent evt) throws MathLinkException {
        KernelLink ml = (KernelLink)evt.getSource();
        int pkt = evt.getPktType();
        int argCount = pkt == 5 ? 2 : 1;
        this.strm.println("Packet type was " + PacketPrinter.pktToName(pkt) + ". Contents follows.");
        for (int i = 0; i < argCount; ++i) {
            Expr e = ml.getExpr();
            this.strm.println(e.toString());
            e.dispose();
        }
        return true;
    }

    private static String pktToName(int pkt) {
        switch (pkt) {
            case 0: {
                return "ILLEGALPKT";
            }
            case 7: {
                return "CallPacket";
            }
            case 13: {
                return "EvaluatePacket";
            }
            case 3: {
                return "ReturnPacket";
            }
            case 8: {
                return "InputNamePacket";
            }
            case 14: {
                return "EnterTextPacket";
            }
            case 15: {
                return "EnterExpressionPacket";
            }
            case 9: {
                return "OutputNamePacket";
            }
            case 4: {
                return "ReturnTextPacket";
            }
            case 16: {
                return "ReturnExpressionPacket";
            }
            case 11: {
                return "DisplayPacket";
            }
            case 12: {
                return "DisplayEndPacket";
            }
            case 5: {
                return "MessagePacket";
            }
            case 2: {
                return "TextPacket";
            }
            case 1: {
                return "InputPacket";
            }
            case 21: {
                return "InputStringPacket";
            }
            case 6: {
                return "MenuPacket";
            }
            case 10: {
                return "SyntaxPacket";
            }
            case 17: {
                return "SuspendPacket";
            }
            case 18: {
                return "ResumePacket";
            }
            case 19: {
                return "BeginDialogPacket";
            }
            case 20: {
                return "EndDialogPacket";
            }
            case 100: {
                return "Special Front End-Defined Packet";
            }
            case 101: {
                return "Special Front End-Defined Packet: ExpressionPacket";
            }
        }
        return "No such packet exists";
    }
}

