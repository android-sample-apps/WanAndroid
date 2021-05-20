package com.mmp.wanandroid.ui.web

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewModel : ViewModel() {

    private val _progress = MutableLiveData<Int>()

    val progress: LiveData<Int> = _progress

    fun currentProcess(progress: Int){
        _progress.value = progress
    }
}