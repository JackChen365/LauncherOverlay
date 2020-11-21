package com.cz.launcher.overlay.sample.search;

import android.content.Context;
import android.os.Bundle;

import com.cz.launcher.overlay.library.component.OverlayComponent;
import com.cz.launcher.overlay.sample.R;

/**
 * @author Created by cz
 * @date 11/20/20 5:20 PM
 * @email bingo110@126.com
 */
public class SearchComponent extends OverlayComponent {

    public SearchComponent(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search);
    }
}
