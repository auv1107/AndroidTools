
package com.ScottDemo.androidtools;

import com.ScottDemo.androidtools.Scene.TimeScene;
import com.ScottDemo.androidtools.Scene.TimeScene.onViewClosedListener;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;

public class TimeActivity extends Activity {
    public TimeScene mTimeScene;
    private WakeLock mDimWakeLock;
    private Object lock = new Object();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mTimeScene = new TimeScene(this, R.layout.layout_timescene);
        mTimeScene.setOnViewClosedListener(new onViewClosedListener() {
            
            @Override
            public void onViewClosed() {
                // TODO Auto-generated method stub
                finish();
            }
        });
        mTimeScene.onEnterPrepare();
        acquireWakeLock();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimeScene.onEnter();
    }

    @Override
    public void onPause() {
        super.onPause();
        
    }

    @Override
    public void onStop() {
        super.onStop();
        mTimeScene.onExit();
        mTimeScene.onExitDone();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseWakeLock();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //mTimeScene.onBackPressed();
        super.onBackPressed();
    }
    
    private void acquireWakeLock() {
        Log.d("TimeActivity", "acquire DimWakeLock");
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mDimWakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "TimeActivity"); 
        KeyguardManager km = (KeyguardManager) getSystemService(
                Context.KEYGUARD_SERVICE);
        KeyguardLock keyguardLock = km.newKeyguardLock(this.getClass().getCanonicalName()); 
        keyguardLock.disableKeyguard(); 
        synchronized (lock) {
            try {
                // mDimWakeLock.acquire(autoSnoozeMinutes * 1000 * 60); 
                mDimWakeLock.acquire();
            } catch (Exception e) {
                Log.d("TimeActivity", "acquire DimWakeLock error", e);
            }
        }
    }

    private void releaseWakeLock() {
        synchronized (lock) {
            Log.d("TimeActivity", "release DimWakeLock");
            if (mDimWakeLock != null && mDimWakeLock.isHeld()) {
                try {
                    mDimWakeLock.release();
                } catch (Exception e) {
                    Log.d("TimeActivity", "release DimWakeLock error", e);
                }
            }
            mDimWakeLock = null;
        }
    }
}
