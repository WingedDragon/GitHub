package com.example.dong.github.Util

import android.Manifest
import android.app.Activity
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 振兴 on 2018.3.15.
 */
object PermissionManage {

    var hasWriteStorage = false

    fun SinglePermissionRequest(activity: Activity,permission:String): Observable<Boolean> {
        val rxPermissions = RxPermissions(activity)
        return rxPermissions.request(permission)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun MultiPermissionRequest(activity: Activity,vararg permissions:String): Observable<Permission> {
        val rxPermissions = RxPermissions(activity)
        return rxPermissions.requestEach(*permissions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}