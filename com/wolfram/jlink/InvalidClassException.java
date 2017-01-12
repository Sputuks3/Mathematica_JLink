/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

class InvalidClassException
extends Exception {
    InvalidClassException() {
        super("Invalid class specification. This class is not loaded into the current Java runtime. It might have been loaded into a different Java runtime, or Java might have been restarted since the class was loaded.");
    }
}

