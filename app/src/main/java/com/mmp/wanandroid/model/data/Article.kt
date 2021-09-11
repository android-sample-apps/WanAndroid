package com.mmp.wanandroid.model.data

import androidx.databinding.Bindable
import androidx.databinding.BaseObservable
import java.io.Serializable


data class Article(val author: String, val chapterName: String, val link: String,
                   val niceShareDate: String, val shareUser: String, val superChapterName: String,
                   val title: String, val id: Int, val tags: List<Tag>,
                   val fresh: Boolean, val type: Int, var collect: Boolean){
    val isTop: Boolean
    get() = type == 1

}

data class ArticleData(val curPage: Int, var datas: List<Article>, val pageCount: Int, val total: Int)

data class Tag(
    val name: String,
    val url: String
)
