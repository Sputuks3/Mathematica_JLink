/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import java.lang.reflect.Method;

class MsgHandlerRecord {
    String methName;
    Method meth;
    Object target;

    MsgHandlerRecord(Object target, Method meth, String methName) {
        this.meth = meth;
        this.target = target;
        this.methName = methName;
    }
}

