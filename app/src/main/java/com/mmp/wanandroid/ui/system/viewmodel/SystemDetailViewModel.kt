package com.mmp.wanandroid.ui.system.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.system.SystemRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class SystemDetailViewModel : ViewModel(){

    private var page = 0

    val articleList = mutableListOf<Article>()

    private val _articleLiveData = MutableLiveData<DataStatus<ArticleData>>()

    val articleData: LiveData<DataStatus<ArticleData>> = _articleLiveData

    fun getRefresh(cid: Int){
        page = 0
        viewModelScope.launch {
            SystemRepository.getSystemArticle(page, cid)
                .flowOn(Dispatchers.IO)
                .onCompletion {
                    page++
                }
                .collect {
                    _articleLiveData.value = it
                }
        }
    }

    fun getLoadMore(cid: Int){
        viewModelScope.launch {
            SystemRepository.getSystemArticle(page, cid)
                .flowOn(Dispatchers.IO)
                .onCompletion {
                    page++
                }
                .collect {
                    _articleLiveData.value = it
                }
        }
    }



}