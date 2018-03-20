package com.example.dong.github.MVP.presenter

import android.util.Log
import com.example.dong.github.Net.API.Data.RepositoriesDetails
import com.example.dong.github.MVP.contract.RepositoriesDetailsContract
import com.example.dong.github.MVP.model.RepositoriesDetailsModel
import com.example.dong.github.Util.DownloadManage
import com.example.dong.github.Util.FileManage
import com.example.dong.github.Util.NetManage
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by 振兴 on 2018.3.1.
 */

class RepositoriesDetailsPresenter(private val view: RepositoriesDetailsContract.View) : RepositoriesDetailsContract.Presenter{
    override fun DeleteStaredRepoisitories(owner: String, name: String) {
        NetManage.getAPIService()
                .deleteStaredRepositors(owner,name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe{
                    if(it.code() == 204){
                        view.deleteStaredSuccess(true)
                    }else if(it.code() == 404){
                        view.setStaredable(false)
                    }
                }
    }

    override fun initButton(owner: String,name: String) {
        NetManage.getAPIService()
                .isStaredRepositors(owner,name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe{
                    if(it.code() == 204){
                        view.setStaredable(false)
                    }else if(it.code() == 404){
                        view.setStaredable(true)
                    }
                }
    }

    override fun StaredRepoisitors(owner: String, name: String) {
        NetManage.getAPIService()
                .StaredRepositors(owner,name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :Observer<Response<ResponseBody>>{
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Response<ResponseBody>) {
                        if(t.code() == 204){
                            view.isStaredSuccess(true)
                        }else if (t.code() == 404){
                            view.isStaredSuccess(false)
                        }
                    }

                    override fun onError(e: Throwable) {
                        view.toast("网络连接超时")
                    }
                })
    }

    val model = RepositoriesDetailsModel()

    override fun getRepositoriesDetails(owner: String, name: String) {
        NetManage.getAPIService()
                .getRepositoriesDetails(owner,name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :Observer<RepositoriesDetails>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: RepositoriesDetails) {
                        view.update(t)
                    }

                    override fun onError(e: Throwable) {
                        view.IsRefresh(false)
                        view.toast("网络连接超时")
                    }

                    override fun onComplete() {
                        view.IsRefresh(false)
                    }
                })
    }

    override fun DownloadFile(owner: String, name: String, url: String) {
        val savePath = FileManage.BasePath
        DownloadManage.AddDownloadTask(url,"$owner&$name.zip",savePath)
    }
}