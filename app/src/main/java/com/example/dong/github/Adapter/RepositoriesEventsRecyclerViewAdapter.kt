package com.example.dong.github.Adapter

import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.dong.github.Net.API.Data.RepositoriesEvents
import com.example.dong.github.APP
import com.example.dong.github.R
import com.liji.circleimageview.CircleImageView
import com.orhanobut.logger.Logger

/**
 * Created by 振兴 on 2018.3.3.
 */
class RepositoriesEventsRecyclerViewAdapter(layoutResId: Int) : BaseQuickAdapter<RepositoriesEvents, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: RepositoriesEvents) {
        Glide.with(APP.getContext()).load(item.actor.avatar_url).into(helper.getView(R.id.repositories_events_recyclerView_item_Picture) as CircleImageView)
        helper.apply {
            setText(R.id.repositories_events_recyclerView_item_Text,when(item.type){
                "ForkEvent" -> "${transform(item.actor.login)} Forked ${transform(item.repo.name)} to ${transform(item.payload.forkee.full_name)}"
                "WatchEvent" -> "${transform(item.actor.login)} Stared ${transform(item.repo.name)}"
                else -> ""
            })
            setText(R.id.repositories_events_recyclerView_item_Date,"${item.created_at.substring(0,9)} ${item.created_at.substring(11,19)}")
        }
    }

    private fun transform(string: String): SpannableString {
        val text = SpannableString(string)
        text.setSpan(object:ClickableSpan(){
            override fun onClick(p0: View?) {
                Toast.makeText(APP.getContext(),"跳转",Toast.LENGTH_SHORT).show()
                Logger.d("跳转")
            }
        },0,string.length,SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        return text
    }
}