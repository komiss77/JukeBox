package com.statiocraft.jukebox.fromapi.util;

public class NumberUtil {

    public static boolean isByte(String string) {
        try {
            Byte.parseByte(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static Byte toByte(String string) {
        return !isByte(string) ? null : Byte.valueOf(Byte.parseByte(string));
    }

    public static boolean isLong(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static Long toLong(String string) {
        return !isLong(string) ? null : Long.valueOf(Long.parseLong(string));
    }

    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static boolean isInt(String string) {
        return isInteger(string);
    }

    public static Integer toInteger(String string) {
        return !isInteger(string) ? null : Integer.valueOf(Integer.parseInt(string));
    }

    public static Integer toInt(String string) {
        return toInteger(string);
    }

    public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static Double toDouble(String string) {
        return !isDouble(string) ? null : Double.valueOf(Double.parseDouble(string));
    }

    public static boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static Float toFloat(String string) {
        return !isFloat(string) ? null : Float.valueOf(Float.parseFloat(string));
    }

    public static boolean isShort(String string) {
        try {
            Short.parseShort(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static Short toShort(String string) {
        return !isShort(string) ? null : Short.valueOf(Short.parseShort(string));
    }
}
