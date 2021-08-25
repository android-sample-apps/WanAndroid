package com.mmp.wanandroid.ui.mine

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.model.data.*
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object MineRepository : BaseRepository(){

    suspend fun getLogin(username: String,password: String) = execute {
        wanAndroidService.getLogin(username, password)
    }

    suspend fun getRegister(username: String,password: String,repassword: String) = execute {
        wanAndroidService.getRegister(username, password, repassword)
    }

    suspend fun logout() = execute {
        wanAndroidService.getLogout()
    }

    suspend fun getUserRank() = execute {
        wanAndroidService.getUserRank()
    }

    suspend fun getCoinList(page: Int) = execute {
        wanAndroidService.getCoinList(page)
    }

    suspend fun getCollectArticle(page: Int) = execute {
        wanAndroidService.getCollectArticle(page)
    }

    suspend fun getCollectTools() = execute {
        wanAndroidService.getCollectTools()
    }

}