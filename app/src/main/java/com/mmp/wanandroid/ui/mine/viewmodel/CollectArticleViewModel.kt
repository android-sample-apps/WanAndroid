package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class CollectArticleViewModel : ViewModel() {

    init {
        getRefresh()
    }

    private var page = 0

    val articleList = mutableListOf<Article>()

    private val _articleLiveData = MutableLiveData<DataStatus<ArticleData>>()

    val articleLiveData: LiveData<DataStatus<ArticleData>> = _articleLiveData

    private val _collectLiveData = MutableLiveData<DataStatus<Any>>()

    val collectLiveData: LiveData<DataStatus<Any>> = _collectLiveData


    fun getRefresh(){
        page = 0
        articleList.clear()
        viewModelScope.launch {
            MineRepository.getCollectArticle(page)
                .flowOn(Dispatchers.IO)
                .onCompletion {
                    page++
                }
                .collect {
                    _articleLiveData.value = it
                }
        }
    }

    fun gerArticleMore(){
        viewModelScope.launch {
            MineRepository.getCollectArticle(page)
                .flowOn(Dispatchers.IO)
                .onCompletion {
                    page++
                }
                .collect {
                    _articleLiveData.value = it
                }
        }
    }


    fun unCollect(id: Int){
        viewModelScope.launch {
            CollectRepository.unCollectArticle(id)
        }
    }

}