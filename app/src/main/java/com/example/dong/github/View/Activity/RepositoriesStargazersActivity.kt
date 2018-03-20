package com.example.dong.github.View.Activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.dong.github.Net.API.Data.Stargazers
import com.example.dong.github.Adapter.RepositoriesStargazersRecyclerViewAdapter
import com.example.dong.github.Util.NetManage
import com.example.dong.github.R
import com.example.dong.github.View.Base.BaseActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_repositories_stargazers.*

class RepositoriesStargazersActivity : BaseActivity() {

    private val adapter = RepositoriesStargazersRecyclerViewAdapter(R.layout.repositories_stargazers_recyclerview_item)
    private var owner = ""
    private var name = ""
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_repositories_stargazers)

        InitView()
    }

    override fun InitView() {
        initIntent()
        initSwipeRefreshLayout()
        initRecyclerView()
        initOnClickListener()
        initData()
    }

    private fun initIntent() {
        owner = intent.getStringExtra("owner")
        name = intent.getStringExtra("name")
    }

    private fun initOnClickListener() {
    }

    private fun initSwipeRefreshLayout() {
        BindSwipeRefreshLayout(repositories_stargazers_SwipeRefreshLayout)
        repositories_stargazers_SwipeRefreshLayout.isEnabled = true
        repositories_stargazers_SwipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    private fun initData(){
        IsRefresh(true)
        RequestLoadMoreFirstPage()
        NetManage.getAPIService()
                .getRepositoriesStargazers(owner,name,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :Observer<List<Stargazers>>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: List<Stargazers>) {
                        if(t.size == 0){
                            toast("无用户Started")
                        }else{
                            adapter.setNewData(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        toast("网络连接超时")
                        IsRefresh(false)
                    }

                    override fun onComplete() {
                        IsRefresh(false)
                        page = 2
                    }
                })
    }

    private fun loadData() {
        NetManage.getAPIService()
                .getRepositoriesStargazers(owner,name,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :Observer<List<Stargazers>>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: List<Stargazers>) {
                        if(t.size == 0){
                            RequestLoadMoreLastPage()
                        }else{
                            adapter.addData(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        RequestLoadMoreError()
                    }

                    override fun onComplete() {
                        RequestLoadMoreComplete()
                        page += 1
                    }
                })
    }

    private fun initRecyclerView() {
        BindRecyclerViewAdapter(adapter)
        repositories_stargazers_RecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        repositories_stargazers_RecyclerView.adapter = adapter
        repositories_stargazers_RecyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        adapter.openLoadAnimation()
        adapter.setOnItemClickListener { adapter, view, position ->
            toast(position.toString())
        }
        adapter.setOnLoadMoreListener(object :BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                loadData()
            }
        },repositories_stargazers_RecyclerView)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
