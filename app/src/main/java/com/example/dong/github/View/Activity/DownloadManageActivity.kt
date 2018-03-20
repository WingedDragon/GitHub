package com.example.dong.github.View.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.example.dong.github.R
import com.example.dong.github.Util.DownloadManage
import com.example.dong.github.View.Base.BaseActivity
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.util.FileDownloadUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_download_manage.*
import org.jetbrains.anko.downloadManager
import zlc.season.rxdownload3.RxDownload
import javax.net.ssl.SSLServerSocket

class DownloadManageActivity : BaseActivity() ,Observer<Int>{
    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: Int) {
    }

    override fun onError(e: Throwable) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_download_manage)

        InitView()
    }

    override fun InitView() {
        initProgressBar()
        initData()
    }

    private fun initData() {
    }

    private fun initProgressBar() {
        download_ProgressBar.max = 100
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
