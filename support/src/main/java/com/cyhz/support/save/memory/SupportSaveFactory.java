package com.cyhz.support.save.memory;

/**
 * Created by liuxiaolong on 17/1/5.
 */
public class SupportSaveFactory {

    /**
     * 获取内存存储容器。
     *
     * liuxiaolong 201701051204
     * @return
     */
    public static SupportSave createMemorySave(){
        return SupportMemorySave.instance();
    }

}
