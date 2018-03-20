package com.example.dong.github.MVP.presenter

import com.example.dong.github.Net.API.Data.SearchResult
import com.example.dong.github.Adapter.SearchResultRecyclerViewAdapter
import com.example.dong.github.MVP.contract.SearchResultContract
import com.example.dong.github.MVP.model.SearchResultModel
import com.example.dong.github.Util.NetManage
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by 振兴 on 2018.2.27.
 */

class SearchResultPresenter(private val view: SearchResultContract.View) : SearchResultContract.Presenter{

    val model = SearchResultModel()

    override fun load(searchResultRecyclerViewAdapter: SearchResultRecyclerViewAdapter, searchContent: String, page: Int, per_page: Int, sort: String, order: String) {
        NetManage.getAPIService()
                .getSearchRepositoriesResult(searchContent,page, per_page, sort, order)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<SearchResult> {
                    override fun onComplete() {
                        view.RequestLoadMoreComplete()
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: SearchResult) {
                        if(t.items.size == 0 ){
                            view.RequestLoadMoreLastPage()
                        }else{
                            view.load(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.RequestLoadMoreError()
                    }
                })
    }

    override fun getSearchResult(key:String){
        view.RequestLoadMoreFirstPage()
        NetManage.getAPIService()
                .getSearchRepositoriesResult(key,1,10,"","")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<SearchResult> {
                    override fun onComplete() {
                        view.IsRefresh(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: SearchResult) {
                        if(t.total_count == 0){
                            view.toast("未搜索到相应结果")
                        }else{
                            view.update(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.IsRefresh(false)
                    }
                })
    }

    override fun getSearchResult(key:String,page:Int,per_page:Int,sort:String,order:String){
        view.RequestLoadMoreFirstPage()
        NetManage.getAPIService()
                .getSearchRepositoriesResult(key,page,per_page,sort,order)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<SearchResult> {
                    override fun onComplete() {
                        view.IsRefresh(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: SearchResult) {
                        if(t.total_count == 0){
                            view.toast("未搜索到相应结果")
                        }else{
                            view.update(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        view.IsRefresh(false)
                        view.toast("网络连接超时")
                    }
                })
    }
}
