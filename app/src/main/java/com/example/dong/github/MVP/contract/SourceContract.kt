package com.example.dong.github.MVP.contract

import com.example.dong.github.Net.API.Data.ProjectIndex
import com.example.dong.github.MVP.Base.MVPBaseView

/**
 * Created by 振兴 on 2018.3.6.
 */

interface SourceContract {
    interface Model

    interface View : MVPBaseView {
        fun updata(t: MutableList<ProjectIndex>)
        fun initRecyclerView(t:MutableList<ProjectIndex>)
        fun loadchangPath(s: String, i: Int)
        fun backchangPath()
        fun loadFile(owner: String, name: String, path: String)
    }

    interface Presenter {
        fun initData(owner: String, name: String, path:String)
        fun loadDir(owner: String, name: String, path: String,filename:String,backflag:Boolean)
        fun loadFile(owner: String, name: String, path: String, fileName: String?)
    }
}
