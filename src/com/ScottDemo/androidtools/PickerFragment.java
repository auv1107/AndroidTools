
package com.ScottDemo.androidtools;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PickerFragment extends ListFragment {
    private static final String TAG = PickerFragment.class.getSimpleName();

    private View mRoot;
    private Cursor mCursor;
    private DetailView mDetailView;

    public interface OnItemSelectedListener {
        public void onItemSelected(int position);
    }

    /*
     * (non-Javadoc)
     * @see android.app.ListFragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log("onCreateView");
        mRoot = inflater.inflate(R.layout.fragment_contact, container);

        initDetailView();

        mCursor = getContacts();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.contact_entry, mCursor,
                new String[] {
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts._ID
                },
                new int[] {
                        R.id.contactEntryText, R.id.contactEntryNumber
                });
        setListAdapter(adapter);
        return mRoot;
    }

    public void initDetailView() {
        mDetailView = new DetailView(getActivity(), mRoot, R.id.vs_detail);
    }

    public void show() {
        if (mDetailView == null) {
            initDetailView();
        }
        mDetailView.show();
    }

    public void setContent(String id) {
        try {
            if (mDetailView != null) {
                mDetailView.setContent(id);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void hide() {
        if (mDetailView != null) {
            mDetailView.hide();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        log("onListItemClicked");
        super.onListItemClick(l, v, position, id);
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            int index = mCursor.getColumnIndex(ContactsContract.Contacts._ID);
            setContent(mCursor.getString(index));
            show();
        }
    }

    private void log(String msg) {
        // TODO Auto-generated method stub
        Log.d(TAG, this.toString() + " - " + msg);
    }

    private Cursor getContacts()
    {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Activity a = getActivity();
        ContentResolver cr = a.getContentResolver();
        Cursor c = cr.query(uri, projection, selection, selectionArgs, sortOrder);
        return c;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Fragment#onPause()
     */
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    /*
     * (non-Javadoc)
     * @see android.app.Fragment#onResume()
     */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    /*
     * (non-Javadoc)
     * @see android.app.Fragment#onStart()
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

}
