package com.statiocraft.jukebox;

public class Console {

    public static void error(Object object) {
        raw("[Error] " + object);
    }

    public static void info(Object object) {
        raw("[Info] " + object);
    }

    public static void raw(Object object) {
        System.out.println("" + object);
    }

    public static void severe(Object object) {
        raw("[Severe] " + object);
    }

    public static void warn(Object object) {
        raw("[Warn] " + object);
    }
}
