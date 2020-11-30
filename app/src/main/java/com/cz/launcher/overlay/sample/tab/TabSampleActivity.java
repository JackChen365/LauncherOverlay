package com.cz.launcher.overlay.sample.tab;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cz.android.sample.api.Register;
import com.cz.launcher.overlay.sample.R;

@Register(title="TabSample")
public class TabSampleActivity extends Activity {
    private static final String SERVICE_HOME_ACTION="com.cz.server.home.WINDOW_OVERLAY";
    private static final String SERVICE_NOTIFICATION_ACTION="com.cz.server.notification.WINDOW_OVERLAY";
    private static final String SERVICE_USER_ACTION="com.cz.server.user.WINDOW_OVERLAY";

    private static final String SERVICE_HOME_CLASS="com.cz.launcher.overlay.sample.tab.home.LauncherOverlayHomeService";
    private static final String SERVICE_NOTIFICATION_CLASS="com.cz.launcher.overlay.sample.tab.notification.LauncherOverlayNotificationService";
    private static final String SERVICE_USER_CLASS="com.cz.launcher.overlay.sample.tab.user.LauncherOverlayUserService";

    private TabServiceClient homeServiceClient;
    private TabServiceClient notificationServiceClient;
    private TabServiceClient userServiceClient;

    private TabServiceClient selectTabServiceClient=null;
    private View selectView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_sample);
        homeServiceClient=new TabServiceClient(this,SERVICE_HOME_ACTION,SERVICE_HOME_CLASS);
        notificationServiceClient=new TabServiceClient(this,SERVICE_NOTIFICATION_ACTION,SERVICE_NOTIFICATION_CLASS);
        userServiceClient=new TabServiceClient(this,SERVICE_USER_ACTION,SERVICE_USER_CLASS);

        selectTabServiceClient = homeServiceClient;
        homeServiceClient.reconnect();

        View tab1=findViewById(R.id.tab1);
        View tab2=findViewById(R.id.tab2);
        View tab3=findViewById(R.id.tab3);
        selectView = tab1;
        tab1.setSelected(true);
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTabView(v);
                showTabMenu(homeServiceClient);
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTabView(v);
                showTabMenu(notificationServiceClient);
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTabView(v);
                showTabMenu(userServiceClient);
            }
        });
    }

    private void showTabView(View view) {
        selectView.setSelected(false);
        view.setSelected(true);
        selectView = view;
    }

    private void showTabMenu(TabServiceClient tabServiceClient) {
        selectTabServiceClient.hide();
        if(!tabServiceClient.isConnected()){
            tabServiceClient.reconnect();
        }
        tabServiceClient.show();
        selectTabServiceClient = tabServiceClient;
    }

    @Override
    protected void onDestroy() {
        homeServiceClient.disconnect();
        notificationServiceClient.disconnect();
        userServiceClient.disconnect();
        super.onDestroy();
    }
}