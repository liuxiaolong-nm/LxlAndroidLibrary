package com.cyhz.support.save.db;

/**
 * Created by liuxiaolong on 16/9/27.
 */
public class SupportSqlConverterFactory {

    public static SupportSqlClassConverter invokeConverter() {
        return new SupportSqlClassConverterImp();
    }
}
