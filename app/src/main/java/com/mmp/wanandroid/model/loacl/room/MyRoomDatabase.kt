package com.mmp.wanandroid.model.loacl.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mmp.wanandroid.model.data.ProjectTree
import com.mmp.wanandroid.ui.base.MyApplication


@Database(entities = [HistoryKey::class,ProjectTree::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyRoomDatabase : RoomDatabase(){

    abstract fun historyKeyDao(): HistoryKeyDao

    abstract fun projectTreeDap(): ProjectTreeDao

    companion object{
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(): MyRoomDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                        MyApplication.getContext(),
                        MyRoomDatabase::class.java,
                        "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}