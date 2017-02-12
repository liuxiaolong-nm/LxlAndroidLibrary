package com.cyhz.extend.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.cyhz.extend.activity.album.ExtAlbumGroupAc;
import com.cyhz.extend.activity.camera.ExtCameraAc;
import com.lxl.support.save.memory.SupportSaveFactory;
import com.lxl.support.util.SupportSDCardUtil;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by MyPC on 2016/3/16.
 *
 * 此类是统一管理用户获取手机图片的Helper，拍照或相册，Helper需要调用者，提供获取图片的相关参数（输出路径和图片总数），
 * 当用户操作完成之后，Helper会把用户选择或拍摄的图片通过observe的方式回传给调用者（list<path>）。
 */
public class ExtPhonePicHelper {

    public final static int TYPE_CAMERA = 111;

    public final static int TYPE_ALBUM = 112;

    /**
     * 通过相机获取图片（调用之后会跳转到自定义相机界面）。
     * @param context 上下文。
     * @param callBack 图片回调。
     */
    public static void camera(Context context,PhonePicCallBack callBack){
        if (callBack.needFactor().get(PhonePicCallBack.FACTOR_CAMERA_OUTPUT_PATH) == null){
            callBack.needFactor().put(PhonePicCallBack.FACTOR_CAMERA_OUTPUT_PATH,cameraDefaultOutputPath(context));
        }
        SupportSaveFactory.createMemorySave().supportSet(TYPE_CAMERA+"",callBack);
        context.startActivity(new Intent(context, ExtCameraAc.class));
    }

    public static PhonePicCallBack fetchCameraCallBack(){
        PhonePicCallBack callBack = SupportSaveFactory.createMemorySave().supportGet(TYPE_CAMERA+"");
        if (callBack == null){
            throw new RuntimeException("调用相机缺乏必要的参数！");
        }
        return callBack;
    }

    /**
     * 通过相册获取图片（调用之后会跳转到自定义相机界面）。
     * @param context 上下文。
     * @param callBack 图片回调。
     */
    public static void album(Context context,PhonePicCallBack callBack){
        if (callBack.needFactor().get(PhonePicCallBack.FACTOR_ALBUM_COUNT) == null){
            callBack.needFactor().put(PhonePicCallBack.FACTOR_ALBUM_COUNT,10);
        }
        SupportSaveFactory.createMemorySave().supportSet(TYPE_ALBUM+"",callBack);
        context.startActivity(new Intent(context, ExtAlbumGroupAc.class));
    }

    public static PhonePicCallBack fetchAlbumCallBack(){
        PhonePicCallBack callBack = SupportSaveFactory.createMemorySave().supportGet(TYPE_ALBUM+"");
        if (callBack == null){
            throw new RuntimeException("调用相册缺乏必要的参数！");
        }
        return callBack;
    }

    /**
     * 调用之后会弹出包括相机和相册的选择弹出框。
     * @param context 上下文。
     * @param callBack 图片回调。
     */
    public static void both(final Context context, final PhonePicCallBack callBack){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 5);
        builder.setTitle("选择图片");
        builder.setItems(new String[] { "选取相册图片", "现在拍摄" }, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        album(context,callBack);
                        break;
                    case 1:{
                        camera(context,callBack);
                    }
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 获取相机默认图片地址。
     * @param context
     * @return
     */
    public static String cameraDefaultOutputPath(Context context){
        String defaultPath = SupportSDCardUtil.getDadaFilesDir(context);
        defaultPath = String.format(defaultPath+"%s.png", File.separator+ UUID.randomUUID());
        return defaultPath;
    }

    public interface PhonePicCallBack{
        /**
         * 相机界面图片的输出路径。
         */
        public static final String FACTOR_CAMERA_OUTPUT_PATH = "outputpath";
        /**
         * 相册界面最多可选图片的上限。
         */
        public static final String FACTOR_ALBUM_COUNT = "count";

        /**
         * 需要传递给Helper的控制参数。
         * @return
         */
        Map needFactor();

        /**
         * Helper回传给调用者用户选择或拍摄的图片地址的集合。
         * @param type 图片来源类型 TYPE_CAMERA or TYPE_ALBUM。
         * @param pics 返回的图片集合。
         */
        void picCallBack(int type, List<String> pics);
    }
}
