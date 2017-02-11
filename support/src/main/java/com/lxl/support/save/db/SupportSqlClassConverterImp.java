package com.lxl.support.save.db;

import android.text.TextUtils;

import com.lxl.support.util.SupportObjectFieldUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuxiaolong on 16/9/25.
 */
public class SupportSqlClassConverterImp implements SupportSqlClassConverter {

    @Override
    public String createTableSql(SupportDBTable table) {
        List<Map> fields = SupportObjectFieldUtil.getFiledsInfo(table, SupportDBFieldType.FIELDTYPELIST);
        List<String> typesList = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i).get(SupportObjectFieldUtil.KEY_NAME).toString();
            if (name.equals(table.supportPrimaryKey())){
                continue;
            }
            String type = fields.get(i).get(SupportObjectFieldUtil.KEY_TYPE).toString();
            typesList.add(type);
            names.add(name);
        }
        String sql = mergeCreateTableSql(table.getClass().getSimpleName(),table.supportPrimaryKey(),typesList.toArray(new String[0]),names.toArray(new String[0]));
        return sql;
    }

    @Override
    public String insetTableRowSql(SupportDBTable table) {
        List<Map> fields = SupportObjectFieldUtil.getFiledsInfo(table,SupportDBFieldType.FIELDTYPELIST);
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i).get(SupportObjectFieldUtil.KEY_NAME).toString();
            String value = fields.get(i).get(SupportObjectFieldUtil.KEY_VALUE).toString();
            String separator = ",";
            if (names.length() == 0){
                separator = "";
            }
            names.append(separator+"'"+name+"'");
            values.append(separator+"'"+value+"'");
        }
        String sql = String.format("INSERT INTO %s ( %s ) VALUES ( %s );",table.getClass().getSimpleName(),names.toString(),values.toString());
        return sql;
    }

    @Override
    public String updateTableRowSql(SupportDBTable table) {
        List<Map> fields = SupportObjectFieldUtil.getFiledsInfo(table,SupportDBFieldType.FIELDTYPELIST);
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            String name = fields.get(i).get(SupportObjectFieldUtil.KEY_NAME).toString();
            String value = fields.get(i).get(SupportObjectFieldUtil.KEY_VALUE).toString();
            String separator = ",";
            if (sql.length() == 0){
                separator = "";
            }
            sql.append(separator+" '"+name+"' = "+"'"+value+"'");
        }
        String mainId = (String) SupportObjectFieldUtil.getFieldValueByName(table.supportPrimaryKey(),table);
        String result = String.format("UPDATE  %s  SET %s WHERE %s = '%s'",table.getClass().getSimpleName(),sql.toString(),table.supportPrimaryKey(),mainId);
        return result;
    }

    @Override
    public String insetOrUpdateTableRowSql(SupportDBTable table) {
        String url = insetTableRowSql(table);
        url = url.replace("INSERT INTO","REPLACE INTO");
        return url;
    }

    @Override
    public String queryTableAllSql() {
        return "SELECT name FROM sqlite_master where type='table' order by name";
    }

    @Override
    public String queryTableRowSql(Class<? extends SupportDBTable> table, String[] field, String[] value) {
        if (field.length != value.length){
            throw new RuntimeException("查询表的field和value数组长度不一致！");
        }
        if (field.length == 0){
            return queryTableRowAllSql(table);
        }
        String sql = String.format("SELECT * FROM %s where ",table.getSimpleName());
        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        for (int i = 0 ; i < field.length ; i++){
            String key = field[i];
            String val = value[i];
            String separator;
            if(i == 0){
                separator="";
            }else {
                separator = " AND";
            }
            builder.append(String.format("%s %s = '%s'",separator,key,val));
        }
        return builder.toString();
    }

    @Override
    public String queryTableRowAllSql(Class<? extends SupportDBTable> table) {
        String sql = String.format("SELECT * FROM %s",table.getSimpleName());
        return sql;
    }

    @Override
    public String deleteTableRowSql(SupportDBTable table) {
        String sql = String.format("DELETE  FROM %s WHERE %s = '%s'",
                table.getClass().getSimpleName(),
                table.supportPrimaryKey(),
                SupportObjectFieldUtil.getFieldValueByName(table.supportPrimaryKey(),table));
        return sql;
    }

    @Override
    public String deleteTableRowAll(Class<? extends SupportDBTable> table) {
        String sql = String.format("DELETE  FROM %s ",
                table.getSimpleName());
        return sql;
    }

    /**
     * 生产建表语句，
     * @param tableName 表名
     * @param types 类型集合
     * @param names 属性集合
     * @return
     */
    private String mergeCreateTableSql(String tableName,String primaryKey,String[] types,String[] names){
        if (types.length != names.length){
            throw new RuntimeException("创建表的type和name数组长度不一致！");
        }
        if (TextUtils.isEmpty(tableName)){
            throw new RuntimeException("创建表名为空！");
        }
        StringBuilder builder = new StringBuilder();
        int length = types.length;
        for (int i = 0; i < length; i++){
            builder.append(" , "+names[i]+" "+sqlFieldMaps(types[i]));
        }
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s TEXT PRIMARY KEY %s);",tableName,primaryKey,builder);
        return sql;
    }

    /**
     * class的类型映射数据表中对应的类型。
     * @param type
     * @return
     */
    private String sqlFieldMaps(String type){
        if (!SupportDBFieldType.FIELDTYPELIST.contains(type)){
            throw new RuntimeException(type+"  没有映射到对应的数据库字段！");
        }
        int index = SupportDBFieldType.FIELDTYPELIST.indexOf(type);
        String tableType = SupportDBFieldType.FIELDTYPELIST.get(++index);
        return tableType;
    }
}
