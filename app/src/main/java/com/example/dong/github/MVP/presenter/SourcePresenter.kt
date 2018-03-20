package com.example.dong.github.MVP.presenter

import com.example.dong.github.Net.API.Data.ProjectIndex
import com.example.dong.github.MVP.contract.SourceContract
import com.example.dong.github.MVP.model.SourceModel
import com.example.dong.github.Util.NetManage
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by 振兴 on 2018.3.6.
 */

class SourcePresenter(private val view: SourceContract.View) : SourceContract.Presenter{

    val model = SourceModel()

    override fun initData(owner: String, name: String, path: String) {
        view.IsRefresh(true)
        NetManage.getAPIService()
                .getProjectIndex(owner, name, path)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :Observer<MutableList<ProjectIndex>>{
                    override fun onComplete() {
                        view.IsRefresh(false)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: MutableList<ProjectIndex>) {
                        Collections.sort(t,object: Comparator<ProjectIndex> {
                            override fun compare(p0: ProjectIndex, p1: ProjectIndex): Int {
                                return p0.itemType.compareTo(p1.itemType)
                        }})
                        view.initRecyclerView(t)
                    }

                    override fun onError(e: Throwable) {
                        view.toast("网络连接超时")
                    }
                })
    }

    override fun loadDir(owner: String, name: String, path: String, filename: String,backflag:Boolean) {
        view.IsRefresh(true)
        NetManage.getAPIService()
                .getProjectIndex(owner, name, "$path$filename")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :Observer<MutableList<ProjectIndex>>{
                    override fun onComplete() {
                        view.IsRefresh(false)
                        if(!backflag){
                            view.loadchangPath("$path$filename",0)
                        }else{
                            view.backchangPath()
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: MutableList<ProjectIndex>) {
                        Collections.sort(t,object: Comparator<ProjectIndex> {
                            override fun compare(p0: ProjectIndex, p1: ProjectIndex): Int {
                                return p0.itemType.compareTo(p1.itemType)
                            }})
                        view.updata(t)
                    }

                    override fun onError(e: Throwable) {
                        view.toast("网络连接超时")
                    }
                })
    }

    override fun loadFile(owner: String, name: String, path: String, fileName: String?) {
        view.loadFile(owner,name,"$path$fileName")
    }
}
