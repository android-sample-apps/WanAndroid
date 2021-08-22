package com.mmp.wanandroid.ui.home


import com.mmp.wanandroid.model.data.*
import com.mmp.wanandroid.model.loacl.room.HistoryKey
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData


object HomeRepository : BaseRepository() {

    private val historyKeyDao = database.historyKeyDao()

    suspend fun getHotKey() = execute {
        wanAndroidService.getHotKey()
    }

    suspend fun getSearchList(page: Int,k: String) = execute {
        wanAndroidService.getSearchArticle(page, k)
    }

    fun getHistoryKeyList() = historyKeyDao.getHistoryKeyList()

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