
package com.ScottDemo.androidtools.Scene;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.WindowManager;

public class BaseScene extends AbstractScene {
    private final String TAG = BaseScene.class.getSimpleName();
    protected final boolean DBG = true;

    public BaseScene(Context context) {
        super(context);
        log("BaseScene");
        // TODO Auto-generated constructor stub
    }

    public boolean onEnterPrepare() {
        log("onEnterPrepare");
        IntentFilter backPressed = new IntentFilter(android.content.Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
       // getContext().registerReceiver(mBackPressedReceiver, backPressed);
        return true;
    }

    public void onEnter() {
        log("onEnter");
    }

    public boolean onExitDone() {
        log("onExitDone");
       // getContext().unregisterReceiver(mBackPressedReceiver);
        return true;
    }

    public void onExit() {
        log("onExit");
    }

    public void start() {
        log("start");
        if (onEnterPrepare()) {
            onEnter();
        }
    }

    public void end() {
        log("end");
        onExit();
        onExitDone();
    }
    
    public void onBackPressed() {
        log("onBackPressed");
        end();
    }
    
    private BroadcastReceiver mBackPressedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            log ("onReceive");
            onBackPressed();
        }
    };
    
    protected void log(String log) {
        if (DBG) {
            Log.d(TAG, log);
        }
    }
}
