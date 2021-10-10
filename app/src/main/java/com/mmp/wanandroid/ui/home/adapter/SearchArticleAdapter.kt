package com.mmp.wanandroid.ui.home.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.databinding.HomeRvItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder
import com.mmp.wanandroid.ui.web.WebActivity
import com.mmp.wanandroid.utils.SPreference
import com.mmp.wanandroid.utils.start
import com.mmp.wanandroid.utils.toast


class SearchArticleAdapter(private val context: Context) : ListAdapter<Article,BindingViewHolder>(COMPARATOR) {

    private var mOnCollectListener: OnCollectListener? = null

    fun setOnCollectListener(onCollectListener: OnCollectListener){
        mOnCollectListener = onCollectListener
    }

    private val isLogin by SPreference("login_state",false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil.inflate<HomeRvItemBinding>(LayoutInflater.from(parent.context), R.layout.home_rv_item,parent,false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val mArticle = getItem(position)
        val binding = holder.binding as HomeRvItemBinding
        binding.article = mArticle
        binding.heart.setOnClickListener {
            if (isLogin){
                if (mArticle.collect){
                    mArticle.collect = false
                    mOnCollectListener?.unCollect(mArticle)
                }else{
                    mArticle.collect = true
                    mOnCollectListener?.onCollect(mArticle)
                }
            }else{
                toast("请先登录")
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
            context.start<WebActivity>(bundle)
        }
        binding.executePendingBindings()
    }

    override fun submitList(list: MutableList<Article>?) {
        super.submitList(if (list == null) list else mutableListOf<Article>().apply {
            addAll(list)
        })
    }

    companion object {
       val COMPARATOR = object : DiffUtil.ItemCallback<Article>() {

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return  oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.collect == newItem.collect && oldItem.niceShareDate == newItem.niceShareDate
            }
        }
    }

    interface OnCollectListener{
        fun onCollect(article: Article)

        fun unCollect(article: Article)
    }
}