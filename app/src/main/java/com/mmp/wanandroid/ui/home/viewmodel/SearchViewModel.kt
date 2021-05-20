package com.mmp.wanandroid.ui.home.viewmodel

import android.text.TextUtils
import androidx.lifecycle.*
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.HotKey
import com.mmp.wanandroid.ui.home.HomeRepository
import com.mmp.wanandroid.room.HistoryKey
import com.mmp.wanandroid.utils.StateLiveData
import com.mmp.wanandroid.utils.toast
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val homeRepository = HomeRepository()
//


    val key = MutableLiveData<String>("")


    val historyKeyList = homeRepository.getKeyList().asLiveData()
//
    fun addKey(){
        if (!TextUtils.isEmpty(key.value)){
            viewModelScope.launch {
                homeRepository.addKey(HistoryKey(0,key.value!!))
            }
        }
    }

    fun clean(){
        viewModelScope.launch {
            homeRepository.clean()
        }
    }

    val hotKeyLiveData = StateLiveData<List<HotKey>>()

    fun getHotKey(){
        viewModelScope.launch {
            homeRepository.getHotKey(hotKeyLiveData)
        }
    }

    val articleLiveData = StateLiveData<ArticleData>()

    fun getArticle(){
        viewModelScope.launch {
            if (TextUtils.isEmpty(key.value)){
                toast("搜索内容不能为空")
            }else{
                homeRepository.getSearch(articleLiveData,key.value!!)
            }
        }
    }


    fun listScrolled(visibleItemCount: Int,lastVisibleItemPosition: Int,totalItemCount: Int){
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount){
            if (!TextUtils.isEmpty(key.value)){
                viewModelScope.launch {
                    homeRepository.getSearchMore(articleLiveData,key.value!!)
                }
            }
        }
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }


//
//    val hotKey = Transformations.switchMap(hotKeyLiveData){
//        HomeRepository.getHotKey()
//    }
//
//    fun getHotKey(){
//        hotKeyLiveData.value = true
//    }
//
//    private val searchArticleLiveData = MutableLiveData<Pair<Int,String>>()
//
//    val article = Transformations.switchMap(searchArticleLiveData){
//        when(it.first){
//            1 -> HomeRepository.getSearchArticle(it.second)
//            2 -> HomeRepository.getMoreSearch(it.second)
//            else -> null
//        }
//    }
//
//    fun getArticle(k: String){
//        searchArticleLiveData.value = Pair(1,k)
//    }
//
//    fun getMore(k: String){
//        searchArticleLiveData.value = Pair(2,k)
//    }
//
}