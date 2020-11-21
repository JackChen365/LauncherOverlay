package com.cz.launcher.overlay.sample.fixed;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cz.android.sample.api.Register;
import com.cz.launcher.overlay.sample.R;

@Register(title="NestedWindow")
public class NestedWindowSampleActivity extends Activity {
    private static final String TAG="NestedWindowSampleActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_window_sample);
        FixedServiceClient serviceClient = new FixedServiceClient(this);
        serviceClient.reconnect();
        View testButton=findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Click!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}