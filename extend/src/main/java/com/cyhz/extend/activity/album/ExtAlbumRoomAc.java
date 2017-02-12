package com.cyhz.extend.activity.album;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyhz.extend.R;
import com.cyhz.extend.activity.ExtPhonePicHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuxiaolong on 16/3/11.
 */
public class ExtAlbumRoomAc extends Activity implements AdapterView.OnItemClickListener{

    public final static String ALBUNROOMTAG = "AlbumRoomActivity";
    public final static String CURRENTCONUT = "currentcount";
    public final static String IMGSELECTACTION = "imgselectaction";
    public final static String SELECTIMGKEY = "imgselectkey";

    GridView mGridView;
    List<ExtAlbumModel> models;
    GridViewAdapter gridViewAdapter;
    private int mCurrentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ext_systemphotos_ac_photoroom);
        mCurrentCount = getIntent().getIntExtra(CURRENTCONUT,0);
        ((TextView)findViewById(R.id.sys_theme_title)).setText("相册");
        setRightText(0 + "/" + mCurrentCount);
        findViewById(R.id.sys_theme_left).setVisibility(View.VISIBLE);
        findViewById(R.id.sys_theme_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExtAlbumRoomAc.this.finish();
            }
        });
        mGridView = (GridView)findViewById(R.id.ac_imgs);
        List<String> original =  getIntent().getStringArrayListExtra(ALBUNROOMTAG);
        models = ExtAlbumModel.stringsTo(original);
        gridViewAdapter = new GridViewAdapter(ExtAlbumRoomAc.this,R.layout.ext_systemphotos_ac_photoroom,models);
        mGridView.setAdapter(gridViewAdapter);
        mGridView.setOnItemClickListener(this);
        findViewById(R.id.ac_btn_perfrom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> selectImg = new ArrayList();
                for (Integer selectIndex : gridViewAdapter.getSelect()){
                    String value = models.get(selectIndex).toString();
                    selectImg.add(value);
                }
                Intent intent = new Intent();
                intent.setAction(IMGSELECTACTION);
                intent.putStringArrayListExtra(SELECTIMGKEY,selectImg);
                ExtPhonePicHelper.fetchAlbumCallBack().picCallBack(ExtPhonePicHelper.TYPE_ALBUM,selectImg);
                sendBroadcast(intent);
                finish();
                ExtSimpleImgLoad.get().dump();
            }
        });
    }

    /**
     * 显示选中数量。
     * @param count
     */
    public void setRightText(String count){
        ((TextView)findViewById(R.id.sys_theme_right)).setText(count);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path = models.get(position).getPath();
        Intent intent = new Intent(this,ExtDisplayBigPicAc.class);
        intent.putExtra("path",path);
        startActivity(intent);
    }

    class GridViewAdapter extends ArrayAdapter<ExtAlbumModel> {
        private List<Integer> select = new ArrayList<>();
        public GridViewAdapter(Context context, int resource, List<ExtAlbumModel> objects) {
            super(context, resource, objects);
        }

        /**
         * 选择item。
         * @param index
         */
        public void select(int index){
            if (select.contains(Integer.valueOf(index))){
                select.remove(Integer.valueOf(index));
            }else {
                if(mCurrentCount > select.size()){
                    select.add(Integer.valueOf(index));
                }
            }
        }

        public List<Integer> getSelect(){
            return select;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.ext_systemphotos_item_room,parent,false);
            }
            final ImageView img = (ImageView)convertView.findViewById(R.id.item_img);
            ExtSimpleImgLoad.get().loadBitmap(getItem(position).getPath(), img);
            final View indicator = convertView.findViewById(R.id.item_select_indicator);
            indicatorControl(indicator,position);
            convertView.findViewById(R.id.item_select_indicator2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    indicator.performClick();
                }
            });
            return convertView;
        }

        /**
         * item 点击显示处理。
         * @param img
         * @param index
         */
        private void indicatorControl(final View img,final int index){
            if (select.contains(Integer.valueOf(index))){
                img.setBackgroundResource(R.drawable.ext_systemphotos_yixuan);
            }else {
                img.findViewById(R.id.item_select_indicator).setBackgroundResource(R.drawable.ext_systemphotos_weixuan);
            }
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select(index);
                    indicatorControl(img, index);
                    setRightText(select.size() + "/" + mCurrentCount);
                }
            });

        }
    }

}
