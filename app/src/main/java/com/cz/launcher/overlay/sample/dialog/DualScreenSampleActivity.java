package com.cz.launcher.overlay.sample.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cz.launcher.overlay.sample.R;

public class DualScreenSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_screen_sample);

        View loadButton=findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                DisplayDialog displayDialog = new DisplayDialog(DualScreenSampleActivity.this);
                displayDialog.setOwnerActivity(DualScreenSampleActivity.this);
                displayDialog.show();
            }
        });
        View clickButton=findViewById(R.id.clickButton);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Click!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}