package com.mmp.wanandroid.ui.home.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.databinding.HomeRvItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder
import com.mmp.wanandroid.ui.home.view.HomeFragment
import com.mmp.wanandroid.ui.web.WebActivity
import com.mmp.wanandroid.utils.start
import com.mmp.wanandroid.utils.toast

class ArticleAdapter : BaseQuickAdapter<Article,BaseDataBindingHolder<HomeRvItemBinding>>(R.layout.home_rv_item){
    override fun convert(holder: BaseDataBindingHolder<HomeRvItemBinding>, item: Article) {
        val binding = holder.dataBinding
        binding?.article = item
//        binding?.heart?.setOnClickListener {
//            if (item.collect){
//                mOnCollectListener?.unCollect(mArticle)
//                mArticle.collect = false
//            }else{
//                mOnCollectListener?.onCollect(mArticle)
//                mArticle.collect = true
//            }
//        }
        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            if (item != null) {
                bundle.putString("url",item.link)
            }
            if (item != null) {
                bundle.putString("title",item.title)
            }
            context.start<WebActivity>(bundle)
        }
    }

}




