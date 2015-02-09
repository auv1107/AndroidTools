
package com.ScottDemo.androidtools;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        if (savedInstanceState == null) {
            PlaceholderFragment pf = new PlaceholderFragment();

            Cursor cursor_contact = getContacts();
            SimpleCursorAdapter sca = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_2,
                    cursor_contact,
                    new String[] {
                            ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID
                    },
                    new int[] {
                            android.R.id.text1, android.R.id.text2
                    },
                    0);
            pf.setListAdapter(sca);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, pf)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
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
    public static class PlaceholderFragment extends ListFragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
            return rootView;
        }

    }

    private Cursor getContacts() {
        StringBuilder sbLog = new StringBuilder();
        // 得到ContentResolver对象
        ContentResolver cr = this.getContentResolver();
        // 取得电话本中开始一项的光标,主要就是查询"contacts"表
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        startManagingCursor(cursor);
        return cursor;

    }

}
