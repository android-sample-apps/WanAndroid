package com.mmp.wanandroid.ui.home


import com.mmp.wanandroid.model.data.*
import com.mmp.wanandroid.model.loacl.room.HistoryKey
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData


object HomeRepository : BaseRepository() {

    private var pager = 0

    private var homePager = 0

    private val historyKeyDao = database.historyKeyDao()

    suspend fun getHotKey(hotKeyLiveData: StateLiveData<List<HotKey>>) =
        executeResp(hotKeyLiveData) {
            wanAndroidService.getHotKey()
        }

    suspend fun getSearch(articleLiveData: StateLiveData<ArticleData>, k: String) =
        executeResp(articleLiveData) {
            pager = 0
            wanAndroidService.getSearchArticle(pager, k)
        }

    suspend fun getSearchMore(articleLiveData: StateLiveData<ArticleData>, k: String) =
        executeResp(articleLiveData) {
            wanAndroidService.getSearchArticle(pager++, k)
        }

    fun getKeyList() = historyKeyDao.getHistoryKeyList()

    suspend fun addKey(historyKey: HistoryKey) = historyKeyDao.addKey(historyKey)

    suspend fun clean() = historyKeyDao.deleteAll()


    suspend fun getHomeArticle(page: Int) = execute {
        wanAndroidService.getHomeArticle(page)
    }

    suspend fun getTopArticle() = execute {
        wanAndroidService.getTopArticle()
    }

    suspend fun getBanner() = execute {
        wanAndroidService.getBanner()
    }
}