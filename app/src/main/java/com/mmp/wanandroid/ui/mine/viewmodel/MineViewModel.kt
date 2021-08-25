package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.Rank
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MineViewModel : ViewModel() {

    init {
        getUserRank()
    }

    var id = ObservableField<String>("--")

    var integral = ObservableField<String>("0")

    private val _integralLiveData = MutableLiveData<DataStatus<Rank>>()

    val integralLiveData: LiveData<DataStatus<Rank>> = _integralLiveData

    fun getUserRank(){
        viewModelScope.launch {
            MineRepository.getUserRank()
                .flowOn(Dispatchers.IO)
                .collect {
                    _integralLiveData.value = it
                }
        }
    }
}