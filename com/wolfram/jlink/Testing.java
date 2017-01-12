/*
 * Decompiled with CFR 0_119.
 */
package com.wolfram.jlink;

import com.wolfram.jlink.Expr;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.KernelLinkImpl;
import com.wolfram.jlink.LoopbackLink;
import com.wolfram.jlink.MathLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import com.wolfram.jlink.MyComplex;
import com.wolfram.jlink.ObjectHandler;
import com.wolfram.jlink.PacketListener;
import com.wolfram.jlink.PacketPrinter;
import com.wolfram.jlink.StdLink;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Date;
import java.util.Vector;

public class Testing {
    float foo = 0.0f;
    public char charField;
    public int intField;
    public String stringField;
    public URL urlField;
    public static double staticDoubleField = 3.14159;
    public static int staticIntField = 10;
    public static final int finalStaticInt = 42;
    public Testing t;
    private int privateIntField;
    protected int protectedIntField;
    int packageIntField;

    public Testing() {
        this.intField = 42;
        this.stringField = "Hello";
    }

    public Testing(int i, String j) {
        this.intField = i;
        this.stringField = j;
    }

    public static Testing createMe() {
        return new Testing();
    }

    public static int staticAddTwo(int i, int j) {
        return i + j;
    }

    public int addTwo(int i, int j) {
        return i + j;
    }

    public static long longIdentity(long i) {
        return i;
    }

    public static byte byteIdentity(byte i) {
        return i;
    }

    public int getIntField() {
        return this.intField;
    }

    public String getStringField() {
        return this.stringField;
    }

    public Object getObjectField() {
        return this.urlField;
    }

    public double getStaticField() {
        return staticDoubleField;
    }

    public Testing getT() {
        return this.t;
    }

    public void setT() {
        this.t = new Testing();
    }

    public static int[][] returnArrayManual(int n, int m) {
        int[][] res = new int[n][m];
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        try {
            ml.put(res);
        }
        catch (MathLinkException e) {
            // empty catch block
        }
        return res;
    }

    public static byte[] byteArrayIdentity(byte[] a) {
        return a;
    }

    public static byte[][] byteArrayIdentity2(byte[][] a) {
        return a;
    }

    public static byte[][][] byteArrayIdentity3(byte[][][] a) {
        return a;
    }

    public static char[] charArrayIdentity(char[] a) {
        return a;
    }

    public static char[][] charArrayIdentity2(char[][] a) {
        return a;
    }

    public static char[][][] charArrayIdentity3(char[][][] a) {
        return a;
    }

    public static short[] shortArrayIdentity(short[] a) {
        return a;
    }

    public static short[][] shortArrayIdentity2(short[][] a) {
        return a;
    }

    public static short[][][] shortArrayIdentity3(short[][][] a) {
        return a;
    }

    public static int[] intArrayIdentity(int[] a) {
        return a;
    }

    public static int[][] intArrayIdentity2(int[][] a) {
        return a;
    }

    public static int[][][] intArrayIdentity3(int[][][] a) {
        return a;
    }

    public static long[] longArrayIdentity(long[] a) {
        return a;
    }

    public static long[][] longArrayIdentity2(long[][] a) {
        return a;
    }

    public static long[][][] longArrayIdentity3(long[][][] a) {
        return a;
    }

    public static Object floatArrayIdentity(float[] a) {
        return a;
    }

    public static Object floatArrayIdentity2(float[][] a) {
        return a;
    }

    public static Object floatArrayIdentity3(float[][][] a) {
        return a;
    }

    public static double[] doubleArrayIdentity(double[] a) {
        return a;
    }

    public static double[][] doubleArrayIdentity2(double[][] a) {
        return a;
    }

    public static double[][][] doubleArrayIdentity3(double[][][] a) {
        return a;
    }

    public static boolean[] boolArrayIdentity(boolean[] a) {
        return a;
    }

    public static boolean[][] boolArrayIdentity2(boolean[][] a) {
        return a;
    }

    public static boolean[][][] boolArrayIdentity3(boolean[][][] a) {
        return a;
    }

    public static String[] stringArrayIdentity(String[] a) {
        return a;
    }

    public static String[][] stringArrayIdentity2(String[][] a) {
        return a;
    }

    public static String[][][] stringArrayIdentity3(String[][][] a) {
        return a;
    }

