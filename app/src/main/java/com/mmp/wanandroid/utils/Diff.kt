package com.mmp.wanandroid.utils

import androidx.recyclerview.widget.DiffUtil
import com.mmp.wanandroid.data.Article

val COMPARATOR = object : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.collect == newItem.collect && oldItem.niceDate == newItem.niceDate
    }
}