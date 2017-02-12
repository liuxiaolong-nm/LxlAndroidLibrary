package com.cyhz.extend.view.cyhzexpandlistview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * 品牌车系View
 * Created by MyPC on 2015/8/6.
 */
public class CYHZ_BrandViewLayout extends FrameLayout implements Side_Bar.OnTouchingLetterChangedListener,
        ExpandableListView.OnChildClickListener,AdapterView.OnItemClickListener,AbsListView.OnScrollListener{
    private ExpandableListView expandableListView ;
    private ListView listView;
    private Side_Bar sideBar;
    private int listViewWidth=(int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,240,
            getResources().getDisplayMetrics()));;
    private int sideWidth=(int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30,
            getResources().getDisplayMetrics()));
    private TextView textView;
    private List<String> sideDatas;
    private Context mContext;
    private OnSelectedListener selectedListener;
    private ScrollListener scrollListener;
    private boolean isOut=false;


    public CYHZ_BrandViewLayout(Context context) {
        super(context);
        initView(context);
    }

    public CYHZ_BrandViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CYHZ_BrandViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        mContext = context;
        expandableListView = new ExpandableListView(context);
        LayoutParams lp = new LayoutParams(-1,-1);
        expandableListView.setLayoutParams(lp);
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnScrollListener(this);
        listView = new ListView(context);
        listView.setBackgroundColor(Color.parseColor("#eeeeee"));
        LayoutParams listViewLp = new LayoutParams(listViewWidth,-1);
        listViewLp.gravity= Gravity.RIGHT;
        listViewLp.setMargins(listViewWidth, 0, -listViewWidth, 0);
        listView.setLayoutParams(listViewLp);
        listView.setOnItemClickListener(this);
        addView(expandableListView);
        addView(listView);
    }

    public void expand(boolean flag){
        if(flag){
            ListAdapter adapter = expandableListView.getAdapter();
            int count = adapter.getCount();
            for(int i=0;i<count;i++){
                expandableListView.expandGroup(i);
            }
        }
    }

    /**
     * 设置listView宽度
     * @param listViewWidth
     */
    public void setListViewWidth(int listViewWidth){
        this.listViewWidth = listViewWidth;
        LayoutParams listViewLp = new LayoutParams(listViewWidth,-1);
        listViewLp.setMargins(listViewWidth, 0, -listViewWidth, 0);
        listViewLp.gravity= Gravity.RIGHT;
        listView.setLayoutParams(listViewLp);
    }

    /**
     * 设置listView margin
     * @param margin
     */
    private void setListViewMargin(int margin){
        LayoutParams params = (LayoutParams)listView.getLayoutParams();
        params.gravity= Gravity.RIGHT;
        params.setMargins(margin,0,-margin,0);
        listView.setLayoutParams(params);
    }

    public void setSelectedListener(OnSelectedListener selectedListener){
        this.selectedListener = selectedListener;
    }

    public void setScrollListener(ScrollListener scrollListener){
        this.scrollListener = scrollListener;
    }


    public ExpandableListView getExpandableListView() {
        return this.expandableListView;
    }


//    listview 滑动动画出来

    public void slideOut(){
        sideBar.setVisibility(View.GONE);
        ObjectAnimator animator = ObjectAnimator.ofInt(this,"listViewMargin",listViewWidth,0);
        animator.setDuration(300);
        animator.start();
        isOut=true;
    }
// listview 滑动动画 滑动出去
    public void slideIn(){
        sideBar.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofInt(this,"listViewMargin",0,listViewWidth);
        animator.setDuration(300);
        animator.start();
        isOut=false;
    }

    public void setSideBarColor(String color){
        sideBar.setPaintColor(color);
    }

    public void setLisViewAdapter(ListAdapter adapter){
        listView.setAdapter(adapter);
    }


    public void setExpandableListViewAdapter(ExpandableListAdapter adapter){
        expandableListView.setAdapter(adapter);
    }

    public void setSideBarData(List<String> sideDatas,int textSize){
        this.sideDatas = sideDatas;
        sideBar = new Side_Bar(mContext);
        LayoutParams sideLp = new LayoutParams(sideWidth,-1);
        sideLp.gravity= Gravity.RIGHT;
        sideBar.setLayoutParams(sideLp);
        sideBar.setCharList(sideDatas);
        sideBar.setSideTextSize(textSize);
        sideBar.setOnTouchingLetterChangedListener(this);
        textView= new TextView(mContext);
        LayoutParams textLp = new LayoutParams(60,60);
        textLp.gravity=Gravity.CENTER;
        textView.setGravity(Gravity.CENTER);
        textView.setVisibility(View.GONE);
        textView.setBackgroundColor(Color.parseColor("#1081e0"));
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setLayoutParams(textLp);
        addView(sideBar);
        addView(textView);
    }

//    @Override
//    public void select(int pos,String str) {
//        expandableListView.setSelectedGroup(pos);//定位到选中的条目
//        textView.setVisibility(View.VISIBLE);
//        textView.setText(str);
////        slideOut();
//
//    }

    @Override
    public void onTouchingLetterChanged(int position, String s) {
        expandableListView.setSelectedGroup(position);//定位到选中的条目
        textView.setVisibility(View.VISIBLE);
        textView.setText(s);
    }

    @Override
    public void onComplete() {
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.GONE);
//                slideIn();
            }
        }, 1000);
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        if(!isOut){
            slideOut();
        }
        if(selectedListener!=null){
                selectedListener.onChildClick(expandableListView,view,i,i1,l);
            }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectedListener!=null){
                    selectedListener.onItemClick(adapterView,view,i,l);
                }
    }

    @Override
    public void onScrollStateChanged(AbsListView var1, int var2) {
        if (isOut) {
            slideIn();
        }
        if(scrollListener!=null){
            scrollListener.onScrollStateChanged(var1,var2);
        }
    }

    @Override
    public void onScroll(AbsListView var1, int var2, int var3, int var4) {
            if(scrollListener!=null){
                scrollListener.onScroll(var1,var2,var3,var4);
            }
    }


    public interface  OnSelectedListener{
        void onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l);
        void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
    }

    public interface ScrollListener{
        void onScrollStateChanged(AbsListView var1, int var2);
        void onScroll(AbsListView var1, int var2, int var3, int var4);
    }
}