    public static Object[] objectArrayIdentityTwo(Object[] a) {
        return a;
    }

    public static Object[][] objectArrayIdentityTwo2(Object[][] a) {
        return a;
    }

    public static void returnStringArrayWithHeads(String[] a, String head1) throws MathLinkException {
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        ml.put(a, new String[]{head1});
    }

    public static void returnStringArray2WithHeads(String[][] a, String head1, String head2) throws MathLinkException {
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        ml.put(a, new String[]{head1, head2});
    }

    public static void returnStringArray3WithHeads(String[][][] a, String head1, String head2, String head3) throws MathLinkException {
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        ml.put(a, new String[]{head1, head2, head3});
    }

    public static void returnIntArrayWithHeads(int[] a, String head1) throws MathLinkException {
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        ml.put(a, new String[]{head1});
    }

    public static void returnIntArray2WithHeads(int[][] a, String head1, String head2) throws MathLinkException {
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        ml.put(a, new String[]{head1, head2});
    }

    public static void returnIntArray3WithHeads(int[][][] a, String head1, String head2, String head3) throws MathLinkException {
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        ml.put(a, new String[]{head1, head2, head3});
    }

    public static int getIntArray(int[] a) {
        return a.length;
    }

    public static int getIntArray2(int[][] a) {
        return a.length * a[0].length;
    }

    public static int getIntArray3(int[][][] a) {
        return a.length * a[0].length * a[0][0].length;
    }

    public static int[] returnIntArray(int len) {
        int[] res = new int[len];
        return res;
    }

    public static int[][] returnIntArray2(int len1, int len2) {
        int[][] res = new int[len1][len2];
        return res;
    }

    public static int[][][] returnIntArray3(int len1, int len2, int len3) {
        int[][][] res = new int[len1][len2][len3];
        return res;
    }

    public static int getLongArray(long[] a) {
        return a.length;
    }

    public static int getLongArray2(long[][] a) {
        return a.length * a[0].length;
    }

    public static int getLongArray3(long[][][] a) {
        return a.length * a[0].length * a[0][0].length;
    }

    public static long[] returnLongArray(int len) {
        return new long[len];
    }

    public static long[][] returnLongArray2(int len1, int len2) {
        return new long[len1][len2];
    }

    public static long[][][] returnLongArray3(int len1, int len2, int len3) {
        return new long[len1][len2][len3];
    }

    public static int getByteArray(byte[] a) {
        return a.length;
    }

    public static int getByteArray2(byte[][] a) {
        return a.length * a[0].length;
    }

    public static int getByteArray3(byte[][][] a) {
        return a.length * a[0].length * a[0][0].length;
    }

    public static byte[] returnByteArray(int len) {
        return new byte[len];
    }

    public static byte[][] returnByteArray2(int len1, int len2) {
        return new byte[len1][len2];
    }

    public static byte[][][] returnByteArray3(int len1, int len2, int len3) {
        return new byte[len1][len2][len3];
    }

    public static int getStringArray3(String[][][] a) {
        return a.length * a[0].length * a[0][0].length;
    }

    public static String[][][] returnStringArray3(int len1, int len2, int len3) {
        String[][][] a = new String[len1][len2][len3];
        for (int i = 0; i < len1; ++i) {
            for (int j = 0; j < len2; ++j) {
                for (int k = 0; k < len3; ++k) {
                    a[i][j][k] = "a" + i * j * k;
                }
            }
        }
        return a;
    }

    public static Testing[] objectArrayIdentity(Testing[] a) {
        return a;
    }

    public static Testing[][] objectArrayIdentity2(Testing[][] a) {
        return a;
    }

    public static Testing[][][] objectArrayIdentity3(Testing[][][] a) {
        return a;
    }

    public static Object[] rawObjectArrayIdentity(Object[] a) {
        String[] foo = (String[])a;
        return a;
    }

    public static double abs(MyComplex c) {
        return Math.sqrt(c.re() * c.re() + c.im() * c.im());
    }

    public static MyComplex conjugate(MyComplex c) {
        return new MyComplex(c.re(), - c.im());
    }

    public static MyComplex[] reverseComplexArray(MyComplex[] a) {
        MyComplex[] rev = new MyComplex[a.length];
        for (int i = 0; i < a.length; ++i) {
            rev[i] = a[a.length - 1 - i];
        }
        return rev;
    }

