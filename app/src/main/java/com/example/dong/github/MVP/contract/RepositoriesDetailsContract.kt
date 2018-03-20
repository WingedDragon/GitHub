package com.example.dong.github.MVP.contract

import com.example.dong.github.Net.API.Data.RepositoriesDetails
import com.example.dong.github.MVP.Base.MVPBaseView
import retrofit2.http.Url

/**
 * Created by 振兴 on 2018.3.1.
 */

interface RepositoriesDetailsContract {
    interface Model

    interface View:MVPBaseView{
        fun update(t: RepositoriesDetails)
        fun initSwipRefreshLayout()
        fun initRecyclerView()
        fun initClickListener()
        fun isStaredSuccess(b: Boolean)
        fun deleteStaredSuccess(b:Boolean)
        fun setStaredable(b: Boolean)
    }

    interface Presenter{
        fun getRepositoriesDetails(owner:String,name:String)
        fun DownloadFile(owner: String,name: String, url: String)
        fun initButton(owner: String,name: String)
        fun StaredRepoisitors(owner: String, name: String)
        fun DeleteStaredRepoisitories(owner: String, name: String)
    }
}
