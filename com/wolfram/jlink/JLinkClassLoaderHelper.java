/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.JLinkClassLoader;
import com.wolfram.jlink.Utils;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JLinkClassLoaderHelper
extends URLClassLoader {
    private final JLinkClassLoader top;
    private final JLinkClassLoaderHelper prevLoader;

    JLinkClassLoaderHelper(URL[] urls, JLinkClassLoaderHelper prevLoader, ClassLoader parent, JLinkClassLoader top) {
        super(urls, parent);
        this.prevLoader = prevLoader;
        this.top = top;
    }

    private Object lockObject() {
        return Thread.holdsLock(this) ? this : this.top;
    }

    @Override
    protected Object getClassLoadingLock(String className) {
        return this.lockObject();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Class findLoadedClassExposed(String name) {
        Object object = this.lockObject();
        synchronized (object) {
            Class c = null;
            if (this.prevLoader != null) {
                c = this.prevLoader.findLoadedClassExposed(name);
            }
            if (c == null) {
                c = this.findLoadedClass(name);
            }
            return c;
        }
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Class findClass(String name) throws ClassNotFoundException {
        Object object = this.lockObject();
        synchronized (object) {
            Class c = this.top.findLoadedClassExposed(name);
            if (c == null) {
                c = super.findClass(name);
            }
            return c;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Object object = this.lockObject();
        synchronized (object) {
            Class c = this.top.findLoadedClassExposed(name);
            if (c != null) {
                if (resolve) {
                    this.resolveClass(c);
                }
            } else {
                c = super.loadClass(name, resolve);
            }
            return c;
        }
    }

    @Override
    protected String findLibrary(String libName) {
        String platformSpecificName = System.mapLibraryName(libName);
        URL[] locs = this.getURLs();
        for (int i = 0; i < locs.length; ++i) {
            boolean isJarOrZip;
            if (!locs[i].getProtocol().equals("file")) continue;
            String fileName = locs[i].getFile();
            boolean bl = isJarOrZip = fileName.toLowerCase().endsWith(".zip") || fileName.toLowerCase().endsWith(".jar");
            if (isJarOrZip) continue;
            File f = new File(fileName, platformSpecificName);
            if (f.exists()) {
                return f.getAbsolutePath();
            }
            f = new File(fileName, "Libraries" + File.separator + Utils.getSystemID() + File.separator + platformSpecificName);
            if (!f.exists()) continue;
            return f.getAbsolutePath();
        }
        return null;
    }

    public Class classFromBytes(String className, byte[] bytes) {
        Class c = this.defineClass(className, bytes, 0, bytes.length);
        this.resolveClass(c);
        return c;
    }

    static {
        ClassLoader.registerAsParallelCapable();
    }
}

