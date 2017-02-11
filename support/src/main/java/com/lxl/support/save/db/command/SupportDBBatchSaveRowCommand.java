package com.lxl.support.save.db.command;

import com.lxl.support.save.db.SupportDBImp;
import com.lxl.support.save.db.SupportDBTable;
import com.lxl.support.save.db.SupportSqlConverterFactory;

import java.util.List;

/**
 * Created by liuxiaolong on 17/1/18.
 *
 * 批量储存的命令。
 */
public class SupportDBBatchSaveRowCommand implements SupportDBCommand{

    private List<? extends SupportDBTable> mTables;
    public SupportDBBatchSaveRowCommand(List<? extends SupportDBTable> rows){
        mTables = rows;
    }

    @Override
    public Object execute() {
        SupportDBImp.instance().startTransaction(new SupportDBImp.TransactionCallBack() {
            @Override
            public void transaction() {
                for (SupportDBTable table : mTables){
                    String sql = SupportSqlConverterFactory.invokeConverter().insetTableRowSql(table);
                    SupportDBImp.instance().execute(sql);
                }
            }
        });
        return null;
    }
}
