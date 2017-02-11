package com.lxl.support.save.db;

import android.util.Log;

import com.lxl.support.save.db.command.SupportDBBatchSaveRowCommand;
import com.lxl.support.save.db.command.SupportDBCommand;
import com.lxl.support.save.db.command.SupportDBCreateTableCommand;
import com.lxl.support.save.db.command.SupportDBDeleteAllCommand;
import com.lxl.support.save.db.command.SupportDBDeleteRowCommand;
import com.lxl.support.save.db.command.SupportDBQueryAllRowCommand;
import com.lxl.support.save.db.command.SupportDBQueryAllTableCommand;
import com.lxl.support.save.db.command.SupportDBQueryCommand;
import com.lxl.support.save.db.command.SupportDBSaveRowCommand;
import com.lxl.support.save.db.command.SupportDBUpdateCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by liuxiaolong on 16/9/27.
 *
 * 需要储存的数据需要继承此类，属性会生产对应的字段，类名会生产对应的表名，基础的数据类型需要以闭包的形式声明。
 */
public class SupportDBTableEngine implements SupportDBTable {
    private static final String TAG = "SupportDBTableEngine";
    public String mainId = UUID.randomUUID().toString();

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    private static Map<Class,List<SupportDBTableChangeListener>> sChangeListener;

    public static final <T> void setChangeListener(Class<T> tClass,SupportDBTableChangeListener<T> changeListener) {
        if (tClass.isAssignableFrom(SupportDBTableEngine.class)){
            throw new RuntimeException("没有继承SupportDBTableEngine！");
        }
        if (sChangeListener == null){
            sChangeListener = new HashMap<>();
        }
        List<SupportDBTableChangeListener> change = sChangeListener.get(tClass);
        if (change == null){
            change = new ArrayList<>();
        }
        change.add(changeListener);
        sChangeListener.put(tClass,change);
    }

    public static final <T> void removeChangeListener(Class<T> tClass) {
        if (tClass.isAssignableFrom(SupportDBTableEngine.class)){
            throw new RuntimeException("没有继承SupportDBTableEngine！");
        }
        sChangeListener.remove(tClass);
    }

    @Override
    public String supportPrimaryKey() {
        return "mainId";
    }

    @Override
    public SupportDBTable supportSave() {
        int beforeSize = 0;
        List<SupportDBTableChangeListener> listeners = fetchLisener();
        if (listeners != null){
            beforeSize = SupportDBTableEngine.supportEngineQueryAll(this.getClass()).size();
        }
        SupportDBCommand createTable = new SupportDBCreateTableCommand(this);
        SupportDBCommand save = new SupportDBSaveRowCommand(this);
        createTable.execute();
        save.execute();
        if (listeners != null){
            int afterSize = SupportDBTableEngine.supportEngineQueryAll(this.getClass()).size();
            for (SupportDBTableChangeListener listener: listeners){
                if (afterSize > beforeSize){
                    listener.change(this,SupportDBTableChangeListener.INSERT,new HashMap<String, String>());
                }else {
                    listener.change(this,SupportDBTableChangeListener.UPDATE,new HashMap<String, String>());
                }
            }
        }
        return this;
    }

    @Override
    public SupportDBTable supportRemove() {
        SupportDBCommand remove = new SupportDBDeleteRowCommand(this);
        remove.execute();
        List<SupportDBTableChangeListener> listeners = fetchLisener();
        if (listeners != null){
            for (SupportDBTableChangeListener listener : listeners){
                listener.change(this,SupportDBTableChangeListener.DELETE,new HashMap<String, String>());
            }
        }
        return this;
    }

    @Override
    public void supportUpdate() {
        SupportDBCommand remove = new SupportDBUpdateCommand(this);
        remove.execute();
    }

    /**
     * 轻量级的保存数据操作，不去控制是更新还是保存。
     */
    public final void supportLiteSave(){
        SupportDBCommand createTable = new SupportDBCreateTableCommand(this);
        SupportDBCommand save = new SupportDBSaveRowCommand(this);
        createTable.execute();
        save.execute();
        Log.e(TAG, "supportLiteSave: "+Thread.currentThread().getName());
    }

    /**
     * 批量轻量级存储。
     * @param rows
     */
    public static final void supportLiteBatchSave(List<? extends SupportDBTable> rows){
        SupportDBCommand createTable = new SupportDBCreateTableCommand(rows.get(0));
        SupportDBCommand save = new SupportDBBatchSaveRowCommand(rows);
        createTable.execute();
        save.execute();
    }

    /**
     * 获取所有表名称的集合。
     * @return
     */
    public static final Set<String> supportQueryAllTableName(){
        SupportDBQueryAllTableCommand tableCommand = new SupportDBQueryAllTableCommand();
        return tableCommand.execute();
    }
    /**
     * 查询对应表的数据。
     *
     * liuxiaolong 201611131216
     * @return List<? extends SupportDBTableEngine>
     */
    public static final <T> List<T> supportEngineQuery(Class<T> target,String[] fields,String[] values){
        if (target.isAssignableFrom(SupportDBTableEngine.class)){
             throw new RuntimeException("没有继承SupportDBTableEngine！");
        }
        SupportDBCommand queryAll = new SupportDBQueryCommand(target,fields,values);
        return (List<T>)queryAll.execute();
    }

    /**
     * 查询对应表所有的数据。
     *
     * liuxiaolong 201611131216
     * @return List<? extends SupportDBTableEngine>
     */
    public static final <T> List<T> supportEngineQueryAll(Class<T> target){
        if (target.isAssignableFrom(SupportDBTableEngine.class)){
            throw new RuntimeException("没有继承SupportDBTableEngine！");
        }
        SupportDBCommand queryAll = new SupportDBQueryAllRowCommand(target);
        return (List<T>)queryAll.execute();
    }

    /**
     * 删除表数据。
     * @param target
     * @param <T>
     */
    public static final <T> void supportEngineDeleteAll(Class<T> target){
        if (target.isAssignableFrom(SupportDBTableEngine.class)){
            throw new RuntimeException("没有继承SupportDBTableEngine！");
        }
        SupportDBCommand supportDBCommand = new SupportDBDeleteAllCommand(target);
        supportDBCommand.execute();
    }

    private List<SupportDBTableChangeListener> fetchLisener(){
        if (sChangeListener == null){
            return null;
        }
        List<SupportDBTableChangeListener> listeners = sChangeListener.get(this.getClass());
        return listeners;
    }
}
