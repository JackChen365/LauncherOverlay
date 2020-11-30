package com.cz.launcher.overlay.sample.tab.user;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.cz.launcher.overlay.library.component.OverlayComponent;
import com.cz.launcher.overlay.sample.R;

/**
 * @author Created by cz
 * @date 11/20/20 2:09 PM
 * @email bingo110@126.com
 */
public class UserOverlay extends OverlayComponent {

    public UserOverlay(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onPreCreated(WindowManager.LayoutParams layoutParams) {
        super.onPreCreated(layoutParams);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float actionBarSize = getActionBarSize();
        layoutParams.height = (int) (displayMetrics.heightPixels-actionBarSize);
        layoutParams.gravity = Gravity.TOP;
        window.setAttributes(layoutParams);
    }

    private float getActionBarSize(){
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return 0;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.component_tab_user);
        View container=findViewById(R.id.container);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Click user page",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
