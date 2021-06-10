package com.mmp.wanandroid.ui.home.viewmodel

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.HotKey
import com.mmp.wanandroid.ui.home.HomeRepository
import com.mmp.wanandroid.room.HistoryKey
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData
import com.mmp.wanandroid.utils.toast
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    val key = ObservableField<String>("")

    val collectLiveData = StateLiveData<Any>()

    val historyKeyList = HomeRepository.getKeyList().asLiveData()

    fun addKey(){
        if (!TextUtils.isEmpty(key.get())){
            viewModelScope.launch {
                HomeRepository.addKey(HistoryKey(0,key.get()!!))
            }
        }
    }

    fun clean(){
        viewModelScope.launch {
            HomeRepository.clean()
        }
    }

    val hotKeyLiveData = StateLiveData<List<HotKey>>()

    fun getHotKey(){
        viewModelScope.launch {
            HomeRepository.getHotKey(hotKeyLiveData)
        }
    }

    val articleLiveData = StateLiveData<ArticleData>()

    fun getArticle(){
        viewModelScope.launch {
            if (TextUtils.isEmpty(key.get())){
                toast("搜索内容不能为空")
            }else{
                HomeRepository.getSearch(articleLiveData,key.get()!!)
            }
        }
    }

    fun collect(id: Int) {
        viewModelScope.launch {
            CollectRepository.collectArticle(collectLiveData,id)
        }
    }

    fun unCollect(id: Int){
        viewModelScope.launch {
            CollectRepository.unCollectArticle(collectLiveData, id)
        }
    }

//    fun getArticleMore(){
//        viewModelScope.launch {
//            HomeRepository.getSearchMore(articleLiveData,key.get()!!)
//        }
//    }

    fun listScrolled(visibleItemCount: Int,lastVisibleItemPosition: Int,totalItemCount: Int){
        if (visibleItemCount + lastVisibleItemPosition + Const.VISIBLE_THRESHOLD >= totalItemCount){
            if (!TextUtils.isEmpty(key.get())){
                viewModelScope.launch {
                    HomeRepository.getSearchMore(articleLiveData,key.get()!!)
                }
            }
        }
    }



}