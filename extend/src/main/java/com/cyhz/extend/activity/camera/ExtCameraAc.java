package com.cyhz.extend.activity.camera;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyhz.extend.R;
import com.cyhz.extend.activity.ExtPhonePicHelper;
import com.cyhz.extend.view.photoview.ExtCameraView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuxiaolong on 16/12/7.
 */
public class ExtCameraAc extends Activity {
    public static final String OUTPUTPATH = "outputpath";
    public static final String REQUESTCODE = "requestcode";
    private ImageView btn_camera_capture = null;
    private TextView btn_camera_cancel = null;
    private TextView btn_camera_ok = null;
    private TextView btn_cancel_camera = null;
    private Camera camera = null;
    private ExtCameraView mySurfaceView = null;
    private byte[] buffer = null;
    private final int TYPE_FILE_IMAGE = 1;
    private final int TYPE_FILE_VEDIO = 2;
    private String mOutFilePath;
    ProgressDialog progressDialog ;
    ExtPhonePicHelper.PhonePicCallBack mPhonePicCallBack;
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (data == null){
                Log.i("MyPicture", "picture taken data: null");
            }else{
                Log.i("MyPicture", "picture taken data: " + data.length);
            }
            buffer = new byte[data.length];
            buffer = data.clone();
        }
    };

    Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case 1:
                    //camera.startPreview();
                    btn_camera_capture.setVisibility(View.INVISIBLE);
                    btn_camera_ok.setVisibility(View.INVISIBLE);
                    btn_camera_cancel.setVisibility(View.INVISIBLE);
                    btn_cancel_camera.setVisibility(View.GONE);
                    List<String> resultPic = new ArrayList<>();
                    resultPic.add(mOutFilePath);
                    mPhonePicCallBack.picCallBack(ExtPhonePicHelper.TYPE_CAMERA,resultPic);
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ext_camera_layout);
        if (getIntent() != null){
            String path = getIntent().getStringExtra(OUTPUTPATH);
            if (path != null){
                mOutFilePath = path;
            }
        }
        mPhonePicCallBack = ExtPhonePicHelper.fetchCameraCallBack();
        mOutFilePath = (String) mPhonePicCallBack.needFactor().get(ExtPhonePicHelper.PhonePicCallBack.FACTOR_CAMERA_OUTPUT_PATH);
        btn_camera_capture = (ImageView) findViewById(R.id.camera_capture);
        btn_camera_ok = (TextView) findViewById(R.id.camera_ok);
        btn_camera_cancel = (TextView) findViewById(R.id.camera_cancel);
        btn_cancel_camera =(TextView)findViewById(R.id.cancel_camera);
        btn_camera_ok.setVisibility(View.GONE);
        btn_camera_cancel.setVisibility(View.GONE);
        btn_camera_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(camera!=null) {
                    camera.takePicture(null, null, pictureCallback);
                    btn_camera_capture.setVisibility(View.INVISIBLE);
                    btn_camera_ok.setVisibility(View.VISIBLE);
                    btn_camera_cancel.setVisibility(View.VISIBLE);
                    btn_cancel_camera.setVisibility(View.GONE);
                }else {
                    Toast.makeText(ExtCameraAc.this,"拍照权限未打开",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_camera_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ExtCameraAc.this);
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveImageToFile();
                        handler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
        btn_camera_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.startPreview();
                btn_camera_capture.setVisibility(View.VISIBLE);
                btn_camera_ok.setVisibility(View.INVISIBLE);
                btn_camera_cancel.setVisibility(View.INVISIBLE);
                btn_cancel_camera.setVisibility(View.VISIBLE);
            } });

        btn_cancel_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(camera!=null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO Auto-generated method stub super.onResume();
        if (camera == null){
            camera = getCameraInstance();
        }
        //必须放在onResume中，不然会出现Home键之后，再回到该APP，黑屏
        mySurfaceView = new ExtCameraView(getApplicationContext(), camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mySurfaceView);
    }

    /*
     *得到一相机对象
     * net。
     */
    private Camera getCameraInstance(){
        Camera camera = null;
        try{
            camera = Camera.open();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this,"拍照权限未打开",Toast.LENGTH_SHORT).show();
        }
        return camera;
    }
    //-----------------------保存图片---------------------------------------
    //坑坑坑。。。。，之前的代码拍摄出来的图片反向旋转的270度。
    private void saveImageToFile(){
        File file = getOutFile(TYPE_FILE_IMAGE);
        if (file == null){
            Toast.makeText(getApplicationContext(), "文件创建失败,请检查SD卡读写权限", Toast.LENGTH_SHORT).show();
            return ;
        }
        Log.i("MyPicture", "自定义相机图片路径:" + file.getPath());
        if (buffer == null){
            Log.i("MyPicture", "自定义相机Buffer: null");
        }else{
            try {
                FileOutputStream fos = new FileOutputStream(file);
                Bitmap realImage = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                ExifInterface exif=new ExifInterface(file.toString());
                Log.d("EXIF value", exif.getAttribute(ExifInterface.TAG_ORIENTATION));
                if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")){
                    realImage= rotate(realImage, 90);
                } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")){
                    realImage= rotate(realImage, 270);
                } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")){
                    realImage= rotate(realImage, 180);
                } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")){
                    realImage= rotate(realImage, 90);
                }
                boolean bo = realImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                Log.d("Info", bo + "");
                realImage.recycle();
            } catch (FileNotFoundException e) {
                Log.d("Info", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("TAG", "Error accessing file: " + e.getMessage());
            }
        }
    }

    /**
     * 旋转bitmap。
     * @param bitmap
     * @param degree
     * @return
     */
    private Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix mtx = new Matrix();
        mtx.setRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    //生成输出文件
    private File getOutFile(int fileType)
    {
        String storageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_REMOVED.equals(storageState))
        {
            Toast.makeText(getApplicationContext(), "oh,no, SD卡不存在", Toast.LENGTH_SHORT).show(); return null;
        }
        if(mOutFilePath != null){
            File picTarget = new File(mOutFilePath);
            if (nullFilter(picTarget.getParentFile())){
                return null;
            }
            return picTarget;
        }
        File mediaStorageDir = new File (Environment .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) ,"CyhzPictures");
        if (nullFilter(mediaStorageDir)){
            return null;
        }
        File file = new File(getFilePath(mediaStorageDir,fileType));
        mOutFilePath = file.getPath();
        return file;
    }

    private boolean nullFilter(File mediaStorageDir) {
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.i("MyPictures", "创建图片存储路径目录失败");
                Log.i("MyPictures", "mediaStorageDir : " + mediaStorageDir.getPath());
                return true;
            }
        }
        return false;
    }

    //生成输出文件路径
    private String getFilePath(File mediaStorageDir, int fileType){
        String timeStamp =new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = mediaStorageDir.getPath() + File.separator;
        if (fileType == TYPE_FILE_IMAGE){
            filePath += ("IMG_" + timeStamp + ".jpg");
        }else if (fileType == TYPE_FILE_VEDIO){
            filePath += ("VIDEO_" + timeStamp + ".mp4");
        } else{
            return null;
        }
        return filePath;
    }
}
