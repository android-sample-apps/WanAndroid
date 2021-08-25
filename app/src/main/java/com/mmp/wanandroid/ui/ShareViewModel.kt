package com.mmp.wanandroid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmp.wanandroid.model.data.User

class ShareViewModel : ViewModel() {

    val loginLiveData = MutableLiveData<User>()

    val logoutLiveData = MutableLiveData<Boolean>()


}