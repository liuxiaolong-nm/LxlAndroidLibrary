package com.cyhz.support.util;

import android.support.v4.content.Loader;

/**
 * Created by liuxiaolong on 16/11/13.
 */
public class SupportClassUtil {
    /**
     * Returns the {@link Class} object that contains a caller's method.
     *
     * @param i the offset on the call stack of the method of interest
     * @return the Class found from the calling context, or {@code null} if not found
     */
    public static Class currentContainClass(int i) {
        Class[] classContext = new SecurityManager() {
            @Override public Class[] getClassContext() {
                return super.getClassContext();
            }
        }.getClassContext();
        if (classContext != null) {
            for (int j = 0; j < classContext.length; j++) {
                if (classContext[j] == Loader.class) {
                    return classContext[i+j];
                }
            }
        } else {
            // SecurityManager.getClassContext() returns null on Android 4.0
            try {
                StackTraceElement[] classNames = Thread.currentThread().getStackTrace();
                for (int j = 0; j < classNames.length; j++) {
                    if (Class.forName(classNames[j].getClassName()) == Loader.class) {
                        return Class.forName(classNames[i+j].getClassName());
                    }
                }
            } catch (ClassNotFoundException e) { }
        }
        return null;
    }
}
