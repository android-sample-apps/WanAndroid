package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.IntegralData
import com.mmp.wanandroid.model.data.Rank
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class IntegralViewModel : ViewModel() {
    val integral = MutableLiveData<Int>(0)

    val coinLiveData = StateLiveData<IntegralData>()

    val integralLiveData = StateLiveData<Rank>()

    fun getCoinList() {
        viewModelScope.launch {
            MineRepository.getCoinList(coinLiveData)
        }
    }

    fun getUserRank(){
        viewModelScope.launch {
            MineRepository.getUserRank(integralLiveData)
        }
    }


    fun listScrolled(visibleItemCount: Int,lastVisibleItemPosition: Int,totalItemCount: Int){
        if (visibleItemCount + lastVisibleItemPosition + Const.VISIBLE_THRESHOLD >= totalItemCount){
            viewModelScope.launch {
                MineRepository.getCoinMore(coinLiveData)
            }
        }
    }

}