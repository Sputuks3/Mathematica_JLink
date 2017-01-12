/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

public class MathLinkException
extends Exception {
    String msg;
    int code;
    Throwable wrappedException;

    public MathLinkException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MathLinkException(int code) {
        this.code = code;
        this.msg = MathLinkException.lookupMessageText(code);
    }

    public MathLinkException(Throwable e) {
        this(e, "");
    }

    public MathLinkException(Throwable e, String extraMsg) {
        this.code = 1006;
        this.wrappedException = e;
        String separator = "";
        if (extraMsg.endsWith(".")) {
            separator = " ";
        } else if (extraMsg.length() > 0) {
            separator = ". ";
        }
        this.msg = extraMsg + separator + "Was a wrapped exception. Original exception was: " + e.toString();
    }

    public int getErrCode() {
        return this.code;
    }

    public String getMessage() {
        return this.msg;
    }

    public Throwable getWrappedException() {
        return this.wrappedException;
    }

    public Throwable getCause() {
        return this.getWrappedException();
    }

    public String toString() {
        return "MathLinkException: " + String.valueOf(this.code) + ": " + this.msg;
    }

    static String lookupMessageText(int code) {
        String res = null;
        switch (code) {
            case 1002: {
                res = "Array is not as deep as requested.";
                break;
            }
            case 1003: {
                res = "Expression could not be read as a complex number.";
                break;
            }
            case 1005: {
                res = "The link was not connected before the requested time limit elapsed.";
                break;
            }
            case 1100: {
                res = "Expression on link is not a valid Java object reference.";
                break;
            }
            default: {
                res = "Extended error message not available.";
            }
        }
        return res;
    }
}

