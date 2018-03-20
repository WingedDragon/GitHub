package com.example.dong.github.Util

import android.content.Context
import android.content.Intent
import com.example.dong.github.View.Activity.Web_AuthenticationActivity
import okhttp3.*

/**
 * Created by 振兴 on 2018.3.10.
 */
object AccountManage {

    val Client_ID = "6b4e7aad86723e741696"
    val Client_Secret = "a7fc8d2fcdcd870788ab4a10343ab01fa8c62795"
    val Scope = arrayListOf<String>("user","public_repo","repo","notifications")
    val Access_Token_Web = "https://github.com/login/oauth/access_token"
    var Code = ""
    var isLogin = false
    var hasLogin = false
    var hasAccess_Token = false
    var hasCode = false

    var Access_Token = ""
    var Login = ""

    fun getOAuth2URL() = "https://github.com/login/oauth/authorize?client_id=${Client_ID}&scope=${Scope}"

    fun Web_Authentication(context: Context){
        val intent = Intent(context, Web_AuthenticationActivity::class.java)
        context.startActivity(intent)
    }

    fun getAvailableAccessToken():String{
        if(hasAccess_Token){
            return Access_Token
        }else{
            return ""
        }
    }

    fun getAccess_Token_Call(): Call {
        val body = FormBody.Builder()
                .add("client_id", Client_ID)
                .add("client_secret", Client_Secret)
                .add("code", Code)
                .build()
        val request = Request.Builder()
                .url(Access_Token_Web)
                .post(body)
                .build()
        return NetManage.getOkhttp().newCall(request)
    }
}