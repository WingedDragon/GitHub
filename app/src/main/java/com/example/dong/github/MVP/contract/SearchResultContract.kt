package com.example.dong.github.MVP.contract

import com.example.dong.github.Net.API.Data.SearchResult
import com.example.dong.github.Adapter.SearchResultRecyclerViewAdapter
import com.example.dong.github.MVP.Base.MVPBaseView

/**
 * Created by 振兴 on 2018.2.27.
 */

interface SearchResultContract {
    interface Model{
    }

    interface View : MVPBaseView{
        fun update(searchResult: SearchResult)
        fun load(t: SearchResult)
        fun initRecyclerView()
        fun initSwipRefreshLayout()
    }

    interface Presenter{
        fun getSearchResult(key: String)
        fun getSearchResult(key: String, page: Int, per_page: Int, sort: String, order: String)
        fun load(searchResultRecyclerViewAdapter: SearchResultRecyclerViewAdapter, searchContent: String, page: Int, per_page: Int, sort: String, order: String)
    }
}
