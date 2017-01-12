/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Utils {
    private static boolean isMacOSX;
    private static boolean isWindows;
    private static boolean allowRaggedArrays;
    private static String jlinkJarDir;

    public static boolean isPrimitiveArray(Class cls) {
        Class leafCls = Utils.getArrayComponentType(cls);
        if (leafCls == null) {
            return false;
        }
        return leafCls.isPrimitive();
    }

    public static Class getArrayComponentType(Class cls) {
        Class compCls = cls.getComponentType();
        if (compCls == null) {
            return null;
        }
        if (compCls.isArray()) {
            return Utils.getArrayComponentType(compCls);
        }
        return compCls;
    }

    public static int[] getArrayDims(Object arr) {
        int depth = Utils.getArrayDepth(arr);
        int[] result = new int[depth];
        Object subArray = arr;
        for (int i = 0; i < depth; ++i) {
            int len = Array.getLength(subArray);
            if (len == 0) {
                for (int j = i; j < depth; ++j) {
                    result[j] = 0;
                }
                break;
            }
            result[i] = len;
            subArray = Array.get(subArray, 0);
        }
        return result;
    }

    public static int getArrayDepth(Object arr) {
        int depth = 0;
        for (Class compCls = arr.getClass().getComponentType(); compCls != null; compCls = compCls.getComponentType()) {
            ++depth;
        }
        return depth;
    }

    public static boolean isMacOSX() {
        return isMacOSX;
    }

    public static boolean isWindows() {
        return isWindows;
    }

    public static boolean isRaggedArrays() {
        return allowRaggedArrays;
    }

    public static void setRaggedArrays(boolean allow) {
        allowRaggedArrays = allow;
    }

    public boolean isRectangularArray(Object arr) {
        boolean result = false;
        int depth = Utils.getArrayDepth(arr);
        if (depth == 1) {
            return true;
        }
        int[] dims = Utils.getArrayDims(arr);
        return this.checkLengths(arr, dims, 1);
    }

    private boolean checkLengths(Object a, int[] dims, int curDepth) {
        int expectedLenOfChildren = dims[curDepth];
        int len = Array.getLength(a);
        boolean goDeeper = curDepth < dims.length - 1;
        for (int i = 0; i < len; ++i) {
            Object subArray = Array.get(a, i);
            if (Array.getLength(subArray) != expectedLenOfChildren) {
                return false;
            }
            if (!goDeeper || this.checkLengths(subArray, dims, curDepth + 1)) continue;
            return false;
        }
        return true;
    }

    public static String determineLinkname(String cmdLine) {
        StringTokenizer st = new StringTokenizer(cmdLine);
        while (st != null && st.hasMoreTokens()) {
            String tok = st.nextToken().toLowerCase();
            if (!tok.equals("-linkname") && !tok.equals("-linklaunch") || !st.hasMoreTokens()) continue;
            return st.nextToken();
        }
        return null;
    }

    public static String determineLinkname(String[] argv) {
        if (argv != null) {
            for (int i = 0; i < argv.length - 1; ++i) {
                String s = argv[i].toLowerCase();
                if (!s.equals("-linkname") && !s.equals("-linklaunch")) continue;
                return argv[i + 1];
            }
        }
        return null;
    }

    public static String determineLinkmode(String cmdLine) {
        StringTokenizer st = new StringTokenizer(cmdLine);
        while (st != null && st.hasMoreTokens()) {
            String tok = st.nextToken().toLowerCase();
            if (!tok.equals("-linkmode") || !st.hasMoreTokens()) continue;
            return st.nextToken().toLowerCase();
        }
        return null;
    }

    public static String determineLinkmode(String[] argv) {
        if (argv != null) {
            for (int i = 0; i < argv.length - 1; ++i) {
                String s = argv[i].toLowerCase();
                if (!s.equals("-linkmode")) continue;
                return argv[i + 1].toLowerCase();
            }
        }
        return null;
    }

    public static void writeEvalToStringExpression(MathLink ml, Object obj, int pageWidth, boolean isOutputForm) throws MathLinkException {
        Utils.writeEvalToStringExpression(ml, obj, pageWidth, isOutputForm ? "OutputForm" : "InputForm");
    }

    public static void writeEvalToStringExpression(MathLink ml, Object obj, int pageWidth, String format) throws MathLinkException {
        ml.putFunction("EvaluatePacket", 1);
        ml.putFunction("ToString", 3);
        if (obj instanceof String) {
            ml.putFunction("ToExpression", 1);
        }
        ml.put(obj);
        ml.putFunction("Rule", 2);
        ml.putSymbol("FormatType");
        ml.putSymbol(format);
        ml.putFunction("Rule", 2);
        ml.putSymbol("PageWidth");
        if (pageWidth > 0) {
            ml.put(pageWidth);
        } else {
            ml.putSymbol("Infinity");
        }
        ml.endPacket();
    }

    public static void writeEvalToTypesetExpression(MathLink ml, Object obj, int pageWidth, boolean useStdForm) throws MathLinkException {
        ml.putFunction("EvaluatePacket", 1);
        int numArgs = 1 + (useStdForm ? 0 : 1) + (pageWidth > 0 ? 1 : 0);
        ml.putFunction("EvaluateToTypeset", numArgs);
        ml.put(obj);
        if (!useStdForm) {
            ml.putSymbol("TraditionalForm");
        }
        if (pageWidth > 0) {
            ml.put(pageWidth);
        }
        ml.endPacket();
    }

    public static void writeEvalToImageExpression(MathLink ml, Object obj, int width, int height, int dpi, boolean useFE) throws MathLinkException {
        ml.putFunction("EvaluatePacket", 1);
        int numArgs = 1 + (useFE ? 1 : 0) + (dpi > 0 ? 1 : 0) + (width > 0 || height > 0 ? 1 : 0);
        ml.putFunction("EvaluateToImage", numArgs);
        ml.put(obj);
        if (useFE) {
            ml.put(true);
        }
        if (dpi > 0) {
            ml.putFunction("Rule", 2);
            ml.putSymbol("ImageResolution");
            ml.put(dpi);
        }
        if (width > 0 || height > 0) {
            ml.putFunction("Rule", 2);
            ml.putSymbol("ImageSize");
            ml.putFunction("List", 2);
            if (width > 0) {
                ml.put(width);
            } else {
                ml.putSymbol("Automatic");
            }
            if (height > 0) {
                ml.put(height);
            } else {
                ml.putSymbol("Automatic");
            }
        }
        ml.endPacket();
    }

    public static String getJLinkVersion() {
        return "4.9.1";
    }

    public static BigDecimal bigDecimalFromString(String s) {
        int scale;
        byte b;
        int i;
        int len = s.length();
        byte[] data = s.getBytes();
        int tickPos = -1;
        boolean finishedWithDigits = false;
        for (i = 0; i < len; ++i) {
            byte b2 = data[i];
            if (!(b2 != 0 && b2 != 32 || finishedWithDigits)) {
                len = i;
                finishedWithDigits = true;
                continue;
            }
            if (b2 != 96) continue;
            tickPos = i;
        }
        int precision = -1;
        byte[] digitBuf = new byte[len];
        int digitCount = 0;
        int decimalPos = -1;
        boolean isNegative = false;
        for (i = 0; i < len; ++i) {
            byte b3 = data[i];
            if (b3 >= 48 && b3 <= 57) {
                digitBuf[digitCount++] = b3;
                continue;
            }
            if (b3 == 45) {
                isNegative = true;
                digitBuf[digitCount++] = b3;
                continue;
            }
            if (b3 != 46) break;
            decimalPos = i;
        }
        String unscaledValue = new String(digitBuf, 0, digitCount);
        int n = scale = decimalPos != -1 ? digitCount - decimalPos : 0;
        while (i < len) {
            b = data[i];
            if (b == 101) {
                ++i;
                break;
            }
            if (b == 42) {
                i += 2;
                break;
            }
            ++i;
        }
        digitCount = 0;
        while (i < len) {
            b = data[i];
            if (b == 45 || b >= 48 && b <= 57) {
                digitBuf[digitCount++] = b;
            } else if (b != 43) break;
            ++i;
        }
        if (digitCount > 0) {
            int exponent = Integer.parseInt(new String(digitBuf, 0, digitCount));
            scale -= exponent;
        }
        if (precision >= 0) {
            return new BigDecimal(new BigInteger(unscaledValue), scale, new MathContext(precision));
        }
        return new BigDecimal(new BigInteger(unscaledValue), scale);
    }

    static String createExceptionMessage(Throwable t) {
        if (t instanceof InvocationTargetException) {
            t = ((InvocationTargetException)t).getTargetException();
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, false);
        t.printStackTrace(pw);
        String stackTrace = sw.toString();
        pw.close();
        String[] lines = stackTrace.split("\\r\\n|\\r|\\n");
        ArrayList<String> linesToKeep = new ArrayList<String>(lines.length);
        for (int i = 0; i < lines.length; ++i) {
            String line = lines[i];
            if (line.indexOf("at com.wolfram.jlink") != -1 || line.indexOf("at java.lang.reflect.Method.invoke") != -1 || line.indexOf("at sun.reflect.NativeMethodAccessorImpl") != -1 || line.indexOf("at sun.reflect.DelegatingMethodAccessorImpl") != -1 || line.indexOf("at sun.reflect.GeneratedMethodAccessor1") != -1 || line.indexOf("at sun.reflect.GeneratedMethodAccessor2") != -1) continue;
            linesToKeep.add(line);
        }
        String shortStackTrace = "";
        Iterator iter = linesToKeep.iterator();
        while (iter.hasNext()) {
            shortStackTrace = shortStackTrace + (String)iter.next() + (iter.hasNext() ? "\n" : "");
        }
        return shortStackTrace;
    }

    public static String[] getSystemID() {
        String os = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");
        if (Utils.isWindows()) {
            return new String[]{"Windows", "Windows-x86-64"};
        }
        if (Utils.isMacOSX()) {
            String[] arrstring;
            if (arch.equals("x86_64")) {
                String[] arrstring2 = new String[2];
                arrstring2[0] = "MacOSX-x86-64";
                arrstring = arrstring2;
                arrstring2[1] = "MacOSX-PowerPC64";
            } else {
                String[] arrstring3 = new String[4];
                arrstring3[0] = "MacOSX-x86";
                arrstring3[1] = "MacOSX-x86-64";
                arrstring3[2] = "MacOSX";
                arrstring = arrstring3;
                arrstring3[3] = "MacOSX-PowerPC64";
            }
            return arrstring;
        }
        if (arch.equals("x86") && os.equals("SunOS")) {
            return new String[]{"Solaris-x86-64"};
        }
        if (arch.equals("i386") || arch.equals("x86")) {
            return new String[]{"Linux"};
        }
        if (arch.equals("amd64") && os.equals("Linux")) {
            return new String[]{"Linux-x86-64"};
        }
        if (arch.equals("sparc")) {
            return new String[]{"Solaris", "UltraSPARC", "Solaris-SPARC"};
        }
        if (arch.startsWith("PA_RISC") || arch.startsWith("PA-RISC")) {
            return new String[]{"HP-RISC", "HPUX-PA64"};
        }
        if (arch.equals("mips")) {
            return new String[]{"IRIX-MIPS32", "IRIX-MIPS64"};
        }
        if (arch.equals("alpha")) {
            String[] arrstring;
            if (os.equals("Linux")) {
                String[] arrstring4 = new String[1];
                arrstring = arrstring4;
                arrstring4[0] = "Linux-AXP";
            } else {
                String[] arrstring5 = new String[1];
                arrstring = arrstring5;
                arrstring5[0] = "DEC-AXP";
            }
            return arrstring;
        }
        if (arch.equals("ppc")) {
            String[] arrstring;
            if (os.equals("Linux")) {
                String[] arrstring6 = new String[1];
                arrstring = arrstring6;
                arrstring6[0] = "Linux-PPC";
            } else {
                String[] arrstring7 = new String[2];
                arrstring7[0] = "IBM-RISC";
                arrstring = arrstring7;
                arrstring7[1] = "AIX-Power64";
            }
            return arrstring;
        }
        if (arch.equals("ia64") && os.equals("Linux")) {
            return new String[]{"Linux-IA64"};
        }
        if (arch.equals("arm") && os.equals("Linux")) {
            return new String[]{"Linux-ARM"};
        }
        return new String[]{""};
    }

    public static String getJLinkJarDir() {
        String jarPath;
        if (jlinkJarDir != null) {
            return jlinkJarDir;
        }
        String jarDir = null;
        URL classURL = null;
        try {
            classURL = MathLink.class.getResource("/com/wolfram/jlink/NativeLink.class");
        }
        catch (Exception e) {
            // empty catch block
        }
        if (classURL != null && (jarPath = classURL.getFile()) != null && jarPath.startsWith("file:") && jarPath.indexOf("JLink.jar") != -1) {
            try {
                jarPath = URLDecoder.decode(jarPath, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                // empty catch block
            }
            jarDir = jarPath.substring(5, jarPath.indexOf("JLink.jar"));
        }
        jlinkJarDir = jarDir;
        return jlinkJarDir;
    }

    static {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            isWindows = osName.startsWith("windows");
            isMacOSX = System.getProperty("mrj.version") != null || osName.startsWith("mac os x");
        }
        catch (SecurityException e) {
            // empty catch block
        }
        try {
            String prop = System.getProperty("JLINK_RAGGED_ARRAYS");
            allowRaggedArrays = prop != null && prop.toLowerCase().equals("true");
        }
        catch (SecurityException e) {
            allowRaggedArrays = false;
        }
        jlinkJarDir = null;
    }
}

