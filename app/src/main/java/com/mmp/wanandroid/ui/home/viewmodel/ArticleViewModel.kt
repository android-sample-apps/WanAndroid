package com.mmp.wanandroid.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.HomeRepository
import kotlinx.coroutines.flow.Flow

//class ArticleViewModel : ViewModel() {
//
//    fun getHomeArticleData(): Flow<PagingData<Article>> {
//        return HomeRepository.getHomeArticleData().cachedIn(viewModelScope)
//    }
//
//    fun getArticleData(cid: Int): Flow<PagingData<Article>> {
//        return HomeRepository.getArticleData(cid).cachedIn(viewModelScope)
//    }
//
//}