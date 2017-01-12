/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

class NumberRangeException
extends Exception {
    private int ivalue = 0;
    private double dvalue = 0.0;
    private String type;

    public NumberRangeException(int offendingValue, String type) {
        this.ivalue = offendingValue;
        this.type = type;
    }

    public NumberRangeException(double offendingValue, String type) {
        this.dvalue = offendingValue;
        this.type = type;
    }

    public String toString() {
        return "NumberRangeException: Argument " + (this.ivalue != 0 ? String.valueOf(this.ivalue) : String.valueOf(this.dvalue)) + " out of range for parameter type " + this.type + ".";
    }
}

