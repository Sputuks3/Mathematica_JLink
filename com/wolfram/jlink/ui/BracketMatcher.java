/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ui;

import java.awt.Point;
import java.util.Stack;

public class BracketMatcher {
    private String text;

    public BracketMatcher() {
        this("");
    }

    public BracketMatcher(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Point balance(int leftEndOfSelection, int selectionLength) {
        int i;
        Stack<Character> stack = new Stack<Character>();
        char c = '\u0000';
        int textLength = this.text.length();
        int rightEndOfSelection = leftEndOfSelection + selectionLength;
        if (leftEndOfSelection < textLength && BracketMatcher.isOpenBracket(this.text.charAt(leftEndOfSelection))) {
            for (i = leftEndOfSelection; i < textLength; ++i) {
                c = this.text.charAt(i);
                if (BracketMatcher.isOpenBracket(c)) {
                    stack.push(new Character(c));
                    continue;
                }
                if (!BracketMatcher.isCloseBracket(c)) continue;
                if (BracketMatcher.matchingBracket(c) != ((Character)stack.peek()).charValue()) {
                    return null;
                }
                stack.pop();
                if (!stack.isEmpty()) continue;
                if (i < rightEndOfSelection) break;
                return new Point(leftEndOfSelection, i + 1);
            }
            if (i == textLength) {
                return null;
            }
        } else {
            if (leftEndOfSelection == 0) {
                return null;
            }
            if (BracketMatcher.isCloseBracket(this.text.charAt(rightEndOfSelection - 1))) {
                for (i = rightEndOfSelection - 1; i >= 0; --i) {
                    c = this.text.charAt(i);
                    if (BracketMatcher.isCloseBracket(c)) {
                        stack.push(new Character(c));
                        continue;
                    }
                    if (!BracketMatcher.isOpenBracket(c)) continue;
                    if (BracketMatcher.matchingBracket(c) != ((Character)stack.peek()).charValue()) {
                        return null;
                    }
                    stack.pop();
                    if (!stack.isEmpty()) continue;
                    if (i >= leftEndOfSelection) break;
                    return new Point(i, rightEndOfSelection);
                }
                if (i < 0) {
                    return null;
                }
            } else if (rightEndOfSelection == textLength) {
                return null;
            }
        }
        int curLeft = leftEndOfSelection - 1;
        int curRight = leftEndOfSelection;
        block2 : do {
            stack.clear();
            for (i = curLeft; i >= 0; --i) {
                c = this.text.charAt(i);
                if (BracketMatcher.isCloseBracket(c)) {
                    stack.push(new Character(c));
                    continue;
                }
                if (!BracketMatcher.isOpenBracket(c)) continue;
                if (stack.isEmpty()) break;
                if (BracketMatcher.matchingBracket(c) != ((Character)stack.peek()).charValue()) {
                    return null;
                }
                stack.pop();
            }
            if (i < 0) {
                return null;
            }
            int bracketPos = i;
            stack.clear();
            stack.push(new Character(c));
            for (i = curRight; i < textLength; ++i) {
                c = this.text.charAt(i);
                if (BracketMatcher.isOpenBracket(c)) {
                    stack.push(new Character(c));
                    continue;
                }
                if (!BracketMatcher.isCloseBracket(c)) continue;
                if (BracketMatcher.matchingBracket(c) != ((Character)stack.peek()).charValue()) {
                    return null;
                }
                stack.pop();
                if (!stack.isEmpty()) continue;
                if (i >= rightEndOfSelection) {
                    return new Point(bracketPos, i + 1);
                }
                curLeft = bracketPos - 1;
                curRight = i + 1;
                continue block2;
            }
        } while (i != textLength);
        return null;
    }

    private static boolean isOpenBracket(char c) {
        return c == '[' || c == '(' || c == '{';
    }

    private static boolean isCloseBracket(char c) {
        return c == ']' || c == ')' || c == '}';
    }

    private static char matchingBracket(char c) {
        switch (c) {
            case '(': {
                return ')';
            }
            case ')': {
                return '(';
            }
            case '[': {
                return ']';
            }
            case ']': {
                return '[';
            }
            case '{': {
                return '}';
            }
            case '}': {
                return '{';
            }
        }
        return '\u0000';
    }
}

