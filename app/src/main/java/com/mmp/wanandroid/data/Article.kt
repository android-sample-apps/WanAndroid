package com.mmp.wanandroid.data



data class Article(val author: String, val chapterName: String, val link: String,
                   val niceDate: String, val shareUser: String, val superChapterName: String,
                   val title: String, val id: Int, val tags: List<Tag>,
                   val type: Int, val fresh: Boolean)

data class Data(val curPage: Int, val datas: List<Article>)

data class Tag(
    val name: String,
    val url: String
)
