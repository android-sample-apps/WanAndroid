package com.mmp.wanandroid.ui.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.model.data.ProjectTree
import com.mmp.wanandroid.ui.project.ProjectRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.launch

class ProjectViewModel : ViewModel() {

    val projectTreeLiveData = StateLiveData<List<ProjectTree>>()

    fun getProjectTree(){
        viewModelScope.launch {
            ProjectRepository.getProjectTree(projectTreeLiveData)
        }
    }

    val treeLiveData = ProjectRepository.getProjectTree().asLiveData()

    fun addProjectTree(list: List<ProjectTree>) {
        viewModelScope.launch {
            ProjectRepository.addProjectTree(list)
        }
    }
}