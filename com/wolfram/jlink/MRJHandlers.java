/*
 * Decompiled with CFR 0_119.
 * 
 * Could not load the following classes:
 *  com.apple.eawt.Application
 *  com.apple.eawt.ApplicationAdapter
 *  com.apple.eawt.ApplicationEvent
 *  com.apple.mrj.MRJAboutHandler
 *  com.apple.mrj.MRJApplicationUtils
 *  com.apple.mrj.MRJQuitHandler
 */
package com.wolfram.jlink;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;
import com.apple.mrj.MRJAboutHandler;
import com.apple.mrj.MRJApplicationUtils;
import com.apple.mrj.MRJQuitHandler;
import com.wolfram.jlink.JLinkSecurityManager;
import com.wolfram.jlink.MathFrame;
import com.wolfram.jlink.Utils;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

public class MRJHandlers
implements MRJAboutHandler,
MRJQuitHandler {
    private MathFrame aboutFrame = new MathFrame("About J/Link");
    private MathFrame quitFrame = new MathFrame("Quit J/Link?");
    private MRJActionListener listener;
    private Button okButton;
    private Button quitButton;
    private Button dontQuitButton;
    private static boolean isSetup = false;

    public static synchronized void setup() {
        if (!isSetup) {
            isSetup = true;
            MRJHandlers hndlrs = new MRJHandlers();
            if (MRJApplicationUtils.isMRJToolkitAvailable()) {
                MRJApplicationUtils.registerAboutHandler((MRJAboutHandler)hndlrs);
                MRJApplicationUtils.registerQuitHandler((MRJQuitHandler)hndlrs);
            } else {
                Application app = new Application();
                JLinkApplicationAdapter jaa = new JLinkApplicationAdapter(hndlrs);
                try {
                    Method meth = app.getClass().getMethod("addApplicationListener", Class.forName("com.apple.eawt.ApplicationListener"));
                    meth.invoke((Object)app, new Object[]{jaa});
                }
                catch (Exception e) {
                    // empty catch block
                }
            }
        }
    }

    private MRJHandlers() {
        this.listener = new MRJActionListener();
        this.okButton = new Button("OK");
        this.quitButton = new Button("Quit");
        this.dontQuitButton = new Button("Don't Quit");
        this.aboutFrame.setResizable(false);
        this.aboutFrame.setSize(370, 180);
        this.aboutFrame.setLocation(200, 200);
        this.aboutFrame.setLayout(null);
        Label l1 = new Label("                           J/Link version " + Utils.getJLinkVersion());
        Label l2 = new Label("     Copyright (c) 1999-2016, Wolfram Research, Inc.");
        Label l3 = new Label("This program is launched and managed by Mathematica");
        Label l4 = new Label("to support calling Java code from Mathematica.");
        Font f = new Font("Dialog", 1, 12);
        l1.setFont(f);
        l2.setFont(f);
        l3.setFont(f);
        l4.setFont(f);
        this.aboutFrame.add(l1);
        this.aboutFrame.add(l2);
        this.aboutFrame.add(l3);
        this.aboutFrame.add(l4);
        this.aboutFrame.addNotify();
        Insets in = this.aboutFrame.getInsets();
        Dimension sz = this.aboutFrame.getSize();
        l1.setBounds(in.left + 10, in.top + 20, 360, 20);
        l2.setBounds(in.left + 10, in.top + 40, 360, 20);
        l3.setBounds(in.left + 10, in.top + 80, 360, 20);
        l4.setBounds(in.left + 10, in.top + 100, 360, 20);
        this.okButton.addActionListener(this.listener);
        this.aboutFrame.add(this.okButton);
        this.okButton.setBounds((sz.width - in.left - in.right - 60) / 2, sz.height - 30, 60, 28);
        this.quitFrame.setResizable(false);
        this.quitFrame.setSize(370, 180);
        this.quitFrame.setLocation(200, 200);
        this.quitFrame.setLayout(null);
        Label ql1 = new Label("This program is launched and managed by Mathematica");
        Label ql2 = new Label("to support calling Java code from Mathematica.");
        Label ql3 = new Label("It is intended to be closed by calling UninstallJava[].");
        Label ql4 = new Label("You should not quit it manually unless you are sure you");
        Label ql5 = new Label("need to do so.");
        ql1.setFont(f);
        ql2.setFont(f);
        ql3.setFont(f);
        ql4.setFont(f);
        ql5.setFont(f);
        this.quitFrame.add(ql1);
        this.quitFrame.add(ql2);
        this.quitFrame.add(ql3);
        this.quitFrame.add(ql4);
        this.quitFrame.add(ql5);
        this.quitFrame.addNotify();
        in = this.quitFrame.getInsets();
        sz = this.quitFrame.getSize();
        ql1.setBounds(in.left + 10, in.top + 20, 360, 20);
        ql2.setBounds(in.left + 10, in.top + 40, 360, 20);
        ql3.setBounds(in.left + 10, in.top + 60, 360, 20);
        ql4.setBounds(in.left + 10, in.top + 80, 360, 20);
        ql5.setBounds(in.left + 10, in.top + 100, 360, 20);
        this.quitButton.addActionListener(this.listener);
        this.dontQuitButton.addActionListener(this.listener);
        this.quitFrame.add(this.dontQuitButton);
        this.quitFrame.add(this.quitButton);
        this.dontQuitButton.setBounds((sz.width - in.left - in.right - 200) / 3, sz.height - 30, 100, 28);
        this.quitButton.setBounds(100 + 2 * (sz.width - in.left - in.right - 200) / 3, sz.height - 30, 100, 28);
    }

    public void handleAbout() {
        this.aboutFrame.setVisible(true);
        this.aboutFrame.toFront();
    }

    public void handleQuit() {
        this.quitFrame.setVisible(true);
        this.quitFrame.toFront();
    }

    static class JLinkApplicationAdapter
    extends ApplicationAdapter {
        MRJHandlers hndlrs;

        JLinkApplicationAdapter(MRJHandlers hndlrs) {
            this.hndlrs = hndlrs;
        }

        public void handleAbout(ApplicationEvent evt) {
            this.hndlrs.handleAbout();
            evt.setHandled(true);
        }

        public void handleQuit(ApplicationEvent evt) {
            this.hndlrs.handleQuit();
        }
    }

    class MRJActionListener
    implements ActionListener {
        MRJActionListener() {
        }

        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == MRJHandlers.this.okButton) {
                MRJHandlers.this.aboutFrame.dispose();
            } else if (source == MRJHandlers.this.dontQuitButton) {
                MRJHandlers.this.quitFrame.dispose();
            } else if (source == MRJHandlers.this.quitButton) {
                JLinkSecurityManager.setAllowExit(true);
                System.exit(0);
            }
        }
    }

}

