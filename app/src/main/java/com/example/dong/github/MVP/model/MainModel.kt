package com.example.dong.github.MVP.model

import android.content.Context
import com.example.dong.github.APP
import com.example.dong.github.MVP.contract.MainContract
import com.example.dong.github.Util.AccountManage

/**
 * Created by 振兴 on 2018.2.18.
 */

class MainModel : MainContract.Model {
    override fun LoginError() {
        AccountManage.hasAccess_Token = false
        APP.getAPP().loginUserInformationSp.edit().putBoolean("has_access_token",false).commit()
    }

    override fun getAccess_Token(){
    }

    override fun getLogin_Code(context: Context) {
        AccountManage.Web_Authentication(context)
    }
}
