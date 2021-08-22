package com.mmp.wanandroid.model.data

data class SystemTree(val children: List<SystemTree>,val id: Int,val name: String,val parentChapterId: Int)
