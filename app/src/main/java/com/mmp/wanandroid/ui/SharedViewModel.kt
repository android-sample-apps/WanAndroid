package com.mmp.wanandroid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmp.wanandroid.data.User
import com.mmp.wanandroid.utils.Event
import com.mmp.wanandroid.utils.SingleLiveEvent

class SharedViewModel : ViewModel() {

    val loginSuccess = MutableLiveData<Event<User>>()

    val logout = MutableLiveData<Event<Boolean>>()


}