package com.mmp.wanandroid.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Article

class ArticleAdapter : PagingDataAdapter<Article, ArticleAdapter.ViewHolder>(COMPARATOR) {

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.text_title)
        val author: TextView = view.findViewById(R.id.text_author)
        val sort: TextView = view.findViewById(R.id.text_sort)
        val time:TextView = view.findViewById(R.id.text_time)
        val textNew: TextView = view.findViewById(R.id.text_new)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null){
            if (article.fresh){
                holder.textNew.visibility = View.VISIBLE
            }
            holder.title.text = article.title
            if (article.author != ""){
                holder.author.text = "作者："+article.author
            }
            if (article.shareUser != ""){
                holder.author.text = "分享者："+article.shareUser
            }
            holder.sort.text = "分类: "+article.superChapterName + "/" + article.chapterName
            holder.time.text = "时间："+article.niceDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_rv_item,parent,false)
        return ViewHolder(view)
    }

}