package com.example.dong.github.View.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.Window
import android.widget.Toast
import com.example.dong.github.Net.API.Data.ReadMe
import com.example.dong.github.Util.NetManage
import com.example.dong.github.R
import com.zzhoujay.richtext.RichText
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_read_me.*

class ReadMeActivity : AppCompatActivity() {

    private var owner = ""
    private var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_read_me)

        initView()
    }

    private fun initView() {
        initIntent()
        initData()
    }

    private fun initIntent() {
        owner = intent.getStringExtra("owner")
        name = intent.getStringExtra("name")
    }

    private fun initData() {
        NetManage.getAPIService()
                .getReadMe(owner, name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object:Observer<ReadMe>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: ReadMe) {
                        Update(t)
                    }

                    override fun onError(e: Throwable) {
                        toast("获取ReadMe失败")
                    }

                    override fun onComplete() {
                    }
                })
    }

    private fun Update(t: ReadMe) {
        RichText.fromMarkdown(String(Base64.decode(t.content,Base64.DEFAULT)))
                .autoPlay(true)
                .clickable(true)
                .into(read_me_Text)
    }

    private fun toast(text: String) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
    }
}
