package com.mmp.wanandroid.model.loacl.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo")
data class Todo(@PrimaryKey(autoGenerate = false)@ColumnInfo(name = "user_id") val userId: Int,
                @ColumnInfo(name = "create_date") val createDate: Calendar = Calendar.getInstance(),
                @ColumnInfo(name = "start_date") val startDate: Calendar,
//                @ColumnInfo(name = "end_date") val endDate: Calendar,
                @ColumnInfo(name = "content") val content: String)