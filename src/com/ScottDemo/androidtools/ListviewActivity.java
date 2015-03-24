
package com.ScottDemo.androidtools;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private ViewStub vs_listview;
        private ListView mListView;
        private List<String> mList;
        private View mRootView;

        public PlaceholderFragment() {
            mList = Arrays.asList(getData());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.fragment_listview, container, false);

            mListView = (ListView) mRootView.findViewById(R.id.lv_listview);

            // ArrayAdapter<String> adapter = new
            // ArrayAdapter<String>(getActivity(),
            // R.layout.layout_for_listview_item, R.id.tv_words, getData());
            mListView.setAdapter(new ListAdapter());
            mListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getActivity(), mList.get(position), Toast.LENGTH_SHORT).show();
                }
            });

            return mRootView;
        }

        private String[] getData() {
            return new String[] {
                    "Apple",
                    "Banana",
                    "Candy",
                    "Danny",
                    "Elephant",
                    "Fly",
                    "Girls"
            };
        }

        public class ListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return mList == null ? 0 : mList.size();
            }

            @Override
            public Object getItem(int position) {
                return mList != null && position >= 0 && mList.size() > 0 ? mList.get(position)
                        : null;
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                StringItem view = (StringItem) convertView;
                if (view == null) {
                    view = (StringItem) StringItem.getInstance(getActivity(), "");
                    Log.d("ListAdapter", "new");
                } else {
                    Log.d("ListAdapter", "recycled");
                }

                view.setText(mList.get(position));
                return view;
            }

        }
    }

}
