package com.mmp.wanandroid.ui.mine.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.User
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import com.mmp.wanandroid.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val username = MutableLiveData<String>("")

    val password = MutableLiveData<String>("")

    private val _loginLiveData = MutableLiveData<DataStatus<User>>()

    val loginLiveData: LiveData<DataStatus<User>> = _loginLiveData

    fun getLogin(username: String,password: String){
        viewModelScope.launch {
            MineRepository.getLogin(username, password)
                .flowOn(Dispatchers.IO)
                .collect {
                    _loginLiveData.value = it
                }
        }
    }
}