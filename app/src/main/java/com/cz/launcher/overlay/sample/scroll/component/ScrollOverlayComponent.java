package com.cz.launcher.overlay.sample.scroll.component;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cz.launcher.overlay.library.component.OverlayComponent;
import com.cz.launcher.overlay.sample.R;

import java.util.ArrayList;

public class ScrollOverlayComponent extends OverlayComponent {
    RecyclerView recyclerView;
    private ExampleAdapter adapter;
    private ArrayList<ExampleItem> exampleList;
    public ScrollOverlayComponent(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onPreCreated(WindowManager.LayoutParams layoutParams) {
        super.onPreCreated(layoutParams);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200f,displayMetrics);
        window.setAttributes(layoutParams);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.component_search_layout);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        decorView.setTranslationX(-displayMetrics.widthPixels);

        recyclerView = findViewById(R.id.recycler_view);
        fillExampleList();
        setUpRecyclerView();
    }

    private void fillExampleList() {
        exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.ic_android, "One", "Ten"));
        exampleList.add(new ExampleItem(R.drawable.ic_audio, "Two", "Eleven"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun, "Three", "Twelve"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Four", "Thirteen"));
        exampleList.add(new ExampleItem(R.drawable.ic_audio, "Five", "Fourteen"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun, "Six", "Fifteen"));
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Seven", "Sixteen"));
        exampleList.add(new ExampleItem(R.drawable.ic_audio, "Eight", "Seventeen"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun, "Nine", "Eighteen"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun, "Ten", "Nineteen"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun, "Eleven", "Twenty"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun, "Twelve", "Twenty-One"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun, "Thirteen", "Twenty-Two"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun, "Fourteen", "Twenty-Three"));
        exampleList.add(new ExampleItem(R.drawable.ic_sun, "Fifteen", "Twenty-Four"));
    }


    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(this, exampleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void onStartScroll(){
    }

    public void onEndScroll(){
    }

    public void onScroll(float progress){
        View decorView = window.getDecorView();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float translationX = (progress * displayMetrics.widthPixels) - displayMetrics.widthPixels;
        decorView.setTranslationX(-translationX);
    }

}
