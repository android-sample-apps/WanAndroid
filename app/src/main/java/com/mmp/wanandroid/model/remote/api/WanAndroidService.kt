package com.mmp.wanandroid.model.remote.api

import com.mmp.wanandroid.model.data.*
import com.mmp.wanandroid.utils.HttpLogger
import com.mmp.wanandroid.utils.SPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.StringBuilder

interface WanAndroidService {


    companion object {
        private const val BASE_URL = "https://www.wanandroid.com"

        private val okHttpClient: OkHttpClient
            get() = OkHttpClient.Builder()
                .cookieJar(CookieJarImpl())
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun create(): WanAndroidService = retrofit.create(WanAndroidService::class.java)


        private fun logIntercept(): Interceptor {
            return HttpLoggingInterceptor(HttpLogger())
        }

        private fun cookieIntercept(): Interceptor {
            return Interceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                val requestUrl = request.url().toString()
                if (requestUrl.contains("user/login") || requestUrl.contains("user/register")) {
                    val mCookie = response.headers("Set-Cookie")
                    saveCookie(parseCookie(mCookie))
                }
                response
            }
        }

        private fun loginIntercept(): Interceptor {
            return Interceptor { chain ->
                val request = chain.request()
                val builder = request.newBuilder()
                val domain = request.url().host()

                if (domain.isNotEmpty()) {
                    val mCookie by SPreference("cookie", "")
                    if (mCookie.isNotEmpty()) {
                        builder.addHeader("Cookie", mCookie)
                    }
                }
                val response = chain.proceed(builder.build())
                response
            }
        }


        private fun parseCookie(it: List<String>): String {
            if (it.isEmpty()) {
                return ""
            }

            val stringBuilder = StringBuilder()

            it.forEach { cookie ->
                stringBuilder.append(cookie).append(";")
            }

            if (stringBuilder.isEmpty()) {
                return ""
            }
            //?????????";"??????
            return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString()
        }

        private fun saveCookie(parseCookie: String) {
            var resutl: String by SPreference("cookie", parseCookie)
            resutl = parseCookie
        }
    }

    /*??????????????????*/
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): BaseResponse<ArticleData>

    /*??????banner*/
    @GET("/banner/json")
    suspend fun getBanner(): BaseResponse<List<Banner>>

    /*????????????*/
    @GET("article/top/json")
    suspend fun getTopArticle(): BaseResponse<List<Article>>

    /*????????????*/
    @GET("/tree/json")
    suspend fun getTree(): BaseResponse<List<SystemTree>>

    /*??????????????????*/
    @GET("/article/list/{page}/json")
    suspend fun getSystemArticle(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ArticleData>

    /*????????????*/
    @GET("/hotkey/json")
    suspend fun getHotKey(): BaseResponse<List<HotKey>>

//    @GET("/wenda/list/1/json ")
//    suspend fun getWenda() : BaseResponse<List<Article>>

    /*??????*/
    @POST("/article/query/{page}/json")
    suspend fun getSearchArticle(
        @Path("page") page: Int,
        @Query("k") k: String
    ): BaseResponse<ArticleData>


    /*??????*/
    @POST("/user/login")
    suspend fun getLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): BaseResponse<User>

    /*??????*/
    @POST("/user/register")
    suspend fun getRegister(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): BaseResponse<Any>

    /*??????*/
    @GET("/user/logout/json")
    suspend fun getLogout(): BaseResponse<Any>

    /*???????????????*/
    @GET("/coin/rank/1/json")
    suspend fun getRank(): BaseResponse<RankData>

    /*????????????*/
    @GET("/lg/coin/userinfo/json")
    suspend fun getUserRank(): BaseResponse<Rank>

    /*????????????????????????*/
    @GET("/lg/coin/list/{page}/json")
    suspend fun getCoinList(@Path("page") page: Int): BaseResponse<IntegralData>

    /*????????????????????????*/
    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectArticle(@Path("page") page: Int): BaseResponse<ArticleData>

    /*??????????????????*/
    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): BaseResponse<Any>

    /*??????????????????*/
    @POST("/lg/collect/add/json")
    suspend fun collectOutStation(
        @Query("title") title: String,
        @Query("author") author: String,
        @Query("link") link: String
    ): BaseResponse<Any>

    /*????????????*/
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): BaseResponse<Any>

    /*??????????????????*/
    @GET("/lg/collect/usertools/json")
    suspend fun getCollectTools(): BaseResponse<List<Web>>

    /*????????????*/
    @POST("/lg/collect/addtool/json")
    suspend fun collectTools(
        @Query("name") name: String,
        @Query("link") link: String
    ): BaseResponse<Any>

    /*??????????????????*/
    @POST("/lg/collect/deletetool/json")
    suspend fun unCollectTools(@Query("id") id: Int): BaseResponse<Any>

    /*????????????*/
    @GET("/navi/json")
    suspend fun getNavList(): BaseResponse<List<NavBean>>

    /*????????????*/
    @GET("/project/tree/json")
    suspend fun getProjectTree(): BaseResponse<List<ProjectTree>>

    /*??????????????????*/
    @GET("/project/list/{page}/json")
    suspend fun getProjectList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseResponse<ProjectBean>

}