package com.cyhz.extend.activity.album;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cyhz.extend.R;
import com.cyhz.extend.activity.ExtPhonePicHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ExtAlbumGroupAc extends Activity implements Runnable,AdapterView.OnItemClickListener{

    List<ExtAlbumModel> mAlbums;
    Map<String,List<ExtAlbumModel>> mGroups;
    ListView mGroupsListView;
    private int mPicCount=10;
    Handler mMainHandle = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case 1:
                    showUi();
                    break;
            }
        }
    };

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ext_systemphotos_activity_main);
        mPicCount = (int)ExtPhonePicHelper.fetchAlbumCallBack().needFactor().get(ExtPhonePicHelper.PhonePicCallBack.FACTOR_ALBUM_COUNT);
        ((TextView)findViewById(R.id.sys_theme_title)).setText("照片");
        ((TextView)findViewById(R.id.sys_theme_right)).setText("取消");
        findViewById(R.id.sys_theme_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtAlbumGroupAc.this.finish();
            }
        });
        mGroupsListView = (ListView)findViewById(R.id.photogroups);
        new Thread(this).start();
        IntentFilter filter = new IntentFilter(ExtAlbumRoomAc.IMGSELECTACTION);
        this.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void run() {
        mAlbums = cursorToAlbumModels();
        mMainHandle.sendMessage(mMainHandle.obtainMessage(1));
    }

    /**
     * 显示ui。
     */
    private void showUi(){
        mGroups = fetchGroup(mAlbums);
        mGroupsListView.setAdapter(new GroupAdapter());
        mGroupsListView.setOnItemClickListener(this);
    }

    /**
     * 图片根据_display_name分组
     * @param models
     * @return
     */
    public Map<String,List<ExtAlbumModel>> fetchGroup(List<ExtAlbumModel> models){
        Map<String,List<ExtAlbumModel>> groups = new LinkedHashMap<>();
        for (ExtAlbumModel model : models){
            List<ExtAlbumModel> albumModels =groups.get(model.getParentName());
            if (albumModels == null){
                albumModels = new ArrayList<>();
                groups.put(model.getParentName(), albumModels);
            }
            albumModels.add(0,model);
        }
        return groups;
    }

    /**
     * Cursor conversion to AlbumModels.
     * @return
     */
    public List<ExtAlbumModel> cursorToAlbumModels(){
        List<ExtAlbumModel> albumModels = new ArrayList<>();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = this.getContentResolver();
        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
        if(mCursor != null){
            while (mCursor.moveToNext()) {
                ExtAlbumModel albumModel = cursorToAlbumModel(mCursor);
                albumModels.add(albumModel);
            }
        }
        return albumModels;
    }

    /**
     * Cursor conversion to AlbumModel.
     * @param cursor
     * @return
     */
    private ExtAlbumModel cursorToAlbumModel(Cursor cursor){
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
        String date = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
        String parentName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
        int width = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
        int height = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
        ExtAlbumModel albumModel = new ExtAlbumModel();
        albumModel.setTitle(title);
        albumModel.setParentName(parentName);
        albumModel.setPath(path);
        albumModel.setTime(date);
        albumModel.setWidth(width + "");
        albumModel.setHeight(height+"");
        return albumModel;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        List<ExtAlbumModel> models = (List<ExtAlbumModel>) mGroups.values().toArray()[i];
        Intent intent = new Intent(this, ExtAlbumRoomAc.class);
        intent.putStringArrayListExtra(ExtAlbumRoomAc.ALBUNROOMTAG,(ArrayList) ExtAlbumModel.toStrings(models));
        intent.putExtra(ExtAlbumRoomAc.CURRENTCONUT,mPicCount);
        startActivity(intent);
    }

    class GroupAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mGroups.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = LayoutInflater.from(ExtAlbumGroupAc.this).inflate(R.layout.ext_systemphotos_item_group,viewGroup,false);
            }
            TextView text = (TextView)view.findViewById(R.id.item_group_text);
            ImageView img = (ImageView)view.findViewById(R.id.item_group_img);
            TextView count =(TextView)view.findViewById(R.id.group_count);
            List<ExtAlbumModel> title = (List<ExtAlbumModel>) mGroups.values().toArray()[i];
            text.setText(title.get(0).getParentName());
            count.setText("（"+title.size()+"）");
            ExtSimpleImgLoad.get().loadBitmap(title.get(0).getPath(), img);
            return view;
        }
    }


}
