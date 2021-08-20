package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.remote.BaseResponse
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class SettingViewModel : ViewModel(){
    private val _logoutLiveData = StateLiveData<Any>()

    val logoutLiveData: LiveData<BaseResponse<Any>> = _logoutLiveData

    fun logout() {
        viewModelScope.launch {
            MineRepository.logout(_logoutLiveData)
        }
    }
}