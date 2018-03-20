package com.example.dong.github.View.Activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.dong.github.Net.API.Data.RepositoriesEvents
import com.example.dong.github.Adapter.RepositoriesEventsRecyclerViewAdapter
import com.example.dong.github.Util.NetManage
import com.example.dong.github.R
import com.example.dong.github.View.Base.BaseActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_repositories_events.*

class RepositoriesEventsActivity : BaseActivity() {

    private var owner = ""
    private var name = ""
    private var page = 1
    private val adapter = RepositoriesEventsRecyclerViewAdapter(R.layout.repositories_events_recyclerview_item)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_repositories_events)

        InitView()
    }

    override fun InitView() {
        initIntent()
        initSwipeRefreshLayout()
        initRecyclerView()
        initData()
    }

    private fun initData() {
        IsRefresh(true)
        NetManage.getAPIService()
                .getRepositorisEvents(owner,name,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :Observer<List<RepositoriesEvents>>{
                    override fun onComplete() {
                        page = 2
                        repositories_events_SwipeRefreshLayout.isRefreshing = false
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: List<RepositoriesEvents>) {
                        adapter.setNewData(t)
                    }

                    override fun onError(e: Throwable) {
                        toast("网络连接超时")
                        IsRefresh(false)
                    }
                })
    }

    private fun initRecyclerView() {
        BindRecyclerViewAdapter(adapter)
        repositories_events_RecyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        repositories_events_RecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        repositories_events_RecyclerView.adapter = adapter
        adapter.openLoadAnimation()
        adapter.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                loadData()
            }
        },repositories_events_RecyclerView)
    }

    private fun initIntent() {
        owner = intent.getStringExtra("owner")
        name = intent.getStringExtra("name")
    }

    private fun initSwipeRefreshLayout() {
        BindSwipeRefreshLayout(repositories_events_SwipeRefreshLayout)
        repositories_events_SwipeRefreshLayout.isEnabled = true
        repositories_events_SwipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    private fun loadData() {
        NetManage.getAPIService()
                .getRepositorisEvents(owner, name, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object:Observer<List<RepositoriesEvents>>{
                    override fun onComplete() {
                        page += 1
                        RequestLoadMoreComplete()
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: List<RepositoriesEvents>) {
                        if(t.size == 0){
                            RequestLoadMoreLastPage()
                        }else {
                            adapter.addData(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        RequestLoadMoreError()
                    }
                })
    }
}
