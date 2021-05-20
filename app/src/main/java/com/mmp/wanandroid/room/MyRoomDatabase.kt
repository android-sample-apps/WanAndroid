package com.mmp.wanandroid.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mmp.wanandroid.ui.base.MyApplication


@Database(entities = [HistoryKey::class],version = 1,exportSchema = false)
abstract class MyRoomDatabase : RoomDatabase(){

    abstract fun historyKeyDao(): HistoryKeyDao

    companion object{
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(): MyRoomDatabase{
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