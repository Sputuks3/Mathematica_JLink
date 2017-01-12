/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.DisabledPreferences;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

public class DisabledPreferencesFactory
implements PreferencesFactory {
    public Preferences systemRoot() {
        return new DisabledPreferences();
    }

    public Preferences userRoot() {
        return new DisabledPreferences();
    }
}

