package com.cz.launcher.overlay.library.component;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.cz.launcher.overlay.library.ILauncherFixedOverlayCallback;

public class OverlayComponent extends DialogOverlayContextWrapper {
    private static final String TAG="OverlayComponent";
    public ILauncherFixedOverlayCallback callback;
    public int windowShift;

    public OverlayComponent(Context context, int theme) {
        super(context, theme);
    }

    @CallSuper
    public void onCreate(Bundle bundle) {
    }

    public void onPreCreated(WindowManager.LayoutParams layoutParams) {
        window.setWindowManager(null, layoutParams.token,
                new ComponentName(this, getBaseContext().getClass()).flattenToShortString(), true);
        windowManager = window.getWindowManager();
        windowShift = 0;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200f,displayMetrics);
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        layoutParams.gravity = Gravity.RIGHT;
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
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        windowManager.addView(this.decorView, window.getAttributes());
    }

    public <T extends View> T findViewById(@IdRes int id){
        return window.findViewById(id);
    }

    @CallSuper
    public void onStart() {
        Log.i(TAG,"OverlayController. onStart");
    }

    @CallSuper
    public void onPause() {
        Log.i(TAG,"OverlayController. onPause");
    }

    @CallSuper
    public void onResume() {
        Log.i(TAG,"OverlayController. onResume");
    }

    @CallSuper
    public void onStop() {
        Log.i(TAG,"OverlayController. onStop");
    }


    @CallSuper
    public void onDestroy() {
        Log.i(TAG,"OverlayController. onDestroy");
        try {
            windowManager.removeView(decorView);
        } catch (Throwable t) {
            Log.w(TAG,"OverlayUI removeView ." + t);
        }
        decorView = null;
    }

    @Override
    public void onBackPressed() {
    }
}
