
package com.ScottDemo.androidtools.Scene;

import android.content.Context;
import android.view.WindowManager;

public abstract class AbstractScene {
    private Context mContext;
    private WindowManager mWm = null;

    public AbstractScene(Context context) {
        setContext(context);
    }

    public abstract boolean onEnterPrepare();

    public abstract void onEnter();

    public abstract boolean onExitDone();

    public abstract void onExit();

    public abstract void start();

    public abstract void end();

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public WindowManager getWindowManager() {
        if (mWm == null) {
            mWm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        }
        return mWm;
    }
}
