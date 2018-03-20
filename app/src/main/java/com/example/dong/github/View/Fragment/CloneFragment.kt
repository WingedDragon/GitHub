package com.example.dong.github.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.dong.github.APP
import com.example.dong.github.Adapter.CloneFragmentRecyclerViewAdapter
import com.example.dong.github.Adapter.RepositoriesFragmentRecyclerViewAdapter
import com.example.dong.github.MVP.contract.CloneFragmentContract
import com.example.dong.github.Net.API.Data.CloneRepositories
import com.example.dong.github.Net.API.Data.UserRepositories

import com.example.dong.github.R
import com.example.dong.github.Util.AccountManage
import com.example.dong.github.Util.NetManage
import com.example.dong.github.View.Activity.RepositoriesDetailsActivity
import com.example.dong.github.View.Base.BaseFragment
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_clone.view.*
import kotlinx.android.synthetic.main.fragment_repositories.view.*

class CloneFragment : BaseFragment(),CloneFragmentContract.View {

    private val adapter = CloneFragmentRecyclerViewAdapter(R.layout.clone_fragment_recyclerview_item)
    private var page = 1
    private lateinit var root_view:View

    private var isViewCreate = false
    private var isViewVisible = false
    private var isInitView = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        root_view = inflater?.inflate(R.layout.fragment_clone,container,false)!!
        isViewCreate = true
        InitView()

        return root_view
    }

    override fun InitView() {
        InitSwipeRefreshLayout(root_view.clone_fragment_SwipeRefreshLayout)
        InitRecyclerView(root_view.clone_fragment_RecyclerView)
//        InitData()
    }

    private fun InitData() {
        RequestLoadMoreFirstPage()
        if(AccountManage.hasLogin){
            page = 1
            IsRefresh(true)
            NetManage.getAPIService()
                    .getStarredRepositories(AccountManage.Login,"created","desc",page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : Observer<MutableList<CloneRepositories>> {
                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: MutableList<CloneRepositories>) {
                            adapter.setNewData(t)
                        }

                        override fun onError(e: Throwable) {
                            IsRefresh(false)
                            toast("网络连接超时")
                        }

                        override fun onComplete() {
                            page = 2
                            IsRefresh(false)
                        }
                    })
        }else{
            IsRefresh(false)
            toast("请先登录GitHub账户")
        }
    }

    private fun LoadData(){
        if(AccountManage.hasLogin){
            NetManage.getAPIService()
                    .getStarredRepositories(AccountManage.Login,"created","desc",page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(object :Observer<MutableList<CloneRepositories>>{
                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: MutableList<CloneRepositories>) {
                            if(t.size == 0){
                                RequestLoadMoreLastPage()
                            }
                        }

                        override fun onError(e: Throwable) {
                            RequestLoadMoreError()
                        }

                        override fun onComplete() {
                            page += 1
                            RequestLoadMoreComplete()
                        }
                    })
        }else{
            RequestLoadMoreComplete()
            toast("请先登录GitHub账户")
        }
    }

    private fun InitRecyclerView(t: RecyclerView) {
        BindRecyclerViewAdapter(adapter)
        t.addItemDecoration(DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL))
        t.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL,false)
        t.adapter = adapter
        adapter.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                LoadData()
            }
        },t)
        adapter.openLoadAnimation()
        adapter.setEnableLoadMore(true)
        adapter.isUpFetchEnable = false
        adapter.setOnItemClickListener { adapter, view, position ->
            val data = adapter.data[position] as CloneRepositories
            val intent = Intent(APP.getContext(), RepositoriesDetailsActivity::class.java)
            intent.putExtra("owner",data.owner.login)
                    .putExtra("name",data.name)
            startActivity(intent)
        }
    }

    private fun InitSwipeRefreshLayout(t: SwipeRefreshLayout) {
        BindSwipeRefreshLayout(t)
        t.isEnabled = true
        t.setOnRefreshListener {
            InitData()
        }
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewVisible = isVisibleToUser
        LazyLoad()
    }

    private fun LazyLoad() {
        if(isViewCreate && isViewVisible  && !isInitView){
            InitData()
            isInitView = true
        }
    }

}
