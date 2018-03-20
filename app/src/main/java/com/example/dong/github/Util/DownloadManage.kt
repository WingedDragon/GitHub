package com.example.dong.github.Util

import com.example.dong.github.APP
import com.example.dong.github.Net.Download.UnzipExtension
import com.example.dong.github.Net.Download.UnzipService
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloadQueueSet
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.services.DownloadMgrInitialParams
import com.liulishuo.filedownloader.services.FileDownloadService
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.DownloadConfig
import zlc.season.rxdownload3.core.Mission
import zlc.season.rxdownload3.core.Status
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by 振兴 on 2018.3.15.
 */
object DownloadManage {

    val mission_list = ArrayList<Mission>()

    fun InitDownloadManae(app:APP): DownloadMgrInitialParams.InitCustomMaker? {
        val builder = DownloadConfig.Builder.create(app)
                .setFps(20)                         //设置更新频率
                .enableAutoStart(true)              //自动开始下载
                .setDefaultPath("custom download path")     //设置默认的下载地址
                .enableDb(true)                             //启用数据库
                //                .setDbActor(CustomSqliteActor(this))        //自定义数据库
                .enableService(true)                        //启用Service
                .enableNotification(true)              //启用Notification
//                .setNotificationFactory(NotificationFactoryImpl()) 	    //自定义通知
//                .setOkHttpClientFacotry(OkHttpClientFactoryImpl()) 	    //自定义OKHTTP
//                .addExtension(ApkInstallExtension::class.java)          //添加扩展
                .addExtension(UnzipExtension::class.java)

        DownloadConfig.init(builder)
        RxDownload.startAll()

       return FileDownloader.setupOnApplicationOnCreate(app)
    }

    fun AddDownloadTask(url: String,fileName:String,path: String){
        val mission = Mission(url,fileName,path)
        RxDownload.create(mission).observeOn(Schedulers.io()).subscribe {
            if(it.downloadSize == it.totalSize){
//                FileManage.Unzip(path + fileName,path)
                APP.getContext().toast("完成")
            }
        }
        mission_list.add(mission)
    }

}
