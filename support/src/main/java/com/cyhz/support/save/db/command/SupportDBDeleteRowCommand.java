package com.cyhz.support.save.db.command;


import com.cyhz.support.save.db.SupportDBImp;
import com.cyhz.support.save.db.SupportDBTable;
import com.cyhz.support.save.db.SupportSqlConverterFactory;

/**
 * Created by liuxiaolong on 16/10/11.
 */
public class SupportDBDeleteRowCommand extends SupportDBCreateTableCommand {

    public SupportDBDeleteRowCommand(SupportDBTable table) {
        super(table);
    }

    @Override
    public Boolean execute() {
        String sql = SupportSqlConverterFactory.invokeConverter().deleteTableRowSql(mTable);
        SupportDBImp.instance().execute(sql);
        return false;
    }
}
