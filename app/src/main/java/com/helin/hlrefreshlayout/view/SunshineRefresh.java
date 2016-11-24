package com.helin.hlrefreshlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.helin.hlrefreshlayout.R;
import com.helin.hlrefreshlayout.uitl.DensityUtil;

/**
 * Created by helin on 2016/11/22 16:36.
 */

public class SunshineRefresh extends RefreshLayout {

    public SunshineRefresh(Context context) {
        super(context);
        initSunshine();
    }

    public SunshineRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSunshine();
    }

    public SunshineRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSunshine();
    }


    private void initSunshine() {
        setHeadHeight(DensityUtil.dip2px(getContext(), 80));

        final View headerView = LayoutInflater.from(getContext()).inflate(R.layout.view_header, null);
        final SunshineView sunshineView  = (SunshineView) headerView.findViewById(R.id.sunshine);
        addHeadView(headerView);

        setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                sunshineView.startAnim();
                sunshineView.postDelayed(new Thread(){
                    @Override
                    public void run() {
                        finishRefreshing();
                        sunshineView.stopAnim();
                    }
                },2000);
            }

            @Override
            public void onStartRefresh() {
                Log.e("SunshineRefresh","onStartRefresh");
            }

            @Override
            public void onFinishRefresh() {
                Log.e("SunshineRefresh","onFinishRefresh");
            }
        });

    }

}
