package com.df.core;

/**
 * @author MFine
 * @version 1.0
 * @date 2021/11/16 22:45
 **/
public class SbringApplication {

    public static void run(Class<?> clazz, String... args) {
        ClassScanner.scan(clazz.getPackageName());
        Network.start();
    }
}
