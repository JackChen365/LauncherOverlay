package com.cz.launcher.overlay.sample.scroll.widget;

import android.util.Property;

final class SlidingPanelLayoutProperty extends Property<SlidingPanelLayout, Integer> {

    SlidingPanelLayoutProperty(Class<Integer> cls, String str) {
        super(cls, str);
    }

    public final Integer get(SlidingPanelLayout view) {
        return view.panelX;
    }

    public final void set(SlidingPanelLayout view, Integer value) {
        view.setPanelX(value);
    }
}
