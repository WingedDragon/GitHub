package com.example.dong.github.MVP.Base

import android.support.v4.widget.SwipeRefreshLayout

/**
 * Created by 振兴 on 2018.3.16.
 */
interface MVPBaseView {
    fun IsRefresh(boolean: Boolean)
    fun toast(s:String)
    fun RequestLoadMoreComplete()
    fun RequestLoadMoreLastPage()
    fun RequestLoadMoreFirstPage()
    fun RequestLoadMoreError()
    fun RefreshError()
}