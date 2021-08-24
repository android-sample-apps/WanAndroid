package com.mmp.wanandroid.ui.home.viewmodel

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.model.data.HotKey
import com.mmp.wanandroid.model.loacl.room.HistoryKey
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.home.HomeRepository
//import com.mmp.wanandroid.model.loacl.room.HistoryKey
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData
import com.mmp.wanandroid.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {


    init{
        getHotKey()
        getHistoryKey()
    }

    val hotkeyList = mutableListOf<String>()

    private val _hotKeyLiveData = MutableLiveData<DataStatus<List<HotKey>>>()

    val hotKeyLiveData: LiveData<DataStatus<List<HotKey>>> = _hotKeyLiveData

    private val _historyLiveData = MutableLiveData<DataStatus<List<HistoryKey>>>()

    val historyLiveData: LiveData<DataStatus<List<HistoryKey>>> = _historyLiveData


    fun addKey(k: String){
        viewModelScope.launch {
            HomeRepository.addKey(HistoryKey(0,k))
        }
    }

    fun clean(){
        viewModelScope.launch {
            HomeRepository.clean()
        }
    }


    fun getHotKey(){
        viewModelScope.launch {
            HomeRepository.getHotKey()
                .flowOn(Dispatchers.IO)
                .collect {
                    _hotKeyLiveData.value = it
                }
        }
    }

    fun getHistoryKey(){
        viewModelScope.launch {
            HomeRepository.getHistoryKeyList()
                .flowOn(Dispatchers.IO)
                .collect {
                    _historyLiveData.value = DataStatus.Success(it)
                }
        }
    }

}