package com.lxl.support.manager;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by liuxiaolong on 16/8/8.
 */
public class SupportKeyboardManager {

    public void invokeKeyboardListerner(Activity context,final KeyboardListener listener) {
        if (listener == null){
            return;
        }
        final int MIN_KEYBOARD_HEIGHT_PX = 150;
        final View decorView = context.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight = decorView.getHeight();

            @Override
            public synchronized void onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();
                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        int currentKeyboardHeight = decorView.getHeight() - windowVisibleDisplayFrame.bottom;
                        listener.onKeyboardShown(currentKeyboardHeight);
                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        listener.onKeyboardHidden();
                    }
                }
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }

    public interface KeyboardListener{
        public  void onKeyboardShown(int height);
        public void onKeyboardHidden();
    }
}
