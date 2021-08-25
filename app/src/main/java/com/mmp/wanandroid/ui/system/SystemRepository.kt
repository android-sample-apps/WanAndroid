package com.mmp.wanandroid.ui.system

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.model.data.SystemTree
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object SystemRepository : BaseRepository(){

    suspend fun getTree() = execute {
        wanAndroidService.getTree()
    }

    suspend fun getSystemArticle(page: Int,cid: Int) = execute {
        wanAndroidService.getSystemArticle(page, cid)
    }
}