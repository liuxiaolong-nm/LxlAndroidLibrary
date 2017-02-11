package com.cyhz.support.save.db.command;

import android.database.Cursor;

import com.cyhz.support.save.db.SupportDBCursorHelp;
import com.cyhz.support.save.db.SupportDBImp;
import com.cyhz.support.save.db.SupportSqlConverterFactory;

import java.util.Set;

/**
 * Created by liuxiaolong on 17/1/18.
 */
public class SupportDBQueryAllTableCommand implements SupportDBCommand<Set<String>>{

    @Override
    public Set<String> execute() {
        String sql = SupportSqlConverterFactory.invokeConverter().queryTableAllSql();
        Cursor cursor = SupportDBImp.instance().query(sql);
        Set<String> names = SupportDBCursorHelp.cursorToTables(cursor);
        return names;
    }
}
