package com.mmp.wanandroid.ui.navigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.NavBean
import com.mmp.wanandroid.ui.navigation.NavigationRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {

    val navLiveData = StateLiveData<List<NavBean>>()

    fun getNavList(){
        viewModelScope.launch {
            NavigationRepository.getNavList(navLiveData)
        }
    }
}