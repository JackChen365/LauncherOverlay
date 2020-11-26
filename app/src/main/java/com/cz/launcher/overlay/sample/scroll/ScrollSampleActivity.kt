package com.cz.launcher.overlay.sample.scroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.cz.launcher.overlay.sample.R
import kotlinx.android.synthetic.main.activity_scroll_sample.*

class ScrollSampleActivity : AppCompatActivity() {
    private var launcherClient: ScrollServiceClient? = null
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
                    launcherClient?.scroll(positionOffset)
                }
            }

            override fun onPageSelected(position: Int) {}

            override fun onPageScrollStateChanged(state: Int) {
                if(ViewPager.SCROLL_STATE_IDLE == state){
                        // Stop scroll and set current item to 1 as this is required for this demo
                        launcherClient?.stopScroll()
                } else if(ViewPager.SCROLL_STATE_DRAGGING == state){
                    // Start scroll on both user drag and settling even to support usage of
                    // viewPager.setCurrentItem(0, true). Setting current item without animating
                    // is unsupported in this demo.
                    launcherClient?.startScroll()
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
        super.onDestroy()
        launcherClient?.onDestroy()
    }
}