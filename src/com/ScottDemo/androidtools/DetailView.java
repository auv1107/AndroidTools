
package com.ScottDemo.androidtools;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailView {
    private Activity mActivity;
    private View mRoot;
    private int mStubId;
    private View mContentView;
    private View mDetailContent;
    private View mDetailBg;

    private TextView mDetailName;
    private TextView mDetailPhone;
    private TextView mDetailEmail;
    private ImageView mDetailAvatar;

    public DetailView(Activity activity, View root, int stubId) {
        mActivity = activity;
        mStubId = stubId;
        mRoot = root;
        initView(stubId);
    }

    public void setContent(String id) {
        String[] data = getData(ContactsContract.Contacts.CONTENT_URI,
                ContactsContract.Contacts.DISPLAY_NAME, id, "_id");
        if (data.length > 0) {
            mDetailName.setText(data[0]);
        }

        data = getData(Phone.CONTENT_URI, Phone.NUMBER, id, "contact_id");
        if (data.length > 0) {
            mDetailPhone.setText(data[0]);
        }
        data = getData(Email.CONTENT_URI, Email.ADDRESS, id, "contact_id");
        if (data.length > 0) {
            mDetailEmail.setText(data[0]);
        }
    }

    public void setContent(Cursor c) {
        int index = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
        if (c.moveToFirst()) {
            String name = c.getString(index);
            mDetailName.setText(name);

            String[] data = getData(Phone.CONTENT_URI, Phone.NUMBER, id, "contact_id");
            if (data.length > 0) {
                mDetailPhone.setText(data[0]);
            }
            data = getData(Email.CONTENT_URI, Email.ADDRESS, id, "contact_id");
            if (data.length > 0) {
                mDetailEmail.setText(data[0]);
            }
        }
    }

    public String[] getData(Uri uri, String targetColumn, String id, String matchColumn) {
        Cursor c;
        String[] projection = new String[] {
                targetColumn
        };
        String selection = matchColumn + "=?";
        String[] selectionArgs = new String[] {
                id
        };
        c = mActivity.getContentResolver().query(uri, projection, selection, selectionArgs, null);

        StringBuilder sb = new StringBuilder();
        while (c.moveToNext()) {
            sb.append(c.getString(c.getColumnIndex(targetColumn))).append(" ");
        }
        c.close();
        return sb.toString().split(" ");
    }

    public Cursor getDetail(String id) {
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = null;
        // String selection = ContactsContract.Data._ID + "=?";
        String selection = null;
        String[] selectionArgs = new String[] {
                id
        };
        String sortOrder = null;
        return mActivity.getContentResolver().query(uri, projection, selection, selectionArgs,
                sortOrder);
    }

    public void show() {
        if (mContentView == null) {
            initView(mStubId);
        }
        mContentView.setVisibility(View.VISIBLE);
        mContentView.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mDetailBg.setAlpha(0.0f);
                TimeInterpolator interpolator = new DecelerateInterpolator();
                mDetailBg.setVisibility(View.VISIBLE);
                mDetailBg.animate().setDuration(400).setInterpolator(interpolator).alpha(0.6f);

                mDetailContent.setVisibility(View.VISIBLE);
                mDetailContent.setTranslationY(mDetailContent.getBottom());
                mDetailContent.animate().setDuration(400)
                        .setInterpolator(new DecelerateInterpolator()).translationY(0f)
                        .setListener(new AnimatorListener() {

                            @Override
                            public void onAnimationStart(Animator animation) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                // TODO Auto-generated method stub
                                mDetailBg.setClickable(true);
                                mDetailPhone.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        try {
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse("tel:"
                                                    + mDetailPhone.getText()));
                                            mActivity.startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                mDetailEmail.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        try {
                                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                                            intent.setData(Uri.parse("mailto:"
                                                    + mDetailEmail.getText()));
                                            mActivity.startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                // TODO Auto-generated method stub

                            }
                        });
            }
        });
    }

    public void hide() {
        if (mContentView == null)
            return;

        mContentView.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mDetailBg.animate().setDuration(300).setInterpolator(new DecelerateInterpolator())
                        .alpha(0f);
                mDetailContent.animate().setDuration(300)
                        .setInterpolator(new DecelerateInterpolator())
                        .translationY(mDetailContent.getBottom())
                        .setListener(new AnimatorListener() {

                            @Override
                            public void onAnimationStart(Animator animation) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                // TODO Auto-generated method stub
                                mDetailBg.setClickable(false);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                // TODO Auto-generated method stub

                            }
                        });
            }
        });
    }

    public void initView(int stubId) {
        ViewStub stub = (ViewStub) mRoot.findViewById(stubId);
        mContentView = stub.inflate();

        mDetailBg = findViewById(R.id.detail_frame);
        mDetailContent = findViewById(R.id.detail_content);
        mDetailName = (TextView) findViewById(R.id.detail_name);
        mDetailPhone = (TextView) findViewById(R.id.detail_phone);
        mDetailEmail = (TextView) findViewById(R.id.detail_email);
        mDetailAvatar = (ImageView) findViewById(R.id.detail_avatar);

        mDetailBg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                hide();
            }
        });
    }

    public View findViewById(int id) {
        if (mContentView != null) {
            return mContentView.findViewById(id);
        } else {
            return null;
        }
    }
}
