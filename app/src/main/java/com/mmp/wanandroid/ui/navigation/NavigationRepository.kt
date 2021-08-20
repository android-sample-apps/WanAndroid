package com.mmp.wanandroid.ui.navigation

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.data.NavBean
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object NavigationRepository : BaseRepository(){

    private val wanAndroidService = WanAndroidService.create()

    suspend fun getNavList(navLiveData: StateLiveData<List<NavBean>>) = executeResp(navLiveData){
        wanAndroidService.getNavList()
    }
}