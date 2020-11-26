package com.cz.launcher.overlay.sample.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cz.android.sample.api.Exclude;
import com.cz.launcher.overlay.sample.R;

@Exclude
public class PagerTextFragment2 extends Fragment {
    public static Fragment newInstance(int index){
        Bundle argument=new Bundle();
        argument.putInt("index",index);
        Fragment fragment=new PagerTextFragment2();
        fragment.setArguments(argument);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager_text_layout2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView textView=view.findViewById(R.id.textView);
        Bundle arguments = getArguments();
        final int index = arguments.getInt("index", 0);
        textView.setText("Page:"+index);
    }
}
