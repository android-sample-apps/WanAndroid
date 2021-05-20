package com.mmp.wanandroid.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryKeyDao {

    @Query("SELECT * FROM history_table")
    fun getHistoryKeyList(): Flow<List<HistoryKey>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addKey(key: HistoryKey)

    @Query("DELETE FROM history_table")
    suspend fun deleteAll()
}