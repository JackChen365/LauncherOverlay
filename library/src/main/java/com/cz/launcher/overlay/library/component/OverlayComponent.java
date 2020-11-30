package com.cz.launcher.overlay.library.component;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlayCallback;

public class OverlayComponent extends DialogOverlayContextWrapper {
    private static final String TAG="OverlayComponent";
    public int windowShift;

    public OverlayComponent(Context context, int theme) {
        super(context, theme);
    }

    @CallSuper
    public void onCreate(Bundle bundle) {
    }

    public boolean isAttachToWindow() {
        return null!=decorView;
    }

    @CallSuper
    public void onPreCreated(WindowManager.LayoutParams layoutParams) {
        window.setWindowManager(null, layoutParams.token,
                new ComponentName(this, getBaseContext().getClass()).flattenToShortString(), true);
        windowManager = window.getWindowManager();
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        windowShift = -Math.max(point.x, point.y);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        layoutParams.gravity = Gravity.LEFT;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.type = Build.VERSION.SDK_INT >= 25
                ? WindowManager.LayoutParams.TYPE_DRAWN_APPLICATION
                : WindowManager.LayoutParams.TYPE_APPLICATION;
        layoutParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;//16
        window.setAttributes(layoutParams);
        window.clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
    }

    protected void setContentView(@LayoutRes int layoutId) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setContentView(layoutId);
        decorView = window.getDecorView();
        decorView.setFitsSystemWindows(true);
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
//                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        windowManager.addView(decorView, window.getAttributes());
    }

    public <T extends View> T findViewById(@IdRes int id){
        return decorView.findViewById(id);
    }

    @CallSuper
    public void onStart() {
        Log.i(TAG,"OverlayComponent. onStart");
    }

    @CallSuper
    public void onPause() {
        Log.i(TAG,"OverlayComponent. onPause");
    }

    @CallSuper
    public void onResume() {
        Log.i(TAG,"OverlayComponent. onResume");
    }

    @CallSuper
    public void onStop() {
        Log.i(TAG,"OverlayComponent. onStop");
    }

    @CallSuper
    public void onDestroy() {
        Log.i(TAG,"OverlayComponent. onDestroy");
        try {
            windowManager.removeView(decorView);
        } catch (Throwable t) {
            Log.w(TAG,"OverlayComponent removeView ." + t);
        }
        decorView = null;
    }

    public final void setTouchable(boolean touchable) {
        final int flag = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        if (touchable) {
            window.clearFlags(flag);
        } else {
            window.addFlags(flag);
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void show() {
        decorView.setVisibility(View.VISIBLE);
        setTouchable(true);
    }

    public void hide(){
        decorView.setVisibility(View.GONE);
        setTouchable(false);
    }

    public boolean isVisible(){
        return null!=decorView&&View.VISIBLE == decorView.getVisibility();
    }
}
