package com.mmp.wanandroid.ui.home.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.model.remote.DataStatus
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

    fun getRefresh(){
        page = 0
        viewModelScope.launch {
            if (TextUtils.isEmpty(key.get())){
                toast("搜索内容不能为空")
            }else{
                HomeRepository.getSearchList(page,key.get()!!)
                    .flowOn(Dispatchers.IO)
                    .onCompletion { page++ }
                    .collect {
                        _searchLiveData.value = it
                    }
            }
        }
    }

    fun getLoadMore(){
        viewModelScope.launch {
            HomeRepository.getSearchList(page,key.get()!!)
                .flowOn(Dispatchers.IO)
                .onCompletion { page++ }
                .collect {
                    _searchLiveData.value = it
                }
        }
    }
}