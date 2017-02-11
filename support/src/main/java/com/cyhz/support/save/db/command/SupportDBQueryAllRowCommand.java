package com.cyhz.support.save.db.command;

import android.database.Cursor;

import com.cyhz.support.save.db.SupportDBCursorHelp;
import com.cyhz.support.save.db.SupportDBImp;
import com.cyhz.support.save.db.SupportDBTable;
import com.cyhz.support.save.db.SupportSqlConverterFactory;

import java.util.List;

/**
 * Created by liuxiaolong on 16/10/11.
 */
public class SupportDBQueryAllRowCommand implements SupportDBCommand<List<? extends SupportDBTable>> {

    protected Class mClass;
    public SupportDBQueryAllRowCommand(Class table) {
        mClass = table;
    }

    @Override
    public List<? extends SupportDBTable> execute() {
        String sql = SupportSqlConverterFactory.invokeConverter().queryTableRowAllSql(mClass);
        Cursor result = SupportDBImp.instance().query(sql);
        List<? extends SupportDBTable> rows = SupportDBCursorHelp.cursorToTableRows(result,mClass);
        return rows;
    }
}
