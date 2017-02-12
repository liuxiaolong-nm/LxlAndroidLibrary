package com.cyhz.extend.view.photoview;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by liuxiaolong on 16/5/27.
 */
public class ExtCameraView extends SurfaceView implements SurfaceHolder.Callback{

    private Camera camera = null;
    private SurfaceHolder surfaceHolder = null;
    public ExtCameraView(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }
    public ExtCameraView(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            camera.setDisplayOrientation(90);//设置相机方向。
            Camera.Parameters parameters = camera.getParameters();
            int size1 = parameters.getSupportedPictureSizes().size();//设置相机拍出图片尺寸的集合。
            Camera.Size size = parameters.getSupportedPictureSizes().get(size1/2);//取中间的size
            parameters.setPictureSize(size.width, size.height);//设置拍照出图片的size。
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE); //自动对焦设置。
            camera.autoFocus(null);
            //设置拍照的时候摄像头在屏幕上显示的画面的大小。
            Camera.Size sizePre = getOptimalPreviewSize(parameters.getSupportedPreviewSizes(),getWidth(),getHeight());
            parameters.setPreviewSize(sizePre.width, sizePre.height);
            camera.setParameters(parameters);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //根本没有可处理的SurfaceView
        if (surfaceHolder.getSurface() == null) {
            return ;
        }
        //先停止Camera的预览
        try{
            camera.stopPreview();
        }catch(Exception e) {
            e.printStackTrace();
        }
         //这里可以做一些我们要做的变换。
         // 重新开启Camera的预览功能
         try{
             camera.setPreviewDisplay(surfaceHolder);
             camera.startPreview();
         }catch(Exception e){
             e.printStackTrace();
         }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = h;
        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
}
