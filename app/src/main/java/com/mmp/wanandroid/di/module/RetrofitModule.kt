package com.mmp.wanandroid.di.module

import com.mmp.wanandroid.model.remote.api.CookieJarImpl
import com.mmp.wanandroid.model.remote.api.WanAndroidService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author chen
 * @date 2021/8/19
 * des 提供retrofit,okhttp的实例
 **/
@InstallIn(ApplicationComponent::class)
@Module
object RetrofitModule {

    private const val BASE_URL = "https://www.wanandroid.com"

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .cookieJar(CookieJarImpl())
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): WanAndroidService = retrofit.create(WanAndroidService::class.java)

}