package com.mmp.wanandroid.ui.system.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.ui.system.SystemRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class SystemDetailViewModel : ViewModel(){

    val articlesLiveData = StateLiveData<ArticleData>()

    fun getSystemArticle(cid: Int){
        viewModelScope.launch {
            SystemRepository.getSystemArticle(articlesLiveData, cid)
        }
    }

    fun getMoreArticle(cid: Int){
        viewModelScope.launch {
            SystemRepository.getMoreArticle(articlesLiveData, cid)
        }
    }

}