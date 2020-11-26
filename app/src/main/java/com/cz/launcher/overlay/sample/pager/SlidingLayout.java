package com.cz.launcher.overlay.sample.pager;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.OverScroller;

import androidx.annotation.Px;
import androidx.core.view.ViewCompat;

/**
 * @author Created by cz
 * @date 2020-03-12 20:21
 * @email bingo110@126.com
 * A view group that we could zoom the sub-view.
 * This view has a few feathers above:
 * 1. You could be able to zoom the View
 * 2. Add sub-view like a ViewGroup
 * 3. Support {@link View#setOnClickListener(OnClickListener)} and {@link View#setOnLongClickListener(OnLongClickListener)}
 * 4. Support background drawable.
 * It's a totally abstract zoom layout.
 *
 */
public class SlidingLayout extends ViewGroup{
    private static final String TAG="SlidingLayout";
    private final static int MIN_DISTANCE_FOR_FLING = 25; // dips\
    private final ViewFlinger viewFlinger;
    // Target of Motion events
    private boolean isBeingDragged = false;
    private boolean isScaleDragged = false;
    private int touchSlop;
    private float lastMotionX = 0f;
    private float lastMotionY = 0f;
    private int scrollX,scrollY;

    private VelocityTracker velocityTracker = null;
    private int minimumVelocity;
    private int maximumVelocity;
    private int flingDistance;

    public SlidingLayout(Context context) {
        this(context,null, 0);
    }

    public SlidingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();
        minimumVelocity = configuration.getScaledMinimumFlingVelocity();
        maximumVelocity = configuration.getScaledMaximumFlingVelocity();
        viewFlinger=new ViewFlinger(context);

        float density = getResources().getDisplayMetrics().density;
        flingDistance = (int) (MIN_DISTANCE_FOR_FLING * density);
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(child, index);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(child, index, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){
        int childCount = getChildCount();
        if(0 < childCount){
            View childView = getChildAt(0);
            childView.layout(left-childView.getMeasuredWidth(),top,left,bottom);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (super.onInterceptTouchEvent(ev)) {
            return true;
        }
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            releaseDrag();
            return false;
        }
        if (action != MotionEvent.ACTION_DOWN&&isBeingDragged) {
            return true;
        }
        if(MotionEvent.ACTION_DOWN==action) {
            viewFlinger.abortAnimation();
            lastMotionX = ev.getX();
            lastMotionY = ev.getY();
        } else if(MotionEvent.ACTION_MOVE==action){
            float x = ev.getX();
            float y = ev.getY();
            float dx = x - lastMotionX;
            float dy = y - lastMotionY;
            if (Math.abs(dx) > touchSlop||Math.abs(dy) > touchSlop) {
                isBeingDragged = true;
                ViewParent parent = getParent();
                if(null!=parent){
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
        } else if(MotionEvent.ACTION_UP==action||MotionEvent.ACTION_CANCEL==action) {
            releaseDrag();
        }
        return isBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(ev);
        int action = ev.getActionMasked();
        if(MotionEvent.ACTION_DOWN==action){
            lastMotionX = ev.getX();
            lastMotionY = ev.getY();
            viewFlinger.abortAnimation();
            invalidate();
            ViewParent parent = getParent();
            if(null!=parent){
                parent.requestDisallowInterceptTouchEvent(true);
            }
        } else if(MotionEvent.ACTION_MOVE==action){
            float x = ev.getX();
            float y = ev.getY();
            float dx = lastMotionX - x;
            float dy = lastMotionY - y;
            if (!isScaleDragged&&!isBeingDragged&&(Math.abs(dx) > touchSlop||Math.abs(dy) > touchSlop)) {
                isBeingDragged = true;
                lastMotionX = x;
                lastMotionY = y;
                ViewParent parent = getParent();
                if(null!=parent){
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
            //To avoid the scale gesture. We check the pointer count.
            int pointerCount = ev.getPointerCount();
            if (1==pointerCount&&!isScaleDragged&&isBeingDragged) {
                lastMotionX = x;
                lastMotionY = y;
                scrollBy(Math.round(dx),Math.round(dy));
                invalidate();
            }
        } else if(MotionEvent.ACTION_UP==action){
            if(!isScaleDragged&&null!=velocityTracker){
                velocityTracker.computeCurrentVelocity(1000,maximumVelocity);
                float xVelocity = velocityTracker.getXVelocity();
                float yVelocity = velocityTracker.getYVelocity();
                if(Math.abs(xVelocity)>minimumVelocity||Math.abs(yVelocity)>minimumVelocity){
                    viewFlinger.fling(-xVelocity,-yVelocity);
                }
            }
            releaseDrag();
        } else if(MotionEvent.ACTION_CANCEL==action){
            releaseDrag();
        }
        return true;
    }

    @Override
    public void scrollBy(int x, int y) {
        final boolean canScrollHorizontal = canScrollHorizontally();
        final boolean canScrollVertical = canScrollVertically();
        if (canScrollHorizontal || canScrollVertical) {
            scrollByInternal(canScrollHorizontal ? x : 0, canScrollVertical ? y : 0);
        }
    }

    protected void scrollByInternal(int dx, int dy) {
        int consumedX=0;
        if (dx != 0) {
            consumedX = scrollHorizontallyBy(dx);
        }
        int consumedY=0;
        if (dy != 0) {
            consumedY = scrollVerticallyBy(dy);
        }
    }

    protected int scrollHorizontallyBy(int dx) {
        int width = getWidth();
        int startBound = -width;
        int endBound = width;
        int delta;
        if (scrollX + dx < startBound) {
            delta = (scrollX-startBound);
        } else if (scrollX + dx > endBound) {
            delta = (endBound-scrollX);
        } else {
            delta = -dx;
        }
        scrollX += delta;
        Log.i(TAG,"scrollHorizontallyBy:"+(dx)+" scrollX:"+scrollX+" endBound:"+endBound+" delta:"+delta);
        return offsetChildrenHorizontal(delta);
    }

    private int determineTargetPage(int currentPage,float pageOffset,int velocity,int deltaX){
        int targetPage=0;
        if (Math.abs(deltaX) > flingDistance && Math.abs(velocity) > minimumVelocity) {
            targetPage = velocity > 0 ? currentPage : currentPage + 1;
        } else {
            targetPage = (int) (currentPage + pageOffset + 0.5f);
        }
        return targetPage;
    }

    protected int scrollVerticallyBy(int dy) {
        return offsetChildrenVertical(-dy);
    }

    /**
     * Query if horizontal scrolling is currently supported. The default implementation
     * returns true.
     *
     * @return True if this View can scroll the current contents horizontally
     */
    public boolean canScrollHorizontally() {
        return true;
    }

    /**
     * Query if vertical scrolling is currently supported. The default implementation
     * returns true.
     *
     * @return True if this View can scroll the current contents vertically
     */
    public boolean canScrollVertically() {
        return false;
    }


    public int offsetChildrenHorizontal(@Px int dx) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.offsetLeftAndRight(dx);
        }
        return dx;
    }

    public int offsetChildrenVertical(@Px int dy) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.offsetTopAndBottom(dy);
        }
        return dy;
    }

