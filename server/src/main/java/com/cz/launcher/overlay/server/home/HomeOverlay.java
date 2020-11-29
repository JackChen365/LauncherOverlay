package com.cz.launcher.overlay.server.home;

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
import com.cz.launcher.overlay.server.R;

/**
 * @author Created by cz
 * @date 11/20/20 2:09 PM
 * @email bingo110@126.com
 */
public class HomeOverlay extends OverlayComponent {

    public HomeOverlay(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onPreCreated(WindowManager.LayoutParams layoutParams) {
        super.onPreCreated(layoutParams);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300f,displayMetrics);
        layoutParams.gravity = Gravity.TOP;
        window.setAttributes(layoutParams);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.component_home);
        TextView textView=findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
