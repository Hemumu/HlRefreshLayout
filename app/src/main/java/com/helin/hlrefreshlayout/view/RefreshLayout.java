package com.helin.hlrefreshlayout.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by helin on 2016/11/22 14:21.
 */

public class RefreshLayout extends FrameLayout {

    /**
     * 子View
     */
    private View mChildView;
    /**
     * 头Layout
     */
    private FrameLayout mHeadLayout;

    /**
     * 刷新状态
     */
    protected boolean isRefreshing;
    /**
     * 触摸获得Y的位置
     */
    private float mTouchY;

    /**
     * 当前Y的位置
     */
    private float mCurrentY;

    /**
     * 阻尼系数，数字越大阻尼越小
     */
    private float mResistance =0.7f;

    /**
     * 头部最高允许的高度
     */
    private float mHeadHeight;
    private DecelerateInterpolator decelerateInterpolator;
    /**
     * PullToRefreshListener
     */
    private PullToRefreshListener pullToRefreshListener;

    public RefreshLayout(Context context) {
        super(context);
        init();
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode()) {
            return;
        }

        if (getChildCount() > 1) {
            throw new RuntimeException("只能拥有一个子控件");
        }

        //在动画开始的地方快然后慢;
        decelerateInterpolator = new DecelerateInterpolator(10);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        FrameLayout headViewLayout = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.TOP;
        headViewLayout.setLayoutParams(layoutParams);
        mHeadLayout = headViewLayout;
        this.addView(mHeadLayout);
        //获得子控件
        mChildView = getChildAt(0);
        if (mChildView == null) {
            return;
        }
        mChildView.setClickable(true);
        mChildView.animate().setInterpolator(new DecelerateInterpolator());//设置速率为递减
        mChildView.animate().setUpdateListener(//通过addUpdateListener()方法来添加一个动画的监听器
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int height = (int) mChildView.getTranslationY();//获得mChildView当前y的位置
                        mHeadLayout.getLayoutParams().height = height;
                        mHeadLayout.requestLayout();//重绘
                    }
                }
        );
    }

    /**
     * 控件在顶端时最后的Y坐标
     */
    float mLastY;
    /**
     * 头部移动的距离
     */
    private float mCurrentPos;

    /**
     * 分发事件
     * <p>
     * @param e
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        //当前正在刷新
        if (isRefreshing) {
            return super.dispatchTouchEvent(e);
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = e.getY();
                mCurrentPos = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = e.getY();
                float dy = currentY - mLastY;//手指移动的距离
                //上拉
                if (mLastY - currentY > 0) {//最后的Y坐标大于当前的Y坐标
                    if (mCurrentPos != 0) {
                        mCurrentPos = dy * mResistance;
                        mCurrentPos = Math.max(0, mCurrentPos);
                        changeView(mCurrentPos);
                    } else {
                        return super.dispatchTouchEvent(e);
                    }
                 //下拉
                } else {
                    if (!canChildScrollUp()) { //是否滑动到顶部
                        mCurrentPos = dy * mResistance;
                        mCurrentPos = Math.max(0, mCurrentPos);
                        changeView(mCurrentPos);
                    } else {
                        mLastY = e.getY();
                        return super.dispatchTouchEvent(e);
                    }
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mChildView != null) {
                    if (mChildView.getTranslationY() >= mHeadHeight) {//手指松开后head达到刷新高度
                        pullToRefreshListener.onStartRefresh();
                        mChildView.animate().translationY(mHeadHeight).start();
                        pullToRefreshListener.onRefresh(this);
                        isRefreshing = true;
                    } else if (mChildView.getTranslationY() > 0)
                        mChildView.animate().translationY(0).start();
                }
        }
        return super.dispatchTouchEvent(e);
    }

    /**
     * 改变ChildView和head的高度
     * @param pos
     */
    private void changeView(float pos ){
        mChildView.setTranslationY(pos);
        mHeadLayout.getLayoutParams().height = (int) (mCurrentPos);
        mHeadLayout.requestLayout();
    }


    /**
     * 拦截事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    /**
     * 用来判断是否可以下拉
     *
     * @return boolean
     */
    public boolean canChildScrollUp() {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, -1);
        }
    }


    /**
     * 设置头部可滑动的最高滑动高度
     *
     * @param headHeight
     */
    public void setHeadHeight(float headHeight) {
        this.mHeadHeight = headHeight;
    }

    /**
     * 添加头部vieww
     *
     * @param header
     */
    public void addHeadView(final View header) {
        post(new Thread() {
            @Override
            public void run() {
                mHeadLayout.addView(header);
            }
        });
    }

    /**
     * 设置刷新监听
     *
     * @param pullToRefreshListener
     */
    public void setPullToRefreshListener(PullToRefreshListener pullToRefreshListener) {
        this.pullToRefreshListener = pullToRefreshListener;
    }

    /**
     * 刷新结束
     */
    public void finishRefreshing() {
        if (mChildView != null) {
            mChildView.animate().translationY(0).start();
        }
        pullToRefreshListener.onFinishRefresh();
        isRefreshing = false;
    }

}
