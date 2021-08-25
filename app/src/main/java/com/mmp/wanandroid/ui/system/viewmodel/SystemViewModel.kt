package com.mmp.wanandroid.ui.system.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.SystemTree
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.system.SystemRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SystemViewModel : ViewModel() {

    init {
        getTree()
    }


    private val _treeLiveData = MutableLiveData<DataStatus<List<SystemTree>>>()

    val treeLiveData: LiveData<DataStatus<List<SystemTree>>> = _treeLiveData

    fun getTree(){
        viewModelScope.launch {
            SystemRepository.getTree()
                .flowOn(Dispatchers.IO)
                .collect {
                    _treeLiveData.value = it
                }
        }
    }

}