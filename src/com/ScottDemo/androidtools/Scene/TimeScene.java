
package com.ScottDemo.androidtools.Scene;

import java.util.Date;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.ScottDemo.androidtools.R;
import com.ScottDemo.androidtools.TimeActivity;

public class TimeScene extends BaseScene {

    public interface onViewClosedListener {
        public void onViewClosed();
    }

    private onViewClosedListener mOnViewClosedListener;

    private LayoutParams params;
    private View contentView;
    private int mRes;

    public TimeScene(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public TimeScene(Context context, int layoutResource) {
        super(context);
        this.mRes = layoutResource;

    }

    @Override
    public boolean onEnterPrepare() {
        // TODO Auto-generated method stub
        initParams();
        initView();
        return super.onEnterPrepare();
    }

    @Override
    public void onEnter() {
        // TODO Auto-generated method stub
        super.onEnter();
        showView();
    }

    @Override
    public boolean onExitDone() {
        // TODO Auto-generated method stub
        return super.onExitDone();
    }

    @Override
    public void onExit() {
        // TODO Auto-generated method stub
        super.onExit();
    }

    public View findViewById(int id) {
        return contentView.findViewById(id);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(mRes, null);
        TextView tv_showTime = (TextView) contentView.findViewById(R.id.tv_showTime);

        String timeStr = (new Date(System.currentTimeMillis())).toGMTString();
        tv_showTime.setText(timeStr);

        Button btn_closeWindow = (Button) findViewById(R.id.btn_closeWindow);
        btn_closeWindow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView();
                if (mOnViewClosedListener != null) {
                    mOnViewClosedListener.onViewClosed();
                }
            }
        });
    }

    private void showView() {
        getWindowManager().addView(contentView, params);
    }

    private void removeView() {
        getWindowManager().removeViewImmediate(contentView);
    }

    private boolean initParams() {
        if (params == null) {
            params = new LayoutParams();
            params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
            // params.type = mWindowType;

            params.flags |= WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            params.flags |= WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
            params.flags |= WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON;
            params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            params.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            params.flags |= WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
            params.width = LayoutParams.MATCH_PARENT;
            params.height = LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.CENTER;
            params.dimAmount = 0.6f;
        }
        return true;
    }

    public onViewClosedListener getOnViewClosedListener() {
        return mOnViewClosedListener;
    }

    public void setOnViewClosedListener(onViewClosedListener mOnViewClosedListener) {
        this.mOnViewClosedListener = mOnViewClosedListener;
    }

}
