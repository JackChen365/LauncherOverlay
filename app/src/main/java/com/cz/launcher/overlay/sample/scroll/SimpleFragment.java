package com.cz.launcher.overlay.sample.scroll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cz.android.sample.api.Exclude;
import com.cz.launcher.overlay.sample.R;

@Exclude
public class SimpleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scroll_layout, container, false);
    }
}
