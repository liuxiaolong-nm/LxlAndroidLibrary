package com.cyhz.extend.view.wheelview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 7/1/14.
 */
public class WheelView extends ScrollView {
    public static final String TAG = WheelView.class.getSimpleName();
    public static final int OFF_SET_DEFAULT = 1;
    int offset = OFF_SET_DEFAULT; // 偏移量（需要在最前面和最后面补全）
    int displayItemCount; // 每页显示的数量
    int selectedIndex = 1;
    List<MenuItem> items;
    private LinearLayout views;
    int initialY;
    Runnable scrollerTask;
    int newCheck = 50; //滑动后校对textview位置的延迟。
    int itemHeight = 0; //一个textview的高度。
    private int scrollDirection = -1;
    private static final int SCROLL_DIRECTION_UP = 0;
    private static final int SCROLL_DIRECTION_DOWN = 1;
    Paint paint;
    int viewWidth; //view的宽度。
    int viewHeight;
    private OnWheelViewListener onWheelViewListener;
    private ItemHeightCallBack itemHeightCallBack;
    int[] selectedAreaBorder;

    public interface OnWheelViewListener {
        public void onSelected(int serialNumber, int selectedIndex, MenuItem item);
    }

    public interface ItemHeightCallBack {
        public void heightCallBack(int height);
    }

