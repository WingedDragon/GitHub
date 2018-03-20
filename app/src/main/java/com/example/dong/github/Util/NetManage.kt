package com.example.dong.github.Util

import android.util.Log
import com.example.dong.github.Net.API.Service.APIService
import com.example.dong.github.APP
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by 振兴 on 2018.2.21.
 */
object NetManage {
    private val client:OkHttpClient
    private val retrofit:Retrofit
    private val cache = Cache(APP.getContext().cacheDir,60 * 60 * 24)

    private val Base_URL_API = "https://api.github.com/"
    private val Base_URL_WWW = "https://github.com/"

    init {
        val httpinterceptor = HttpLoggingInterceptor()
        httpinterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(5,TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .addInterceptor(httpinterceptor)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val httpurl = original.url()
                            .newBuilder()
//                                .addQueryParameter("client_id","6b4e7aad86723e741696")
//                                .addQueryParameter("client_secret","a7fc8d2fcdcd870788ab4a10343ab01fa8c62795")
                            .addQueryParameter("access_token", AccountManage.getAvailableAccessToken())
                            .build()
                    val request = original.newBuilder()
                            .url(httpurl)
                            .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                            .addHeader("UserAgent","GitCode")
                            .build()
                    chain.proceed(request)
                }
                .addNetworkInterceptor (object:Interceptor{
                    override fun intercept(chain: Interceptor.Chain?): Response {
                        val originalResponseBody = chain?.proceed(chain.request())
                        return originalResponseBody?.newBuilder()!!.build()
                    }
                })
                .cache(cache)
                .build()
        retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(Base_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun getOkhttp():OkHttpClient{
        return client
    }

    fun getRetrofit(): Retrofit {
        return retrofit
    }

    fun getAPIService(): APIService {
        return retrofit.create(APIService::class.java)
    }
}