/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ui;

public class SyntaxTokenizer {
    public static final int NORMAL = 0;
    public static final int STRING = 1;
    public static final int COMMENT = 2;
    public static final int SYMBOL = 3;
    private static final int UNKNOWN = 4;
    private String text;
    private int state = 4;
    private int commentLevel = 0;
    private int tokenStart = 0;
    private int charIndex = -1;
    private int len = 0;

    public void setText(String text) {
        this.text = text;
        this.reset();
    }

    public void reset() {
        this.len = this.text.length();
        this.commentLevel = 0;
        this.tokenStart = 0;
        this.state = 4;
        this.charIndex = -1;
    }

    public SyntaxRecord getNextRecord() {
        Object result = null;
        block7 : while (++this.charIndex < this.len) {
            char c = this.text.charAt(this.charIndex);
            switch (this.getState()) {
                case 2: {
                    if (c == ')' && this.text.charAt(this.charIndex - 1) == '*' && this.text.charAt(this.charIndex - 2) != '(') {
                        this.decrementComment();
                        if (this.getCommentLevel() != 0) break;
                        return this.switchState(2, 4);
                    }
                    if (c != '(' || this.charIndex >= this.len - 1 || this.text.charAt(this.charIndex + 1) != '*') break;
                    this.setState(2);
                    break;
                }
                case 1: {
                    if (c != '\"') break;
                    int numBackslashes = 0;
                    for (int i = this.charIndex - 1; i >= 0 && this.text.charAt(i) == '\\'; --i) {
                        ++numBackslashes;
                    }
                    if (numBackslashes % 2 != 0) continue block7;
                    return this.switchState(1, 4);
                }
                case 3: {
                    if (c == '\"') {
                        return this.switchState(3, 1);
                    }
                    if (c == '(' && this.charIndex < this.len - 1 && this.text.charAt(this.charIndex + 1) == '*') {
                        return this.switchState(3, 2);
                    }
                    if (SyntaxTokenizer.isSymbolChar(c)) break;
                    return this.switchState(3, 0);
                }
                case 0: {
                    if (c == '\"') {
                        return this.switchState(0, 1);
                    }
                    if (c == '(' && this.charIndex < this.len - 1 && this.text.charAt(this.charIndex + 1) == '*') {
                        return this.switchState(0, 2);
                    }
                    if (!SyntaxTokenizer.isSymbolStart(c)) break;
                    return this.switchState(0, 3);
                }
                case 4: {
                    if (c == '\"') {
                        this.setState(1);
                        break;
                    }
                    if (c == '(' && this.charIndex < this.len - 1 && this.text.charAt(this.charIndex + 1) == '*') {
                        this.setState(2);
                        break;
                    }
                    if (SyntaxTokenizer.isSymbolStart(c)) {
                        this.setState(3);
                        break;
                    }
                    this.setState(0);
                }
            }
        }
        if (this.getState() == 3) {
            return this.switchState(3, 4);
        }
        if (this.getState() != 4) {
            return this.switchState(0, 4);
        }
        this.setState(4);
        return null;
    }

    public boolean hasMoreRecords() {
        return this.charIndex < this.len - 1 || this.charIndex == this.len - 1 && this.getState() != 4;
    }

    private SyntaxRecord switchState(int from, int to) {
        int length = from == 2 || from == 1 ? this.charIndex - this.tokenStart + 1 : this.charIndex - this.tokenStart;
        SyntaxRecord result = new SyntaxRecord(from, this.tokenStart, length);
        this.setState(to);
        return result;
    }

    private void setState(int type) {
        this.state = type;
        if (type == 2) {
            if (++this.commentLevel == 1) {
                this.tokenStart = this.charIndex;
            }
        } else {
            this.tokenStart = this.charIndex;
        }
    }

    private int getState() {
        return this.state;
    }

    private void decrementComment() {
        --this.commentLevel;
    }

    private int getCommentLevel() {
        return this.commentLevel;
    }

    private static boolean isSymbolStart(char c) {
        return Character.isLetter(c) || c == '$' || c == '`';
    }

    private static boolean isSymbolChar(char c) {
        return Character.isLetterOrDigit(c) || c == '$' || c == '`';
    }

    public class SyntaxRecord {
        public int type;
        public int start;
        public int length;

        SyntaxRecord(int type, int start, int length) {
            this.type = type;
            this.start = start;
            this.length = length;
        }
    }

}

