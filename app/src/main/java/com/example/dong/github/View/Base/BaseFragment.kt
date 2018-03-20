package com.example.dong.github.View.Base

import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.dong.github.MVP.Base.MVPBaseFragmentView

/**
 * Created by 逗比振兴 on 2018.3.12.
 */

abstract class BaseFragment : Fragment(), MVPBaseFragmentView {

    private lateinit var s: SwipeRefreshLayout
    private lateinit var a: BaseQuickAdapter<*, *>

    abstract fun InitView()

    override fun toast(s: String) {
        Toast.makeText(activity.applicationContext,s, Toast.LENGTH_SHORT).show()
    }

    fun BindSwipeRefreshLayout(s:SwipeRefreshLayout){
        this.s = s
    }

    fun BindRecyclerViewAdapter(a: BaseQuickAdapter<*,*>){
        this.a = a
    }

    override fun RequestLoadMoreComplete(){
        a.loadMoreComplete()
    }

    override fun RequestLoadMoreLastPage(){
        a.setEnableLoadMore(false)
        a.loadMoreComplete()
        toast("这是最后一页")
    }

    override fun RequestLoadMoreFirstPage(){
        a.setEnableLoadMore(true)
    }

    override fun RequestLoadMoreError(){
        toast("网络连接超时")
    }

    override fun IsRefresh(boolean: Boolean){
        s.isRefreshing = boolean
    }

    override fun RefreshError() {
        IsRefresh(false)
        toast("网络连接超时")
    }
}
