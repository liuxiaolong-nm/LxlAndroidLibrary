package com.lxl.support.save.db;

import android.database.Cursor;

import java.util.Set;

/**
 * Created by liuxiaolong on 16/9/25.
 */
public interface SupportDB {
    public void open();
    public void close();
    public Set<String> tables();
    public Cursor query(String sql);
    public void execute(String sql);
}
