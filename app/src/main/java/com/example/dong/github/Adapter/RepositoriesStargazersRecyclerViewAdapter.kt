package com.example.dong.github.Adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.dong.github.Net.API.Data.Stargazers
import com.example.dong.github.APP
import com.example.dong.github.R
import com.liji.circleimageview.CircleImageView

/**
 * Created by 振兴 on 2018.3.2.
 */
class RepositoriesStargazersRecyclerViewAdapter(layoutResId: Int): BaseQuickAdapter<Stargazers, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: Stargazers) {
        Glide.with(APP.getContext()).load(item.avatar_url).into(helper.getView(R.id.repositories_stargazers_RecyclerView_item_Picture) as CircleImageView)
        helper.setText(R.id.repositories_stargazers_RecyclerView_item_Owner,item.login)
    }
}