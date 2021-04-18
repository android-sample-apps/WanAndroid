package com.mmp.wanandroid.data

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("author")val author: String,
    @SerializedName("chapterName")val chapterName: String,
    @SerializedName("link")val link: String,
    @SerializedName("niceDate")val niceDate: String,
    @SerializedName("shareUser")val shareUser: String,
    @SerializedName("superChapterName")val superChapterName: String,
    @SerializedName("title")val title: String,
    @SerializedName("id")val id: Int,
    @SerializedName("tags")val tags: List<Tag>,
    @SerializedName("type")val type: Int,
    @SerializedName("fresh")val fresh: Boolean)

data class Data(
    @SerializedName("curPage")val curPage: Int,
    @SerializedName("datas")val datas: List<Article>)

data class Tag(
    @SerializedName("name")val name: String,
    @SerializedName("url")val url: String
)
