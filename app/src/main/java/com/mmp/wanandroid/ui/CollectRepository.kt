package com.mmp.wanandroid.ui

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object CollectRepository : BaseRepository() {

    suspend fun unCollectArticle(collectLiveData: StateLiveData<Any>, id: Int) =
        executeResp(collectLiveData) {
            wanAndroidService.unCollectArticle(id)
        }

    suspend fun collectArticle(collectLiveData: StateLiveData<Any>, id: Int) =
        executeResp(collectLiveData) {
            wanAndroidService.collectArticle(id)
        }

    suspend fun collectTools(collectLiveData: StateLiveData<Any>,name: String,link: String) = executeResp(collectLiveData){
        wanAndroidService.collectTools(name, link)
    }

    suspend fun unCollectTools(collectLiveData: StateLiveData<Any>,id: Int) = executeResp(collectLiveData){
        wanAndroidService.unCollectTools(id)
    }

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