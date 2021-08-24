package com.mmp.wanandroid.ui.project.viewmodel

import androidx.lifecycle.*
import com.mmp.wanandroid.model.data.ProjectTree
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.project.ProjectRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class ProjectViewModel : ViewModel() {

    init {
        getProjectTree()
    }

    val treeList = mutableListOf<ProjectTree>()

    private val _projectTreeLiveData = MutableLiveData<DataStatus<List<ProjectTree>>>()

    val projectTreeLiveData: LiveData<DataStatus<List<ProjectTree>>> = _projectTreeLiveData

    val localTreeLiveData = ProjectRepository.getLocalTree().asLiveData()

    fun getProjectTree(){
        viewModelScope.launch {
            ProjectRepository.getProjectTree()
                .flowOn(Dispatchers.IO)
                .collect {
                    _projectTreeLiveData.value = it
                }
        }
    }

    fun addProjectTree(list: List<ProjectTree>) {
        viewModelScope.launch {
            ProjectRepository.addProjectTree(list)
        }
    }
}