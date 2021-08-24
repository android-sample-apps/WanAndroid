package com.mmp.wanandroid.ui.navigation

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.model.data.NavBean
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object NavigationRepository : BaseRepository(){

    suspend fun getNavList() = execute {
        wanAndroidService.getNavList()
    }
}