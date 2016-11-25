package com.helin.hlrefreshlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by helin on 2016/11/25 09:40.
 */

public class TextViewGroup extends LinearLayout{
    public TextViewGroup(Context context) {
        super(context);
    }

    public TextViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("dispatchTouchEvent***","ACTION_DOWN------");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("dispatchTouchEvent***","ACTION_MOVE------");
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("onTouchEvent***","ACTION_DOWN------");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("onTouchEvent***","ACTION_MOVE------");
                break;
        }

        return super.onTouchEvent(event);
    }
}
