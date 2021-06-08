package com.mmp.wanandroid.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmp.wanandroid.data.ProjectTree
import kotlinx.coroutines.flow.Flow


@Dao
interface ProjectTreeDao {
    @Query("SELECT * FROM project_tree")
    fun getProjectTree(): Flow<List<ProjectTree>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTree(list: List<ProjectTree>)
}