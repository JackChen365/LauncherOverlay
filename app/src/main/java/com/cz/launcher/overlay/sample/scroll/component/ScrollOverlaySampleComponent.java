package com.cz.launcher.overlay.sample.scroll.component;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cz.launcher.overlay.sample.R;
import com.cz.launcher.overlay.sample.scroll.widget.ScrollOverlayComponent;

import java.util.ArrayList;

public class ScrollOverlaySampleComponent extends ScrollOverlayComponent {
    private static final String TAG="ScrollOverlayComponent";
    RecyclerView recyclerView;
    private ExampleAdapter adapter;
    private ArrayList<ExampleItem> exampleList;
    public ScrollOverlaySampleComponent(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.component_search_layout);
        TextView titleTextView=findViewById(R.id.titleTextView);
        titleTextView.setText("Process:"+ Process.myPid());
        recyclerView = findViewById(R.id.recyclerView);
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
}
