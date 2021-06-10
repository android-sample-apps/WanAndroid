package com.mmp.wanandroid.ui.home.viewmodel

import android.text.TextUtils
import android.widget.BaseExpandableListAdapter
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mmp.wanandroid.api.BaseResponse
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.home.HomeRepository

import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    var page = 0

    private val _bannerLiveData = StateLiveData<List<Banner>>()

    val bannerLiveData: LiveData<BaseResponse<List<Banner>>> = _bannerLiveData

    private val _topArticleLiveData = StateLiveData<List<Article>>()

    val topArticleLiveData: LiveData<BaseResponse<List<Article>>> = _topArticleLiveData

    private val _collectLiveData = StateLiveData<Any>()

    val collectLiveData: LiveData<BaseResponse<Any>> = _collectLiveData

    private val _homeArticleLiveData = StateLiveData<ArticleData>()

    val homeArticleLiveData: LiveData<BaseResponse<ArticleData>> = _homeArticleLiveData


    fun getBanner(){
        viewModelScope.launch {
            HomeRepository.getBanner(_bannerLiveData)
        }
    }

    fun getTopArticle(){
        viewModelScope.launch {
            HomeRepository.getTopArticle(_topArticleLiveData)
        }
    }


    fun getHomeArticle(){
        viewModelScope.launch {
            HomeRepository.getHomeArticle(_homeArticleLiveData,page)
            page++
        }
    }

    fun getArticleMore(){
        viewModelScope.launch {
            HomeRepository.getHomeArticle(_homeArticleLiveData,page)
            page++
        }
    }



    fun collect(id: Int) {
        viewModelScope.launch {
            CollectRepository.collectArticle(_collectLiveData,id)
        }
    }

    fun unCollect(id: Int){
        viewModelScope.launch {
            CollectRepository.unCollectArticle(_collectLiveData,id)
        }
    }

    suspend fun getCacheArticle(): List<Article> {
        var list: List<Article>
        withContext(Dispatchers.IO){
            list = HomeRepository.getCacheList()
        }
        return list
    }
//
//
    fun putCacheArticle(value: List<Article>){
        HomeRepository.putCache(value)
    }


}