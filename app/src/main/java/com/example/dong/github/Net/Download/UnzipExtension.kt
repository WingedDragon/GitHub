package com.example.dong.github.Net.Download

import android.content.Context
import android.content.Intent
import android.util.Log
import io.reactivex.Maybe
import zlc.season.rxdownload3.core.DownloadConfig
import zlc.season.rxdownload3.core.RealMission
import zlc.season.rxdownload3.extension.Extension

/**
 * Created by 振兴 on 2018.3.20.
 */
class UnzipExtension:Extension {

    lateinit var mission: RealMission
    lateinit var context: Context

    override fun init(mission: RealMission) {
        this.mission = mission
        this.context = DownloadConfig.context!!
    }

    override fun action(): Maybe<Any> {
        var flag = false
        Log.d("EXT","1")
        val intent = Intent(context,UnzipService::class.java)
        intent.putExtra("ZipFile",mission.getFile()?.path)
        intent.putExtra("TargetDir",mission.getFile()?.parent)
        context.startService(intent)
        Log.d("EXT","2")
        return Maybe.create<Any>{
            it.onSuccess(1)
        }
    }
}