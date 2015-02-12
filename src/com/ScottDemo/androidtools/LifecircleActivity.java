
package com.ScottDemo.androidtools;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class LifecircleActivity extends ActionBarActivity {
    private static final String TAG = LifecircleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecircle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /*
     * (non-Javadoc)
     * @see android.support.v7.app.ActionBarActivity#onStop()
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        log("onStop");
        super.onStop();
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        log("onDestroy");
        super.onDestroy();
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        log("onPause");
        super.onPause();
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        log("onResume");
        super.onResume();
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onStart()
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        log("onStart");
        super.onStart();
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRestart()
     */
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        log("onRestart");
        super.onRestart();
    }

    /*
     * (non-Javadoc)
     * @see android.support.v7.app.ActionBarActivity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        log("onBackPressed");
        super.onBackPressed();
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#finish()
     */
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        log("finish");
        super.finish();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_lifecircle, container, false);
            return rootView;
        }
    }

    public void log(String msg) {
        Log.d(TAG, this + " - " + msg);
    }
}
