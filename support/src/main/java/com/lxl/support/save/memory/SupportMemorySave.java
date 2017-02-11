package com.lxl.support.save.memory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxiaolong on 16/12/26.
 */
 class SupportMemorySave implements SupportSave {
    private Map<String,Object> mMemorySave = new HashMap<>();
    private static SupportMemorySave mSupportMemorySave = new SupportMemorySave();

    private SupportMemorySave(){

    }

    public static SupportSave instance(){
        return mSupportMemorySave;
    }

    @Override
    public <T> void supportSet(String key, T t) {
        mMemorySave.put(key,t);
    }

    @Override
    public <T> T supportGet(String key) {
        return (T)mMemorySave.get(key);
    }

    @Override
    public void supportDelete(String key) {
        mMemorySave.remove(key);
    }

    @Override
    public boolean supportQuery(String key) {
        return mMemorySave.containsKey(key);
    }
}
