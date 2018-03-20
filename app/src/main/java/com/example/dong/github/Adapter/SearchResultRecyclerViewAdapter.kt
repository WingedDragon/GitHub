package com.example.dong.github.Adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.dong.github.Net.API.Data.SearchResult
import com.example.dong.github.APP
import com.example.dong.github.R
import com.liji.circleimageview.CircleImageView

/**
 * Created by 振兴 on 2018.2.28.
 */

class SearchResultRecyclerViewAdapter : BaseQuickAdapter<SearchResult.ItemsBean, BaseViewHolder> {

    constructor(layoutResId: Int) : super(layoutResId) {}

    override fun convert(helper: BaseViewHolder, item: SearchResult.ItemsBean) {
        Glide.with(APP.getContext()).load(item.owner.avatar_url).into(helper.getView(R.id.search_result_recyclerView_item_cardView_Picture) as CircleImageView)
        helper.apply {
            setText(R.id.search_result_recyclerView_item_cardView_Name, item.name.toString())
            setText(R.id.search_result_recyclerView_item_cardView_Stars, "${item.stargazers_count}")
            setText(R.id.search_result_recyclerView_item_cardView_Forks, "${item.forks_count}")
            setText(R.id.search_result_recyclerView_item_cardView_Owner, "${item.owner.login}")
        }
        if (item.description != null) {
            helper.setText(R.id.search_result_recyclerView_item_cardView_Detail, item.description.toString())
        } else {
            helper.setText(R.id.search_result_recyclerView_item_cardView_Detail, "无")
        }
    }
}
