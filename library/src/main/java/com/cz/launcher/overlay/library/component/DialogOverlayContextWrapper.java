package com.cz.launcher.overlay.library.component;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;

import com.cz.launcher.overlay.library.R;

public class DialogOverlayContextWrapper extends ContextThemeWrapper implements Callback {

    public WindowManager windowManager;
    public View decorView;
    public final Window window;

    public DialogOverlayContextWrapper(Context context, int theme) {
        super(context, theme);
        Dialog dialog = new Dialog(context, R.style.WindowTheme);
        this.window = dialog.getWindow();
        if (window == null) {
            throw new IllegalStateException("new Dialog has no window?");
        }
        this.window.setCallback(this);
        if (VERSION.SDK_INT >= 21) {
            window.addFlags(LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//Integer.MIN_VALUE);
        } else {
            window.addFlags(LayoutParams.FLAG_TRANSLUCENT_NAVIGATION | LayoutParams.FLAG_TRANSLUCENT_STATUS);//201326592);
        }
    }

    public void onBackPressed() {
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != KeyEvent.KEYCODE_BACK
                || keyEvent.getAction() != KeyEvent.ACTION_UP
                || keyEvent.isCanceled()) {
            return window.superDispatchKeyEvent(keyEvent);
        }
        onBackPressed();
        return true;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return window.superDispatchKeyShortcutEvent(keyEvent);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return window.superDispatchTouchEvent(motionEvent);
    }

    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return window.superDispatchTrackballEvent(motionEvent);
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return window.superDispatchGenericMotionEvent(motionEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    public View onCreatePanelView(int i) {
        return null;
    }

    public boolean onCreatePanelMenu(int i, Menu menu) {
        return false;
    }

    public boolean onPreparePanel(int i, View view, Menu menu) {
        return true;
    }

    public boolean onMenuOpened(int i, Menu menu) {
        return true;
    }

    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        return false;
    }

    public void onWindowAttributesChanged(LayoutParams layoutParams) {
        if (this.decorView != null) {
            this.windowManager.updateViewLayout(this.decorView, layoutParams);
        }
    }

    public void onContentChanged() {
    }

    public void onWindowFocusChanged(boolean z) {
    }

    public void onAttachedToWindow() {
    }

    public void onDetachedFromWindow() {
    }

    public void onPanelClosed(int i, Menu menu) {
    }

    public boolean onSearchRequested() {
        return false;
    }

    public boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        return null;
    }

    public void onActionModeStarted(ActionMode actionMode) {
    }

    public void onActionModeFinished(ActionMode actionMode) {
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void startActivity(Intent intent, Bundle bundle) {
        super.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), bundle);
    }
}
