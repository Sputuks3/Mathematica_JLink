/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import java.io.Serializable;

public class MLFunction
implements Serializable {
    public String name;
    public int argCount;

    protected MLFunction(String name, int argc) {
        this.name = name;
        this.argCount = argc;
    }
}

