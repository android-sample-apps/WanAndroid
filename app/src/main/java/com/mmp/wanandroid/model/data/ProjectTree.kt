package com.mmp.wanandroid.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "project_tree")
data class ProjectTree(@PrimaryKey val id: Int,@ColumnInfo(name = "name") val name: String)