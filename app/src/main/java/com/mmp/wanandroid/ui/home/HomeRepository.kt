package com.mmp.wanandroid.ui.home

import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mmp.wanandroid.api.WanAndroidService
import com.mmp.wanandroid.data.*
import com.mmp.wanandroid.room.HistoryKey
import com.mmp.wanandroid.room.MyRoomDatabase
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


object HomeRepository : BaseRepository(){

    private var pager = 0

    private var homePager = 0

    private val wanAndroidService = WanAndroidService.create()

    private val historyKeyDao = MyRoomDatabase.getDatabase().historyKeyDao()


    suspend fun getTopArticle(topLiveData: StateLiveData<List<Article>>) = executeResp(topLiveData){
        wanAndroidService.getTopArticle()
    }

    suspend fun getHomeArticle(homeArticleLiveData: StateLiveData<ArticleData>,page: Int) = executeResp(homeArticleLiveData){
        wanAndroidService.getHomeArticle(page)
    }


    suspend fun getBanner(bannerLiveData: StateLiveData<List<Banner>>) = executeResp(bannerLiveData){
        wanAndroidService.getBanner()
    }

    suspend fun getHotKey(hotKeyLiveData: StateLiveData<List<HotKey>>) = executeResp(hotKeyLiveData){
        wanAndroidService.getHotKey()
    }

    suspend fun getSearch(articleLiveData: StateLiveData<ArticleData>,k: String) = executeResp(articleLiveData){
        pager = 0
        wanAndroidService.getSearchArticle(pager,k)
    }

    suspend fun getSearchMore(articleLiveData: StateLiveData<ArticleData>, k: String) = executeResp(articleLiveData){
        wanAndroidService.getSearchArticle(pager++,k)
    }

    fun getKeyList() = historyKeyDao.getHistoryKeyList()

    suspend fun addKey(historyKey: HistoryKey) =  historyKeyDao.addKey(historyKey)

    suspend fun clean() = historyKeyDao.deleteAll()


    fun getCacheList(): List<Article> {
        return LocalSource.getCache<Article>(Const.HOME_ARTICLE) ?: emptyList()
    }

    fun putCache(value: List<Article>){
        LocalSource.putCache(value)
    }
}