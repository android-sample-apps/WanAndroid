package com.mmp.wanandroid.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history_table")
data class HistoryKey(@PrimaryKey(autoGenerate = true) val id: Int,@ColumnInfo(name = "name")val name: String)