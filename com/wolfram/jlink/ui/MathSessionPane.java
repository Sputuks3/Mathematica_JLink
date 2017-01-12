/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ui;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.Utils;
import com.wolfram.jlink.ui.MathSessionTextPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.RepaintManager;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MathSessionPane
extends JScrollPane {
    private MathSessionTextPane textPane;
    private boolean showTiming = true;
    private PropertyChangeSupport propChangeSupport;

    public MathSessionPane() {
        this(22, 32);
    }

    public MathSessionPane(int vsbPolicy, int hsbPolicy) {
        this.propChangeSupport = new PropertyChangeSupport(this);
        this.textPane = new MathSessionTextPane();
        this.textPane.addComputationPropertyChangeListener(new PropertyChangeListener(){

            public void propertyChange(PropertyChangeEvent evt) {
                MathSessionPane.this.propChangeSupport.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
            }
        });
        ScrollPaneLayout layout = new ScrollPaneLayout(){

            public void layoutContainer(Container parent) {
                super.layoutContainer(parent);
                JScrollBar sb = this.getHorizontalScrollBar();
                if (sb != null) {
                    Rectangle r = sb.getBounds();
                    int timingPanelSize = MathSessionPane.this.isShowTiming() ? 100 : 0;
                    sb.setBounds(r.x + timingPanelSize, r.y, r.width - timingPanelSize, r.height);
                    this.lowerLeft.setBounds(0, r.y, timingPanelSize, r.height);
                    if (Utils.isMacOSX() && MathSessionPane.this.isShowTiming()) {
                        RepaintManager.currentManager(MathSessionPane.this).markCompletelyClean(MathSessionPane.this);
                    }
                }
                sb.validate();
            }
        };
        this.setLayout(layout);
        layout.syncWithScrollPane(this);
        this.setVerticalScrollBar(this.createVerticalScrollBar());
        this.setHorizontalScrollBar(this.createHorizontalScrollBar());
        this.setVerticalScrollBarPolicy(vsbPolicy);
        this.setHorizontalScrollBarPolicy(hsbPolicy);
        this.setViewportView(this.textPane);
        this.setDoubleBuffered(true);
        final JPanel timingPanel = new JPanel(){

            public void paintComponent(Graphics g) {
                String s = Double.toString(MathSessionPane.this.textPane.getLastTiming()) + " seconds";
                g.setColor(MathSessionPane.this.getHorizontalScrollBar().getBackground());
                g.fillRect(0, 0, this.getSize().width, this.getSize().height);
                g.setColor(Color.black);
                g.drawString(s, 6, this.getHeight() - 2);
            }
        };
        this.setCorner("LOWER_LEFT_CORNER", timingPanel);
        this.textPane.addComputationPropertyChangeListener(new PropertyChangeListener(){

            public void propertyChange(PropertyChangeEvent evt) {
                if (!((Boolean)evt.getNewValue()).booleanValue()) {
                    timingPanel.repaint();
                }
            }
        });
        this.getViewport().addChangeListener(new ChangeListener(){
            private Dimension lastVisibleBounds;

            public void stateChanged(ChangeEvent e) {
                Dimension visibleBounds = MathSessionPane.this.textPane.getVisibleTextBounds();
                if (!visibleBounds.equals(this.lastVisibleBounds)) {
                    MathSessionPane.this.textPane.doSyntaxColor();
                    this.lastVisibleBounds = visibleBounds;
                }
            }
        });
    }

    public void setLink(KernelLink ml) {
        this.textPane.setLink(ml);
    }

    public KernelLink getLink() {
        return this.textPane.getLink();
    }

    public void setLinkArguments(String linkArgs) {
        this.textPane.setLinkArguments(linkArgs);
    }

    public void setLinkArgumentsArray(String[] linkArgs) {
        this.textPane.setLinkArgumentsArray(linkArgs);
    }

    public String getLinkArguments() {
        return this.textPane.getLinkArguments();
    }

    public String[] getLinkArgumentsArray() {
        return this.textPane.getLinkArgumentsArray();
    }

    public void setConnectTimeout(int timeoutMillis) {
        this.textPane.setConnectTimeout(timeoutMillis);
    }

    public int getConnectTimeout() {
        return this.textPane.getConnectTimeout();
    }

    public String getText() {
        return this.textPane.getText();
    }

    public JTextPane getTextPane() {
        return this.textPane;
    }

    public boolean isComputationActive() {
        return this.textPane.isComputationActive();
    }

    public int getTextSize() {
        return this.textPane.getTextSize();
    }

    public void setTextSize(int size) {
        this.textPane.setTextSize(size);
    }

    public boolean isSyntaxColoring() {
        return this.textPane.isSyntaxColoring();
    }

    public void setSyntaxColoring(boolean b) {
        this.textPane.setSyntaxColoring(b);
    }

    public Color getTextColor() {
        return this.textPane.getTextColor();
    }

    public void setTextColor(Color c) {
        this.textPane.setTextColor(c);
    }

    public Color getStringColor() {
        return this.textPane.getStringColor();
    }

    public void setStringColor(Color c) {
        this.textPane.setStringColor(c);
    }

    public Color getCommentColor() {
        return this.textPane.getCommentColor();
    }

    public void setCommentColor(Color c) {
        this.textPane.setCommentColor(c);
    }

    public Color getSystemSymbolColor() {
        return this.textPane.getSystemSymbolColor();
    }

    public void setSystemSymbolColor(Color c) {
        this.textPane.setSystemSymbolColor(c);
    }

    public Color getBackgroundColor() {
        return this.textPane.getBackgroundColor();
    }

    public void setBackgroundColor(Color c) {
        this.textPane.setBackgroundColor(c);
    }

    public Color getPromptColor() {
        return this.textPane.getPromptColor();
    }

    public void setPromptColor(Color c) {
        this.textPane.setPromptColor(c);
    }

    public Color getMessageColor() {
        return this.textPane.getMessageColor();
    }

    public void setMessageColor(Color c) {
        this.textPane.setMessageColor(c);
    }

    public int getLeftIndent() {
        return this.textPane.getLeftIndent();
    }

    public void setLeftIndent(int indent) {
        this.textPane.setLeftIndent(indent);
    }

    public boolean isInputBoldface() {
        return this.textPane.isInputBoldface();
    }

    public void setInputBoldface(boolean bold) {
        this.textPane.setInputBoldface(bold);
    }

    public void setFitGraphics(boolean fit) {
        this.textPane.setFitGraphics(fit);
    }

    public boolean isFitGraphics() {
        return this.textPane.isFitGraphics();
    }

    public void setFrontEndGraphics(boolean b) {
        this.textPane.setFrontEndGraphics(b);
    }

    public boolean isFrontEndGraphics() {
        return this.textPane.isFrontEndGraphics();
    }

    public void addColoredSymbols(String[] syms, Color c) {
        this.textPane.addColoredSymbols(syms, c);
    }

    public void clearColoredSymbols() {
        this.textPane.clearColoredSymbols();
    }

    public void setShowTiming(boolean show) {
        this.showTiming = show;
        this.doLayout();
    }

    public boolean isShowTiming() {
        return this.showTiming;
    }

    public void evaluateInput() {
        this.textPane.evaluateInput();
    }

    public void undo() {
        this.textPane.undo();
    }

    public void redo() {
        this.textPane.redo();
    }

    public boolean canRedo() {
        return this.textPane.canRedo();
    }

    public boolean canUndo() {
        return this.textPane.canUndo();
    }

    public void copyInputFromAbove() {
        this.textPane.copyInputFromAbove();
    }

    public void balanceBrackets() {
        this.textPane.balanceBrackets();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        if (this.textPane != null) {
            this.textPane.addComputationPropertyChangeListener(listener);
        }
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        super.removePropertyChangeListener(listener);
        if (this.textPane != null) {
            this.textPane.removeComputationPropertyChangeListener(listener);
        }
    }

    public void connect() throws MathLinkException {
        this.textPane.connect();
    }

    public void closeLink() {
        this.textPane.closeLink();
    }

}

