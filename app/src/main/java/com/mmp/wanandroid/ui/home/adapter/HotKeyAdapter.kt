package com.mmp.wanandroid.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.HotKey
import com.mmp.wanandroid.databinding.SearchKeyRvItemBinding

class HotKeyAdapter : BaseQuickAdapter<HotKey,BaseDataBindingHolder<SearchKeyRvItemBinding>>(R.layout.search_key_rv_item) {

    override fun convert(holder: BaseDataBindingHolder<SearchKeyRvItemBinding>, item: HotKey) {
        holder.dataBinding?.apply {
            key = item
        }
    }

}