package com.mmp.wanandroid.ui.system

import com.mmp.wanandroid.api.WanAndroidService
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.SystemTree
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object SystemRepository : BaseRepository(){

    private var page = 0

    private val wanAndroidService = WanAndroidService.create()

    suspend fun getTree(treeLiveData: StateLiveData<List<SystemTree>>) = executeResp(treeLiveData){
        wanAndroidService.getTree()
    }

    suspend fun getSystemArticle(articlesLiveData: StateLiveData<ArticleData>,cid: Int) = executeResp(articlesLiveData){
        page = 0
        wanAndroidService.getSystemArticle(page,cid)
    }

    suspend fun getMoreArticle(articlesLiveData: StateLiveData<ArticleData>, cid: Int) = executeResp(articlesLiveData){
        page++
        wanAndroidService.getSystemArticle(page,cid)
    }
}