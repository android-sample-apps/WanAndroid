package com.mmp.wanandroid.ui.home.viewmodel

import androidx.lifecycle.*
import com.mmp.wanandroid.api.BaseResponse
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.data.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {


    private val _bannerLiveData =  MutableLiveData<BaseResponse<List<Banner>>>()

    val bannerLiveData: LiveData<BaseResponse<List<Banner>>> = _bannerLiveData


    fun getBanner(){
        viewModelScope.launch {
            _bannerLiveData.value = HomeRepository.getBanner()
        }
    }

}