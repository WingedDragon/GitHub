package com.example.dong.github.View.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.webkit.*
import android.widget.Toast
import com.example.dong.github.Util.AccountManage
import com.example.dong.github.R
import kotlinx.android.synthetic.main.activity_web__authentication.*

class Web_AuthenticationActivity : AppCompatActivity() {

    private val context:Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_web__authentication)

        initView()
    }

    private fun initView() {
        initIntent()
        initWebView()
        initData()
    }

    private fun initData() {
        web_authentication_WebView.loadUrl(AccountManage.getOAuth2URL())
    }

    private fun toast(string: String){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val setting = web_authentication_WebView.settings
        val client = object:WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                Log.d("request",request.url.toString())
                if(request.url.toString().contains("http://my.github.url/oauth2/callback?code=")){
                    AccountManage.Code = request.url.toString().removeRange(0,42)
                    AccountManage.hasCode = true
                    AccountManage.isLogin = true
                    val intent = Intent(context, MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                }
                return true
            }
        }
        setting.apply {
            javaScriptEnabled = true
            useWideViewPort = true //将图片调整到适合webview的大小
            loadWithOverviewMode = true // 缩放至屏幕的大小
            builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
            displayZoomControls = false //隐藏原生的缩放控件

            cacheMode = WebSettings.LOAD_NO_CACHE //关闭webview中缓存
            allowFileAccess = false //设置可以访问文件
            javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
            loadsImagesAutomatically = true //支持自动加载图片
            defaultTextEncodingName = "utf-8" //设置编码格式
        }

        web_authentication_WebView.webViewClient = client

    }

    private fun initIntent() {
    }

    override fun onDestroy() {
        web_authentication_WebView.clearCache(true)
        web_authentication_WebView.clearHistory()
        web_authentication_WebView.clearFormData()
        WebViewClearCache()
        super.onDestroy()
    }

    private fun WebViewClearCache() {
        CookieSyncManager.createInstance(this)
        CookieSyncManager.getInstance().startSync()//清除WebView有效
        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            removeSessionCookie()
            removeAllCookie()
        }
    }
}
