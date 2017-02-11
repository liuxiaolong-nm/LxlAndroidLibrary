package com.lxl.support;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by liuxiaolong on 17/1/18.
 */
public class DBTest extends AndroidTestCase {

    private static final String TAG = "DBTest";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void testAllTableName(){
//        SupportDBImp.instance(getContext()).open();
//        Set<String> tableNames = DbEntityTest.supportQueryAllTableName();
//        Log.e(TAG, "testAllTableName: "+tableNames);
//
//        List<DbEntityTest> dbTests = new ArrayList<>();
//        Log.e(TAG, "testAllTableName: 开始 "+System.currentTimeMillis());
//        for (int i = 0; i < 10000; i++){
//            DbEntityTest dbTest = new DbEntityTest();
//            dbTest.setName(""+i);
//            dbTests.add(dbTest);
//          //  dbTest.supportLiteSave();
//        }
//         DbEntityTest.supportLiteBatchSave(dbTests);
//        Log.e(TAG, "testAllTableName: 结束 "+System.currentTimeMillis());

        List<String> ps = new ArrayList<>();
        for (int i = 0 ; i < 9;i++) {
            ps.add(i+"");
        }
        ForkJoinPool pool = new ForkJoinPool(4);
        JsonParseTask jsonParseTask = new JsonParseTask(0,ps.size(),ps);
        Future<List<String>> result = pool.submit(jsonParseTask);
        try {
            Log.e(TAG, "testAllTableName2: "+result.get().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class JsonParseTask extends RecursiveTask<List<String>> {

        private List<String> targers;
        private int start;
        private int end;
        private int THRESHOLD = 10;

        public JsonParseTask(int start,int end,List<String> targers){
            Log.e(TAG, "JsonParseTask: "+start+" "+end+" "+targers+" "+Thread.currentThread().getName());
            this.targers = targers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected List<String> compute() {
            if (end - start < THRESHOLD){
                List<String> contactEntyties = new ArrayList<>();
                for (int i  = start; i < end; i++){
                    contactEntyties.add(targers.get(i)+" a");
                }
                return contactEntyties;
            }else {
                int middle = (start + end)/2;
                JsonParseTask leftTask = new JsonParseTask(start,middle,targers);
                JsonParseTask rightTask = new JsonParseTask(middle+1,end,targers);
                leftTask.fork();
                rightTask.fork();
                List<String> leftResult = leftTask.join();
                List<String> rightResult = rightTask.join();
                List<String> result = new ArrayList<>();
                result.addAll(leftResult);
                result.addAll(rightResult);
                return result;
            }
        }
    }

}
