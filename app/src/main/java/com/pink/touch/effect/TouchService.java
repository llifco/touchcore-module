package com.pink.touch.effect;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

public class TouchService extends AccessibilityService {

    private WindowManager mWindowManager;
    private View touchDotView;

    @Override
    public void onCreate() {
        super.onCreate();
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        createTouchView();
    }

    private void createTouchView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.LEFT;

        touchDotView = new View(this);
        touchDotView.setBackgroundResource(android.R.drawable.ic_menu_call);

        touchDotView.setOnTouchListener((v, event) -> {
            params.x = (int) event.getRawX();
            params.y = (int) event.getRawY();
            mWindowManager.updateViewLayout(touchDotView, params);
            return true;
        });

        try {
            mWindowManager.addView(touchDotView, params);
        } catch (Exception ignored) {}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(touchDotView != null){
            try{
                mWindowManager.removeView(touchDotView);
            }catch (Exception ignored){}
        }
    }
}
