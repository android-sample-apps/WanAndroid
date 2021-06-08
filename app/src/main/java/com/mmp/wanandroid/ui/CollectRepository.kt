package com.mmp.wanandroid.ui

import com.mmp.wanandroid.api.BaseResponse
import com.mmp.wanandroid.api.WanAndroidService
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData

object CollectRepository : BaseRepository() {
    private val wanAndroidService = WanAndroidService.create()

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
}