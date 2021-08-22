package com.mmp.wanandroid.ui.mine.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.Rank
import com.mmp.wanandroid.ui.mine.MineRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class MineViewModel : ViewModel() {

    var id = ObservableField<String>("--")

    var integral = ObservableField<String>("0")

    val integralLiveData = StateLiveData<Rank>()

    fun getUserRank(){
        viewModelScope.launch {
            MineRepository.getUserRank(integralLiveData)
        }
    }
}