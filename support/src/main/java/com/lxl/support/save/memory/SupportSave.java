package com.lxl.support.save.memory;

/**
 * Created by liuxiaolong on 16/12/26.
 */
public interface SupportSave {

     <T> void supportSet(String key,T t);

     <T> T supportGet(String key);

     void supportDelete(String key);

     boolean supportQuery(String key);
}
