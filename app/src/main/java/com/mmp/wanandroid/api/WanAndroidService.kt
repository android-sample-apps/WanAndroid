package com.mmp.wanandroid.api

import com.mmp.wanandroid.data.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface WanAndroidService {

    companion object{
        private const val BASE_URL = "https://www.wanandroid.com"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun create() : WanAndroidService = retrofit.create(WanAndroidService::class.java)
    }

    /*首页文章列表*/
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page")page: Int) : BaseResponse<Data>

    /*首页banner*/
    @GET("/banner/json")
    suspend fun getBanner(): BaseResponse<List<Banner>>

    /*置顶文章*/
    @GET("article/top/json")
    suspend fun getTopArticle(): BaseResponse<List<Article>>

    @GET("/tree/json")
    suspend fun getTree() : BaseResponse<List<Tree>>

    @GET("/article/list/{page}/json")
    suspend fun getArticle(@Path("page") page: Int,@Query("cid")cid: Int) : BaseResponse<Data>

    @GET("/hotkey/json")
    suspend fun getHotKey() : BaseResponse<List<HotKey>>

    @GET("/wenda/list/1/json ")
    suspend fun getWenda() : BaseResponse<Data>
}