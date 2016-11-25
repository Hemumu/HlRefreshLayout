package com.helin.hlpullrefresh.view;

/**
 * Created by helin on 2016/11/22 16:30.
 */

public interface PullToRefreshListener {
    /**
     * 刷新中
     * @param refreshLayout
     */
    void onRefresh(RefreshLayout refreshLayout);

    /**
     * 开始刷新
     */
    void onStartRefresh();
    /**
     * 刷新完成
     */
    void onFinishRefresh();
}
