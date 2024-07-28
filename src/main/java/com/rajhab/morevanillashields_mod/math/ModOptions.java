package com.rajhab.morevanillashields_mod.math;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

/**
 * Utility class for reading system properties.
 *
 * @author Kai Burjack
 */
public final class ModOptions {

    /**
     * Whether certain debugging checks should be made, such as that only direct NIO Buffers are used when Unsafe is active,
     * and a proxy should be created on calls to readOnlyView().
     */
    public static final boolean DEBUG = hasOption(System.getProperty("joml.debug", "false"));

    /**
     * Whether <i>not</i> to use sun.misc.Unsafe when copying memory with MemUtil.
     */
    public static final boolean NO_UNSAFE = hasOption(System.getProperty("joml.nounsafe", "false"));
    /**
     * Whether to <i>force</i> the use of sun.misc.Unsafe when copying memory with MemUtil.
     */
    public static final boolean FORCE_UNSAFE = hasOption(System.getProperty("joml.forceUnsafe", "false"));

    /**
     * Whether fast approximations of some java.lang.Math operations should be used.
     */
    public static final boolean FASTMATH = hasOption(System.getProperty("joml.fastmath", "false"));

    /**
     * When {@link #FASTMATH} is <code>true</code>, whether to use a lookup table for sin/cos.
     */
    public static final boolean SIN_LOOKUP = hasOption(System.getProperty("joml.sinLookup", "false"));

    /**
     * When {@link #SIN_LOOKUP} is <code>true</code>, this determines the table size.
     */
    public static final int SIN_LOOKUP_BITS = Integer.parseInt(System.getProperty("joml.sinLookup.bits", "14"));

    /**
     * Whether to use a {@link NumberFormat} producing scientific notation output when formatting matrix,
     * vector and quaternion components to strings.
     */
    public static final boolean useNumberFormat = hasOption(System.getProperty("joml.format", "true"));

    /**
     * Whether to try using java.lang.Math.fma() in most matrix/vector/quaternion operations if it is available.
     * If the CPU does <i>not</i> support it, it will be a lot slower than `a*b+c` and potentially generate a lot of memory allocations
     * for the emulation with `java.util.BigDecimal`, though.
     */
    public static final boolean USE_MATH_FMA = hasOption(System.getProperty("joml.useMathFma", "false"));

    /**
     * When {@link #useNumberFormat} is <code>true</code> then this determines the number of decimal digits
     * produced in the formatted numbers.
     */
    public static final int numberFormatDecimals = Integer.parseInt(System.getProperty("joml.format.decimals", "3"));

    /**
     * The {@link NumberFormat} used to format all numbers throughout all JOML classes.
     */
    public static final NumberFormat NUMBER_FORMAT = decimalFormat();

    private ModOptions() {
    }

    private static NumberFormat decimalFormat() {
        NumberFormat df;
        if (useNumberFormat) {
            char[] prec = new char[numberFormatDecimals];
            Arrays.fill(prec, '0');
            df = new DecimalFormat(" 0." + new String(prec) + "E0;-");
        } else {
            df = NumberFormat.getNumberInstance(Locale.ENGLISH);
            df.setGroupingUsed(false);
        }
        return df;
    }

    private static boolean hasOption(String v) {
        if (v == null)
            return false;
        if (v.trim().length() == 0)
            return true;
        return Boolean.valueOf(v).booleanValue();
    }

}