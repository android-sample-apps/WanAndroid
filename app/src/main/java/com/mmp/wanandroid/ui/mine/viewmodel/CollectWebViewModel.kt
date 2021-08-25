package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.Web
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CollectWebViewModel : ViewModel() {

    init {
        getCollectTools()
    }
    val toolList = mutableListOf<Web>()

    private val _webLiveData = MutableLiveData<DataStatus<List<Web>>>()

    val webLiveData: LiveData<DataStatus<List<Web>>> = _webLiveData

    private val _collectLiveData = MutableLiveData<DataStatus<Any>>()

    val collectLiveData: LiveData<DataStatus<Any>> = _collectLiveData

    fun getCollectTools(){
        viewModelScope.launch {
            MineRepository.getCollectTools()
                .flowOn(Dispatchers.IO)
                .collect {
                    _webLiveData.value = it
                }
        }
    }

    fun collect(name: String,link: String){
        viewModelScope.launch {
            CollectRepository.collectTools(name, link)
                .flowOn(Dispatchers.IO)
                .collect {
                    _collectLiveData.value = it
                }
        }
    }

    fun unCollect(id: Int){
        viewModelScope.launch {
            CollectRepository.unCollectTools(id)
                .flowOn(Dispatchers.IO)
                .collect {
                    _collectLiveData.value = it
                }
        }
    }
}