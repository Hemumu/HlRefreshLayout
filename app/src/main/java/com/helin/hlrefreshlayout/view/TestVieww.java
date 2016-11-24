package com.helin.hlrefreshlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by helin on 2016/11/24 17:07.
 */

public class TestVieww extends TextView {
    public TestVieww(Context context) {
        super(context);
    }

    public TestVieww(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestVieww(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("dispatchTouchEvent","ACTION_DOWN------");

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
            case MotionEvent.ACTION_MOVE:
                Log.e("onTouchEvent","ACTION_MOVE------");
                break;
        }
        return  true;
//        return super.onTouchEvent(event);

    }
}
