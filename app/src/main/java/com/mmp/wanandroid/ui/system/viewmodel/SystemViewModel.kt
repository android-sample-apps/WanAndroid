package com.mmp.wanandroid.ui.system.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.data.SystemTree
import com.mmp.wanandroid.ui.system.SystemRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class SystemViewModel : ViewModel() {

    val treeLiveData = StateLiveData<List<SystemTree>>()

    fun getTree(){
        viewModelScope.launch {
            SystemRepository.getTree(treeLiveData)
        }
    }

}