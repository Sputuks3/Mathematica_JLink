/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.JLinkClassLoader;
import java.io.InputStream;
import java.net.URL;

public class JLinkSystemClassLoader
extends ClassLoader {
    private final ClassLoader origSystemLoader;

    private ClassLoader getDelegateLoader() {
        JLinkClassLoader jlinkLoader = JLinkClassLoader.getInstance();
        return jlinkLoader != null ? jlinkLoader : this.origSystemLoader;
    }

    public JLinkSystemClassLoader(ClassLoader origSystemLoader) {
        super(origSystemLoader);
        this.origSystemLoader = origSystemLoader;
    }

    public synchronized void clearAssertionStatus() {
        this.getDelegateLoader().clearAssertionStatus();
    }

    public URL getResource(String name) {
        return this.getDelegateLoader().getResource(name);
    }

    public InputStream getResourceAsStream(String name) {
        return this.getDelegateLoader().getResourceAsStream(name);
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        return this.getDelegateLoader().loadClass(name);
    }

    public synchronized void setClassAssertionStatus(String className, boolean enabled) {
        this.getDelegateLoader().setClassAssertionStatus(className, enabled);
    }

    public synchronized void setDefaultAssertionStatus(boolean enabled) {
        this.getDelegateLoader().setDefaultAssertionStatus(enabled);
    }

    public synchronized void setPackageAssertionStatus(String packageName, boolean enabled) {
        this.getDelegateLoader().setPackageAssertionStatus(packageName, enabled);
    }
}

