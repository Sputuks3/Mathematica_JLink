/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsoleStream
extends OutputStream {
    private OutputStream wrappedStream = null;
    private static ConsoleStream stdout;
    private static ConsoleStream stderr;

    public static void setSystemStdoutStream(OutputStream strm) {
        System.setOut(new ConsolePrintStream(ConsoleStream.getStdoutStream().wrapStream(strm)));
    }

    public static void setSystemStderrStream(OutputStream strm) {
        System.setErr(new ConsolePrintStream(ConsoleStream.getStderrStream().wrapStream(strm)));
    }

    private ConsoleStream() {
    }

    static synchronized ConsoleStream getStdoutStream() {
        if (stdout == null) {
            stdout = new ConsoleStream();
        }
        return stdout;
    }

    static synchronized ConsoleStream getStderrStream() {
        if (stderr == null) {
            stderr = new ConsoleStream();
        }
        return stderr;
    }

    OutputStream getWrappedStream() {
        return this.wrappedStream;
    }

    private OutputStream wrapStream(OutputStream wrappedStream) {
        this.wrappedStream = wrappedStream;
        return this;
    }

    public void write(int b) throws IOException {
        if (this.wrappedStream != null) {
            this.wrappedStream.write(b);
        }
    }

    public void flush() throws IOException {
        if (this.wrappedStream != null) {
            this.wrappedStream.flush();
        }
    }

    public void close() throws IOException {
        if (this.wrappedStream != null) {
            this.wrappedStream.close();
        }
    }

    static class ConsolePrintStream
    extends PrintStream {
        ConsolePrintStream(OutputStream strm) {
            super(strm);
        }
    }

}

