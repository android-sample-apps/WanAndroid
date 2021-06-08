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
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.databinding.HomeRvItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder
import com.mmp.wanandroid.ui.home.view.HomeFragment
import com.mmp.wanandroid.ui.web.WebActivity
import com.mmp.wanandroid.utils.start
import com.mmp.wanandroid.utils.toast

class ArticleAdapter(private val fragment: Fragment) : PagingDataAdapter<Article, BindingViewHolder>(COMPARATOR){


    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<Article>() {

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return  oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding: HomeRvItemBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.home_rv_item,parent,false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val mArticle = getItem(position)
        val binding = (holder.binding) as HomeRvItemBinding
        binding.article = mArticle
        binding.heart.setOnClickListener {
            if (mArticle != null) {
                if (mArticle.collect){
                    (fragment as HomeFragment).viewModel.unCollect(mArticle.id)
                    mArticle.collect = false
                }else{
                    (fragment as HomeFragment).viewModel.collect(mArticle.id)
                    mArticle.collect = true
                }
            }
        }
        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            if (mArticle != null) {
                bundle.putString("url",mArticle.link)
            }
            if (mArticle != null) {
                bundle.putString("title",mArticle.title)
            }
            fragment.activity?.start<WebActivity>(bundle)
        }

        binding.executePendingBindings()
    }




}




