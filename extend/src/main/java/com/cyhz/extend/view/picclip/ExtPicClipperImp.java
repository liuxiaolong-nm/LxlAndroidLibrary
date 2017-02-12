package com.cyhz.extend.view.picclip;

import android.content.Intent;

/**
 * Created by liuxiaolong on 16/12/17.
 */
public class ExtPicClipperImp implements ExtPicClipper {

    public final static String TAG_BEAN = "tag_bean";

    public final static String TAG_CALLBACK = "tag_callback";

    protected ExtPicClipperImp(){

    }

    @Override
    public void picClip(ExtPicClipBean bean, ExtClipCallBack clipCallBack) {
        ExtMessageRouter.invokeRouter().register(TAG_BEAN,bean);
        ExtMessageRouter.invokeRouter().register(TAG_CALLBACK,clipCallBack);
        Intent intent = new Intent(bean.getContext(),ExtPicClipAc.class);
        bean.getContext().startActivity(intent);
    }
}
