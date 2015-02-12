
package com.ScottDemo.androidtools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class ArticleFragment extends Fragment {
    public static final String TAG = ArticleFragment.class.getSimpleName();
    public static final String ARG_POSITION = "ARG_POSITION";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onCreateView");
        if (container != null)
            Log.d(TAG, "not null");
        else
            Log.d(TAG, "null");
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    public void updateArticleView(int position) {
        ((TextView) this.getView().findViewById(R.id.tv_article)).setText(String.valueOf(position));
    }
}
