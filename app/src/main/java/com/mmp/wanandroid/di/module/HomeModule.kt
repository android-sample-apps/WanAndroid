package com.mmp.wanandroid.di.module

import android.content.Context
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.ui.home.adapter.ArticleAdapter
import com.mmp.wanandroid.ui.home.adapter.ImageAdapter
import com.youth.banner.adapter.BannerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped

/**
 * @author chen
 * @date 2021/8/19
 * des
 **/
@Module
@InstallIn(FragmentComponent::class)
class HomeModule {


    @Provides
    fun provideArticleList(): List<Article> = arrayListOf()


    @Provides
    fun provideBannerList(): List<Banner> = arrayListOf()



}