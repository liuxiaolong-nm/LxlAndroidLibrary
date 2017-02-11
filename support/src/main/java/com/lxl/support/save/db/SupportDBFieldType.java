package com.lxl.support.save.db;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuxiaolong on 16/10/11.
 */
public class SupportDBFieldType {

    /**
     * model属性的数据类型和sqlite中的table字段属性的映射。如果添加映射设置修改这里。
     */
    public static String[] FIELDTYPES = {
            "class java.lang.String","TEXT",
            "class java.lang.Integer","INTEGER",
            "class java.lang.Float","REAL",
            "int","INTEGER",
            "float","REAL"
    };

    public static List<String> FIELDTYPELIST = Arrays.asList(FIELDTYPES);
}
