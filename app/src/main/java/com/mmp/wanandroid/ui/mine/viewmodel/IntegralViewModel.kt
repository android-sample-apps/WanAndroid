package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.Integral
import com.mmp.wanandroid.model.data.IntegralData
import com.mmp.wanandroid.model.data.Rank
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.Const
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class IntegralViewModel : ViewModel() {


    private var page = 0

    val coinList = mutableListOf<Integral>()

    val integral = MutableLiveData(0)

    private val _coinLiveData = MutableLiveData<DataStatus<IntegralData>>()

    val coinLiveData: LiveData<DataStatus<IntegralData>> = _coinLiveData

    private val _integralLiveData = MutableLiveData<DataStatus<Rank>>()

    val integralLiveData: LiveData<DataStatus<Rank>> = _integralLiveData

    val  getRefresh: () -> Unit = {
        page =0
        coinList.clear()
        viewModelScope.launch {
            MineRepository.getCoinList(page)
                .flowOn(Dispatchers.IO)
                .onCompletion {
                    page++
                }
                .collect {
                    _coinLiveData.value = it
                }
        }
    }


    val getLoadMore: () -> Unit = {
        viewModelScope.launch {
            MineRepository.getCoinList(page)
                .flowOn(Dispatchers.IO)
                .onCompletion {
                    page++
                }
                .collect {
                    _coinLiveData.value = it
                }
        }
    }

    fun getIntegral(){
        viewModelScope.launch {
            MineRepository.getUserRank()
                .flowOn(Dispatchers.IO)
                .collect {
                    _integralLiveData.value = it
                }
        }
    }

    init {
        getRefresh()
    }

}