    public WheelView(Context context) {
        super(context);
        init(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private List<MenuItem> getItems() {
        return items;
    }

    public int getOffset() {
        return offset;
    }

    /**
     * 设置初始显示top和bottom item的数量，要在setItems之前调用。
     *
     * @param offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setItems(List<MenuItem> list) {
        if (list == null){
            list = new ArrayList<>();
        }
        if (null == items) {
            items = new ArrayList<MenuItem>();
        }
        items.clear();
        items.addAll(list);
        // 前面和后面补全
        for (int i = 0; i < offset; i++) {
            items.add(0, new MenuItem());
            items.add(new MenuItem());
        }
        initData();
    }

    public void startScrollerTask() {
        initialY = getScrollY();
        this.postDelayed(scrollerTask, newCheck);
    }

    public MenuItem getSeletedItem() {
        if (selectedIndex >= items.size()){
            return items.get(items.size() - 1);
        }

        return items.get(selectedIndex);
    }

    public int getSeletedIndex() {
        return selectedIndex - offset;
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    public void setSeletion(int position) {
        final int p = position;
        selectedIndex = p + offset;
        this.post(new Runnable() {
            @Override
            public void run() {
                WheelView.this.smoothScrollTo(0, p * itemHeight);
            }
        });
    }

    public OnWheelViewListener getOnWheelViewListener() {
        return onWheelViewListener;
    }

    public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener) {
        this.onWheelViewListener = onWheelViewListener;
    }

    public void setItemHeightCallBack(ItemHeightCallBack heightCallBack) {
        itemHeightCallBack = heightCallBack;
    }

    /**
     * 配置WheelView。
     *
     * @param datas
     * @param offset
     * @param listener
     */
    public void configParam(List<MenuItem> datas, int offset, OnWheelViewListener listener) {
        setOffset(offset);
        setItems(datas);
        setOnWheelViewListener(listener);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {

        if (viewWidth == 0) {
            viewWidth = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
        }

        if (null == paint) {
            paint = new Paint();
            //  paint.setColor(Color.parseColor("#83cde6"));
            paint.setColor(Color.parseColor("#bababa"));
            paint.setStrokeWidth(dip2px(getContext(), 1f));
        }
        final Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setStyle(Paint.Style.FILL);

        final Paint paint2 = new Paint();
        paint2.setColor(Color.parseColor("#f1f1f1"));
        paint2.setStyle(Paint.Style.FILL);
        background = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
//                canvas.drawLine(viewWidth * 1 / 6, obtainSelectedAreaBorder()[0], viewWidth * 5 / 6, obtainSelectedAreaBorder()[0], paint);
//                canvas.drawLine(viewWidth * 1 / 6, obtainSelectedAreaBorder()[1], viewWidth * 5 / 6, obtainSelectedAreaBorder()[1], paint);
//                canvas.drawLine(0, obtainSelectedAreaBorder()[0], viewWidth, obtainSelectedAreaBorder()[0], paint);
//                canvas.drawLine(0, obtainSelectedAreaBorder()[1], viewWidth, obtainSelectedAreaBorder()[1], paint);
//                +paint.getStrokeWidth()
                Rect rect = new Rect(0, (int) (obtainSelectedAreaBorder()[0]), viewWidth, (int) (obtainSelectedAreaBorder()[1]));
                canvas.drawRect(rect, paint1);
                Rect rect1 = new Rect(0, 0, viewWidth, (int) (obtainSelectedAreaBorder()[0]));
                Rect rect2 = new Rect(0, (int) (obtainSelectedAreaBorder()[1]), viewWidth, viewHeight);
                canvas.drawRect(rect1, paint2);
                canvas.drawRect(rect2, paint2);

            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };
        super.setBackgroundDrawable(background);

    }

    private void init(Context context) {
        setVerticalScrollBarEnabled(false);
        views = new LinearLayout(context);
        views.setOrientation(LinearLayout.VERTICAL);
        addView(views, -1, -1);
        setFillViewport(true);
        scrollerTask = new Runnable() {
            public void run() {
                int newY = getScrollY();
                if (initialY - newY == 0) { // stopped
                    final int remainder = initialY % itemHeight;
                    final int divided = initialY / itemHeight;
                    if (remainder == 0) {
                        selectedIndex = divided + offset;
                        onSeletedCallBack();
                    } else {
                        if (remainder > itemHeight / 2) {
                            WheelView.this.post(new Runnable() {
                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY - remainder + itemHeight);
                                    selectedIndex = divided + offset + 1;
                                    onSeletedCallBack();
                                }
                            });
                        } else {
                            WheelView.this.post(new Runnable() {
                                @Override
                                public void run() {
                                    WheelView.this.smoothScrollTo(0, initialY - remainder);
                                    selectedIndex = divided + offset;
                                    onSeletedCallBack();
                                }
                            });
                        }
                    }
                } else {
                    initialY = getScrollY();
                    WheelView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    private void initData() {
        if (items == null)
            return;
        if (viewWidth > 0 && viewHeight > 0 && getChildCount() == 1 && items.size() > 0) {
            displayItemCount = offset * 2 + 1;
            itemHeight = viewHeight / displayItemCount;
            if (itemHeightCallBack != null) {
                itemHeightCallBack.heightCallBack(itemHeight);
            }
            views.removeAllViews();
            for (MenuItem item : items) {
                views.addView(createView(item.getTitle()), -1, itemHeight);
            }
            refreshItemView(0);
        }
        //  addTopAndBottomLayout(itemHeight);
    }

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    private void addTopAndBottomLayout(int itemHeight){
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1,itemHeight);
//        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(-1,itemHeight);
//        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        ImageView top = new ImageView(getContext());
//        ImageView bottom = new ImageView(getContext());
//        ColorDrawable drawable = new ColorDrawable(Color.BLACK);
//        drawable.setAlpha(100);
//        top.setBackground(drawable);
//        bottom.setBackground(drawable);
//        root.addView(top,params);
//        root.addView(bottom,params1);
//    }

    /**
     * 创建滑动显示的textview。
     *
     * @param item
     * @return
     */
    private TextView createView(String item) {
        TextView tv = new TextView(getContext());
        tv.setSingleLine(true);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //   tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (itemHeight * 0.7));
        tv.setTextSize(16);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER);
        // ScreenAdapterUtils.adapterViews(tv, false, false);
        return tv;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemView(t);
        if (t > oldt) {
//            Logger.d(TAG, "向下滚动");
            scrollDirection = SCROLL_DIRECTION_DOWN;
        } else {
//            Logger.d(TAG, "向上滚动");
            scrollDirection = SCROLL_DIRECTION_UP;

        }

    }

    /**
     * 设置显示选中textview颜色。
     *
     * @param y
     */
    private void refreshItemView(int y) {
//        int position = y / itemHeight + offset;
//        int remainder = y % itemHeight;
//        int divided = y / itemHeight;

        int position = y == 0 ? offset : y / itemHeight + offset;
        int remainder = y == 0 ? 0 : y % itemHeight;
        int divided = y == 0 ? 0 : y / itemHeight;

        if (remainder == 0) {
            position = divided + offset;
        } else {
            if (remainder > itemHeight / 2) {
                position = divided + offset + 1;
            }
        }

        int childSize = views.getChildCount();
        for (int i = 0; i < childSize; i++) {
            TextView itemView = (TextView) views.getChildAt(i);
            if (null == itemView) {
                return;
            }
            if (position == i) {
                //    itemView.setTextColor(Color.parseColor("#0288ce"));
                itemView.setTextColor(Color.parseColor("#545454"));
                //  itemView.setBackgroundColor(Color.WHITE);
            } else {
                itemView.setTextColor(Color.parseColor("#b5b5b5"));
                //  itemView.setBackgroundColor(Color.parseColor("#f1f1f1"));
            }
            TextView q = (TextView) views.getChildAt(position - 2);
            TextView h = (TextView) views.getChildAt(position + 2);
            if (q != null) {
                q.setTextColor(Color.parseColor("#d7d7d7"));
            }
            if (h != null) {
                h.setTextColor(Color.parseColor("#d7d7d7"));
            }
        }
    }

    /**
     * 获取view中两条蓝色横线的高度。
     *
     * @return
     */
    private int[] obtainSelectedAreaBorder() {
        if (null == selectedAreaBorder) {
            selectedAreaBorder = new int[2];
            selectedAreaBorder[0] = itemHeight * offset;
            selectedAreaBorder[1] = itemHeight * (offset + 1);
        }
        return selectedAreaBorder;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != 0 && h != 0) {
            viewWidth = w;
            viewHeight = h;
            initData();
        }
        setBackgroundDrawable(null);
    }

    /**
     * 选中回调
     */
    private void onSeletedCallBack() {
        if (null != onWheelViewListener) {
            onWheelViewListener.onSelected(getId(), selectedIndex - offset, items.get(selectedIndex));
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    private int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}


