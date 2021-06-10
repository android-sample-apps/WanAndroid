package com.mmp.wanandroid.data

import androidx.databinding.Bindable
import androidx.databinding.BaseObservable
import androidx.databinding.library.baseAdapters.BR
import java.io.Serializable


data class Article(val author: String, val chapterName: String, val link: String,
                   val niceShareDate: String, val shareUser: String, val superChapterName: String,
                   val title: String, val id: Int, val tags: List<Tag>,
                   val fresh: Boolean,val type: Int) : BaseObservable(),Serializable{

    @Bindable
    var collect: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.collect)
        }


}

data class ArticleData(val curPage: Int, val datas: List<Article>,val pageCount: Int,val total: Int)

data class Tag(
    val name: String,
    val url: String
)
