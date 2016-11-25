package com.helin.hlrefreshlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by helin on 2016/11/24 17:07.
 */

public class TestView extends TextView {
    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("dispatchTouchEvent","ACTION_DOWN------");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("dispatchTouchEvent","ACTION_MOVE------");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("onTouchEvent","onTouchEvent------");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("onTouchEvent","ACTION_DOWN------");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("onTouchEvent","ACTION_MOVE------");
                break;
        }
        return super.onTouchEvent(event);

    }
}
