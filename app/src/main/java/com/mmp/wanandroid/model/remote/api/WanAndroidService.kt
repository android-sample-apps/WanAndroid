package com.mmp.wanandroid.model.remote.api

import com.mmp.wanandroid.data.*
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


    companion object{
        private const val BASE_URL = "https://www.wanandroid.com"

        private val okHttpClient: OkHttpClient
            get() = OkHttpClient.Builder()
                    .addInterceptor(cookieIntercept())
                    .addInterceptor(loginIntercept())
                    .addNetworkInterceptor(logIntercept())
                    .build()

        private val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        fun create() : WanAndroidService = retrofit.create(WanAndroidService::class.java)


        private fun logIntercept(): Interceptor{
            return HttpLoggingInterceptor(HttpLogger())
        }

        private fun cookieIntercept(): Interceptor{
            return Interceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                val requestUrl = request.url.toString()
                if (requestUrl.contains("user/login") || requestUrl.contains("user/register")){
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
                val domain = request.url.host

                if(domain.isNotEmpty()){
                    val mCookie by SPreference("cookie","")
                    if(mCookie.isNotEmpty()){
                        builder.addHeader("Cookie",mCookie)
                    }
                }
                val response = chain.proceed(builder.build())
                response
            }
        }



        private fun parseCookie(it: List<String>): String {
            if(it.isEmpty()){
                return ""
            }

            val stringBuilder = StringBuilder()

            it.forEach { cookie ->
                stringBuilder.append(cookie).append(";")
            }

            if(stringBuilder.isEmpty()){
                return ""
            }
            //末尾的";"去掉
            return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString()
        }

        private fun saveCookie(parseCookie: String) {
            var resutl: String by SPreference("cookie",parseCookie)
            resutl = parseCookie
        }
    }

    /*首页文章列表*/
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page")page: Int) : BaseResponse<ArticleData>

    /*首页banner*/
    @GET("/banner/json")
    suspend fun getBanner(): BaseResponse<List<Banner>>

    /*置顶文章*/
    @GET("article/top/json")
    suspend fun getTopArticle(): BaseResponse<List<Article>>

    /*体系数据*/
    @GET("/tree/json")
    suspend fun getTree(): BaseResponse<List<SystemTree>>

    /*体系下的文章*/
    @GET("/article/list/{page}/json")
    suspend fun getSystemArticle(@Path("page") page: Int,
                           @Query("cid")cid: Int): BaseResponse<ArticleData>

    /*搜索热词*/
    @GET("/hotkey/json")
    suspend fun getHotKey(): BaseResponse<List<HotKey>>

//    @GET("/wenda/list/1/json ")
//    suspend fun getWenda() : BaseResponse<List<Article>>

    /*搜索*/
    @POST("/article/query/{page}/json")
    suspend fun getSearchArticle(@Path("page")page: Int,
                                 @Query("k")k: String): BaseResponse<ArticleData>


    /*登录*/
    @POST("/user/login")
    suspend fun getLogin(@Query("username")username: String,
                         @Query("password")password: String): BaseResponse<User>

    /*注册*/
    @POST("/user/register")
    suspend fun getRegister(@Query("username")username: String,
                            @Query("password")password: String,
                            @Query("repassword")repassword: String): BaseResponse<Any>

    /*登出*/
    @GET("/user/logout/json")
    suspend fun getLogout(): BaseResponse<Any>

    /*积分排行榜*/
    @GET("/coin/rank/1/json")
    suspend fun getRank(): BaseResponse<RankData>

    /*个人积分*/
    @GET("/lg/coin/userinfo/json")
    suspend fun getUserRank(): BaseResponse<Rank>

    /*个人积分获取列表*/
    @GET("/lg/coin/list/{page}/json")
    suspend fun getCoinList(@Path("page")page: Int): BaseResponse<IntegralData>

    /*个人收藏文章列表*/
    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectArticle(@Path("page")page: Int): BaseResponse<ArticleData>

    /*收藏站内文章*/
    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id")id: Int): BaseResponse<Any>

    /*收藏站外文章*/
    @POST("/lg/collect/add/json")
    suspend fun collectOutStation(@Query("title")title: String,
                                  @Query("author")author: String,
                                  @Query("link")link: String): BaseResponse<Any>

    /*取消收藏*/
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id")id: Int): BaseResponse<Any>

    /*收藏网站列表*/
    @GET("/lg/collect/usertools/json")
    suspend fun getCollectTools(): BaseResponse<List<Web>>

    /*收藏网址*/
    @POST("/lg/collect/addtool/json")
    suspend fun collectTools(@Query("name")name: String,
                             @Query("link")link: String): BaseResponse<Any>

    /*删除收藏网址*/
    @POST("/lg/collect/deletetool/json")
    suspend fun unCollectTools(@Query("id")id: Int): BaseResponse<Any>

    /*导航数据*/
    @GET("/navi/json")
    suspend fun getNavList() : BaseResponse<List<NavBean>>

    /*项目分类*/
    @GET("/project/tree/json")
    suspend fun getProjectTree() : BaseResponse<List<ProjectTree>>

    /*项目列表数据*/
    @GET("/project/list/{page}/json")
    suspend fun getProjectList(@Path("page")page: Int,@Query("cid")cid: Int) : BaseResponse<ProjectBean>

}