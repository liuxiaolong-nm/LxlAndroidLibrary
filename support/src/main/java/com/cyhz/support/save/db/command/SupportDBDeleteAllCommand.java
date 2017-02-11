package com.cyhz.support.save.db.command;

import com.cyhz.support.save.db.SupportDBImp;
import com.cyhz.support.save.db.SupportDBTable;
import com.cyhz.support.save.db.SupportSqlConverterFactory;

import java.util.List;

/**
 * Created by liuxiaolong on 16/12/20.
 */
public class SupportDBDeleteAllCommand extends SupportDBQueryAllRowCommand {

    public SupportDBDeleteAllCommand(Class table) {
        super(table);
    }

    @Override
    public List<? extends SupportDBTable> execute() {
        String sql = SupportSqlConverterFactory.invokeConverter().deleteTableRowAll(mClass);
        SupportDBImp.instance().execute(sql);
        return null;
    }
}
