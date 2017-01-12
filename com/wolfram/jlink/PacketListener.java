/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.PacketArrivedEvent;
import java.util.EventListener;

public interface PacketListener
extends EventListener {
    public boolean packetArrived(PacketArrivedEvent var1) throws MathLinkException;
}

