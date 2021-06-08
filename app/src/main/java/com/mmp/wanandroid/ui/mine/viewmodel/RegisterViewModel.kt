package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {


    val registerLiveData = StateLiveData<Any>()

    fun getRegister(username: String,password: String,repassword: String){
        viewModelScope.launch {
            MineRepository.getRegister(registerLiveData,username, password, repassword)
        }
    }
}