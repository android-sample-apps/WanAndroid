package com.mmp.wanandroid.ui

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object CollectRepository : BaseRepository() {

    suspend fun collectArticle(id: Int) = execute {
        wanAndroidService.collectArticle(id)
    }

    suspend fun unCollectArticle(id: Int) = execute{
        wanAndroidService.unCollectArticle(id)
    }

    suspend fun collectTools(name: String,link: String) = execute {
        wanAndroidService.collectTools(name,link)
    }

    suspend fun unCollectTools(id: Int) = execute {
        wanAndroidService.unCollectTools(id)
    }
}