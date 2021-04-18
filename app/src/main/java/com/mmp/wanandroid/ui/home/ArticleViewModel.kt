package com.mmp.wanandroid.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.Repository
import kotlinx.coroutines.flow.Flow

class ArticleViewModel : ViewModel() {

    fun getHomeArticleData(): Flow<PagingData<Article>> {
        return Repository.getHomeArticleData().cachedIn(viewModelScope)
    }

    fun getArticleData(cid: Int): Flow<PagingData<Article>> {
        return Repository.getArticleData(cid).cachedIn(viewModelScope)
    }

}