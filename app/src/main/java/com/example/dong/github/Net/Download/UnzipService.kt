package com.example.dong.github.Net.Download

import android.app.IntentService
import android.content.Intent
import android.util.Log

import com.example.dong.github.Util.FileManage

/**
 * Created by 振兴 on 2018.3.20.
 */

class UnzipService : IntentService("UnzipService") {

    override fun onHandleIntent(intent: Intent) {
        val zipFile = intent.getStringExtra("ZipFile")
        val targetDir = intent.getStringExtra("TargetDir")
        FileManage.Unzip(zipFile, targetDir)
        Log.d("service","1")
    }
}
