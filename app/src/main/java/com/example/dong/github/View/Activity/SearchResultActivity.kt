package com.example.dong.github.View.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.dong.github.Net.API.Data.SearchResult
import com.example.dong.github.View.Base.BaseActivity
import com.example.dong.github.Adapter.SearchResultRecyclerViewAdapter
import com.example.dong.github.MVP.contract.SearchResultContract
import com.example.dong.github.MVP.presenter.SearchResultPresenter
import com.example.dong.github.R
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : BaseActivity(),SearchResultContract.View {

    private val presenter = SearchResultPresenter(this)
    private val context:Context
    private val searchResultRecyclerViewAdapter:SearchResultRecyclerViewAdapter
    private var page = 1
    private val per_page = 10
    lateinit var searchContent:String

    init {
        context = this
        searchResultRecyclerViewAdapter = SearchResultRecyclerViewAdapter(R.layout.search_result_recyclerview_item)
    }

    override fun update(searchResult: SearchResult) {
        page = 2
        searchResultRecyclerViewAdapter.setNewData(searchResult.items)
    }

    override fun load(t: SearchResult) {
        page += 1
        searchResultRecyclerViewAdapter.addData(t.items)
    }

    override fun initRecyclerView() {
        search_result_recyclerView.adapter = searchResultRecyclerViewAdapter
        search_result_recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        searchResultRecyclerViewAdapter.isUpFetchEnable = false
        searchResultRecyclerViewAdapter.setEnableLoadMore(false)
        searchResultRecyclerViewAdapter.setPreLoadNumber(5)
        searchResultRecyclerViewAdapter.openLoadAnimation()
        search_result_recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        searchResultRecyclerViewAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this, RepositoriesDetailsActivity::class.java)
            val data = adapter.data as ArrayList<SearchResult.ItemsBean>
            intent.putExtra("owner",data[position].owner.login)
            intent.putExtra("name",data[position].name)
            startActivity(intent)
        }
        searchResultRecyclerViewAdapter.setOnLoadMoreListener(object :BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                presenter.load(searchResultRecyclerViewAdapter,searchContent,page,per_page,"","")
            }
        },search_result_recyclerView)
    }

    override fun initSwipRefreshLayout() {
        search_result_swipeRefreshLayout.isEnabled = true
        search_result_swipeRefreshLayout.setOnRefreshListener {
            page = 1
            presenter.getSearchResult(searchContent,page,per_page,"","")
        }
    }

    override fun InitView() {
        BindSwipeRefreshLayout(search_result_swipeRefreshLayout)
        BindRecyclerViewAdapter(searchResultRecyclerViewAdapter)
        initData()
        initSwipRefreshLayout()
        initRecyclerView()
        BindSwipeRefreshLayout(search_result_swipeRefreshLayout)
        BindRecyclerViewAdapter(searchResultRecyclerViewAdapter)
    }

    private fun initData() {
        IsRefresh(true)
        page = 1
        presenter.getSearchResult(searchContent,page,per_page,"","")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_search_result)

        searchContent = intent.getStringExtra("SearchContent")

        InitView()
    }
}
