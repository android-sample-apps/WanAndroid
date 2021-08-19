package com.mmp.wanandroid.di.module

import android.content.Context
import androidx.room.Room
import com.mmp.wanandroid.room.HistoryKeyDao
import com.mmp.wanandroid.room.MyRoomDatabase
import com.mmp.wanandroid.room.ProjectTreeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * @author chen
 * @date 2021/8/19
 * des  提供room实例
 **/
@InstallIn(ApplicationComponent::class)
@Module
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MyRoomDatabase = Room.databaseBuilder(
        appContext,
        MyRoomDatabase::class.java,
        "database"
    ).build()


    @Provides
    fun provideHistoryKeyDao(database: MyRoomDatabase): HistoryKeyDao = database.historyKeyDao()


    @Provides
    fun provideProjectTree(database: MyRoomDatabase): ProjectTreeDao = database.projectTreeDap()
}