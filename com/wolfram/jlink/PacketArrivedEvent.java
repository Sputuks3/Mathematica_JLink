/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.KernelLink;
import java.util.EventObject;

public class PacketArrivedEvent
extends EventObject {
    private int pkt;

    PacketArrivedEvent(KernelLink ml, int pkt) {
        super(ml);
        this.pkt = pkt;
    }

    public int getPktType() {
        return this.pkt;
    }

    public Object getSource() {
        return super.getSource();
    }

    public String toString() {
        return "PacketArrivedEvent on KernelLink " + this.getSource().toString() + ". Packet type was " + this.pkt;
    }
}

