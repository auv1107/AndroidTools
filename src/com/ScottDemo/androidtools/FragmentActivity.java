
package com.ScottDemo.androidtools;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FragmentActivity extends ActionBarActivity implements HeadFragment.OnHeadlineSelectedListener {

    public static long startTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        startTime = SystemClock.elapsedRealtime();
        
        Log.d("FragmentActivity", "onCreate");
        if (savedInstanceState == null) {
            HeadFragment hf = new HeadFragment();
            hf.setArguments(getIntent().getExtras());
            
            ArrayAdapter aa =  new ArrayAdapter(this, android.R.layout.simple_list_item_1,android.R.id.text1, getData());
            hf.setListAdapter(aa);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content1, hf)
                    .add(R.id.content2, new ArticleFragment(), "articleFragment")
                    .commit();
        }
        
    }
    
    private String[] getData() {
        return new String[] {
                "Apple",
                "Bumper",
                "Canada",
                "Dolphin",
                "Elephant",
                "FranklinRudy"
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
       // if (id == R.id.action_settings) {
       //     return true;
       // }
        return super.onOptionsItemSelected(item);
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
            View rootView = inflater.inflate(R.layout.fragment_, container, false);
            return rootView;
        }
    }

    @Override
    public void onArticleSelected(int position) {
        // TODO Auto-generated method stub
        ArticleFragment af = (ArticleFragment)getSupportFragmentManager().findFragmentById(R.id.content2);
        if (af != null) {
            af.updateArticleView(position);
        } else {
            ArticleFragment newAf = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newAf.setArguments(args);
            
            getSupportFragmentManager().beginTransaction().replace(R.id.content2, newAf).addToBackStack(null).commit();
        }
    }
}
