package com.example.dong.github.MVP.Base

/**
 * Created by 振兴 on 2018.3.16.
 */
interface MVPBaseFragmentView {
    fun IsRefresh(boolean: Boolean)
    fun toast(s:String)
    fun RequestLoadMoreComplete()
    fun RequestLoadMoreLastPage()
    fun RequestLoadMoreFirstPage()
    fun RequestLoadMoreError()
    fun RefreshError()
}