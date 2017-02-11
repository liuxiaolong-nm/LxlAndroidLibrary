package com.lxl.support.save.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by liuxiaolong on 16/9/25.
 */
public class SupportDBImp implements SupportDB {
    private Context mContext;
    private static Object lock  = new Object();
    private static SupportDBImp dbImp ;
    private DB mDb;
    private SQLiteDatabase mSQLiteDatabase;
    private TransactionCallBack mTransactionCallBack;
    private Set<String> tableNames = new HashSet<>();

    private SupportDBImp(Context context){
        mContext = context;
        mDb = new DB();
    }

    public static SupportDBImp instance(){
        if (dbImp == null){
            throw new RuntimeException("实例为空，请先调用instance(Context context)函数");
        }
        return dbImp;
    }

    public synchronized static SupportDBImp instance(Context context){
        if (dbImp == null){
            dbImp = new SupportDBImp(context);
        }
        return dbImp;
    }

    @Override
    public void open() {
        if (mSQLiteDatabase  == null){
            mSQLiteDatabase = mDb.getWritableDatabase();
            String sql = SupportSqlConverterFactory.invokeConverter().queryTableAllSql();
            Cursor cursor = mSQLiteDatabase.rawQuery(sql,new String[]{});
            Set<String> names = SupportDBCursorHelp.cursorToTables(cursor);
            tableNames.addAll(names);
        }
    }

    @Override
    public void close() {
        if (mSQLiteDatabase != null){
            mSQLiteDatabase.close();
            tableNames.clear();
            mSQLiteDatabase = null;
        }
    }

    /**
     * 开始事物操作。
     * @param mTransactionCallBack
     */
    public void startTransaction(TransactionCallBack mTransactionCallBack) {
        try {
            mSQLiteDatabase.beginTransaction();
            if (mTransactionCallBack != null){
                mTransactionCallBack.transaction();
            }
            mSQLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mSQLiteDatabase.endTransaction();
        }
    }

    @Override
    public Set<String> tables() {
        return tableNames;
    }

    @Override
    public Cursor query(String sql) {
        try {
            if (mSQLiteDatabase != null){
                return mSQLiteDatabase.rawQuery(sql,null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        throw new RuntimeException("mSQLiteDatabase 为空");
    }

    @Override
    public void execute(String sql) {
        if (mSQLiteDatabase != null) {
            mSQLiteDatabase.execSQL(sql);
        }
    }

    private class DB extends SQLiteOpenHelper{

        public DB(){
            super(mContext, "cyhz_save", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public interface TransactionCallBack{
        public void transaction();
    }

}
