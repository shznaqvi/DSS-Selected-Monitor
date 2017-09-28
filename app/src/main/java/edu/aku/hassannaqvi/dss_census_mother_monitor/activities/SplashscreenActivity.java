package edu.aku.hassannaqvi.dss_census_mother_monitor.activities;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.dss_census_mother_monitor.R;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends Activity {
/*
    private static final boolean AUTO_HIDE = true;

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private static final int UI_ANIMATION_DELAY = 300;
    private static int SPLASH_TIME_OUT = 3000;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        new Handler().postDelayed(new Runnable() {


             */
    /**
     * Showing splash screen with a timer. This will be useful when you
     * want to show case your app logo / company
     *//*


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashscreenActivity.this, LoginActivity.class);
                toggle();
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }*/


    @BindView(R.id.splash_ball)
    View ball;
    @BindView(R.id.splash_scroll_view_house)
    RelativeLayout scrollHouse;
    @BindView(R.id.splash_post_bg)
    View mainBg;
    @BindView(R.id.splash_next_bg)
    View splashNextBg;
    @BindView(R.id.splash_txt_appname)
    TextView splashNext;

    private static final int STATE_INITIAL_ANIMATING = 0;
    private static final int STATE_INITIAL_ANIMATED = 1;
    private static final int STATE_OPTIONS_APPEAR_ANIMATED = 3;
    private static final int STATE_OPTIONS_APPEAR_ANIMATING = 2;

    private Interpolator initAnimationInterpolator = PathInterpolatorCompat.create(.8f, 0, .2f, 1);
    private Interpolator midAnimationInterpolator = PathInterpolatorCompat.create(.01f, 0, .025f, 1);

    private AnimatorSet finalAnimator;

    private int state = STATE_INITIAL_ANIMATING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ButterKnife.bind(this);

        wireUpWidgets();
        startAnimation();
    }

    private void wireUpWidgets() {
        scrollHouse.getLayoutParams().width = (int) (Utils.getScreenWidth(this) * .7f);
        scrollHouse.getLayoutParams().height = (int) (Utils.getScreenHeight(this) * .4f);
        scrollHouse.setLayoutParams(scrollHouse.getLayoutParams());

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (state == STATE_INITIAL_ANIMATED) {
                    optionsAppearAnimation();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                    }, 400);
                }
            }
        }, 3500);
    }

    private void startAnimation() {
        if (finalAnimator != null) {
            finalAnimator.removeAllListeners();
            finalAnimator.cancel();
        }

        ball.setTranslationY(-((Utils.getScreenHeight(this) * .5f) + getResources().getDimension(R.dimen.splash_ball_size)));
        ObjectAnimator ballFallAnimator = ObjectAnimator.ofFloat(ball, "translationY", 0);
        ballFallAnimator.setDuration(400);
        ballFallAnimator.setInterpolator(new AccelerateInterpolator(2.5f));

        ObjectAnimator ballFallDownSquezeOut = ObjectAnimator.ofFloat(ball, "scaleY", 1, 1.3f);
        ballFallDownSquezeOut.setDuration(150);
        ballFallDownSquezeOut.setInterpolator(new AccelerateInterpolator(2.5f));

        ObjectAnimator ballOnDownSquezeOutY = ObjectAnimator.ofFloat(ball, "scaleY", 1);
        ballOnDownSquezeOutY.setDuration(250);
        ballOnDownSquezeOutY.setInterpolator(new DecelerateInterpolator(3f));
        ObjectAnimator ballOnDownSquezeOutX = ObjectAnimator.ofFloat(ball, "scaleX", 1.3f);
        ballOnDownSquezeOutX.setDuration(250);
        ballOnDownSquezeOutX.setInterpolator(new DecelerateInterpolator(3f));


        ObjectAnimator ballFallAnimatorReverse = ObjectAnimator.ofFloat(ball, "translationY", -((Utils.getScreenHeight(this) * .5f) + getResources().getDimension(R.dimen.splash_ball_size)) * .5f);
        ballFallAnimatorReverse.setDuration(300);
        ballFallAnimatorReverse.setInterpolator(new DecelerateInterpolator(2.5f));


        ObjectAnimator ballOnDownSquezeOutYReverse = ObjectAnimator.ofFloat(ball, "scaleY", 1.3f);
        ballOnDownSquezeOutYReverse.setDuration(300);
        ballOnDownSquezeOutYReverse.setInterpolator(new DecelerateInterpolator(3f));
        ObjectAnimator ballOnDownSquezeOutXReverse = ObjectAnimator.ofFloat(ball, "scaleX", 1);
        ballOnDownSquezeOutXReverse.setDuration(300);
        ballOnDownSquezeOutXReverse.setInterpolator(new DecelerateInterpolator(3f));

        ObjectAnimator ballFallAnimatorZoom = ObjectAnimator.ofFloat(ball, "translationY", 0);
        ballFallAnimatorZoom.setDuration(300);

        float ballScale = (float) (Math.sqrt(Math.pow(Utils.getScreenHeight(this), 2) + Math.pow(Utils.getScreenWidth(this), 2)) / getResources().getDimension(R.dimen.splash_ball_size));
        ObjectAnimator ballOnDownSquezeOutYZoom = ObjectAnimator.ofFloat(ball, "scaleY", ballScale);
        ballOnDownSquezeOutYZoom.setDuration(300);
        ballOnDownSquezeOutYZoom.setInterpolator(new AccelerateInterpolator(3f));
        ObjectAnimator ballOnDownSquezeOutXZoom = ObjectAnimator.ofFloat(ball, "scaleX", ballScale);
        ballOnDownSquezeOutXZoom.setDuration(300);
        ballOnDownSquezeOutXZoom.setInterpolator(new AccelerateInterpolator(3f));

        ObjectAnimator ballOnDownPivotYZoom = ObjectAnimator.ofFloat(ball, "pivotY", getResources().getDimension(R.dimen.splash_ball_size_half));
        ballOnDownPivotYZoom.setDuration(300);
        ObjectAnimator ballOnDownPivotXZoom = ObjectAnimator.ofFloat(ball, "pivotX", getResources().getDimension(R.dimen.splash_ball_size_half));
        ballOnDownPivotXZoom.setDuration(300);

        AnimatorSet initSet = new AnimatorSet();
        AnimatorSet midSet = new AnimatorSet();
        AnimatorSet endSet = new AnimatorSet();

        initSet.play(ballFallAnimator).with(ballFallDownSquezeOut).before(ballOnDownSquezeOutX).before(ballOnDownSquezeOutY);

        midSet.play(ballFallAnimatorReverse).with(ballOnDownSquezeOutXReverse).with(ballOnDownSquezeOutYReverse);

        endSet.play(ballFallAnimatorZoom).with(ballOnDownSquezeOutXZoom).with(ballOnDownSquezeOutYZoom).with(ballOnDownPivotYZoom).with(ballOnDownPivotXZoom);

        AnimatorSet ballAnimationSet = new AnimatorSet();
        ballAnimationSet.play(midSet).after(initSet).before(endSet);
        ballAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                state = STATE_INITIAL_ANIMATED;
            }
        });

        mainBg.setAlpha(0);
        scrollHouse.setAlpha(0);
        splashNext.setTranslationY(getResources().getDimension(R.dimen.splash_next_button_height));
        splashNextBg.setTranslationY(getResources().getDimension(R.dimen.splash_next_button_height));

        ObjectAnimator mainBgSwipeUp = ObjectAnimator.ofFloat(mainBg, "translationY", Utils.getScreenHeight(this) * .5f, 0);
        mainBgSwipeUp.setInterpolator(new DecelerateInterpolator(4f));
        mainBgSwipeUp.setDuration(1600);

        ObjectAnimator mainBgFadeIn = ObjectAnimator.ofFloat(mainBg, "alpha", 0, 1);
        mainBgFadeIn.setInterpolator(new DecelerateInterpolator(1.3f));
        mainBgFadeIn.setDuration(500);


        ObjectAnimator scrollSwipeUp = ObjectAnimator.ofFloat(scrollHouse, "translationY", Utils.getScreenHeight(this) * .1f, -Utils.getScreenHeight(this) * .1f);
        scrollSwipeUp.setInterpolator(new DecelerateInterpolator(4f));
        scrollSwipeUp.setStartDelay(100);
        scrollSwipeUp.setDuration(1600);

        ObjectAnimator scrollFadeIn = ObjectAnimator.ofFloat(scrollHouse, "alpha", 0, 1);
        scrollFadeIn.setInterpolator(new DecelerateInterpolator(1.3f));
        scrollFadeIn.setStartDelay(100);
        scrollFadeIn.setDuration(500);

        ObjectAnimator nextSwipeUp = ObjectAnimator.ofFloat(splashNext, "translationY", 0);
        nextSwipeUp.setInterpolator(new DecelerateInterpolator(4f));
        nextSwipeUp.setStartDelay(100);
        nextSwipeUp.setDuration(1600);

        ObjectAnimator nextBgSwipeUp = ObjectAnimator.ofFloat(splashNextBg, "translationY", 0);
        nextBgSwipeUp.setInterpolator(new DecelerateInterpolator(4f));
        nextBgSwipeUp.setStartDelay(100);
        nextBgSwipeUp.setDuration(1600);

        AnimatorSet postAnimationSet = new AnimatorSet();
        postAnimationSet.play(mainBgSwipeUp).with(mainBgFadeIn).with(scrollSwipeUp).with(scrollFadeIn).with(nextSwipeUp).with(nextBgSwipeUp);

        finalAnimator = new AnimatorSet();
        finalAnimator.play(postAnimationSet).after(ballAnimationSet);

        finalAnimator.setStartDelay(400);

        finalAnimator.start();
    }


    private void optionsAppearAnimation() {
        state = STATE_OPTIONS_APPEAR_ANIMATING;

        if (finalAnimator != null) {
            finalAnimator.removeAllListeners();
            finalAnimator.cancel();
        }

        Utils.setElevation(scrollHouse, 0);
        splashNextBg.setPivotX(Utils.getScreenWidth(this) * .5f);
        splashNextBg.setPivotY(getResources().getDimension(R.dimen.splash_next_button_height));

        ObjectAnimator bgScaleUp = ObjectAnimator.ofFloat(splashNextBg, "scaleY", Utils.getScreenHeight(this) / getResources().getDimension(R.dimen.splash_next_button_height));
        bgScaleUp.setInterpolator(initAnimationInterpolator);
        ObjectAnimator bgSwipeUp = ObjectAnimator.ofFloat(splashNextBg, "translationY", 0);
        bgSwipeUp.setInterpolator(initAnimationInterpolator);
        ObjectAnimator mainBgSwipeUp = ObjectAnimator.ofFloat(mainBg, "translationY", 0);
        mainBgSwipeUp.setInterpolator(initAnimationInterpolator);


        ObjectAnimator nextTranslateDown = ObjectAnimator.ofFloat(splashNext, "translationY", getResources().getDimension(R.dimen.splash_next_button_height));
        nextTranslateDown.setInterpolator(initAnimationInterpolator);
        ObjectAnimator scrollTranslateUp = ObjectAnimator.ofFloat(scrollHouse, "translationY", 0);
        scrollTranslateUp.setInterpolator(initAnimationInterpolator);

        AnimatorSet initSet = new AnimatorSet();
        initSet.setDuration(400);
        initSet.setInterpolator(initAnimationInterpolator);
        initSet.play(bgScaleUp).with(mainBgSwipeUp).with(bgSwipeUp).with(nextTranslateDown).with(scrollTranslateUp);
        initSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                state = STATE_OPTIONS_APPEAR_ANIMATED;
            }
        });


        AnimatorSet midSet = new AnimatorSet();
        midSet.setDuration(600);
        midSet.setInterpolator(midAnimationInterpolator);
        midSet.setStartDelay(60);

        Interpolator bulletScaleInterpolator = PathInterpolatorCompat.create(.2f, 0, .2f, 1);

        AnimatorSet bulletsScale = new AnimatorSet();
//        bulletsScale.setDuration(300);
//        bulletsScale.setStartDelay(400);
        bulletsScale.setInterpolator(bulletScaleInterpolator);

        ObjectAnimator scrollHide = ObjectAnimator.ofFloat(scrollHouse, "alpha", 0);
        scrollHide.setDuration(0);

        finalAnimator = new AnimatorSet();
        finalAnimator.play(midSet).after(initSet).with(scrollHide).with(bulletsScale);
        finalAnimator.start();
    }


    public static class Utils {

        public static int getScreenWidth(Activity activity) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            return width;
        }

        public static int getScreenHeight(Activity activity) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int height = size.y;
            return height;
        }

        public static void setElevation(View view, float elevation) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                view.setElevation(elevation);
        }
    }

}
