package com.mmp.wanandroid.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mmp.wanandroid.model.remote.api.BaseResponse
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.network.DataStatus
import com.mmp.wanandroid.network.None
import com.mmp.wanandroid.network.Success
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.home.HomeRepository

import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val repository: HomeRepository
): ViewModel() {

    init {
        getArticle()
        getBanner()
    }

    val articleList = mutableListOf<Article>()
    val bannerList = mutableListOf<Banner>()
    var page = 0

    private val _collectLiveData = StateLiveData<Any>()

    val collectLiveData: LiveData<BaseResponse<Any>> = _collectLiveData

    private val _articleLiveData = MutableLiveData<DataStatus>(None)

    val articleLiveData: LiveData<DataStatus> = _articleLiveData

    private val _bannerLiveData = MutableLiveData<DataStatus>(None)

    val bannerLiveData: LiveData<DataStatus> = _bannerLiveData

    fun getBanner(){
        viewModelScope.launch {
            repository.getBanner()
                .flowOn(Dispatchers.IO)
                .collect {
                    _bannerLiveData.value = it
                }
        }
    }

    fun getArticle(){
        viewModelScope.launch {
            repository.getHomeArticle(page)
                .zip(repository.getTopArticle()){ a,b ->
                    if (page == 0){
                        if (a is Success<*> && b is Success<*>) {
                            (b.data as MutableList<Article>).addAll((a.data as ArticleData).datas)
                            Timber.d(b.data.toString())
                        }
                        b
                    }else{
                        a
                    }
                }.flowOn(Dispatchers.IO)
                .onCompletion {
                    page++
                }
                .collect { dataStatus ->
                    _articleLiveData.value = dataStatus
                }
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



}