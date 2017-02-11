package com.cyhz.support.save.db.command;

import com.cyhz.support.save.db.SupportDBImp;
import com.cyhz.support.save.db.SupportDBTable;
import com.cyhz.support.save.db.SupportSqlConverterFactory;

/**
 * Created by liuxiaolong on 16/12/20.
 */
public class SupportDBUpdateCommand extends SupportDBSaveRowCommand {

    public SupportDBUpdateCommand(SupportDBTable table) {
        super(table);
    }

    @Override
    public Boolean execute() {
        String sql = SupportSqlConverterFactory.invokeConverter().updateTableRowSql(mTable);
        SupportDBImp.instance().execute(sql);
        return false;
    }
}
