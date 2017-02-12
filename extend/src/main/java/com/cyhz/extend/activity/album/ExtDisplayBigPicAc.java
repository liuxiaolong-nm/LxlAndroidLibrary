package com.cyhz.extend.activity.album;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.cyhz.extend.R;
import com.cyhz.extend.view.photoview.EasePhotoView;

/**
 * 作者：sunjian on 16/7/7 15:21
 * 邮箱：sunjian@cyhz.com
 */
public class ExtDisplayBigPicAc extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ext_show_big_img);
        EasePhotoView photoView = (EasePhotoView)findViewById(R.id.show_img);
        showImage(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void showImage(EasePhotoView photoView){
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        int width=getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        Bitmap bitmap = ExtSimpleBitmapZip.setBit(path,width,height);
        photoView.setImageBitmap(bitmap);
    }
}
