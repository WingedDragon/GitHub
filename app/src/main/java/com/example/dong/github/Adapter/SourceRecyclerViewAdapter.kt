package com.example.dong.github.Adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.dong.github.Net.API.Data.ProjectIndex
import com.example.dong.github.R

/**
 * Created by 振兴 on 2018.3.7.
 */
class SourceRecyclerViewAdapter(data: MutableList<ProjectIndex>?) : BaseMultiItemQuickAdapter<ProjectIndex, BaseViewHolder>(data) {

    init {
        addItemType(0, R.layout.source_recyclerview_dir_item)
        addItemType(1,R.layout.source_recyclerview_file_item)
    }

    override fun convert(helper: BaseViewHolder, item: ProjectIndex) {
        when(helper.itemViewType){
            0 -> {
                helper.setText(R.id.source_RecyclerView_dir_item_Name,item.name)
                helper.setText(R.id.source_RecyclerView_dir_item_Message,item.sha)
            }
            1 -> {
                helper.setText(R.id.source_RecyclerView_file_item_Name,item.name)
                helper.setText(R.id.source_RecyclerView_file_item_Message,item.sha)
            }
        }
    }
}