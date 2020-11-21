package com.cz.launcher.overlay.sample.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cz.launcher.overlay.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by cz
 * @date 11/20/20 5:40 PM
 * @email bingo110@126.com
 */
public class DisplayDialog extends Dialog {
    public DisplayDialog(@NonNull Context context) {
        super(context,R.style.FillScreenWindowTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialWindowAttributes();
        initialContentView();
        setContentView(R.layout.dialog_list);
        ListView listView=findViewById(R.id.listView);
        List<String> list=new ArrayList<>();
        list.add("Item1");
        list.add("Item2");
        list.add("Item3");
        Context context = getContext();
        listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialWindowAttributes() {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS|
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        layoutParams.gravity = Gravity.RIGHT;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.type = Build.VERSION.SDK_INT >= 25
                ? WindowManager.LayoutParams.TYPE_DRAWN_APPLICATION
                : WindowManager.LayoutParams.TYPE_APPLICATION;
        layoutParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        window.setAttributes(layoutParams);
    }

    private void initialContentView(){
        final ViewGroup contentView=findViewById(android.R.id.content);
        contentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Activity ownerActivity;
                Context context = getContext();
                if(context instanceof ContextThemeWrapper){
                    ContextThemeWrapper contextThemeWrapper = (ContextThemeWrapper) context;
                    context=contextThemeWrapper.getBaseContext();
                }
                if(context instanceof Activity){
                    ownerActivity = (Activity) context;
                } else {
                    ownerActivity = getOwnerActivity();
                }
                if(null!=ownerActivity){
                    Window window = ownerActivity.getWindow();
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    attributes.gravity = Gravity.LEFT;
                    window.setAttributes(attributes);

                    View contentView=window.findViewById(android.R.id.content);
                    DisplayMetrics displayMetrics = ownerActivity.getResources().getDisplayMetrics();
                    ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
                    layoutParams.width = displayMetrics.widthPixels - (right-left);
                    contentView.requestLayout();
                }
            }
        });
    }
}
