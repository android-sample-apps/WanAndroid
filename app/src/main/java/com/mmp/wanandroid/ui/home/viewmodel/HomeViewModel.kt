package com.mmp.wanandroid.ui.home.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.ui.home.HomeRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel :ViewModel() {

    private val repository = HomeRepository()

//    private val bannerLiveData =  MutableLiveData<Boolean>()
//
//    val banner = Transformations.switchMap(bannerLiveData){
//        HomeRepository.getBanner()
//    }
//
//    private val articleLiveData = MutableLiveData<Int>()
//
//    val article = Transformations.switchMap(articleLiveData){
//        when(it){
//            1 -> HomeRepository.getHomeArticle()
//            2 -> HomeRepository.getMoreArticle()
//            else -> null
//        }
//    }
//
//
//    fun requestBanner(){
//        bannerLiveData.value = true
//    }
//
//    fun requestArticle(){
//        articleLiveData.value = 1
//    }
//
//    fun requestMore(){
//        articleLiveData.value = 2
//    }

//     val articleLiveData = StateLiveData<ArticleData>()
//
//
//
//    fun getHomeArticle(){
//        viewModelScope.launch {
//            repository.getHomeArticle(articleLiveData)
//        }
//    }
//
//    fun getMoreHomeArticle(){
//        viewModelScope.launch {
//            repository.getMoreHomeArticle(articleLiveData)
//        }
//    }

    val bannerLiveData = StateLiveData<List<Banner>>()

    fun getBanner(){
        viewModelScope.launch {
            repository.getBanner(bannerLiveData)
        }
    }

    fun getHomeArticle(): Flow<PagingData<Article>>{
        return repository.getHomeArticle().cachedIn(viewModelScope)
    }

}