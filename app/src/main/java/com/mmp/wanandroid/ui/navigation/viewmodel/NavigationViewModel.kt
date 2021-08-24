package com.mmp.wanandroid.ui.navigation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.NavBean
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.navigation.NavigationRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {

    init {
        getNavList()
    }

    val navList = mutableListOf<NavBean>()

    private val _navLiveData = MutableLiveData<DataStatus<List<NavBean>>>()

    val navLiveData: LiveData<DataStatus<List<NavBean>>> = _navLiveData

    fun getNavList(){
        viewModelScope.launch {
            NavigationRepository.getNavList()
                .flowOn(Dispatchers.IO)
                .collect {
                    _navLiveData.value = it
                }
        }
    }
}