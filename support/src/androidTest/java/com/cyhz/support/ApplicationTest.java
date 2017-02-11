package com.cyhz.support;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.cyhz.support.save.db.SupportDBImp;
import com.cyhz.support.save.db.SupportDBTableEngine;
import com.cyhz.support.save.db.SupportSqlConverterFactory;
import com.cyhz.support.util.SupportDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = "ApplicationTest";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        SupportDBImp.instance(getContext()).open();
    }

    public ApplicationTest() {
        super(Application.class);
    }

    /**
     * 创建表的语句
     */
    public void testSqlCreateString(){
        TableTest6 table = new TableTest6();
        table.setAge(16);
        table.setName("小明5");
        table.setHeadUrl("图片5");
        String sql = SupportSqlConverterFactory.invokeConverter().createTableSql(table);
        Log.e(TAG, "testSqlCreateString: "+sql);
        //table.supportSave();
    }
    /**
     * 条件查询查询sql
     */
    public void testSqlQueryString(){
        String[] fields = {};
        String[] values = {};
        String sql = SupportSqlConverterFactory.invokeConverter().queryTableRowSql(TableTest6.class,fields,values);
        Log.e(TAG,sql);
    }

    /**
     * 更新sql
     */
    public void testSqlUpdateString(){
        TableTest6 table = new TableTest6();
        table.setAge(15);
        table.setName("小明5");
        table.setHeadUrl("图片5");
        String sql = SupportSqlConverterFactory.invokeConverter().updateTableRowSql(table);
        Log.e(TAG, "testSqlUpdateString: "+sql);
    }

    /**
     * 表储存。
     */
    public void testSaveTable(){
        TableTest6 table = new TableTest6();
        table.setAge(18);
        table.setName("小明8");
        table.setHeadUrl("图片8");
        table.supportSave();
        Log.e(TAG, "testSaveTable: "+table.toString());
    }

    /**
     * 更新表。
     */
    public void testUpdateTable(){
        List<TableTest6> tables = TableTest6.supportEngineQueryAll(TableTest6.class);
        Log.e(TAG, "testUpdateTable...before: "+tables);
        tables.get(0).setName("小李8");
        tables.get(0).supportUpdate();
        List<TableTest6> tables1 = TableTest6.supportEngineQueryAll(TableTest6.class);
        Log.e(TAG, "testUpdateTable...after: "+tables1);
    }

    /**
     * 查询表所有数据。
     */
    public void testQueryAll(){
        List<TableTest6> tables = TableTest6.supportEngineQueryAll(TableTest6.class);
        Log.e(TAG, "testQueryAll: "+tables.toString());
    }

    /**
     * 查询数据。
     */
    public void testQuery(){
        List<TableTest6> tableTest1s = TableTest6.supportEngineQuery(TableTest6.class,new String[]{},new String[]{});
        Log.e(TAG, "testQuery: "+tableTest1s.toString());
    }

    /**
     * 清空表
     */
    public void testDeleteAllTable(){
        TableTest6.supportEngineDeleteAll(TableTest6.class);
        Log.e(TAG, "testClearTable: "+TableTest6.class.getSimpleName());
    }

    public void testAddAllTable(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TableTest6> tables = new ArrayList<>();
                for (int i = 0; i < 100 ; i++){
                    TableTest6 tableTest6 = new TableTest6();
                    tableTest6.setName(i+"_name");
                    tableTest6.setAge(new Integer(i));
                    tableTest6.setHeadUrl(SupportDataUtil.getCurrentDateTime());
                    tables.add(tableTest6);
                    tableTest6.supportLiteSave();
                }
                //TableTest6.supportLiteBatchSave(tables);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TableTest7> tables = new ArrayList<>();
                for (int i = 100; i < 200 ; i++){
                    TableTest7 tableTest6 = new TableTest7();
                    tableTest6.setName(i+"");
                    tableTest6.setAge(new Integer(i));
                    tableTest6.setHeadUrl(SupportDataUtil.getCurrentDateTime());
                    tables.add(tableTest6);
                    tableTest6.supportLiteSave();
                }
                //TableTest6.supportLiteBatchSave(tables);
            }
        });
    }

    public static class TableTest6 extends SupportDBTableEngine{
        public String name;
        public String headUrl;
        public Integer age;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "{" +
                    " \"name\":" + "\"" + name + "\"" +
                    ", \"headUrl\":" + "\"" + headUrl + "\"" +
                    ", \"age\":" + "\"+" +age+ "\"" +
                    "}";
        }
    }

    public static class TableTest7 extends SupportDBTableEngine{
        public String name;
        public String headUrl;
        public Integer age;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "{" +
                    " \"name\":" + "\"" + name + "\"" +
                    ", \"headUrl\":" + "\"" + headUrl + "\"" +
                    ", \"age\":" + "\"+" +age+ "\"" +
                    "}";
        }
    }
}