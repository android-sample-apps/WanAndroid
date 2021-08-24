package com.mmp.wanandroid.ui.home.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.home.HomeRepository
import com.mmp.wanandroid.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class SearchFragmentViewModel : ViewModel() {


    var page = 0

    val searchList = mutableListOf<Article>()

    private val _searchLiveData = MutableLiveData<DataStatus<ArticleData>>()

    val searchLiveData: LiveData<DataStatus<ArticleData>> = _searchLiveData

    private val _collectLiveData = MutableLiveData<DataStatus<Any>>()

    val collectLiveData: LiveData<DataStatus<Any>> = _collectLiveData

    fun getRefresh(k: String){
        page = 0
        viewModelScope.launch {
            HomeRepository.getSearchList(page,k)
                .flowOn(Dispatchers.IO)
                .onCompletion { page++ }
                .collect {
                    _searchLiveData.value = it
                }
        }
    }

    fun getLoadMore(k: String){
        viewModelScope.launch {
            HomeRepository.getSearchList(page,k)
                .flowOn(Dispatchers.IO)
                .onCompletion { page++ }
                .collect {
                    _searchLiveData.value = it
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