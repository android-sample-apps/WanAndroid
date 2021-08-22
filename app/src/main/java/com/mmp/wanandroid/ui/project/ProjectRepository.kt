package com.mmp.wanandroid.ui.project

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.model.data.ProjectBean
import com.mmp.wanandroid.model.data.ProjectTree
//import com.mmp.wanandroid.model.loacl.room.MyRoomDatabase
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object ProjectRepository : BaseRepository(){

    private var page = 1

    private val wanAndroidService = WanAndroidService.create()

    private val projectTreeDao = MyRoomDatabase.getDatabase().projectTreeDap()

    suspend fun getProjectTree(projectTreeLiveData: StateLiveData<List<ProjectTree>>) = executeResp(projectTreeLiveData){
        wanAndroidService.getProjectTree()
    }

    suspend fun getProjectList(projectListLiveData: StateLiveData<ProjectBean>,cid: Int) = executeResp(projectListLiveData){
        page = 1
        wanAndroidService.getProjectList(page,cid)
    }

    suspend fun getMoreProject(projectListLiveData: StateLiveData<ProjectBean>, cid: Int) = executeResp(projectListLiveData){
        page++
        wanAndroidService.getProjectList(page, cid)
    }

    fun getProjectTree() = projectTreeDao.getProjectTree()

    suspend fun addProjectTree(list: List<ProjectTree>) = projectTreeDao.addTree(list)
}