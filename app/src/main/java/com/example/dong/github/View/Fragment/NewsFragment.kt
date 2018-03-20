package com.example.dong.github.View.Fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.dong.github.R
import com.example.dong.github.View.Base.BaseFragment
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsFragment : BaseFragment() {

    private lateinit var root_view:View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        root_view = inflater?.inflate(R.layout.fragment_news,container,false)!!
        InitView()

        return root_view
    }

    override fun InitView() {
        InitSwipeRefreshLayout(root_view.news_fragment_SwipeRefreshLayout)
        InitRecyclerView(root_view.news_fragment_RecyclerView)
        InitData()
    }

    private fun InitData() {

    }

    private fun InitSwipeRefreshLayout(t: SwipeRefreshLayout) {
        BindSwipeRefreshLayout(t)
        t.isEnabled = true
        t.setOnRefreshListener {

        }
    }

    private fun InitRecyclerView(t: RecyclerView) {
        t.addItemDecoration(DividerItemDecoration(this.activity,DividerItemDecoration.VERTICAL))
        t.layoutManager = LinearLayoutManager(this.activity,LinearLayoutManager.VERTICAL,false)
    }

}
