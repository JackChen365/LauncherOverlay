package com.cz.launcher.overlay.sample.tab;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cz.android.sample.api.Register;
import com.cz.launcher.overlay.sample.R;

@Register(title="TabSample")
public class TabSampleActivity extends Activity {
    private static final String SERVICE_HOME_ACTION="com.cz.server.home.WINDOW_OVERLAY";
    private static final String SERVICE_NOTIFICATION_ACTION="com.cz.server.notification.WINDOW_OVERLAY";
    private static final String SERVICE_USER_ACTION="com.cz.server.user.WINDOW_OVERLAY";

    private static final String SERVICE_HOME_CLASS="com.cz.launcher.overlay.server.home.LauncherOverlayHomeService";
    private static final String SERVICE_NOTIFICATION_CLASS="com.cz.launcher.overlay.server.notification.LauncherOverlayNotificationService";
    private static final String SERVICE_USER_CLASS="com.cz.launcher.overlay.server.user.LauncherOverlayUserService";

    private boolean isVisible=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_sample);

        final TabServiceClient homeServiceClient=new TabServiceClient(this,SERVICE_HOME_ACTION,SERVICE_HOME_CLASS);
        TabServiceClient notificationServiceClient=new TabServiceClient(this,SERVICE_NOTIFICATION_ACTION,SERVICE_NOTIFICATION_CLASS);
        TabServiceClient userServiceClient=new TabServiceClient(this,SERVICE_USER_ACTION,SERVICE_USER_CLASS);

        homeServiceClient.reconnect();

        View tab1=findViewById(R.id.tab1);
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisible){
                    homeServiceClient.hide();
                } else {
                    homeServiceClient.show();
                }
                isVisible=!isVisible;
                Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_SHORT).show();
            }
        });
    }
}