    public static MyComplex[][] complexArrayIdentity2(MyComplex[][] a) {
        return a;
    }

    public static MyComplex[][][] complexArrayIdentity3(MyComplex[][][] a) {
        return a;
    }

    public static BigInteger bigIntegerIdentity(BigInteger i) {
        return i;
    }

    public static BigDecimal bigDecimalIdentity(BigDecimal i) {
        return i;
    }

    public static BigInteger[] bigIntegerArrayIdentity(BigInteger[] a) {
        return a;
    }

    public static BigInteger[][] bigIntegerArrayIdentity2(BigInteger[][] a) {
        return a;
    }

    public static BigInteger[][][] bigIntegerArrayIdentity3(BigInteger[][][] a) {
        return a;
    }

    public static BigDecimal[] bigDecimalArrayIdentity(BigDecimal[] a) {
        return a;
    }

    public static BigDecimal[][] bigDecimalArrayIdentity2(BigDecimal[][] a) {
        return a;
    }

    public static BigDecimal[][][] bigDecimalArrayIdentity3(BigDecimal[][][] a) {
        return a;
    }

    public static double[] returnNanArray() {
        return new double[]{Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY};
    }

    public static double[][] returnNanArray2() {
        double[][] result = new double[][]{Testing.returnNanArray(), Testing.returnNanArray()};
        return result;
    }

    public static boolean ambig(short s, int i) {
        return true;
    }

    public static boolean ambig(byte b, int i) {
        return false;
    }

    public static boolean ambig2(int i, int j) {
        return false;
    }

    public static boolean ambig2(long i, int j) {
        return true;
    }

    public static boolean ambig2(int i, long j) {
        return true;
    }

    public static boolean ambigArray(byte[] b, int i) {
        return false;
    }

    public static boolean ambigArray(short[] s, int i) {
        return true;
    }

    public static boolean ambigObject(Object o, int i) {
        return false;
    }

    public static boolean ambigObject(Vector o, int i) {
        return true;
    }

    public static boolean ambigObject2(Testing o, int i, byte b) {
        return true;
    }

    public static boolean ambigObject2(Vector o, byte b, int i) {
        return false;
    }

    public static boolean ambigObjectFail(Object o, long i) {
        return false;
    }

    public static boolean ambigObjectFail(Vector o, int i) {
        return true;
    }

    public static boolean realIntAmbig1(int i, double j) {
        return true;
    }

    public static boolean realIntAmbig2(float j, Object o) {
        return true;
    }

    public static boolean realIntAmbig3(double[] d) {
        return true;
    }

    public static boolean realIntAmbig4(int i, double j) {
        return false;
    }

    public static boolean realIntAmbig4(int i, int j) {
        return true;
    }

    public int manual() throws MathLinkException {
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        ml.put(true);
        return 1;
    }

    public int manualTwice() throws MathLinkException {
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        ml.beginManual();
        ml.put(true);
        return 1;
    }

