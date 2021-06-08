package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class CollectArticleViewModel : ViewModel() {

    val articlesLiveData = StateLiveData<ArticleData>()

    val collectLiveData = StateLiveData<Any>()



    fun getCollectArticle(){
        viewModelScope.launch {
            MineRepository.getCollectArticle(articlesLiveData)
        }
    }

    fun gerArticleMore(){
        viewModelScope.launch {
            MineRepository.getArticleMore(articlesLiveData)
        }
    }


    fun unCollect(id: Int){
        viewModelScope.launch {
            CollectRepository.unCollectArticle(collectLiveData,id)
        }
    }

}