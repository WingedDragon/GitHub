package com.example.dong.github.View.Activity

import android.os.Bundle
import android.util.Base64
import android.view.Window
import android.widget.Toast
import com.example.dong.github.Net.API.Data.FileContent
import com.example.dong.github.View.Base.BaseActivity
import com.example.dong.github.MVP.contract.ReadFileContract
import com.example.dong.github.MVP.presenter.ReadFilePresenter
import com.example.dong.github.R
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_read_file.*

class ReadFileActivity : BaseActivity(),ReadFileContract.View {

    private val presenter = ReadFilePresenter(this)

    private var owner = ""
    private var name = ""
    private var path = ""

//    override fun openSwipeRefreshLayout() {
//        read_file_SwipeRefreshLayout.isRefreshing = true
//    }
//
//    override fun stopSwipeRefreshLayout() {
//        read_file_SwipeRefreshLayout.isRefreshing = false
//    }

    override fun update(f: FileContent) {
        RichText.fromMarkdown(String(Base64.decode(f.content,Base64.DEFAULT))).into(read_file_text)
    }

    override fun toast(s: String) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    override fun InitView() {
        initIntent()
//        initSwipeRefreshLayout()
        initData()
    }

//    private fun initSwipeRefreshLayout() {
//        read_file_SwipeRefreshLayout.isEnabled = true
//        read_file_SwipeRefreshLayout.setOnRefreshListener {
//            presenter.initData(owner, name, path)
//        }
//    }

    private fun initData() {
        presenter.initData(owner, name, path)
    }

    private fun initIntent() {
        owner = intent.getStringExtra("owner")
        name = intent.getStringExtra("name")
        path = intent.getStringExtra("path")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_read_file)

        InitView()
    }
}