    public int manualException(int flag) throws MathLinkException {
        if (flag == 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        if (flag == 2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ml.putFunction("List", 1);
        if (flag == 3) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ml.put(true);
        if (flag == 4) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return 1;
    }

    public int manualMathLinkException(int flag) throws MathLinkException {
        if (flag == 1) {
            throw new MathLinkException(42, "no msg");
        }
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        if (flag == 2) {
            throw new MathLinkException(42, "no msg");
        }
        ml.putFunction("List", 1);
        if (flag == 3) {
            throw new MathLinkException(42, "no msg");
        }
        ml.put(true);
        if (flag == 4) {
            throw new MathLinkException(42, "no msg");
        }
        return 1;
    }

    public int manualWithPrint() throws MathLinkException {
        KernelLink ml = StdLink.getLink();
        ml.beginManual();
        ml.print("see this printed");
        ml.put(true);
        return 1;
    }

    public static int outerFunc() {
        KernelLink ml = StdLink.getLink();
        try {
            ml.evaluate("Testing`innerFunc[]");
            ml.waitForAnswer();
            return ml.getInteger();
        }
        catch (MathLinkException e) {
            return -1;
        }
    }

    public static int innerFunc() {
        KernelLink ml = StdLink.getLink();
        ml.print("in innerFunc");
        return 42;
    }

    public void throwException(String exc) throws Throwable {
        Class cls;
        KernelLink ml = StdLink.getLink();
        try {
            cls = Class.forName(exc);
        }
        catch (ClassNotFoundException e) {
            ml.print("Exception class not found.");
            return;
        }
        throw (Throwable)cls.newInstance();
    }

    public static int exprLength(Expr e) throws MathLinkException {
        return e.length();
    }

    public static int[] exprArray1(Expr[] ea) throws MathLinkException {
        return new int[]{ea.length, ea[0].length()};
    }

    public static int[] exprArray2(Expr[][] ea) throws MathLinkException {
        int len = ea.length;
        Expr[] ea0 = ea[0];
        Expr[] ea1 = ea[1];
        Expr ea00 = ea[0][0];
        return new int[]{len, ea0.length, ea1.length, ea00.length()};
    }

    public static String[] exprArrayToString(Expr[] ea) throws MathLinkException {
        int len = ea.length;
        String[] res = new String[len];
        for (int i = 0; i < len; ++i) {
            res[i] = ea[i].toString();
        }
        return res;
    }

    public static Expr exprArrayPart(Expr[][] ea, int i, int j) throws MathLinkException {
        return ea[i][j];
    }

    public static Expr exprIdentity(Expr e) {
        return e;
    }

    public static Expr[] exprArrayIdentity(Expr[] e) {
        return e;
    }

    public static Expr[][] exprArrayIdentity2(Expr[][] e) {
        return e;
    }

    public static Expr[][][] exprArrayIdentity3(Expr[][][] e) {
        return e;
    }

    public static /* varargs */ int varargs1(int ... a) {
        int sum = 0;
        for (int i = 0; i < a.length; ++i) {
            sum += a[i];
        }
        return sum;
    }

    public static /* varargs */ int varargs2(int i, Object ... obja) {
        return obja.length;
    }

    public void takeLong(int secs) {
        try {
            Thread.sleep(secs * 1000);
        }
        catch (InterruptedException e) {
            // empty catch block
        }
    }

    public boolean testAbort() {
        KernelLink ml = StdLink.getLink();
        int i = 0;
        while (!ml.wasInterrupted() && ++i <= 7) {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {}
        }
        ml.clearInterrupt();
        return i < 8;
    }

    public static void testTerminate() {
        try {
            StdLink.getLink().getInteger();
        }
        catch (MathLinkException e) {
            // empty catch block
        }
    }

    public boolean testMsgHandler() {
        KernelLink ml = StdLink.getLink();
        if (ml.addMessageHandler(this.getClass(), null, "staticMsgHandler")) {
            int i = 0;
            while (++i <= 5) {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {}
            }
            ml.removeMessageHandler("staticMsgHandler");
            return true;
        }
        return false;
    }

    public static void staticMsgHandler(int msg, int n) {
        System.out.println("In staticMsgHandler. msg = " + msg);
    }

    public static int staticYielder() {
        System.out.println("In staticYielder.");
        return 0;
    }

    public static int a_plus_b(int a, int b) {
        return a + b;
    }

    public void testError() {
        try {
            KernelLink ml = StdLink.getLink();
            ml.putFunction("List", 3);
            ml.put(1);
            throw new NoClassDefFoundError();
        }
        catch (MathLinkException e) {
            return;
        }
    }

    public void speedTest1(int n) {
        KernelLink ml = StdLink.getLink();
        for (int i = 0; i < n; ++i) {
            ml.error();
        }
    }

    public void speedTest2(int n) {
        int j = 0;
        for (int i = 0; i < n; ++i) {
            ++j;
        }
    }

    public void speedTest3(int n) {
        boolean j = false;
        for (int i = 0; i < n; ++i) {
            this.hashCode();
        }
    }

    public static Object returnArg(Object obj) {
        return obj;
    }

    public static void mainFOO(String[] argv) throws Exception {
        ObjectHandler globalVariableOfTypeObjectHandler = null;
        KernelLink kl1 = null;
        KernelLink kl2 = null;
        String[] args = new String[]{"-linkmode", "launch", "-linkname", "c:/program files/wolfram research/mathematica/8.0.4/mathkernel"};
        kl1 = MathLinkFactory.createKernelLink(args);
        kl1.addPacketListener(new PacketPrinter());
        if (globalVariableOfTypeObjectHandler == null) {
            globalVariableOfTypeObjectHandler = ((KernelLinkImpl)kl1).getObjectHandler();
        } else {
            ((KernelLinkImpl)kl1).setObjectHandler(globalVariableOfTypeObjectHandler);
        }
        kl1.discardAnswer();
        kl1.enableObjectReferences();
        kl2 = MathLinkFactory.createKernelLink(args);
        kl2.addPacketListener(new PacketPrinter());
        if (globalVariableOfTypeObjectHandler == null) {
            globalVariableOfTypeObjectHandler = ((KernelLinkImpl)kl2).getObjectHandler();
        } else {
            ((KernelLinkImpl)kl2).setObjectHandler(globalVariableOfTypeObjectHandler);
        }
        kl2.discardAnswer();
        kl2.enableObjectReferences();
        kl1.close();
        kl2.close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] argv) throws IOException {
        MathLink kl = null;
        LoopbackLink loop = null;
        FileOutputStream fos = null;
        PrintStream out = null;
        String desc = null;
        try {
            Object res;
            Object[][] oaa;
            int[][] iaa;
            double[][][] daaa;
            Expr e1;
            double[][] daa;
            double[] da;
            int[][][] iaaa;
            int[] ia;
            int i;
            int k;
            double[][][] res2;
            int i2;
            int j;
            long[][][] laaa;
            byte[][][] baaa;
            float[][][] faaa;
            int i3;
            loop = MathLinkFactory.createLoopbackLink();
            ia = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
            da = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
            iaa = new int[][]{{1, 2, 3}, {4, 5, 6}};
            daa = new double[][]{{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}};
            iaaa = new int[][][]{{{1, 2, 3}, {4, 5, 6}}, {{7, 8, 9}, {10, 11, 12}}};
            daaa = new double[][][]{{{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}}, {{7.0, 8.0, 9.0}, {10.0, 11.0, 12.0}}};
            short[][][] saaa = new short[][][]{{{1, 2, 3}, {4, 5, 6}}, {{7, 8, 9}, {10, 11, 12}}};
            faaa = new float[][][]{{{1.0f, 2.0f, 3.0f}, {4.0f, 5.0f, 6.0f}}, {{7.0f, 8.0f, 9.0f}, {10.0f, 11.0f, 12.0f}}};
            laaa = new long[][][]{{{1, 2, 3}, {4, 5, 6}}, {{7, 8, 9}, {10, 11, 12}}};
            baaa = new byte[][][]{{{1, 2, 3}, {4, 5, 6}}, {{7, 8, 9}, {10, 11, 12}}};
            out = System.out;
            out.println("================  STARTING TEST RUN  ===================");
            out.println(new Date().toString());
            out.println("========================================================");
            desc = "loopbacks";
            try {
                out.println("Starting test " + desc);
                for (i3 = 0; i3 < 1000; ++i3) {
                    LoopbackLink l = MathLinkFactory.createLoopbackLink();
                    l.close();
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + " " + e.toString());
            }
            desc = "listening";
            try {
                out.println("Starting test " + desc);
                for (i3 = 0; i3 < 300; ++i3) {
                    MathLink ml = MathLinkFactory.createMathLink("-linkmode listen -linkoptions MLDontInteract");
                    ml.close();
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + " " + e.toString());
            }
            desc = "ia";
            try {
                out.println("Starting test " + desc);
                loop.put(ia);
                loop.getIntArray2();
                out.println("***** Test FAILED: " + desc);
            }
            catch (MathLinkException e) {
                if (e.getErrCode() != 1002) {
                    System.out.println("***** Test FAILED: " + desc + ". Bad exception code");
                }
                loop.clearError();
            }
            finally {
                loop.newPacket();
            }
            desc = "iaa";
            try {
                out.println("Starting test " + desc);
                loop.put(iaa);
                loop.getArray(-5, 3);
                out.println("***** Test FAILED: " + desc);
            }
            catch (MathLinkException e) {
                if (e.getErrCode() != 1002) {
                    System.out.println("***** Test FAILED: " + desc + ". Bad exception code");
                }
                loop.clearError();
            }
            finally {
                loop.newPacket();
            }
            desc = "da";
            try {
                out.println("Starting test " + desc);
                loop.put(da);
                loop.getDoubleArray2();
                out.println("***** Test FAILED: " + desc);
            }
            catch (MathLinkException e) {
                if (e.getErrCode() != 1002) {
                    System.out.println("***** Test FAILED: " + desc + ". Bad exception code");
                }
                loop.clearError();
            }
            finally {
                loop.newPacket();
            }
            desc = "daa";
            try {
                out.println("Starting test " + desc);
                loop.put(daa);
                loop.getArray(-8, 3);
                out.println("***** Test FAILED: " + desc);
            }
            catch (MathLinkException e) {
                if (e.getErrCode() != 1002) {
                    System.out.println("***** Test FAILED: " + desc + ". Bad exception code");
                }
                loop.clearError();
            }
            finally {
                loop.newPacket();
            }
            desc = "daaa";
            try {
                out.println("Starting test " + desc);
                loop.put(daaa);
                res2 = (double[][][])loop.getArray(-8, 3);
                for (int i4 = 0; i4 < daaa.length; ++i4) {
                    for (j = 0; j < daaa[0].length; ++j) {
                        for (k = 0; k < daaa[0][0].length; ++k) {
                            if (daaa[i4][j][k] == res2[i4][j][k]) continue;
                            out.println("***** Test FAILED: " + desc);
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "iaaa";
            try {
                out.println("Starting test " + desc);
                loop.put(iaaa);
                res2 = (int[][][])loop.getArray(-5, 3);
                for (i2 = 0; i2 < iaaa.length; ++i2) {
                    for (j = 0; j < iaaa[0].length; ++j) {
                        for (k = 0; k < iaaa[0][0].length; ++k) {
                            if (iaaa[i2][j][k] == res2[i2][j][k]) continue;
                            out.println("***** Test FAILED: " + desc);
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "iaaa";
            try {
                out.println("Starting test " + desc);
                loop.put(iaaa);
                res2 = (int[][][])loop.getArray(-5, 3);
                for (i2 = 0; i2 < iaaa.length; ++i2) {
                    for (j = 0; j < iaaa[0].length; ++j) {
                        for (k = 0; k < iaaa[0][0].length; ++k) {
                            if (iaaa[i2][j][k] == res2[i2][j][k]) continue;
                            out.println("***** Test FAILED: " + desc);
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "faaa";
            try {
                out.println("Starting test " + desc);
                loop.put(faaa);
                res2 = (float[][][])loop.getArray(-7, 3);
                for (i2 = 0; i2 < faaa.length; ++i2) {
                    for (j = 0; j < faaa[0].length; ++j) {
                        for (k = 0; k < faaa[0][0].length; ++k) {
                            if (faaa[i2][j][k] == res2[i2][j][k]) continue;
                            out.println("***** Test FAILED: " + desc);
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "baaa";
            try {
                out.println("Starting test " + desc);
                loop.put(baaa);
                res2 = (byte[][][])loop.getArray(-2, 3);
                for (i2 = 0; i2 < baaa.length; ++i2) {
                    for (j = 0; j < baaa[0].length; ++j) {
                        for (k = 0; k < baaa[0][0].length; ++k) {
                            if (baaa[i2][j][k] == res2[i2][j][k]) continue;
                            out.println("***** Test FAILED: " + desc);
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "laaa";
            try {
                out.println("Starting test " + desc);
                loop.put(laaa);
                res2 = (long[][][])loop.getArray(-6, 3);
                for (i2 = 0; i2 < laaa.length; ++i2) {
                    for (j = 0; j < laaa[0].length; ++j) {
                        for (k = 0; k < laaa[0][0].length; ++k) {
                            if (laaa[i2][j][k] == res2[i2][j][k]) continue;
                            out.println("***** Test FAILED: " + desc);
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "complexarray";
            try {
                out.println("Starting test " + desc);
                loop.setComplexClass(MyComplex.class);
                MyComplex[][] caa = new MyComplex[][]{{new MyComplex(0.0, 0.0), new MyComplex(0.0, 1.0)}, {new MyComplex(1.0, 0.0), new MyComplex(1.0, 1.0)}};
                loop.put(caa);
                Object[][] res3 = loop.getComplexArray2();
                for (int i5 = 0; i5 < caa.length; ++i5) {
                    for (int j = 0; j < caa[0].length; ++j) {
                        double d2;
                        double d1 = caa[i5][j].re();
                        if (d1 == (d2 = ((MyComplex)res3[i5][j]).re())) continue;
                        out.println("***** Test FAILED: " + desc);
                    }
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "put/getdata";
            try {
                out.println("Starting test " + desc);
                byte[] a = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
                loop.putNext(34);
                loop.putSize(a.length);
                loop.putData(a);
                if (loop.getNext() != 34) {
                    System.out.println("***** Test FAILED: " + desc);
                }
                int count = loop.bytesToGet();
                byte[] res4 = loop.getData(a.length);
                for (i = 0; i < a.length; ++i) {
                    if (res4[i] == a[i]) continue;
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "putarraywithheads1";
            try {
                out.println("Starting test " + desc);
                loop.put(iaa, new String[]{"foo", "bar"});
                long mark = loop.createMark();
                int[][] res5 = loop.getIntArray2();
                for (i = 0; i < iaa.length; ++i) {
                    for (int j = 0; j < iaa[0].length; ++j) {
                        if (iaa[i][j] == res5[i][j]) continue;
                        out.println("***** Test FAILED: " + desc);
                    }
                }
                loop.seekMark(mark);
                desc = "putarraywithheads2";
                e1 = loop.getExpr();
                if (!e1.part(1).head().asString().equals("bar")) {
                    out.println("***** Test FAILED: " + desc);
                }
                loop.destroyMark(mark);
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "intexpr";
            try {
                out.println("Starting test " + desc);
                loop.put(42);
                e1 = loop.getExpr();
                if (!e1.integerQ()) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "realexpr";
            try {
                out.println("Starting test " + desc);
                loop.put(42.01);
                e1 = loop.getExpr();
                if (!e1.realQ()) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "intarrayexpr";
            try {
                out.println("Starting test " + desc);
                loop.put(ia);
                e1 = loop.getExpr();
                if (!e1.listQ() || !e1.vectorQ() || !e1.vectorQ(1) || e1.matrixQ()) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "intarray2expr";
            try {
                out.println("Starting test " + desc);
                loop.put(iaa);
                e1 = loop.getExpr();
                if (!e1.listQ() || e1.vectorQ() || !e1.matrixQ() || !e1.matrixQ(1)) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "intarray3expr";
            try {
                out.println("Starting test " + desc);
                loop.put(iaaa);
                e1 = loop.getExpr();
                if (!e1.listQ() || e1.vectorQ() || e1.matrixQ() || !e1.part(new int[]{1, 1}).listQ() || !e1.part(2).matrixQ() || !e1.part(new int[]{1, 1, 1}).integerQ() || e1.part(new int[]{1, 2, 3}).asInt() != 6) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "realarrayexpr";
            try {
                out.println("Starting test " + desc);
                loop.put(da);
                e1 = loop.getExpr();
                if (!e1.listQ() || !e1.vectorQ() || !e1.vectorQ(2) || e1.matrixQ()) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "realarray2expr";
            try {
                out.println("Starting test " + desc);
                loop.put(daa);
                e1 = loop.getExpr();
                if (!e1.listQ() || e1.vectorQ() || !e1.matrixQ() || !e1.matrixQ(2)) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            desc = "realarray3expr";
            try {
                out.println("Starting test " + desc);
                loop.put(daaa);
                e1 = loop.getExpr();
                if (!e1.listQ() || e1.vectorQ() || e1.matrixQ() || !e1.part(new int[]{1, 1}).listQ() || !e1.part(2).matrixQ() || !e1.part(new int[]{1, 1, 1}).realQ()) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                loop.clearError();
            }
            KernelLink k = MathLinkFactory.createKernelLink("-linkmode launch -linkname '" + argv[0] + "'");
            k.discardAnswer();
            kl = k;
            kl.enableObjectReferences();
            kl.addPacketListener(new PacketPrinter(System.out));
            kl.putFunction("EvaluatePacket", 1);
            String s = "foobar";
            kl.put(s);
            kl.endPacket();
            kl.waitForAnswer();
            System.out.println(kl.getString());
            kl.newPacket();
            kl.putFunction("EvaluatePacket", 1);
            Object obj = new Object();
            kl.put(obj);
            kl.endPacket();
            kl.waitForAnswer();
            System.out.println(kl.getExpr().toString());
            kl.newPacket();
            kl.putFunction("EvaluatePacket", 1);
            kl.putFunction("List", 1);
            kl.putSymbol("Null");
            kl.endPacket();
            kl.waitForAnswer();
            kl.checkFunction("List");
            kl.getSymbol();
            kl.newPacket();
            desc = "objectarray";
            try {
                out.println("Starting test " + desc);
                oaa = new Object[][]{{new Object(), new Object()}, {new Object(), new Object()}};
                kl.putFunction("EvaluatePacket", 1);
                kl.put(oaa);
                kl.endPacket();
                kl.waitForAnswer();
                System.out.println("" + kl.checkFunction("List"));
                System.out.println("" + kl.checkFunction("List"));
                Object o11 = kl.getObject();
                Object o12 = kl.getObject();
                kl.checkFunction("List");
                Object o21 = kl.getObject();
                Object o22 = kl.getObject();
                if (o11 != oaa[0][0] || o12 != oaa[0][1] || o21 != oaa[1][0] || o22 != oaa[1][1]) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                kl.clearError();
            }
            finally {
                kl.newPacket();
            }
            desc = "objectarray2";
            try {
                out.println("Starting test " + desc);
                oaa = new Object[][]{{new Exception(), new Object()}, {new Thread(), new Testing()}};
                kl.putFunction("EvaluatePacket", 1);
                kl.put(oaa);
                kl.endPacket();
                kl.waitForAnswer();
                res = (Object[][])kl.getArray(Object.class, 2);
                if (res[0][0] != oaa[0][0] || res[0][1] != oaa[0][1] || res[1][0] != oaa[1][0] || res[1][1] != oaa[1][1]) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                kl.clearError();
            }
            finally {
                kl.newPacket();
            }
            desc = "objectarray3";
            try {
                out.println("Starting test " + desc);
                kl.putFunction("EvaluatePacket", 1);
                kl.put(iaa);
                kl.endPacket();
                kl.waitForAnswer();
                int[][] res6 = (int[][])kl.getArray(Integer.TYPE, 2);
                if (res6[0][0] != iaa[0][0] || res6[0][1] != iaa[0][1] || res6[1][0] != iaa[1][0] || res6[1][1] != iaa[1][1]) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                kl.clearError();
            }
            finally {
                kl.newPacket();
            }
            desc = "objectarrayref";
            try {
                out.println("Starting test " + desc);
                oaa = new String[][]{{new String(), new String()}, {new String(), new String()}};
                kl.putFunction("EvaluatePacket", 1);
                kl.putReference(oaa);
                kl.endPacket();
                kl.waitForAnswer();
                res = kl.getObject();
                if (res != oaa) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                kl.clearError();
            }
            finally {
                kl.newPacket();
            }
            desc = "yield";
            try {
                out.println("Starting test " + desc);
                kl.setYieldFunction(Testing.class, null, "beepYielder");
                kl.evaluate("Do[2+2,{1000000}]");
                kl.waitForAnswer();
                if (!kl.getSymbol().equals("Null")) {
                    out.println("***** Test FAILED: " + desc);
                }
                kl.setYieldFunction(null, null, null);
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                kl.clearError();
            }
            finally {
                kl.newPacket();
            }
            desc = "abort";
            try {
                out.println("Starting test " + desc);
                kl.evaluate("While[True]");
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                    // empty catch block
                }
                kl.putMessage(3);
                kl.waitForAnswer();
                if (!kl.getSymbol().equals("$Aborted")) {
                    out.println("***** Test FAILED: " + desc);
                }
            }
            catch (Exception e) {
                System.out.println("***** Test FAILED: " + desc + ". " + e.toString());
                kl.clearError();
            }
            finally {
                kl.newPacket();
            }
            out.println("=================  ENDING TEST  ===================");
        }
        catch (Exception e) {
            System.out.println("MATHLINK EXCEPTION:");
            System.out.println(e.toString());
            e.printStackTrace();
        }
        finally {
            loop.close();
            if (kl != null) {
                kl.close();
            }
            if (fos != null) {
                fos.close();
                out.close();
            }
        }
        System.exit(1);
    }

    public static boolean beepYielder() {
        System.out.println("yielder");
        return false;
    }
}

