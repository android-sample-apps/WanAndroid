package com.mmp.wanandroid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mmp.wanandroid.data.Repository

class MainViewModel : ViewModel() {
    private val freshLiveData = MutableLiveData<Boolean>()

    fun setFresh(isFresh: Boolean){
        freshLiveData.value = isFresh
    }

    val treeLiveData = Transformations.switchMap(freshLiveData){
        Repository.getTree()
    }
}