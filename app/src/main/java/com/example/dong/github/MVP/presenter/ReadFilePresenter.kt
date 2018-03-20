package com.example.dong.github.MVP.presenter

import com.example.dong.github.Net.API.Data.FileContent
import com.example.dong.github.MVP.contract.ReadFileContract
import com.example.dong.github.Util.NetManage
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by 振兴 on 2018.3.10.
 */

class ReadFilePresenter(private val view: ReadFileContract.View) : ReadFileContract.Presenter{

    override fun initData(owner:String,name:String,path: String){
//        view.openSwipeRefreshLayout()
        NetManage.getAPIService()
                .getFileContent(owner,name,path)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :Observer<FileContent>{
                    override fun onComplete() {
//                        view.stopSwipeRefreshLayout()
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: FileContent) {
                        view.update(t)
                    }

                    override fun onError(e: Throwable) {
                        view.toast("网络连接超时")
                    }
                })
    }

}
