package com.mmp.wanandroid.data

import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mmp.wanandroid.api.BaseResponse
import com.mmp.wanandroid.api.WanAndroidService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object HomeRepository {

    private val wanAndroidService = WanAndroidService.create()

    suspend fun getHomeArticle(page: Int) = wanAndroidService.getHomeArticle(page)

    suspend fun getBanner(): BaseResponse<List<Banner>> = wanAndroidService.getBanner()

    suspend fun getTopArticle() = wanAndroidService.getTopArticle()
}