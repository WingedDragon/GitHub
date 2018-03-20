package com.example.dong.github.MVP.contract

import com.example.dong.github.Net.API.Data.FileContent

/**
 * Created by 振兴 on 2018.3.10.
 */

interface ReadFileContract {
    interface Model

    interface View {
//        fun openSwipeRefreshLayout()
//        fun stopSwipeRefreshLayout()
        fun update(f:FileContent)
        fun toast(s: String)
    }

    interface Presenter {
        fun initData(owner: String, name: String, path: String)
    }
}
