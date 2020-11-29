package com.cz.launcher.overlay.server

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cz.launcher.overlay.library.fixed.ILauncherFixedOverlay
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private companion object{
        private const val TAG="MainActivity"
    }
    private var overlay: ILauncherFixedOverlay? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent= Intent("com.cz.server.home.WINDOW_OVERLAY")
            .setClassName("com.cz.launcher.overlay.server","com.cz.launcher.overlay.server.home.LauncherOverlayHomeService")
        val bindService = bindService(intent, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.i(TAG,"onServiceDisconnected")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                overlay = ILauncherFixedOverlay.Stub.asInterface(service)
            }
        }, Context.BIND_AUTO_CREATE)
        var isVisible=true
        testButton.setOnClickListener {
            if(isVisible){
                overlay?.hide()
            } else {
                overlay?.show()
            }
            isVisible=!isVisible
        }
    }
}
