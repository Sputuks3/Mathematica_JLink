/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.JLinkClassLoaderHelper;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.StdLink;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class JLinkClassLoader
extends ClassLoader {
    protected volatile JLinkClassLoaderHelper helper;

    public synchronized void clearAssertionStatus() {
        this.helper.clearAssertionStatus();
    }

    public synchronized void setClassAssertionStatus(String className, boolean enabled) {
        this.helper.setClassAssertionStatus(className, enabled);
    }

    public synchronized void setDefaultAssertionStatus(boolean enabled) {
        this.helper.setDefaultAssertionStatus(enabled);
    }

    public synchronized void setPackageAssertionStatus(String packageName, boolean enabled) {
        this.helper.setPackageAssertionStatus(packageName, enabled);
    }

    public synchronized URL getResource(String name) {
        return this.helper.getResource(name);
    }

    public Enumeration getResources(String name) throws IOException {
        return this.helper.getResources(name);
    }

    public synchronized InputStream getResourceAsStream(String name) {
        return this.helper.getResourceAsStream(name);
    }

    public synchronized Class loadClass(String name) throws ClassNotFoundException {
        return this.helper.loadClass(name);
    }

    Class findLoadedClassExposed(String name) {
        return this.helper.findLoadedClassExposed(name);
    }

    public static JLinkClassLoader getInstance() {
        KernelLink link = StdLink.getLink();
        return link != null ? link.getClassLoader() : null;
    }

    public static Class classFromName(String name) throws ClassNotFoundException {
        return Class.forName(name, false, JLinkClassLoader.getInstance());
    }

    public JLinkClassLoader() {
        this(JLinkClassLoader.class.getClassLoader());
    }

    public JLinkClassLoader(ClassLoader parent) {
        super(parent);
        this.helper = new JLinkClassLoaderHelper(new URL[0], null, parent, this);
    }

    public synchronized void addLocations(String[] locations, boolean searchForJars) {
        this.addLocations(locations, searchForJars, false);
    }

    public synchronized void addLocations(String[] locations, boolean searchForJars, boolean prepend) {
        if (locations == null) {
            return;
        }
        ArrayList<URL> urlsToAdd = new ArrayList<URL>(locations.length);
        for (int i = 0; i < locations.length; ++i) {
            String thisLocation = locations[i];
            if (thisLocation.toLowerCase().startsWith("http:")) {
                try {
                    urlsToAdd.add(new URL(thisLocation));
                }
                catch (MalformedURLException e) {}
                continue;
            }
            if (!thisLocation.toLowerCase().endsWith(".jar") && !thisLocation.toLowerCase().endsWith(".zip")) {
                String dirName = thisLocation.endsWith(File.separator) ? thisLocation : thisLocation + File.separator;
                try {
                    String[] filesInDir;
                    urlsToAdd.add(JLinkClassLoader.fileToURL(new File(dirName)));
                    if (!searchForJars || (filesInDir = new File(dirName).list()) == null) continue;
                    for (int j = 0; j < filesInDir.length; ++j) {
                        String lowerCaseFile = filesInDir[j].toLowerCase();
                        if (!lowerCaseFile.endsWith(".jar") && !lowerCaseFile.endsWith(".zip")) continue;
                        File f = new File(dirName, filesInDir[j]);
                        urlsToAdd.add(JLinkClassLoader.fileToURL(f));
                    }
                    continue;
                }
                catch (Exception e) {
                    continue;
                }
            }
            try {
                urlsToAdd.add(JLinkClassLoader.fileToURL(new File(thisLocation)));
                continue;
            }
            catch (Exception e) {
                // empty catch block
            }
        }
        this.addAll(urlsToAdd, prepend);
    }

    public synchronized String[] getClassPath() {
        URL[] existingLocs = this.getURLs();
        String[] result = new String[existingLocs.length];
        for (int i = 0; i < existingLocs.length; ++i) {
            if (existingLocs[i].getProtocol().equals("file")) {
                String fileString = existingLocs[i].getFile();
                try {
                    URI u = new URI(existingLocs[i].toString());
                    fileString = u.getPath();
                }
                catch (Exception e) {
                    // empty catch block
                }
                result[i] = fileString;
                continue;
            }
            result[i] = existingLocs[i].toString();
        }
        return result;
    }

    public synchronized Class classFromBytes(String className, byte[] bytes) {
        return this.helper.classFromBytes(className, bytes);
    }

    private void addAll(List urlsToAdd, boolean prepend) {
        URL[] existingLocs = this.getURLs();
        if (prepend) {
            if (urlsToAdd.size() > 0) {
                ArrayList<URL> newURLSet = new ArrayList<URL>(existingLocs.length + urlsToAdd.size());
                newURLSet.addAll(urlsToAdd);
                for (int i = 0; i < existingLocs.length; ++i) {
                    URL u = existingLocs[i];
                    if (urlsToAdd.contains(u)) continue;
                    newURLSet.add(u);
                }
                this.helper = new JLinkClassLoaderHelper(newURLSet.toArray(new URL[newURLSet.size()]), this.helper, this.getParent(), this);
            }
        } else {
            urlsToAdd.removeAll(Arrays.asList(existingLocs));
            Iterator iter = urlsToAdd.iterator();
            while (iter.hasNext()) {
                this.helper.addURL((URL)iter.next());
            }
        }
    }

    private URL[] getURLs() {
        return this.helper.getURLs();
    }

    private static URL fileToURL(File f) throws MalformedURLException {
        return f.toURI().toURL();
    }
}

