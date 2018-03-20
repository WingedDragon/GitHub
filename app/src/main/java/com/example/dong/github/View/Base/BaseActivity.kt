package com.example.dong.github.View.Base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.dong.github.MVP.Base.MVPBaseView
import java.lang.reflect.Type

/**
 * Created by 振兴 on 2018.2.8.
 */
abstract class BaseActivity: AppCompatActivity() ,MVPBaseView {

    private lateinit var s: SwipeRefreshLayout
    private lateinit var a: BaseQuickAdapter<*,*>

    abstract fun InitView()

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

    override fun toast(s: String) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }
}
