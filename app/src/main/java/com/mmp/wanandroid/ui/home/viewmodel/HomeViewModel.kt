package com.mmp.wanandroid.ui.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mmp.wanandroid.model.remote.BaseResponse
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.model.remote.DataStatus

import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.home.HomeRepository

import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @ViewModelInject constructor(
    private val repository: HomeRepository
): ViewModel() {

    init {
        getArticle(0)
        getBanner()
    }

    @Inject lateinit var articleList: MutableList<Article>
    @Inject lateinit var bannerList: MutableList<Banner>
//    val articleList = mutableListOf<Article>()
//    val bannerList = mutableListOf<Banner>()

    private val _collectLiveData = StateLiveData<Any>()

    val collectLiveData: LiveData<BaseResponse<Any>> = _collectLiveData

    private val _articleLiveData = MutableLiveData<DataStatus<MutableList<Article>>>()

    val articleLiveData: LiveData<DataStatus<MutableList<Article>>> = _articleLiveData

    private val _bannerLiveData = MutableLiveData<DataStatus<List<Banner>>>()

    val bannerLiveData: LiveData<DataStatus<List<Banner>>> = _bannerLiveData

    fun getBanner(){
        viewModelScope.launch {
            repository.getBanner()
                .flowOn(Dispatchers.IO)
                .collect {
                    _bannerLiveData.value = it
                }
        }
    }

    fun getArticle(page: Int){
        viewModelScope.launch {
            repository.getHomeArticle(page)
                .zip(repository.getTopArticle()){ a,b ->
                    if (page == 0){
                        if (a is DataStatus.Success && b is DataStatus.Success) {
                            b.data.addAll(a.data.datas)
                        }
                        b
                    }else{
                        when(a){
                            is DataStatus.Success -> DataStatus.Success(a.data.datas)
                            DataStatus.Empty -> DataStatus.Empty
                            is DataStatus.Failure -> DataStatus.Failure(a.t)
                            DataStatus.Loading -> DataStatus.Loading
                            DataStatus.None -> DataStatus.None
                        }
                    }
                }.flowOn(Dispatchers.IO)
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