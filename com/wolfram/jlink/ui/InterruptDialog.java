/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ui;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.PacketArrivedEvent;
import com.wolfram.jlink.PacketListener;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class InterruptDialog
extends JDialog
implements ActionListener,
PacketListener {
    private String response;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton b6;
    private JButton b7;
    Frame parentFrame;
    Dialog parentDialog;

    public InterruptDialog(Frame parent) {
        super(parent, true);
        this.parentFrame = parent;
        this.setup();
    }

    public InterruptDialog(Dialog parent) {
        super(parent, true);
        this.parentDialog = parent;
        this.setup();
    }

    private void setup() {
        this.setTitle("Interrupt");
        this.setResizable(false);
        Container contentPane = this.getContentPane();
        JPanel p = new JPanel();
        contentPane.add(p);
        p.setLayout(new GridLayout(9, 1, 6, 6));
        this.b1 = new JButton("Abort Command Being Evaluated");
        this.b2 = new JButton("Enter Inspector Dialog");
        this.b3 = new JButton("Send Interrupt to Linked Program");
        this.b4 = new JButton("Send Abort to Linked Program");
        this.b5 = new JButton("Kill Linked Program");
        this.b6 = new JButton("Continue Evaluation");
        this.b7 = new JButton("Quit the Mathematica Kernel");
        Panel p1 = new Panel();
        Panel p2 = new Panel();
        p.add(this.b1);
        p.add(this.b2);
        p.add(p1);
        p.add(this.b3);
        p.add(this.b4);
        p.add(this.b5);
        p.add(p2);
        p.add(this.b6);
        p.add(this.b7);
        this.b1.addActionListener(this);
        this.b2.addActionListener(this);
        this.b3.addActionListener(this);
        this.b4.addActionListener(this);
        this.b5.addActionListener(this);
        this.b6.addActionListener(this);
        this.b7.addActionListener(this);
        this.pack();
        this.doLayout();
        Rectangle r = p.getBounds();
        contentPane.setLayout(null);
        p.setBounds(r.x + 10, r.y + 5, r.width, r.height);
        this.setSize(this.getSize().width + 20, this.getSize().height + 10);
    }

    public boolean packetArrived(PacketArrivedEvent evt) throws MathLinkException {
        boolean allowFurtherProcessing = true;
        KernelLink ml = (KernelLink)evt.getSource();
        if (evt.getPktType() == 6) {
            int type = ml.getInteger();
            String prompt = ml.getString();
            this.doDialog(type);
            ml.put(this.response != null ? this.response : "c");
            ml.flush();
            this.response = null;
            allowFurtherProcessing = false;
        }
        return allowFurtherProcessing;
    }

    private void doDialog(int type) {
        switch (type) {
            case 1: {
                this.b1.setEnabled(true);
                this.b2.setEnabled(true);
                this.b3.setEnabled(false);
                this.b4.setEnabled(false);
                this.b5.setEnabled(false);
                break;
            }
            case 3: {
                this.b1.setEnabled(false);
                this.b2.setEnabled(false);
                this.b3.setEnabled(true);
                this.b4.setEnabled(true);
                this.b5.setEnabled(true);
                break;
            }
            default: {
                return;
            }
        }
        if (this.parentFrame != null) {
            this.setLocationRelativeTo(this.parentFrame);
        } else {
            this.setLocationRelativeTo(this.parentDialog);
        }
        this.doLayout();
        this.show();
    }

    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == this.b1) {
            this.response = "a";
        } else if (source == this.b2) {
            this.response = "i";
        } else if (source == this.b3) {
            this.response = "r";
        } else if (source == this.b4) {
            this.response = "a";
        } else if (source == this.b5) {
            this.response = "k";
        } else if (source == this.b6) {
            this.response = "c";
        } else if (source == this.b7) {
            this.response = "exit";
        }
        this.dispose();
    }
}

