## readme

We have four different demos.

1. Display activity and dialog in the same time. The activity on the left side and the dialog in the right side.

2. Display view from another process.

3. Display view from another process, Just like the Google feed.

4. The hardest one. Ask three services for views and display one view at a time.

### Sample

[download APK](https://github.com/momodae/LibraryResources/blob/master/LauncherOverlay/file/app-debug.apk?raw=true)

### Image

![image1](https://github.com/momodae/LibraryResources/blob/master/LauncherOverlay/image/image1.gif)

![image2](https://github.com/momodae/LibraryResources/blob/master/LauncherOverlay/image/image2.gif)

![image3](https://github.com/momodae/LibraryResources/blob/master/LauncherOverlay/image/image3.gif)

![image4](https://github.com/momodae/LibraryResources/blob/master/LauncherOverlay/image/image4.gif)

To be honest, All I want is just the first one. But I was wondering if we could implement the fourth one.
After a few attempts. I realized it is easier than I had thought.

You may noticed I have put all the services inside this application. Because It is hard to lunch the service from another application.
So that's why we include all the services inside this applications. But as you can see, Each services has its own process.

It is easy to split the window and level the dialog some space. I do this work in the snippet below:

```
private void initialWindowAttributes() {
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
    layoutParams.flags = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS|
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
    layoutParams.gravity = Gravity.RIGHT;
    layoutParams.format = PixelFormat.TRANSLUCENT;
    layoutParams.type = Build.VERSION.SDK_INT >= 25
            ? WindowManager.LayoutParams.TYPE_DRAWN_APPLICATION
            : WindowManager.LayoutParams.TYPE_APPLICATION;
    layoutParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
    window.setAttributes(layoutParams);
}

private void initialContentView(){
    final ViewGroup contentView=findViewById(android.R.id.content);
    contentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            Activity ownerActivity;
            Context context = getContext();
            if(context instanceof ContextThemeWrapper){
                ContextThemeWrapper contextThemeWrapper = (ContextThemeWrapper) context;
                context=contextThemeWrapper.getBaseContext();
            }
            if(context instanceof Activity){
                ownerActivity = (Activity) context;
            } else {
                ownerActivity = getOwnerActivity();
            }
            if(null!=ownerActivity){
                Window window = ownerActivity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.gravity = Gravity.LEFT;
                window.setAttributes(attributes);

                View contentView=window.findViewById(android.R.id.content);
                DisplayMetrics displayMetrics = ownerActivity.getResources().getDisplayMetrics();
                ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
                layoutParams.width = displayMetrics.widthPixels - (right-left);
                contentView.requestLayout();
            }
        }
    });
}
```

The approach for adding view in other process was:

```
@CallSuper
public void onPreCreated(WindowManager.LayoutParams layoutParams) {
    //!!!We use the same window token to create the WindowManager in this process.
    window.setWindowManager(null, layoutParams.token,
            new ComponentName(this, getBaseContext().getClass()).flattenToShortString(), true);
    windowManager = window.getWindowManager();
    Point point = new Point();
    windowManager.getDefaultDisplay().getRealSize(point);
    windowShift = -Math.max(point.x, point.y);
    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
    layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
    layoutParams.gravity = Gravity.LEFT;
    layoutParams.format = PixelFormat.TRANSLUCENT;
    layoutParams.type = Build.VERSION.SDK_INT >= 25
            ? WindowManager.LayoutParams.TYPE_DRAWN_APPLICATION
            : WindowManager.LayoutParams.TYPE_APPLICATION;
    layoutParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;//16
    window.setAttributes(layoutParams);
    window.clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
}

```

The trick about touch event. It is impossible to handle the event like the normal window. But I have found something that is interesting.
If you re-size your window like: 100x100 then if you touch the screen and inside the rectangle. You could handle it. The event outside the window could receive by the Activity.

That is how it worked in the second sample.

But for the third one. There is a little difference. We have to change the window flags frequently. When we use ViewPager scroll to the left side. The service window handles the event completely.
The view could be a little tricky for the user. This view could scroll like the ViewPager. After we scroll back to the Activity. The window changes its window flags and makes it disable to receive user touch events.

That's the tricky part. I holy this sample will help understand the window stuff better. Good luck. 
