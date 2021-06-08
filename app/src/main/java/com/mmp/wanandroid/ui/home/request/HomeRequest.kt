package com.mmp.wanandroid.ui.home.request

import androidx.lifecycle.LiveData
import com.mmp.wanandroid.api.BaseResponse
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.home.HomeRepository
import com.mmp.wanandroid.utils.StateLiveData

class HomeRequest {


    private val bannerLiveData = StateLiveData<List<Banner>>()

    val getBannerLiveData: LiveData<BaseResponse<List<Banner>>> = bannerLiveData

    private val topArticleLiveData = StateLiveData<List<Article>>()

    val getTopArticleLiveData: LiveData<BaseResponse<List<Article>>> = topArticleLiveData

    private val collectLiveData = StateLiveData<Any>()

    val getCollectLiveData: LiveData<BaseResponse<Any>> = collectLiveData


    private val homeArticleLiveData = StateLiveData<ArticleData>()

    val getHomeArticleLiveData: LiveData<BaseResponse<ArticleData>> = homeArticleLiveData


    suspend fun getBanner() = HomeRepository.getBanner(bannerLiveData)

    suspend fun getTopArticle() = HomeRepository.getTopArticle(topArticleLiveData)

    suspend fun getHomeArticle(page: Int) = HomeRepository.getHomeArticle(homeArticleLiveData, page)

    suspend fun collect(id: Int) = CollectRepository.collectArticle(collectLiveData, id)

    suspend fun unCollect(id: Int) = CollectRepository.unCollectArticle(collectLiveData, id)
}