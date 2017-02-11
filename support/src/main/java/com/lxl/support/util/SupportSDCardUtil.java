package com.lxl.support.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 *
 * SDCard工具类
 * Created by qinghua on 2015/6/16.
 */
public class SupportSDCardUtil {


    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }


    public static final int UNIT_BYTE = 0;
    public static final int UNIT_KB = 1;
    public static final int UNIT_MB = 2;
    public static final int UNIT_GB = 3;

    /**
     * 获得sd卡剩余容量，即可用大小
     * @param unit UNIT_BYTE / UNIT_KB / UNIT_MB / UNIT_GB
     * @return
     */
    public static float getSDAvailableSize(int unit) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        switch (unit){
            case UNIT_BYTE:
                return blockSize*availableBlocks;
            case UNIT_KB:
                return (blockSize*availableBlocks)/1024;
            case UNIT_MB:
                return (blockSize*availableBlocks)/1024 /1024;
            case UNIT_GB:
                return Math.round((blockSize*availableBlocks*100)/(1024*1024*1024))/100.0f;
        }
        return -1;
    }

}
