/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ui;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import com.wolfram.jlink.PacketListener;
import com.wolfram.jlink.StdLink;
import com.wolfram.jlink.Utils;
import com.wolfram.jlink.ui.BracketMatcher;
import com.wolfram.jlink.ui.InterruptDialog;
import com.wolfram.jlink.ui.PktHandler;
import com.wolfram.jlink.ui.SyntaxTokenizer;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Keymap;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

class MathSessionTextPane
extends JTextPane {
    private KernelLink ml;
    private String linkArgs;
    private String[] linkArgsArray;
    private int connectTimeout = 100000000;
    protected UndoManager undoManager = new UndoManager();
    private PropertyChangeSupport computationPropChangeSupport;
    private int leftIndent;
    private int fontSize;
    private Color textColor;
    private Color promptColor;
    private Color messageColor;
    private Color backgroundColor;
    private boolean isInputBold;
    private boolean fitGraphics;
    private boolean feGraphics;
    private double lastTiming;
    private Style base;
    private Style input;
    private Style output;
    private Style prompt;
    private Style print;
    private Style message;
    private Style graphics;
    private double charWidth;
    private int lineHeight;
    private boolean useSyntaxColoring;
    private boolean colorsHaveChanged;
    private Color stringColor;
    private Color commentColor;
    private Color systemColor;
    private MutableAttributeSet attrNormal;
    private MutableAttributeSet attrString;
    private MutableAttributeSet attrSystem;
    private MutableAttributeSet attrComment;
    private HashMap systemSymbols;
    private Vector userSymbols;
    private Vector userColors;
    private boolean isInputMode;
    private BracketMatcher bracketMatcher;
    private boolean wrap;

    MathSessionTextPane() {
        this.computationPropChangeSupport = new PropertyChangeSupport(this);
        this.leftIndent = 20;
        this.fontSize = 12;
        this.textColor = Color.black;
        this.promptColor = Color.blue;
        this.messageColor = Color.red;
        this.backgroundColor = Color.white;
        this.isInputBold = true;
        this.fitGraphics = false;
        this.feGraphics = false;
        this.lastTiming = 0.0;
        this.lineHeight = 10;
        this.useSyntaxColoring = true;
        this.colorsHaveChanged = false;
        this.stringColor = new Color(20, 159, 175);
        this.commentColor = new Color(94, 206, 11);
        this.systemColor = new Color(132, 38, 187);
        this.attrNormal = new SimpleAttributeSet();
        this.attrString = new SimpleAttributeSet();
        this.attrSystem = new SimpleAttributeSet();
        this.attrComment = new SimpleAttributeSet();
        this.systemSymbols = new HashMap();
        this.bracketMatcher = new BracketMatcher();
        this.wrap = false;
        this.setStyledDocument(new MTDocument());
        this.setEditable(false);
        this.setDoubleBuffered(true);
        this.setOpaque(true);
        Style def = StyleContext.getDefaultStyleContext().getStyle("default");
        this.base = this.addStyle("base", def);
        this.input = this.addStyle("input", this.base);
        this.output = this.addStyle("output", this.base);
        this.prompt = this.addStyle("prompt", this.base);
        this.message = this.addStyle("message", this.base);
        this.print = this.addStyle("print", this.base);
        this.graphics = this.addStyle("graphics", this.base);
        this.setBackgroundColor(this.backgroundColor);
        this.setTextSize(this.fontSize);
        this.setTextColor(this.textColor);
        this.setPromptColor(this.promptColor);
        this.setMessageColor(this.messageColor);
        this.setLeftIndent(this.leftIndent);
        this.setInputBoldface(this.isInputBold);
        this.setStringColor(this.stringColor);
        this.setSystemSymbolColor(this.systemColor);
        this.setCommentColor(this.commentColor);
        StyleConstants.setFontFamily(this.base, "Monospaced");
        int cmdKey = Utils.isMacOSX() ? 4 : 2;
        int abortKey = Utils.isMacOSX() ? 4 : 8;
        Keymap keymap = MathSessionTextPane.addKeymap(null, this.getKeymap());
        AbstractAction evalAction = new AbstractAction(){

            public void actionPerformed(ActionEvent e) {
                MathSessionTextPane.this.evaluateInput();
            }
        };
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(10, 1), evalAction);
        AbstractAction undoAction = new AbstractAction(){

            public void actionPerformed(ActionEvent e) {
                MathSessionTextPane.this.undo();
            }
        };
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(90, cmdKey), undoAction);
        AbstractAction redoAction = new AbstractAction(){

            public void actionPerformed(ActionEvent e) {
                MathSessionTextPane.this.redo();
            }
        };
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(89, cmdKey), redoAction);
        AbstractAction bracketAction = new AbstractAction(){

            public void actionPerformed(ActionEvent e) {
                MathSessionTextPane.this.balanceBrackets();
            }
        };
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(66, cmdKey), bracketAction);
        AbstractAction cifaAction = new AbstractAction(){

            public void actionPerformed(ActionEvent e) {
                MathSessionTextPane.this.copyInputFromAbove();
            }
        };
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(76, cmdKey), cifaAction);
        AbstractAction abortAction = new AbstractAction(){

            public void actionPerformed(ActionEvent e) {
                MathSessionTextPane.this.abortEval();
            }
        };
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(46, abortKey), abortAction);
        AbstractAction interruptAction = new AbstractAction(){

            public void actionPerformed(ActionEvent e) {
                MathSessionTextPane.this.interruptEval();
            }
        };
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(44, abortKey), interruptAction);
        this.setKeymap(keymap);
        this.getDocument().addUndoableEditListener(new UndoableEditListener(){

            public void undoableEditHappened(UndoableEditEvent e) {
                if (MathSessionTextPane.this.isInputMode() && MathSessionTextPane.this.getDoc().isRecordUndos()) {
                    MathSessionTextPane.this.undoManager.addEdit(e.getEdit());
                }
            }
        });
    }

    void setLink(KernelLink ml) {
        this.ml = ml;
    }

    KernelLink getLink() {
        return this.ml;
    }

    void setLinkArguments(String linkArgs) {
        this.linkArgs = linkArgs;
    }

    String getLinkArguments() {
        return this.linkArgs;
    }

    void setLinkArgumentsArray(String[] linkArgs) {
        this.linkArgsArray = linkArgs;
    }

    String[] getLinkArgumentsArray() {
        return this.linkArgsArray;
    }

    void setConnectTimeout(int timeoutMillis) {
        this.connectTimeout = timeoutMillis;
    }

    int getConnectTimeout() {
        return this.connectTimeout;
    }

    public boolean isComputationActive() {
        return this.ml != null && !this.isInputMode();
    }

    int getTextSize() {
        return this.fontSize;
    }

    void setTextSize(int size) {
        this.fontSize = size;
        StyleConstants.setFontSize(this.base, size);
    }

    Color getTextColor() {
        return this.textColor;
    }

    void setTextColor(Color c) {
        this.textColor = c;
        StyleConstants.setForeground(this.base, c);
        StyleConstants.setForeground(this.attrNormal, this.textColor);
        this.colorsHaveChanged = true;
        this.doSyntaxColor();
        this.repaint();
    }

    Color getStringColor() {
        return this.stringColor;
    }

    void setStringColor(Color c) {
        this.stringColor = c;
        StyleConstants.setForeground(this.attrString, this.stringColor);
        this.colorsHaveChanged = true;
        this.doSyntaxColor();
        this.repaint();
    }

    Color getCommentColor() {
        return this.commentColor;
    }

    void setCommentColor(Color c) {
        this.commentColor = c;
        StyleConstants.setForeground(this.attrComment, this.commentColor);
        this.colorsHaveChanged = true;
        this.doSyntaxColor();
        this.repaint();
    }

    Color getSystemSymbolColor() {
        return this.systemColor;
    }

    void setSystemSymbolColor(Color c) {
        this.systemColor = c;
        StyleConstants.setForeground(this.attrSystem, this.systemColor);
        this.colorsHaveChanged = true;
        this.doSyntaxColor();
        this.repaint();
    }

    Color getBackgroundColor() {
        return this.backgroundColor;
    }

    void setBackgroundColor(Color c) {
        this.backgroundColor = c;
        this.setBackground(c);
        this.setCaretColor(new Color(this.backgroundColor.getRed() ^ 255, this.backgroundColor.getGreen() ^ 255, this.backgroundColor.getBlue() ^ 255));
    }

    Color getPromptColor() {
        return this.promptColor;
    }

    void setPromptColor(Color c) {
        this.promptColor = c;
        StyleConstants.setForeground(this.prompt, c);
    }

    Color getMessageColor() {
        return this.messageColor;
    }

    void setMessageColor(Color c) {
        this.messageColor = c;
        StyleConstants.setForeground(this.message, c);
    }

    int getLeftIndent() {
        return this.leftIndent;
    }

    void setLeftIndent(int indent) {
        this.leftIndent = indent;
        StyleConstants.setLeftIndent(this.input, indent);
        StyleConstants.setLeftIndent(this.output, indent);
        StyleConstants.setLeftIndent(this.message, indent);
        StyleConstants.setLeftIndent(this.print, indent);
        StyleConstants.setLeftIndent(this.graphics, indent);
    }

    boolean isInputBoldface() {
        return this.isInputBold;
    }

    void setInputBoldface(boolean bold) {
        this.isInputBold = bold;
        StyleConstants.setBold(this.input, bold);
    }

    void setFitGraphics(boolean fit) {
        this.fitGraphics = fit;
    }

    boolean isFitGraphics() {
        return this.fitGraphics;
    }

    void setFrontEndGraphics(boolean b) {
        this.feGraphics = b;
    }

    boolean isFrontEndGraphics() {
        return this.feGraphics;
    }

    double getLastTiming() {
        return this.lastTiming;
    }

    boolean isSyntaxColoring() {
        return this.useSyntaxColoring;
    }

    void setSyntaxColoring(boolean b) {
        this.useSyntaxColoring = b;
        this.colorsHaveChanged = true;
        this.doSyntaxColor();
        this.repaint();
    }

    private void setSystemSymbols(String[] syms) {
        this.systemSymbols.clear();
        for (int i = 0; i < syms.length; ++i) {
            this.systemSymbols.put(syms[i], syms[i]);
        }
    }

    void addColoredSymbols(String[] syms, Color c) {
        if (this.userSymbols == null) {
            this.userSymbols = new Vector(1);
        }
        if (this.userColors == null) {
            this.userColors = new Vector(1);
        }
        HashMap<String, String> h = new HashMap<String, String>();
        for (int i = 0; i < syms.length; ++i) {
            h.put(syms[i], syms[i]);
        }
        this.userSymbols.addElement(h);
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, c);
        this.userColors.addElement(attr);
        this.colorsHaveChanged = true;
        this.doSyntaxColor();
        this.repaint();
    }

    void clearColoredSymbols() {
        this.userSymbols = null;
        this.userColors = null;
        this.colorsHaveChanged = true;
        this.doSyntaxColor();
        this.repaint();
    }

    void doSyntaxColor() {
        this.getDoc().doSyntaxColor();
    }

    void undo() {
        if (this.undoManager.canUndo()) {
            this.undoManager.undo();
        }
    }

    void redo() {
        if (this.undoManager.canRedo()) {
            this.undoManager.redo();
        }
    }

    boolean canRedo() {
        return this.undoManager.canRedo();
    }

    boolean canUndo() {
        return this.undoManager.canUndo();
    }

    private void setInputMode(boolean isInputMode) {
        boolean oldValue = this.isInputMode;
        this.isInputMode = isInputMode;
        if (oldValue != isInputMode) {
            this.computationPropChangeSupport.firePropertyChange("computationActive", new Boolean(!oldValue), new Boolean(!isInputMode));
        }
    }

    private boolean isInputMode() {
        return this.isInputMode;
    }

    public void addComputationPropertyChangeListener(PropertyChangeListener listener) {
        this.computationPropChangeSupport.addPropertyChangeListener(listener);
    }

    public void removeComputationPropertyChangeListener(PropertyChangeListener listener) {
        this.computationPropChangeSupport.removePropertyChangeListener(listener);
    }

    void connect() throws MathLinkException {
        this.setCursor(Cursor.getPredefinedCursor(3));
        this.setInputMode(false);
        try {
            if (this.ml == null) {
                if (this.linkArgs != null) {
                    this.ml = MathLinkFactory.createKernelLink(this.linkArgs);
                } else if (this.linkArgsArray != null) {
                    this.ml = MathLinkFactory.createKernelLink(this.linkArgsArray);
                }
            }
            this.ml.connect(this.connectTimeout);
        }
        catch (MathLinkException e) {
            this.setCursor(Cursor.getPredefinedCursor(0));
            throw e;
        }
        boolean isStdLink = this.ml.equals(StdLink.getLink());
        if (isStdLink) {
            StdLink.requestTransaction();
        }
        this.ml.evaluate("$Line");
        while (this.ml.waitForAnswer() != 3) {
            this.ml.newPacket();
        }
        final String firstPrompt = "In[" + Integer.toString(this.ml.getInteger()) + "]:=\n";
        this.ml.newPacket();
        if (isStdLink) {
            StdLink.requestTransaction();
        }
        this.ml.evaluate("Needs[\"JLink`\"]");
        this.ml.discardAnswer();
        if (isStdLink) {
            StdLink.requestTransaction();
        }
        this.ml.evaluate("Names[\"System`*\"]");
        this.ml.waitForAnswer();
        String[] syms = this.ml.getStringArray1();
        this.setSystemSymbols(syms);
        this.ml.addPacketListener(new PktHandler(this, this.getDoc()));
        Frame parentFrame = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, this);
        Dialog parentDialog = (Dialog)SwingUtilities.getAncestorOfClass(Dialog.class, this);
        if (parentFrame != null) {
            this.ml.addPacketListener(new InterruptDialog(parentFrame));
        } else if (parentDialog != null) {
            this.ml.addPacketListener(new InterruptDialog(parentDialog));
        }
        SwingUtilities.invokeLater(new Runnable(){

            public void run() {
                MTDocument doc = MathSessionTextPane.this.getDoc();
                doc.setLogicalStyle(doc.getLength(), MathSessionTextPane.this.getStyle("prompt"));
                try {
                    doc.insertString(doc.getLength(), firstPrompt, null);
                }
                catch (BadLocationException e) {
                    // empty catch block
                }
                try {
                    MathSessionTextPane.this.charWidth = (MathSessionTextPane.this.modelToView(7).getX() - MathSessionTextPane.this.modelToView(0).getX()) / 7.0;
                    MathSessionTextPane.this.lineHeight = (int)(MathSessionTextPane.this.modelToView(MathSessionTextPane.this.getDoc().getLength()).getY() - MathSessionTextPane.this.modelToView(0).getY());
                }
                catch (BadLocationException e) {
                    // empty catch block
                }
                doc.setFirstEditPos(doc.getLength());
                doc.setLogicalStyle(doc.getLength(), MathSessionTextPane.this.getStyle("input"));
                MathSessionTextPane.this.setInputMode(true);
                MathSessionTextPane.this.setEditable(true);
                MathSessionTextPane.this.setCursor(Cursor.getPredefinedCursor(2));
                MathSessionTextPane.this.getCaret().setVisible(true);
            }
        });
    }

    void closeLink() {
        if (this.ml != null) {
            this.ml.close();
            this.ml = null;
        }
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return this.lineHeight;
    }

    public boolean getScrollableTracksViewportWidth() {
        if (!this.wrap) {
            Container parent = this.getParent();
            TextUI ui = this.getUI();
            int uiWidth = ui.getPreferredSize((JComponent)this).width;
            int parentWidth = parent.getSize().width;
            return parent != null ? ui.getPreferredSize((JComponent)this).width < parent.getSize().width : true;
        }
        return super.getScrollableTracksViewportWidth();
    }

    public void setBounds(int x, int y, int width, int height) {
        if (this.wrap) {
            super.setBounds(x, y, width, height);
        } else {
            Dimension size = this.getPreferredSize();
            super.setBounds(x, y, Math.max(size.width, width), Math.max(size.height, height));
        }
    }

    Dimension getVisibleTextBounds() {
        JViewport v = (JViewport)this.getParent();
        Point startVisible = v.getViewPosition();
        Point endVisible = new Point(startVisible.x + v.getSize().width, startVisible.y + v.getSize().height);
        return new Dimension(this.viewToModel(startVisible), this.viewToModel(endVisible));
    }

    private MTDocument getDoc() {
        return (MTDocument)this.getDocument();
    }

    void evaluateInput() {
        if (!this.isInputMode() || this.ml == null) {
            return;
        }
        int firstEditPos = this.getDoc().getFirstEditPos();
        if (this.getSelectionStart() >= firstEditPos && this.getSelectionEnd() >= firstEditPos) {
            try {
                this.getDoc().insertString(this.getDoc().getLength(), "\n\n", null);
            }
            catch (BadLocationException exc) {
                // empty catch block
            }
            this.undoManager.discardAllEdits();
            EvalTask evalTask = new EvalTask();
            new Thread(evalTask).start();
        } else {
            this.getToolkit().beep();
        }
    }

    private void abortEval() {
        if (!this.isInputMode()) {
            try {
                this.ml.putMessage(3);
            }
            catch (MathLinkException e) {
                e.printStackTrace();
            }
        }
    }

    private void interruptEval() {
        if (!this.isInputMode()) {
            try {
                this.ml.putMessage(2);
            }
            catch (MathLinkException e) {
                e.printStackTrace();
            }
        }
    }

    void balanceBrackets() {
        this.bracketMatcher.setText(this.getDoc().getEvalInput());
        int inputStart = this.getDoc().getFirstEditPos();
        Point result = null;
        if (this.getSelectionStart() >= inputStart) {
            result = this.bracketMatcher.balance(this.getSelectionStart() - inputStart, this.getSelectionEnd() - this.getSelectionStart());
        }
        if (result != null) {
            this.setSelectionStart(inputStart + result.x);
            this.setSelectionEnd(inputStart + result.y);
        } else {
            this.getToolkit().beep();
        }
    }

    void copyInputFromAbove() {
        if (this.isInputMode()) {
            MTDocument doc = this.getDoc();
            int firstEditPos = doc.getFirstEditPos();
            if (this.getSelectionStart() >= firstEditPos && this.getSelectionEnd() >= firstEditPos) {
                int inputStyleStart = 0;
                int inputStyleEnd = 0;
                Style inputStyle = this.getStyle("input");
                for (int pos = firstEditPos - 1; pos >= 0; --pos) {
                    if (doc.getLogicalStyle(pos).equals(inputStyle)) {
                        if (inputStyleEnd != 0) continue;
                        inputStyleEnd = pos + 1;
                        continue;
                    }
                    if (inputStyleEnd == 0) continue;
                    inputStyleStart = pos + 1;
                    break;
                }
                if (inputStyleStart != 0 && inputStyleEnd != 0) {
                    try {
                        String s = this.getText(inputStyleStart, inputStyleEnd - inputStyleStart);
                        if (s.endsWith("\n\n")) {
                            s = s.substring(0, s.length() - 2);
                        }
                        this.replaceSelection(s);
                    }
                    catch (BadLocationException exc) {}
                }
            } else {
                this.getToolkit().beep();
            }
        }
    }

    private static boolean intervalsIntersect(int a_start, int a_end, int b_start, int b_end) {
        return a_start <= b_end && a_end >= b_start;
    }

    class MTDocument
    extends DefaultStyledDocument {
        private int firstEditPos;
        private SyntaxTokenizer tokenizer;
        private int lastStyledStart;
        private int lastStyledEnd;
        protected boolean recordUndos;
        private boolean lastPassWasColored;

        MTDocument() {
            this.tokenizer = new SyntaxTokenizer();
            this.lastStyledStart = Integer.MAX_VALUE;
            this.lastStyledEnd = Integer.MIN_VALUE;
            this.recordUndos = true;
            this.lastPassWasColored = false;
        }

        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
            if (offset < this.firstEditPos) {
                MathSessionTextPane.this.getToolkit().beep();
            } else {
                super.insertString(offset, str, a);
                this.lastStyledStart = Integer.MAX_VALUE;
                this.lastStyledEnd = Integer.MIN_VALUE;
                this.doSyntaxColor();
            }
        }

        public void remove(int offset, int len) throws BadLocationException {
            if (offset < this.firstEditPos) {
                MathSessionTextPane.this.getToolkit().beep();
            } else {
                super.remove(offset, len);
                this.lastStyledStart = Integer.MAX_VALUE;
                this.lastStyledEnd = Integer.MIN_VALUE;
                this.doSyntaxColor();
            }
        }

        public boolean isRecordUndos() {
            return this.recordUndos;
        }

        void setFirstEditPos(int pos) {
            this.firstEditPos = pos;
        }

        int getFirstEditPos() {
            return this.firstEditPos;
        }

        String getEvalInput() {
            try {
                return this.getText(this.firstEditPos, this.getLength() - this.firstEditPos);
            }
            catch (BadLocationException e) {
                e.printStackTrace();
                return "";
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void doSyntaxColor() {
            int firstVisibleChar;
            int lastVisibleChar;
            int startChar;
            int endChar;
            if (!MathSessionTextPane.this.isInputMode()) {
                return;
            }
            if (!MathSessionTextPane.this.isSyntaxColoring() && !MathSessionTextPane.this.colorsHaveChanged) {
                return;
            }
            if (!MathSessionTextPane.this.isSyntaxColoring()) {
                this.recordUndos = false;
                try {
                    this.setCharacterAttributes(this.firstEditPos, this.getLength() - this.firstEditPos, MathSessionTextPane.this.attrNormal, false);
                }
                finally {
                    this.recordUndos = false;
                    MathSessionTextPane.this.colorsHaveChanged = false;
                }
                return;
            }
            Dimension d = MathSessionTextPane.this.getVisibleTextBounds();
            firstVisibleChar = Math.max(d.width - this.firstEditPos, 0);
            lastVisibleChar = d.height - this.firstEditPos;
            if (lastVisibleChar < 0) {
                return;
            }
            startChar = firstVisibleChar;
            endChar = lastVisibleChar;
            if (MathSessionTextPane.this.colorsHaveChanged) {
                startChar = 0;
                endChar = this.getLength() - this.firstEditPos;
            } else {
                if (firstVisibleChar >= this.lastStyledStart && lastVisibleChar <= this.lastStyledEnd) {
                    return;
                }
                if (firstVisibleChar < this.lastStyledStart) {
                    endChar = lastVisibleChar > this.lastStyledEnd ? lastVisibleChar : this.lastStyledStart;
                } else if (lastVisibleChar > this.lastStyledEnd) {
                    startChar = firstVisibleChar < this.lastStyledStart ? firstVisibleChar : this.lastStyledEnd;
                }
            }
            MathSessionTextPane.this.colorsHaveChanged = false;
            String input = this.getEvalInput();
            this.tokenizer.setText(input);
            this.recordUndos = false;
            try {
                this.setCharacterAttributes(this.firstEditPos + startChar, endChar - startChar, MathSessionTextPane.this.attrNormal, false);
                MutableAttributeSet curAttr = MathSessionTextPane.this.attrNormal;
                AttributeSet attr = null;
                while (this.tokenizer.hasMoreRecords()) {
                    SyntaxTokenizer.SyntaxRecord rec = this.tokenizer.getNextRecord();
                    switch (rec.type) {
                        case 0: {
                            attr = MathSessionTextPane.this.attrNormal;
                            break;
                        }
                        case 2: {
                            attr = MathSessionTextPane.this.attrComment;
                            break;
                        }
                        case 1: {
                            attr = MathSessionTextPane.this.attrString;
                            break;
                        }
                        case 3: {
                            attr = MathSessionTextPane.this.attrNormal;
                            String sym = input.substring(rec.start, rec.start + rec.length);
                            boolean wasUserSymbol = false;
                            if (MathSessionTextPane.this.userSymbols != null) {
                                int sz = MathSessionTextPane.this.userSymbols.size();
                                for (int i = 0; i < sz; ++i) {
                                    HashMap h = (HashMap)MathSessionTextPane.this.userSymbols.elementAt(i);
                                    if (!h.containsKey(sym)) continue;
                                    wasUserSymbol = true;
                                    attr = (AttributeSet)MathSessionTextPane.this.userColors.elementAt(i);
                                }
                            }
                            if (wasUserSymbol || !MathSessionTextPane.this.systemSymbols.containsKey(sym)) break;
                            attr = MathSessionTextPane.this.attrSystem;
                            break;
                        }
                    }
                    if (attr != MathSessionTextPane.this.attrNormal && attr != curAttr && MathSessionTextPane.intervalsIntersect(rec.start, rec.start + rec.length, startChar, endChar)) {
                        this.setCharacterAttributes(rec.start + this.firstEditPos, rec.length, attr, false);
                    }
                    curAttr = attr;
                }
            }
            finally {
                this.recordUndos = true;
            }
            this.lastStyledStart = Math.min(this.lastStyledStart, Math.min(startChar, firstVisibleChar));
            this.lastStyledEnd = Math.max(this.lastStyledEnd, Math.max(endChar, lastVisibleChar));
        }
    }

    class EvalTask
    implements Runnable {
        EvalTask() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void run() {
            long start;
            block20 : {
                try {
                    SwingUtilities.invokeAndWait(new Runnable(){

                        public void run() {
                            MathSessionTextPane.this.setEditable(false);
                            MathSessionTextPane.this.setInputMode(false);
                            MathSessionTextPane.this.setCursor(Cursor.getPredefinedCursor(3));
                        }
                    });
                }
                catch (Exception e) {
                    // empty catch block
                }
                start = System.currentTimeMillis();
                try {
                    if (MathSessionTextPane.this.ml.equals(StdLink.getLink())) {
                        StdLink.requestTransaction();
                        KernelLink kernelLink = MathSessionTextPane.this.ml;
                        synchronized (kernelLink) {
                            this.preEval();
                        }
                        StdLink.requestTransaction();
                        kernelLink = MathSessionTextPane.this.ml;
                        synchronized (kernelLink) {
                            MathSessionTextPane.this.ml.putFunction("EnterTextPacket", 1);
                            MathSessionTextPane.this.ml.put(MathSessionTextPane.this.getDoc().getEvalInput());
                            MathSessionTextPane.this.ml.discardAnswer();
                        }
                        StdLink.requestTransaction();
                        kernelLink = MathSessionTextPane.this.ml;
                        synchronized (kernelLink) {
                            this.postEval();
                            break block20;
                        }
                    }
                    KernelLink kernelLink = MathSessionTextPane.this.ml;
                    synchronized (kernelLink) {
                        this.preEval();
                        MathSessionTextPane.this.ml.putFunction("EnterTextPacket", 1);
                        MathSessionTextPane.this.ml.put(MathSessionTextPane.this.getDoc().getEvalInput());
                        MathSessionTextPane.this.ml.discardAnswer();
                        this.postEval();
                    }
                }
                catch (MathLinkException e) {
                    if (!MathSessionTextPane.this.ml.clearError() || e.getErrCode() == 11) {
                        MathSessionTextPane.this.closeLink();
                    }
                    MathSessionTextPane.this.ml.newPacket();
                }
            }
            MathSessionTextPane.this.lastTiming = (double)(System.currentTimeMillis() - start) / 1000.0;
            try {
                SwingUtilities.invokeAndWait(new Runnable(){

                    public void run() {
                        MathSessionTextPane.this.setCaretPosition(MathSessionTextPane.this.getDoc().getLength());
                        MathSessionTextPane.this.getCaret().setVisible(true);
                        MathSessionTextPane.this.undoManager.discardAllEdits();
                        if (MathSessionTextPane.this.getLink() != null) {
                            MathSessionTextPane.this.setInputMode(true);
                            MathSessionTextPane.this.setEditable(true);
                            MathSessionTextPane.this.setCursor(Cursor.getPredefinedCursor(2));
                        } else {
                            MathSessionTextPane.this.setCursor(Cursor.getPredefinedCursor(0));
                        }
                    }
                });
            }
            catch (Exception e) {
                // empty catch block
            }
        }

        private void preEval() throws MathLinkException {
            int paneWidthInChars = (int)((double)(MathSessionTextPane.this.getParent().getSize().width - MathSessionTextPane.this.getLeftIndent()) / MathSessionTextPane.this.charWidth);
            String df = "(LinkWrite[$ParentLink, DisplayPacket[EvaluateToImage[#, " + (MathSessionTextPane.this.feGraphics ? "True" : "False") + (MathSessionTextPane.this.fitGraphics ? new StringBuilder().append(", ImageSize->{").append(MathSessionTextPane.this.getParent().getSize().width - MathSessionTextPane.this.getLeftIndent() - 10).append(", Automatic}").toString() : "") + "]]]; #)&";
            MathSessionTextPane.this.ml.evaluate("{JLink`Private`cfv, JLink`Private`sopts, JLink`Private`ddf} = {FormatValues[Continuation], Options[\"stdout\"], $DisplayFunction} ; Format[Continuation[_], OutputForm] = \"\" ; SetOptions[\"stdout\", FormatType -> OutputForm, CharacterEncoding -> \"Unicode\", PageWidth -> " + paneWidthInChars + "] ; " + "$DisplayFunction = " + df + ";");
            MathSessionTextPane.this.ml.discardAnswer();
        }

        private void postEval() throws MathLinkException {
            MathSessionTextPane.this.ml.evaluate("FormatValues[Continuation] = JLink`Private`cfv ; SetOptions[\"stdout\", JLink`Private`sopts] ; $DisplayFunction = JLink`Private`ddf ;");
            MathSessionTextPane.this.ml.discardAnswer();
        }

    }

}

