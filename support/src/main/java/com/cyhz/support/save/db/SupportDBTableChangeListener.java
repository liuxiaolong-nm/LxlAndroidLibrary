package com.cyhz.support.save.db;

import java.util.Map;

/**
 * Created by liuxiaolong on 16/11/13.
 */
public interface SupportDBTableChangeListener<T> {
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    public void change(T engine, int changeCode, Map<String,String> desc);

}
