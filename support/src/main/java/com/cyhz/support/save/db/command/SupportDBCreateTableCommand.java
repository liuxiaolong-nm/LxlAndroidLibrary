package com.cyhz.support.save.db.command;

import com.cyhz.support.save.db.SupportDBImp;
import com.cyhz.support.save.db.SupportDBTable;
import com.cyhz.support.save.db.SupportSqlConverterFactory;

/**
 * Created by liuxiaolong on 16/10/11.
 */
public class SupportDBCreateTableCommand implements SupportDBCommand<Boolean> {

    protected SupportDBTable mTable;
    public SupportDBCreateTableCommand(SupportDBTable table) {
        super();
        mTable = table;
    }

    @Override
    public Boolean execute() {
        String sql = SupportSqlConverterFactory.invokeConverter().createTableSql(mTable);
        SupportDBImp.instance().execute(sql);
        return true;
    }

}
