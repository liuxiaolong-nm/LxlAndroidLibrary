package com.cyhz.extend.view.picclip;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyhz.extend.R;
import com.cyhz.extend.view.photoview.PhotoViewAttacher;

public class ExtPicClipAc extends Activity{

    ExtPicClipBean mModel;
    ExtClipCallBack mCallBack;
    ImageView mImgContent,mImgClip;
    boolean isFrist;
    PhotoViewAttacher attacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ext_layout_picclip);
        mModel = (ExtPicClipBean) ExtMessageRouter.invokeRouter().fetch(ExtPicClipperImp.TAG_BEAN);
        mCallBack = (ExtClipCallBack) ExtMessageRouter.invokeRouter().fetch(ExtPicClipperImp.TAG_CALLBACK);
        mImgContent = (ImageView)findViewById(R.id.imgcontent);
        mImgClip = (ImageView)findViewById(R.id.imgclip);
        attacher = new PhotoViewAttacher(mImgContent);
        attacher.setRotate(true);
        attacher.setMaxScale(5);
        if(mModel.getClipDecorateImage() != 0){
            mImgClip.setBackgroundResource(mModel.getClipDecorateImage());
        }
        if (mModel.getImg() != null){
            mImgContent.setImageBitmap(mModel.getImg());
        }
        if (mModel.getClipWidth() != 0 && mModel.getClipHeight() != 0){
            ViewGroup.LayoutParams params = mImgClip.getLayoutParams();
            params.width = (int) mModel.getClipWidth();
            params.height = (int) mModel.getClipHeight();
            mImgClip.setLayoutParams(params);
        }
        findViewById(R.id.top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtPicClipAc.this.finish();
            }
        });
        findViewById(R.id.bottom_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//旋转图片
                attacher.setRotateAngle(90);
            }
        });
        findViewById(R.id.bottom_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//确定
                Bitmap result = saveViewBitmap(mImgContent);
                float left = (float) mImgClip.getLeft()/mImgContent.getWidth() * result.getWidth();
                float top = (float) mImgClip.getTop()/mImgContent.getHeight() * result.getHeight();
                float width = (float) mImgClip.getWidth()/mImgContent.getWidth() * result.getWidth();
                float height = (float) mImgClip.getHeight()/mImgContent.getHeight() * result.getHeight();
                Bitmap target = Bitmap.createBitmap(result,(int)left,(int)top,(int)width,(int)height);
                result.recycle();
                mCallBack.complete(target);
                findViewById(R.id.top).performClick();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isFrist){
            attacher.zoomTo(1.2f,mImgContent.getWidth()/2,mImgContent.getHeight()/2);
            int top = mImgClip.getTop();
            int left = mImgClip.getLeft();
            attacher.paddingScrollTB = top;
            attacher.paddingScrollLR = left;
            isFrist = true;
        }
    }

    private Bitmap saveViewBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = view.getDrawingCache(true);
        Bitmap bmp = duplicateBitmap(bitmap);
        if (bitmap != null && !bitmap.isRecycled()) { bitmap.recycle(); bitmap = null; }
        view.setDrawingCacheEnabled(false);
        return bmp;
    }

    private Bitmap duplicateBitmap(Bitmap bmpSrc) {
        if (null == bmpSrc)
        { return null; }
        int bmpSrcWidth = bmpSrc.getWidth();
        int bmpSrcHeight = bmpSrc.getHeight();
        Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888);
        if (null != bmpDest) {
            Canvas canvas = new Canvas(bmpDest);
            final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
            canvas.drawBitmap(bmpSrc, rect, rect, null);
        }
        return bmpDest;
    }
}
