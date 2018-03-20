package com.example.dong.github.View.Activity

import android.os.Bundle
import android.view.Window
import com.example.dong.github.Net.API.Data.RepositoriesIssues
import com.example.dong.github.Util.NetManage
import com.example.dong.github.R
import com.example.dong.github.View.Base.BaseActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_repositories_issues.*

class RepositoriesIssuesActivity : BaseActivity() {

    private var owner = ""
    private var name = ""
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_repositories_issues)

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
        RequestLoadMoreFirstPage()
        NetManage.getAPIService()
                .getRepositorisIssues(owner,name,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :Observer<List<RepositoriesIssues>>{
                    override fun onComplete() {
                        page = 2
                        IsRefresh(true)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: List<RepositoriesIssues>) {

                    }

                    override fun onError(e: Throwable) {
                        toast("网络连接超时")
                        IsRefresh(false)
                    }
                })
    }

    private fun initRecyclerView() {

    }

    private fun initSwipeRefreshLayout() {
        BindSwipeRefreshLayout(repositories_issues_SwipeRefreshLayout)
        repositories_issues_SwipeRefreshLayout.isEnabled = true
        repositories_issues_SwipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        NetManage.getAPIService()
                .getRepositorisIssues(owner,name,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :Observer<List<RepositoriesIssues>>{
                    override fun onComplete() {
                        page += 1
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: List<RepositoriesIssues>) {
                        if(t.size == 0){
                            RequestLoadMoreLastPage()
                        }else{
                            LoadAfter()
                        }
                    }

                    override fun onError(e: Throwable) {
                        RequestLoadMoreError()
                    }
                })
    }

    private fun LoadAfter() {

    }

    private fun initIntent() {
        owner = intent.getStringExtra("owner")
        name = intent.getStringExtra("name")
    }
}
