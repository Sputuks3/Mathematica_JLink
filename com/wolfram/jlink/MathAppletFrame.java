/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public class MathAppletFrame
extends Frame
implements Runnable,
AppletStub,
AppletContext {
    private String[] args = null;
    private String name;
    private Applet applet;
    private Dimension appletSize;
    private URL codeBase;

    public MathAppletFrame(Applet applet, String[] args) {
        this.applet = applet;
        this.args = args;
        applet.setStub(this);
        this.name = applet.getClass().getName();
        this.setTitle(this.name);
        if (args != null) {
            MathAppletFrame.parseArgs(args, System.getProperties());
        }
        String widthStr = this.getParameter("width");
        String heightStr = this.getParameter("height");
        int width = 300;
        int height = 300;
        if (widthStr != null && heightStr != null) {
            width = Integer.parseInt(widthStr);
            height = Integer.parseInt(heightStr);
        }
        this.setLayout(new BorderLayout());
        this.add("Center", applet);
        this.pack();
        this.validate();
        this.appletSize = applet.getSize();
        applet.setSize(width, height);
        this.setResizable(false);
        this.show();
        this.enableEvents(-1);
        new Thread(this).start();
    }

    private static void parseArgs(String[] args, Properties props) {
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            int ind = arg.indexOf(61);
            if (ind == -1) {
                props.put("parameter." + arg.toLowerCase(), "");
                continue;
            }
            props.put("parameter." + arg.substring(0, ind).toLowerCase(), arg.substring(ind + 1));
        }
    }

    protected void processEvent(AWTEvent evt) {
        if (evt.getID() == 201) {
            this.setVisible(false);
            this.remove(this.applet);
            this.applet.stop();
            this.applet.destroy();
            this.dispose();
        }
        super.processEvent(evt);
    }

    public void run() {
        this.applet.init();
        this.applet.start();
    }

    public boolean isActive() {
        return true;
    }

    public URL getDocumentBase() {
        String dir = System.getProperty("user.dir");
        String urlDir = dir.replace(File.separatorChar, '/');
        try {
            return new URL("file:" + urlDir + "/");
        }
        catch (MalformedURLException e) {
            return null;
        }
    }

    public URL getCodeBase() {
        if (this.codeBase == null) {
            String path = System.getProperty("java.class.path");
            StringTokenizer st = new StringTokenizer(path, ":");
            while (st.hasMoreElements()) {
                String dir = (String)st.nextElement();
                String filename = dir + File.separatorChar + this.name + ".class";
                File file = new File(filename);
                if (!file.exists()) continue;
                String urlDir = dir.replace(File.separatorChar, '/');
                try {
                    this.codeBase = new URL("file:" + urlDir + "/");
                    return this.codeBase;
                }
                catch (MalformedURLException e) {
                    return null;
                }
            }
            return null;
        }
        return this.codeBase;
    }

    public String getParameter(String name) {
        return System.getProperty("parameter." + name.toLowerCase());
    }

    public void appletResize(int width, int height) {
        Dimension frameSize = this.getSize();
        frameSize.width += width - this.appletSize.width;
        frameSize.height += height - this.appletSize.height;
        this.setSize(frameSize);
        this.appletSize = this.applet.getSize();
    }

    public AppletContext getAppletContext() {
        return this;
    }

    public AudioClip getAudioClip(URL url) {
        return null;
    }

    public Image getImage(URL url) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        try {
            ImageProducer prod = (ImageProducer)url.getContent();
            return tk.createImage(prod);
        }
        catch (IOException e) {
            return null;
        }
    }

    public Applet getApplet(String name) {
        if (name.equals(this.name)) {
            return this.applet;
        }
        return null;
    }

    public Enumeration getApplets() {
        Vector<Applet> v = new Vector<Applet>();
        v.addElement(this.applet);
        return v.elements();
    }

    public void showDocument(URL url) {
    }

    public void showDocument(URL url, String target) {
    }

    public void showStatus(String status) {
    }

    public void setStream(String key, InputStream stream) throws IOException {
    }

    public InputStream getStream(String key) {
        return null;
    }

    public Iterator getStreamKeys() {
        return null;
    }
}

