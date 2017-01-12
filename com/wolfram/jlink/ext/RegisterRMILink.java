/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink.ext;

import com.wolfram.jlink.ext.NativeRemoteLink;
import java.io.PrintStream;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class RegisterRMILink {
    public static void main(String[] argv) {
        try {
            String rmiName = RegisterRMILink.determineRMIName(argv);
            if (rmiName == null) {
                System.out.println("Error: -rminame option not specified.");
                return;
            }
            NativeRemoteLink ml = new NativeRemoteLink(argv);
            UnicastRemoteObject.exportObject(ml);
            Naming.rebind(rmiName, ml);
            System.out.println(rmiName + " successfully bound in RMI registry.");
        }
        catch (Exception e) {
            System.out.println("RMITest err: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String determineRMIName(String[] argv) {
        if (argv != null) {
            for (int i = 0; i < argv.length - 1; ++i) {
                if (!argv[i].toLowerCase().equals("-rminame")) continue;
                return argv[i + 1].toLowerCase();
            }
        }
        return null;
    }
}

