package com.cz.launcher.overlay.sample.scroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.cz.android.sample.api.Register
import com.cz.launcher.overlay.sample.R
import kotlinx.android.synthetic.main.activity_scroll_sample.*

@Register(title = "Scroll sample")
class ScrollSampleActivity : AppCompatActivity() {
    private var launcherClient: ScrollServiceClient?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_sample)
        launcherClient = ScrollServiceClient(this)
        // Set up view pager starting at index 1
        viewPager.adapter = SimpleFragmentPagerAdapter(supportFragmentManager)
        viewPager.currentItem = 1
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 0) {
                    // Update the overlay scroll if we're at the left-most page
                    launcherClient?.scroll(1 - positionOffset)
                }
            }

            override fun onPageSelected(position: Int) {}

            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_DRAGGING -> launcherClient?.startScroll()
                    ViewPager.SCROLL_STATE_IDLE -> {
                        launcherClient?.stopScroll()
                        if(0 == viewPager.currentItem){
                            viewPager.setCurrentItem(1,false)
                        }
                    }
                }
            }
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launcherClient?.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        launcherClient?.onDetachedFromWindow()
    }

    override fun onStart() {
        super.onStart()
        launcherClient?.onStart()
    }

    override fun onStop() {
        super.onStop()
        launcherClient?.onStop()
    }

    override fun onResume() {
        super.onResume()
        launcherClient?.onResume()
    }

    override fun onPause() {
        super.onPause()
        launcherClient?.onPause()
    }

    override fun onDestroy() {
        launcherClient?.disconnect()
        super.onDestroy()
    }
}