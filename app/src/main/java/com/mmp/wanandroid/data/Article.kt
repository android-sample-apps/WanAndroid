package com.mmp.wanandroid.data



data class Article(val author: String, val chapterName: String, val link: String,
                   val niceDate: String, val shareUser: String, val superChapterName: String,
                   val title: String, val id: Int, val tags: List<Tag>,
                   val type: Int, val fresh: Boolean,val collect: Boolean)

data class ArticleData(val curPage: Int, val datas: List<Article>,val pageCount: Int)

data class Tag(
    val name: String,
    val url: String
)
