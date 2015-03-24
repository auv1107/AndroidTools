
package com.ScottDemo.androidtools;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StringItem extends LinearLayout {
    private TextView tv_words;
    private TextView tv_time;

    private String mWords;
    private long mTime;

    public static StringItem getInstance(final Context context, String text) {
        // LayoutInflater inflater = LayoutInflater.from(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        StringItem item = (StringItem) inflater.inflate(R.layout.layout_for_listview_item,
                null);
        item.setText(text);
        item.setTime(System.currentTimeMillis() / 1000);

        return item;
    }

    public StringItem(Context context) {
        this(context, null);
    }

    public StringItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StringItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        tv_words = (TextView) findViewById(R.id.tv_words);
        tv_time = (TextView) findViewById(R.id.tv_time);
        final Runnable r = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mTime = System.currentTimeMillis() / 1000;
                tv_time.setText(mTime + "");
                tv_words.setText(mWords);
                invalidate();
                postDelayed(this, 1000);
            }
        };
        postDelayed(r, 1000);
        super.onFinishInflate();
    }

    public void setText(String text) {
        mWords = text;
    }

    public void setTime(long time) {
        mTime = time;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        // super.onDraw(canvas);
        // canvas.save();
        // canvas.rotate(30);
        // canvas.drawColor(R.color.black_overlay);
        // canvas.restore();
        //
        // tv_time.setText(mTime + "");
        // tv_words.setText(mWords);
    }
}
