package com.cz.launcher.overlay.sample.tab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cz.launcher.overlay.sample.R;

public class TabSampleActivity extends AppCompatActivity {
    private static final String SERVICE_HOME_ACTION="com.cz.server.home.WINDOW_OVERLAY";
    private static final String SERVICE_NOTIFICATION_ACTION="com.cz.server.notification.WINDOW_OVERLAY";
    private static final String SERVICE_USER_ACTION="com.cz.server.user.WINDOW_OVERLAY";

    private static final String SERVICE_HOME_CLASS="com.cz.launcher.overlay.server.home.LauncherOverlayHomeService";
    private static final String SERVICE_NOTIFICATION_CLASS="com.cz.launcher.overlay.server.notification.LauncherOverlayNotificationService";
    private static final String SERVICE_USER_CLASS="com.cz.launcher.overlay.server.user.LauncherOverlayUserService";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_sample);

        TabServiceClient homeServiceClient=new TabServiceClient(this,SERVICE_HOME_ACTION,SERVICE_HOME_CLASS);
        TabServiceClient notificationServiceClient=new TabServiceClient(this,SERVICE_NOTIFICATION_ACTION,SERVICE_NOTIFICATION_CLASS);
        TabServiceClient userServiceClient=new TabServiceClient(this,SERVICE_USER_ACTION,SERVICE_USER_CLASS);

        homeServiceClient.reconnect();
    }
}