package com.mmp.wanandroid.ui.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.ProjectBean
import com.mmp.wanandroid.ui.project.ProjectRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class ProjectContentViewModel : ViewModel() {

    val projectLiveData = StateLiveData<ProjectBean>()

    fun getProjectList(cid: Int){
        viewModelScope.launch {
            ProjectRepository.getProjectList(projectLiveData,cid)
        }
    }

    fun getMoreProject(cid: Int){
        viewModelScope.launch {
            ProjectRepository.getMoreProject(projectLiveData,cid)
        }
    }
}