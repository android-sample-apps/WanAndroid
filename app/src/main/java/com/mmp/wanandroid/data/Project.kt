package com.mmp.wanandroid.data

data class ProjectBean(val curPage: Int,val datas: List<Project>,val pageCount: Int){
    data class Project(val author: String,val chapterId: Int,val chapterName: String,
                       val collect: String,val desc: String,val envelopePic: String,
                       val id: Int,val link: String,val niceDate: String,val title: String)
}