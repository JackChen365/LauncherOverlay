package com.cz.launcher.overlay.sample.fixed.component;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cz.launcher.overlay.library.component.OverlayComponent;
import com.cz.launcher.overlay.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by cz
 * @date 11/20/20 2:09 PM
 * @email bingo110@126.com
 */
public class ListSampleOverlay extends OverlayComponent {

    public ListSampleOverlay(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onPreCreated(WindowManager.LayoutParams layoutParams) {
        super.onPreCreated(layoutParams);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200f,displayMetrics);
        layoutParams.gravity = Gravity.RIGHT;
        window.setAttributes(layoutParams);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.component_list);

        ListView listView=findViewById(R.id.listView);
        List<String> list=new ArrayList<>();
        list.add("Item1");
        list.add("Item2");
        list.add("Item3");
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
