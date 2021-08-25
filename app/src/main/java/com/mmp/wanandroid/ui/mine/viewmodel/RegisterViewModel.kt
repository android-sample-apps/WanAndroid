package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    val username = MutableLiveData("")

    val password = MutableLiveData("")

    val repassword = MutableLiveData("")

    private val _registerLiveData = MutableLiveData<DataStatus<Any>>()

    val registerLiveData: LiveData<DataStatus<Any>> = _registerLiveData

    fun getRegister(username: String,password: String,repassword: String){
        viewModelScope.launch {
            MineRepository.getRegister(username, password, repassword)
                .flowOn(Dispatchers.IO)
                .collect {
                    _registerLiveData.value = it
                }
        }
    }
}