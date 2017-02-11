package com.cyhz.support.save.db;

/**
 * Created by liuxiaolong on 16/9/25.
 */
public interface SupportSqlClassConverter {

    public String createTableSql(SupportDBTable table);

    public String insetTableRowSql(SupportDBTable table);

    public String updateTableRowSql(SupportDBTable table);

    public String insetOrUpdateTableRowSql(SupportDBTable table);

    public String queryTableAllSql();

    public String queryTableRowSql(Class<? extends SupportDBTable> table,String[] field,String[] value);

    public String queryTableRowAllSql(Class<? extends SupportDBTable> table);

    public String deleteTableRowSql(SupportDBTable table);

    public String deleteTableRowAll(Class<? extends SupportDBTable> table);

}