    public void offsetChild(View childView,@Px int dx,@Px int dy) {
        if(null!=childView){
            childView.offsetLeftAndRight(dx);
            childView.offsetTopAndBottom(dy);
        }
    }

    public void offsetChildren(@Px int dx,@Px int dy) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.offsetLeftAndRight(dx);
            childView.offsetTopAndBottom(dy);
        }
    }

    /**
     * Release the drag.
     */
    private void releaseDrag() {
        lastMotionX=0f;
        lastMotionY=0f;
        isBeingDragged = false;
        isScaleDragged = false;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    public class ViewFlinger implements Runnable{
        private final OverScroller overScroller;
        private int lastFlingX = 0;
        private int lastFlingY = 0;

        public ViewFlinger(Context context) {
            overScroller=new OverScroller(context);
        }

        @Override
        public void run() {
            if(!overScroller.isFinished()&&overScroller.computeScrollOffset()){
                int currX = overScroller.getCurrX();
                int currY = overScroller.getCurrY();
                int dx = currX - lastFlingX;
                int dy = currY - lastFlingY;
                lastFlingX = currX;
                lastFlingY = currY;
//                // We are done scrolling if scroller is finished, or for both the x and y dimension,
//                // we are done scrolling or we can't scroll further (we know we can't scroll further
//                // when we have unconsumed scroll distance).  It's possible that we don't need
//                // to also check for scroller.isFinished() at all, but no harm in doing so in case
//                // of old bugs in OverScroller.
//                boolean scrollerFinishedX = overScroller.getCurrX() == overScroller.getFinalX();
//                boolean scrollerFinishedY = overScroller.getCurrY() == overScroller.getFinalY();
//                final boolean doneScrolling = overScroller.isFinished()
//                        || ((scrollerFinishedX || dx != 0) && (scrollerFinishedY || dy != 0));
                scrollBy(dx,dy);
                invalidate();
                postOnAnimation();
            }
        }

        void startScroll(int startX,int startY,int dx,int dy) {
            lastFlingX = startX;
            lastFlingY = startY;
            overScroller.startScroll(startX, startY, dx, dy);
            if (Build.VERSION.SDK_INT < 23) {
                // b/64931938 before API 23, startScroll() does not reset getCurX()/getCurY()
                // to start values, which causes fillRemainingScrollValues() put in obsolete values
                // for LayoutManager.onLayoutChildren().
                overScroller.computeScrollOffset();
            }
            postOnAnimation();
        }

        /**
         * abort the animation
         */
        void abortAnimation(){
            if(!overScroller.isFinished()){
                overScroller.abortAnimation();
                postInvalidate();
            }
        }

        void fling(float velocityX,float velocityY) {
            lastFlingX = lastFlingY = 0;
            overScroller.fling(0, 0, (int)velocityX, (int)velocityY,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            postOnAnimation();
        }

        void postOnAnimation() {
            removeCallbacks(this);
            ViewCompat.postOnAnimation(SlidingLayout.this, this);
        }
    }
}