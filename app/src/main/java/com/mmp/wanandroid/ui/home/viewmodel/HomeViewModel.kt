package com.mmp.wanandroid.ui.home.viewmodel

import androidx.lifecycle.*
import com.mmp.wanandroid.model.remote.api.BaseResponse
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.model.data.Banner
import com.mmp.wanandroid.model.data.DataState
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.home.HomeRepository

import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

   init {
       getBanner()
       getRefresh()
   }

    var page = 0

    val articleList = mutableListOf<Article>()

    val bannerList = mutableListOf<Banner>()

    private val _bannerLiveData = MutableLiveData<DataStatus<List<Banner>>>()

    val bannerLiveData: LiveData<DataStatus<List<Banner>>> = _bannerLiveData

    private val _collectLiveData = MutableLiveData<DataStatus<Any>>()

    val collectLiveData: LiveData<DataStatus<Any>> = _collectLiveData

    private val _articleLiveData = MutableLiveData<DataStatus<ArticleData>>()

    val articleLiveData: LiveData<DataStatus<ArticleData>> = _articleLiveData

    fun getRefresh(){
        page = 0
        viewModelScope.launch {
            HomeRepository.getHomeArticle(page)
                .zip(HomeRepository.getTopArticle()){ a,b ->
                    if (a is DataStatus.Success<ArticleData> && b is DataStatus.Success<List<Article>>){
                        val list = mutableListOf<Article>()
                        list.addAll(b.data)
                        list.addAll(a.data.datas)
                        a.data.datas = list
                    }
                    a
                }
                .onCompletion { page++ }
                .flowOn(Dispatchers.IO)
                .collect {
                    _articleLiveData.value = it
                }
        }
    }

    fun getLoadMore(){
        viewModelScope.launch {
            HomeRepository.getHomeArticle(page)
                .flowOn(Dispatchers.IO)
                .onCompletion { page++ }
                .collect {
                    _articleLiveData.value = it
                }
        }
    }
    fun getBanner(){
        viewModelScope.launch {
            HomeRepository.getBanner()
                .flowOn(Dispatchers.IO)
                .collect {
                    _bannerLiveData.value = it
                }
        }
    }


    fun collect(id: Int) {
        viewModelScope.launch {
            CollectRepository.collectArticle(id)
                .flowOn(Dispatchers.IO)
                .collect {
                    _collectLiveData.value = it
                }
        }
    }

    fun unCollect(id: Int){
        viewModelScope.launch {
            CollectRepository.unCollectArticle(id)
                .flowOn(Dispatchers.IO)
                .collect {
                    _collectLiveData.value = it
                }

        }
    }

}