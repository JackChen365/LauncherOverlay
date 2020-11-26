package com.cz.launcher.overlay.sample.pager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cz.launcher.overlay.sample.R
import kotlinx.android.synthetic.main.activity_pager_sample.*

class PagerSampleActivity : AppCompatActivity() {
    companion object{
        private const val TAG="PagerSampleActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_sample)

        val fragmentList1= mutableListOf<Fragment>()
        fragmentList1.add(PagerTextFragment1.newInstance(0))
        val adapter1=PagerWrapperAdapter(supportFragmentManager,fragmentList1,1)
        viewPager1.adapter=adapter1
        viewPager1.currentItem = 1
        viewPager1.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
            }
        })

        val fragmentList2= mutableListOf<Fragment>()
        for(i in 0 until 3){
            fragmentList2.add(PagerTextFragment2.newInstance(i+1))
        }
        val adapter2=PagerWrapperAdapter(supportFragmentManager,fragmentList2,0)
//        viewPager2.adapter=adapter2
//        viewPager2.currentItem = 1
//
//        viewPager2.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
//            private var lastPositionOffsetPixels = 0
//            override fun onPageScrollStateChanged(state: Int) {
//                if(ViewPager.SCROLL_STATE_IDLE == state){
//                    if(viewPager1.isFakeDragging){
//                        viewPager1.endFakeDrag()
//                    }
//                } else if(ViewPager.SCROLL_STATE_DRAGGING==state){
//                    if(!viewPager1.isFakeDragging){
//                        viewPager1.beginFakeDrag()
//                        lastPositionOffsetPixels = if(0 == viewPager2.currentItem) 0 else viewPager1.width
//                    }
//                } else if(ViewPager.SCROLL_STATE_SETTLING == state){
//
//                }
//            }
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                if(0 == position){
//                    viewPager1.fakeDragBy((lastPositionOffsetPixels - positionOffsetPixels).toFloat())
//                    Log.i(TAG,"positionOffsetPixels:$positionOffsetPixels offset:${lastPositionOffsetPixels-positionOffsetPixels}")
//                    lastPositionOffsetPixels = positionOffsetPixels
//                }
//            }
//            override fun onPageSelected(position: Int) {
//            }
//        })
    }
}