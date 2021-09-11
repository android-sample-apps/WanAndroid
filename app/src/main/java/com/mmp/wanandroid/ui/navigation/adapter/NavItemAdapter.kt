package com.mmp.wanandroid.ui.navigation.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.databinding.NavItemRvItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder
import com.mmp.wanandroid.ui.web.WebActivity
import com.mmp.wanandroid.utils.start

class NavItemAdapter(private val context: Context) : ListAdapter<Article,BindingViewHolder>(COMPARATOR) {
    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil.inflate<NavItemRvItemBinding>(LayoutInflater.from(parent.context),
            R.layout.nav_item_rv_item,
            parent,
            false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val mArticle = getItem(position)
        (holder.binding as NavItemRvItemBinding).article = mArticle.title
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title",mArticle.title)
            bundle.putString("url",mArticle.link)
            context.start<WebActivity>(bundle)
        }
    }
}