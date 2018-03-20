package com.example.dong.github;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.dong.github.Util.AccountManage;
import com.example.dong.github.Util.DownloadManage;
import com.example.dong.github.Util.FileManage;
import com.zzhoujay.richtext.RichText;

import zlc.season.rxdownload3.core.DownloadConfig;

/**
 * Created by 振兴 on 2018.2.19.
 */

public class APP extends Application {
    private static Context context;
    private SharedPreferences SPLI;
    private SharedPreferences SPLUI;
    private SharedPreferences SPSI;

    public SharedPreferences getLoginInformationSP() {
        return SPLI;
    }
    public SharedPreferences getLoginUserInformationSp(){ return SPLUI; }
    public SharedPreferences getSettingInformation(){ return SPSI; }
    //WifiRequired 设置任务是否只允许在Wifi网络环境下进行下载。 默认值 false

    private boolean hasLogin = false;
    private String token;
    @SuppressLint("StaticFieldLeak")
    private static APP com;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        com = this;

        initView();
    }

    private void initAccountManage() {
        AccountManage.INSTANCE.setAccess_Token(SPLUI.getString("access_token",""));
        AccountManage.INSTANCE.setHasAccess_Token(SPLUI.getBoolean("has_access_token",false));

    }

    private void initSharedPreferences() {
        SPLI = getSharedPreferences("LoginInformation",Context.MODE_PRIVATE);
        SPLUI = getSharedPreferences("LoginUserInformation",Context.MODE_PRIVATE);
        SPSI = getSharedPreferences("SettingInformation",Context.MODE_PRIVATE);
    }

    private void initView() {
        initRichText();
        initSharedPreferences();
        initAccountManage();
        initGlide();
        initFileManage();
        DownloadManage.INSTANCE.InitDownloadManae(this);
    }

    private void initFileManage() {
        FileManage.INSTANCE.initFilePath();
    }

    private void initGlide() {
    }

    private void initRichText() {
        RichText.initCacheDir(this);
    }

    public static Context getContext(){
        return context;
    }

    public static APP getAPP(){
        if(com == null){
            com = new APP();
            return com;
        }
        return com;
    }

    public boolean isHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(boolean hasLogin) {
        this.hasLogin = hasLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
