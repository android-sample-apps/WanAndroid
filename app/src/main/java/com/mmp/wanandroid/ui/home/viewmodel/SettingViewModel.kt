package com.mmp.wanandroid.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mmp.wanandroid.data.HomeRepository

//class SettingViewModel : ViewModel(){
//
//    private val freshLiveData = MutableLiveData<Boolean>()
//
//    val treeLiveData = Transformations.switchMap(freshLiveData){
//        HomeRepository.getTree()
//    }
//
//    fun setFresh(){
//        freshLiveData.value = true
//    }
//}