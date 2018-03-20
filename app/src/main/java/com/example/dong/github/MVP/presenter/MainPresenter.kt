package com.example.dong.github.MVP.presenter

import android.content.Context
import com.example.dong.github.Net.API.Data.LoginUserInformation
import com.example.dong.github.APP
import com.example.dong.github.MVP.contract.MainContract
import com.example.dong.github.MVP.model.MainModel
import com.example.dong.github.Util.AccountManage
import com.example.dong.github.Util.NetManage
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * Created by 振兴 on 2018.2.18.
 */

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter{

    private val model = MainModel()

    override fun getLogin_Code(context: Context) {
        model.getLogin_Code(context)
    }

    override fun Search(key: String) {
        view.CallSearchResultActivity(key)
    }

    override fun getAccess_Token() {
        AccountManage.getAccess_Token_Call()
                .enqueue(object :Callback{
                    override fun onResponse(call: Call?, response: Response?) {
                        val r = response?.body()?.string()
                        AccountManage.Access_Token = r?.split("&")!![0].removeRange(0,13)
                        AccountManage.hasAccess_Token = true
                        Login()
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        view.toast("网络连接超时")
                    }
                })
    }

    override fun Login() {
        NetManage.getAPIService()
                .getLoginUserInformation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object :Observer<LoginUserInformation>{
                    override fun onComplete() {
                        AccountManage.hasLogin = true
                        view.toast("登录成功")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: LoginUserInformation) {
                        view.Login(t)
                    }

                    override fun onError(e: Throwable) {
                        view.toast("登录超时")
                        model.LoginError()
                    }
                })
    }

    override fun saveLoginUserInformation(t: LoginUserInformation) {
        val sp = APP.getAPP().loginUserInformationSp.edit().apply {
            putString("login",t.login)
            putString("picture",t.avatar_url)
            putString("url",t.url)
            putString("access_token", AccountManage.Access_Token)
            putBoolean("has_access_token", AccountManage.hasAccess_Token)
        }.commit()
    }

    override fun saveSignOutInformation(){
        val sp = APP.getAPP().loginUserInformationSp.edit().apply {
            putBoolean("has_access_token", AccountManage.hasAccess_Token)
        }.commit()
    }
}
