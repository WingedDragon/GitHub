package com.example.dong.github.MVP.contract

import android.content.Context
import com.example.dong.github.Net.API.Data.LoginUserInformation
import com.example.dong.github.MVP.Base.MVPBaseView

/**
 * Created by 振兴 on 2018.2.18.
 */

interface MainContract {
    interface Model{
        fun getLogin_Code(context: Context)
        fun getAccess_Token()
        fun LoginError()
    }

    interface View : MVPBaseView{
        fun CallSearchResultActivity(t: String)
        fun Login(t: LoginUserInformation)
    }

    interface Presenter{
        fun getLogin_Code(context: Context)
        fun Search(key: String)
        fun getAccess_Token()
        fun Login()
        fun saveLoginUserInformation(t: LoginUserInformation)
        fun saveSignOutInformation()
    }
}
