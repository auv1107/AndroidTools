
package com.ScottDemo.androidtools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ScottDemo.androidtools.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private LinearLayout main_container;

    private TextView mContentView;
    boolean no = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        /*
                         * if (Build.VERSION.SDK_INT >=
                         * Build.VERSION_CODES.HONEYCOMB_MR2) { // If the
                         * ViewPropertyAnimator API is available // (Honeycomb
                         * MR2 and later), use it to animate the // in-layout UI
                         * controls at the bottom of the // screen. if
                         * (mControlsHeight == 0) { mControlsHeight =
                         * controlsView.getHeight(); } if (mShortAnimTime == 0)
                         * { mShortAnimTime = getResources().getInteger(
                         * android.R.integer.config_shortAnimTime); }
                         * controlsView.animate() .translationY(visible ? 0 :
                         * mControlsHeight) .setDuration(mShortAnimTime); } else
                         * { // If the ViewPropertyAnimator APIs aren't //
                         * available, simply show or hide the in-layout UI //
                         * controls. controlsView.setVisibility(visible ?
                         * View.VISIBLE : View.GONE); } if (visible &&
                         * AUTO_HIDE) { // Schedule a hide(). //
                         * delayedHide(AUTO_HIDE_DELAY_MILLIS); }
                         */
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        main_container = (LinearLayout) findViewById(R.id.main_container);
        mContentView = (TextView) contentView;
        initView();
    }

    public void initView() {
        if (main_container != null) {
            Button btnLifeCircle = new Button(this);
            btnLifeCircle.setText("生命周期");
            btnLifeCircle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(MainActivity.this, LifecircleActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
            main_container.addView(btnLifeCircle);

            Button btnContact = new Button(this);
            btnContact.setText("通讯录");
            btnContact.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(MainActivity.this, ContactManagerActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
            main_container.addView(btnContact);

            Button btnDialog = new Button(this);
            btnDialog.setText("对话框");
            btnDialog.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("多选框")
                            .setMultiChoiceItems(new String[] {
                                    "选项1", "选项2", "选项3", "选项4"
                            }, null, null)
                            .setPositiveButton("确定", null)
                            .setNegativeButton("取消", null)
                            .show();
                }
            });
            main_container.addView(btnDialog);

            Button btnAnimation = new Button(this);
            btnAnimation.setText("动画");
            btnAnimation.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ImageView iv = (ImageView) findViewById(R.id.imageAnimation);
                    final int heightMeasure = main_container.getMeasuredHeight();
                    final int widthMeasure = iv.getMeasuredWidth();
                    final Animation animation1 = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            super.applyTransformation(interpolatedTime, t);
                            int tmp = heightMeasure - (int) (heightMeasure * interpolatedTime);
                            main_container.getLayoutParams().height = tmp;
                            main_container.requestLayout();
                        }
                    };
                    Animation animation = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            super.applyTransformation(interpolatedTime, t);
                            if (interpolatedTime == 1) {
                                iv.getLayoutParams().height = 1;
                                iv.requestLayout();
                                iv.setVisibility(View.GONE);
                            } else if (interpolatedTime > 0.5 && no == false) {
                                no = true;
                                animation1.setDuration(2000);
                                animation1.setInterpolator(new DecelerateInterpolator(1f));
                                main_container.startAnimation(animation1);
                            }
                            else {
                                iv.getLayoutParams().width = widthMeasure
                                        - (int) (widthMeasure * interpolatedTime);
                                iv.requestLayout();
                            }
                        }

                        @Override
                        public boolean willChangeBounds() {
                            // TODO Auto-generated method stub
                            return true;
                        }
                    };
                    animation.setDuration(2000);
                    animation.setInterpolator(new DecelerateInterpolator(1f));
                    iv.startAnimation(animation);
                }
            });
            main_container.addView(btnAnimation);

            Button btnDelayDialog = new Button(this);
            btnDelayDialog.setText("DelayDialog");
            btnDelayDialog.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("Dialog Displaying")
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    }, 30000);
                }
            });
            main_container.addView(btnDelayDialog);

            Button btnListview = new Button(this);
            btnListview.setText("ListView");
            btnListview.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(MainActivity.this, ListviewActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
            main_container.addView(btnListview);

            String a = getWindow().getAttributes().toString();
            Toast.makeText(this, "" + a, Toast.LENGTH_LONG).show();
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mContentView.setText(dm.toString());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
