package com.cz.launcher.overlay.sample.scroll.widget;

import android.os.Build;
import android.util.Log;
import android.view.WindowManager.LayoutParams;

import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

public final class OverlayControllerStateChanger implements DragListener {

    private final ScrollOverlayComponent overlayController;

    public OverlayControllerStateChanger(ScrollOverlayComponent overlayControllerVar) {
        overlayController = overlayControllerVar;
    }

    public final void drag() {
        final PanelState dragging = PanelState.DRAGGING;    //Todo: PanelState.uof was default
        if (overlayController.panelState != dragging) {
            overlayController.setState(overlayController.panelState);
        }
        LayoutParams attributes = overlayController.window.getAttributes();
        if (Build.VERSION.SDK_INT >= 26) {
            if (1.0f != attributes.alpha) {
                attributes.alpha = 1.0f;
                overlayController.window.setAttributes(attributes);
            }
        } else {
            attributes.x = 0;
            attributes.flags &= ~FLAG_LAYOUT_NO_LIMITS;
            overlayController.unZ = true;
            overlayController.window.setAttributes(attributes);
        }
    }

    public final void dragTouchable() {
        PanelState panelStateVar = PanelState.DRAGGING; //Todo: PanelState.uof was default
        if (overlayController.panelState != panelStateVar) {
            overlayController.panelState = panelStateVar;
            overlayController.setState(overlayController.panelState);
        }
        overlayController.setTouchable(true);
        LayoutParams attributes = overlayController.window.getAttributes();
        if (Build.VERSION.SDK_INT >= 26) {
            if (1.0f != attributes.alpha) {
                attributes.alpha = 1.0f;
                overlayController.window.setAttributes(attributes);
            }
        } else {
            attributes.x = 0;
            attributes.flags &= ~FLAG_LAYOUT_NO_LIMITS;
            overlayController.unZ = true;
            overlayController.window.setAttributes(attributes);
        }
    }

    public final void close(boolean z) {
        ScrollOverlayComponent overlayControllerVar = this.overlayController;
        PanelState panelStateVar = PanelState.DRAGGING;//Todo: PanelState.uof was default
        if (overlayControllerVar.panelState != panelStateVar) {
            overlayControllerVar.panelState = panelStateVar;
            overlayControllerVar.setState(overlayControllerVar.panelState);
        }
        overlayController.setTouchable(false);

    }

    public final void open() {
        overlayController.setState(PanelState.OPEN_AS_DRAWER);//Todo: PanelState.uog was default
    }

    @Override
    public final void overlayScrollChanged(float f) {
        if (overlayController.callback != null && !Float.isNaN(f)) {
            try {
                overlayController.callback.overlayScrollChanged(f);
//                overlayController.afterReply(f);//empty.ignore.
            } catch (Throwable e) {
                Log.e("wo.OverlayController", "Error notfying client", e);
            }
        }
    }

    public final void close() {
        if (overlayController.window == null) return;
        LayoutParams attributes = overlayController.window.getAttributes();
        if (Build.VERSION.SDK_INT >= 26) {
            float f = attributes.alpha;
            attributes.alpha = 0.0f;
            if (f != attributes.alpha) {
                overlayController.window.setAttributes(attributes);
            }
        } else {
            attributes.x = overlayController.windowShift;
            attributes.flags |= LayoutParams.FLAG_LAYOUT_NO_LIMITS; //512;
            overlayController.unZ = false;
            overlayController.window.setAttributes(attributes);
        }
        overlayController.setState(PanelState.CLOSED);//Todo: PanelState.uoe was default
    }

    public final boolean cnI() {
        return false;//this.overlayController.Ho();
    }
}
