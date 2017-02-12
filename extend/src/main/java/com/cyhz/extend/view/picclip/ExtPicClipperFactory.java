package com.cyhz.extend.view.picclip;

/**
 * Created by liuxiaolong on 16/12/17.
 */
public class ExtPicClipperFactory {

    public static ExtPicClipper clipper(){
        return new ExtPicClipperImp();
    }
}
