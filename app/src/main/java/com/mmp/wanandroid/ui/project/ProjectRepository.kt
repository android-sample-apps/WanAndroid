package com.mmp.wanandroid.ui.project

import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.model.data.ProjectBean
import com.mmp.wanandroid.model.data.ProjectTree
//import com.mmp.wanandroid.model.loacl.room.MyRoomDatabase
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData

object ProjectRepository : BaseRepository(){

    private val projectTreeDao = database.projectTreeDap()

    suspend fun getProjectList(cid: Int,page: Int) = execute {
        wanAndroidService.getProjectList(page, cid)
    }

    suspend fun getProjectTree() = execute {
        wanAndroidService.getProjectTree()
    }

    fun getLocalTree() = projectTreeDao.getProjectTree()

    suspend fun addProjectTree(list: List<ProjectTree>) = projectTreeDao.addTree(list)
}