package com.mmp.wanandroid.ui.home.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.databinding.HomeRvItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder
import com.mmp.wanandroid.ui.web.WebActivity
import com.mmp.wanandroid.utils.COMPARATOR
import com.mmp.wanandroid.utils.start

class ArticleAdapter(private val context: Context) : PagingDataAdapter<Article, BindingViewHolder>(COMPARATOR){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding: HomeRvItemBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.home_rv_item,parent,false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val mArticle = getItem(position)
        val binding = DataBindingUtil.getBinding<HomeRvItemBinding>(holder.itemView)?.apply{
            article = mArticle
        }
        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            if (mArticle != null) {
                bundle.putString("url",mArticle.link)
            }
            if (mArticle != null) {
                bundle.putString("title",mArticle.title)
            }
            context.start<WebActivity>(bundle)
        }
        binding?.executePendingBindings()
    }

    //
//
//    override fun convert(holder: BaseDataBindingHolder<HomeRvItemBinding>, item: Article) {
//        holder.dataBinding?.apply {
//            article = item
//        }
//        holder.itemView.setOnClickListener{
//            val bundle = Bundle()
//            bundle.putString("url",item.link)
//            bundle.putString("title",item.title)
//            context.start<WebActivity>(bundle)
//        }
//    }


}




