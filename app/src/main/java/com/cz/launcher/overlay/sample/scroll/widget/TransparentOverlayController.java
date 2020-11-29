package com.cz.launcher.overlay.sample.scroll.widget;

import android.os.Build;
import android.view.WindowManager.LayoutParams;

public final class TransparentOverlayController implements DragListener {
    private final ScrollOverlayComponent scrollOverlayComponent;

    public TransparentOverlayController(ScrollOverlayComponent ui) {
        scrollOverlayComponent = ui;
    }

    public final void drag() {
    }

    public final void dragTouchable() {
    }

    public final void close(boolean z) {
    }

    public final void open() {
        scrollOverlayComponent.setTouchable(true);
        LayoutParams attributes = scrollOverlayComponent.window.getAttributes();
        if (Build.VERSION.SDK_INT >= 26) {
            float f = attributes.alpha;
            attributes.alpha = 1.0f;
            if (f != attributes.alpha) {
                scrollOverlayComponent.window.setAttributes(attributes);
            }
        } else {
            attributes.x = 0;
            attributes.flags &= ~LayoutParams.FLAG_LAYOUT_NO_LIMITS;//-513;
            scrollOverlayComponent.unZ = true;
            scrollOverlayComponent.window.setAttributes(attributes);
        }
        scrollOverlayComponent.setState(PanelState.OPEN_AS_LAYER);//Todo: PanelState.uoh was default
    }

    public final void close() {
        LayoutParams attributes = scrollOverlayComponent.window.getAttributes();
        if (Build.VERSION.SDK_INT >= 26) {
            float f = attributes.alpha;
            attributes.alpha = 0.0f;
            if (f != attributes.alpha) {
                scrollOverlayComponent.window.setAttributes(attributes);
            }
        } else {
            attributes.x = scrollOverlayComponent.windowShift;
            attributes.flags |= LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            scrollOverlayComponent.unZ = false;
            scrollOverlayComponent.window.setAttributes(attributes);
        }
        scrollOverlayComponent.setTouchable(false);
        if (scrollOverlayComponent.panelState != PanelState.CLOSED) {
            scrollOverlayComponent.panelState = PanelState.CLOSED;
            scrollOverlayComponent.setState(scrollOverlayComponent.panelState);
        }
        SlidingPanelLayout slidingPanelLayout = scrollOverlayComponent.getSlidingPanelLayout();
        slidingPanelLayout.dragListener = scrollOverlayComponent.overlayControllerStateChanger;
    }

    public final void overlayScrollChanged(float f) {
    }

    public final boolean cnI() {
        return true;
    }
}
