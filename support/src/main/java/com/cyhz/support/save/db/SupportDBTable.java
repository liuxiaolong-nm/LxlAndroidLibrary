package com.cyhz.support.save.db;

/**
 * Created by liuxiaolong on 16/9/21.
 *
 */
public interface SupportDBTable {

    public String supportPrimaryKey();

    public SupportDBTable supportSave();

    public SupportDBTable supportRemove();

    public void supportUpdate();

}
