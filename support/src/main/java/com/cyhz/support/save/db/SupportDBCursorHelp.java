package com.cyhz.support.save.db;

import android.database.Cursor;

import com.cyhz.support.util.SupportObjectFieldUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liuxiaolong on 16/9/27.
 */
public class SupportDBCursorHelp {

    /**
     * 解析查询所有表返回的cursor。
     *
     * @param cursor
     * @return
     *
     * liuxiaolong 201609271759
     */
    public static Set<String> cursorToTables(Cursor cursor){
        if (cursor == null){
            throw new RuntimeException("cursor 为空");
        }
        Set<String> tables = new HashSet<>();
        while(cursor.moveToNext()){
            //遍历出表名
            String name = cursor.getString(0);
            tables.add(name);
        };
        return tables;
    }

    /**
     * cursor 对应映射成对应的表集合。
     * @param cursor
     * @param table
     * @return
     *
     * liuxiaolong 201609271858
     */
    public static List<SupportDBTable> cursorToTableRows(Cursor cursor,Class<? extends SupportDBTable> table){
        List<SupportDBTable> tables = new ArrayList<>();
        if (cursor != null){
            try {
                Object newObJect = table.newInstance();
                List<Map> fields = SupportObjectFieldUtil.getFiledsInfo(newObJect, SupportDBFieldType.FIELDTYPELIST);
                if (cursor.getColumnCount() != fields.size()){
                    throw new RuntimeException("class 属性数量和 Cursor返回的列数量不匹配");
                }
                while (cursor.moveToNext()){
                    SupportDBTable newObject = table.newInstance();
                    for (Map map: fields){
                        String name = map.get(SupportObjectFieldUtil.KEY_NAME).toString();
                        String type = map.get(SupportObjectFieldUtil.KEY_TYPE).toString();
                        if (!SupportDBFieldType.FIELDTYPELIST.contains(type)){
                            continue;
                        }
                        int index = cursor.getColumnIndex(name);
                        Object value = selectExecuteMothod(cursor,index,type);
                        SupportObjectFieldUtil.setFieldValueByName(name,value,newObject);
                    }
                    tables.add(newObject);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return tables;
    }

    /**
     *  Cursor 根据类型设置Cursor调用函数。
     * @param cursor
     * @param index
     * @param type
     * @return
     *
     * liuxiaolong 201609271850
     */
    private static Object selectExecuteMothod(Cursor cursor, int index,String type){
            if (type.equals("class java.lang.String")){
                return cursor.getString(index);
            }
            if (type.equals("class java.lang.Integer")){
                return new Integer(cursor.getInt(index));
            }
            if (type.equals("class java.lang.Float")){
                return new Float(cursor.getFloat(index));
            }
            if(type.equals("int")){
                return cursor.getInt(index);
            }
            if (type.equals("float")){
                return cursor.getFloat(index);
            }
            throw new RuntimeException(type +" 没有映射到对应的数据库字段！");
    }

}
