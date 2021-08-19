package com.mmp.wanandroid.ui.home

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.data.*
import com.mmp.wanandroid.room.HistoryKey
import com.mmp.wanandroid.room.HistoryKeyDao
import com.mmp.wanandroid.room.MyRoomDatabase
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData
import javax.inject.Inject


class HomeRepository @Inject constructor() : BaseRepository(){

    @Inject lateinit var wanAndroidService: WanAndroidService

    @Inject lateinit var historyKeyDao: HistoryKeyDao



    fun getKeyList() = historyKeyDao.getHistoryKeyList()

    suspend fun addKey(historyKey: HistoryKey) =  historyKeyDao.addKey(historyKey)

    suspend fun clean() = historyKeyDao.deleteAll()


    suspend fun getTopArticle() = execute {
        wanAndroidService.getTopArticle()
    }

    suspend fun getHomeArticle(page: Int) = execute {
        wanAndroidService.getHomeArticle(page)
    }

    suspend fun getBanner() = execute {
        wanAndroidService.getBanner()
    }
}