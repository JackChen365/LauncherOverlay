package com.cz.launcher.overlay.sample.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cz.launcher.overlay.sample.R;

import java.util.Locale;

public class SearchSampleActivity extends AppCompatActivity {
    private static final String TAG="SearchSampleActivity";
//    private LauncherSearchClient launcherClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sample);
        final ViewPager viewPager = findViewById(R.id.viewPager);
//        log("lifecycle create int == " + Lifecycle.Event.ON_CREATE.ordinal()); // 0
        viewPager.setAdapter(new Adapter());
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG,String.format(Locale.CHINA, "onPageScrolled.Pos=%d, offset=%f, offPx=%d ",
                        position, positionOffset, positionOffsetPixels));
                if (position == 0) {
//                    launcherClient.onScrolled(1 - positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(TAG,"onPageScrollStateChanged  " + state);
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
//                        launcherClient.endMove();
                        viewPager.setCurrentItem(1);
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
//                        launcherClient.startMove();
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                }
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        launcherClient.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
//        launcherClient.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        launcherClient.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        launcherClient.onResume();
    }

    @Override
    protected void onDestroy() {
//        launcherClient.onDestroy();
        super.onDestroy();
    }

    class Adapter extends PagerAdapter implements View.OnClickListener {

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TextView view = new TextView(container.getContext());
            if (Build.VERSION.SDK_INT >= 23) {
                view.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
            }
            view.setBackgroundColor(position % 2 == 0 ? Color.GRAY : Color.YELLOW);
            view.setText("page : " + (1 + position));
            view.setGravity(Gravity.CENTER);
            container.addView(view, -1, -1);
            view.setOnClickListener(this);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (object instanceof View) {
                container.removeView((View) object);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void onClick(View v) {
//            launcherClient.openOverlay(false);
        }
    }
}