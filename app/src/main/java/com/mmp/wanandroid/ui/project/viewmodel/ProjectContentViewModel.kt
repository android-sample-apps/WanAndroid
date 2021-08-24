package com.mmp.wanandroid.ui.project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.ProjectBean
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.project.ProjectRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class ProjectContentViewModel : ViewModel() {

    private var page = 0

    private val _projectLiveData = MutableLiveData<DataStatus<ProjectBean>>()

    val projectLiveData: LiveData<DataStatus<ProjectBean>> = _projectLiveData

    fun getRefresh(cid: Int){
        page = 0
        viewModelScope.launch {
            ProjectRepository.getProjectList(cid,page)
                .flowOn(Dispatchers.IO)
                .onCompletion {
                    page++
                }
                .collect {
                    _projectLiveData.value = it
                }
        }
    }

    fun getLoadMore(cid: Int){
        viewModelScope.launch {
            ProjectRepository.getProjectList(cid,page)
                .flowOn(Dispatchers.IO)
                .onCompletion {
                    page++
                }
                .collect {
                    _projectLiveData.value = it
                }
        }
    }
}