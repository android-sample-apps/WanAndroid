package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.data.Web
import com.mmp.wanandroid.ui.CollectRepository
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class CollectWebViewModel : ViewModel() {

    val websLiveData = StateLiveData<List<Web>>()

    val collectLiveData = StateLiveData<Any>()

    fun getCollectTools(){
        viewModelScope.launch {
            MineRepository.getCollectTools(websLiveData)
        }
    }

    fun collect(name: String,link: String){
        viewModelScope.launch {
            CollectRepository.collectTools(collectLiveData,name, link)
        }
    }

    fun unCollect(id: Int){
        viewModelScope.launch {
            CollectRepository.unCollectTools(collectLiveData,id)
        }
    }
}