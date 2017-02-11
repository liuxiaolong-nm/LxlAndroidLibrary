package com.lxl.support.save.db.command;

import android.database.Cursor;

import com.lxl.support.save.db.SupportDBCursorHelp;
import com.lxl.support.save.db.SupportDBImp;
import com.lxl.support.save.db.SupportDBTable;
import com.lxl.support.save.db.SupportSqlConverterFactory;

import java.util.List;

/**
 * Created by liuxiaolong on 16/12/20.
 */
public class SupportDBQueryCommand extends SupportDBQueryAllRowCommand {

    private String[] fields,values;
    public SupportDBQueryCommand(Class table,String[] fields,String[] values) {
        super(table);
        this.fields = fields;
        this.values = values;
    }

    @Override
    public List<? extends SupportDBTable> execute() {
        String sql = SupportSqlConverterFactory.invokeConverter().queryTableRowSql(mClass,fields,values);
        Cursor result = SupportDBImp.instance().query(sql);
        List<? extends SupportDBTable> rows = SupportDBCursorHelp.cursorToTableRows(result,mClass);
        return rows;
    }
}
