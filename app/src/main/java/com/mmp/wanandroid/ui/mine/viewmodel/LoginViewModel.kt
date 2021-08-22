package com.mmp.wanandroid.ui.mine.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.User
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import com.mmp.wanandroid.utils.toast
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val username = MutableLiveData<String>("")

    val password = MutableLiveData<String>("")

    val loginLiveDaa = StateLiveData<User>()

    fun getLogin(){
        if (!TextUtils.isEmpty(username.value) && !TextUtils.isEmpty(password.value)){
            viewModelScope.launch {
                MineRepository.getLogin(loginLiveDaa,username.value!!, password.value!!)
            }
        }else{
            toast("账号或密码为空，请重新输入")
        }
    }
}