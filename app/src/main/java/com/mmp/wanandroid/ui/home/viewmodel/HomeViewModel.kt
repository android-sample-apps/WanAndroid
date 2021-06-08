package com.mmp.wanandroid.ui.home.viewmodel

import android.text.TextUtils
import android.widget.BaseExpandableListAdapter
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mmp.wanandroid.api.BaseResponse
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.home.HomeRepository
import com.mmp.wanandroid.ui.home.request.HomeRequest
import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var isCache = false

    private var page = 0

    val request = HomeRequest()

    val articleList = MutableLiveData(mutableListOf<Article>())

    val bannerList = MutableLiveData<List<Banner>>()

    fun getBanner(){
        viewModelScope.launch {
            request.getBanner()
        }
    }

    fun getTopArticle(){
        viewModelScope.launch {
            request.getTopArticle()
        }
    }


    fun getHomeArticle(){
        val list = getCacheArticle()
        if (list != null){
            articleList.value = articleList.value?.apply {
                addAll(list)
            }
            page++
            isCache = true
        }else{
            viewModelScope.launch {
                request.getHomeArticle(page)
                page++
            }
        }
    }

    fun getArticleMore(){
        viewModelScope.launch {
            request.getHomeArticle(page)
            page++
        }
    }



    fun collect(id: Int) {
        viewModelScope.launch {
            request.collect(id)
        }
    }

    fun unCollect(id: Int){
        viewModelScope.launch {
            request.unCollect(id)
        }
    }

    private fun getCacheArticle(): ArrayList<Article>? {
        return HomeRepository.getCacheList<ArrayList<Article>>("articleList")
    }


    fun putCacheArticle(value: Any){
        HomeRepository.putCache("articleList",value)
    }


}