package com.wenld.junitdemo;

/**
 * <p/>
 * Author: wenld on 2017/8/21 11:46.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class CaculeClass {
    public int add(int a, int b) {
        return a + b;
    }

    private int mPrivateField;

    private final int mPrivateFinalField = 0;

    private static int mPrivateStaticField = 0;

    private static final int mPrivateStaticFinalField = 0;

    public int mPublicField;

    public final int mPublicFinalField = 0;

    public static int mPublicStaticField = 0;

    public static final int mPublicStaticFinalField = 0;

    public void voidPublic(int a, int b) {
        return;
    }

    public int addPublic(int a, int b) {
        return a + b;
    }

    private int addPrivate(int a, int b) {
        return a + b;
    }

    public static int addPublicStatic(int a, int b) {
        return a + b;
    }

    private static int addPrivateStatic(int a, int b) {
        return a + b;
    }
}
