package com.example.dong.github.Adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.dong.github.Net.API.Data.UserRepositories
import com.example.dong.github.APP
import com.example.dong.github.R
import com.liji.circleimageview.CircleImageView

/**
 * Created by 振兴 on 2018.3.12.
 */
class RepositoriesFragmentRecyclerViewAdapter(layoutResId: Int) : BaseQuickAdapter<UserRepositories, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: UserRepositories?) {
        Glide.with(APP.getContext()).load(item?.owner?.avatar_url ).into(helper?.getView(R.id.repositories_fragment_RecyclerView_item_CardView_Picture) as CircleImageView)
        helper.setText(R.id.repositories_fragment_RecyclerView_item_CardView_Name,item?.name)
                .setText(R.id.repositories_fragment_RecyclerView_item_CardView_Stars,"Stars:${item?.stargazers_count}")
                .setText(R.id.repositories_fragment_RecyclerView_item_CardView_Forks,"Forks:${item?.forks_count}")
                .setText(R.id.repositories_fragment_RecyclerView_item_CardView_Owner,"Owner:${item?.owner?.login}")
        helper.setText(R.id.repositories_fragment_RecyclerView_item_CardView_Detail, if(item?.description != null) item.description.toString() else "无")
    }
}