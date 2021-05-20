package com.mmp.wanandroid.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mmp.wanandroid.api.WanAndroidService
import com.mmp.wanandroid.data.*
import com.mmp.wanandroid.room.HistoryKey
import com.mmp.wanandroid.room.MyRoomDatabase
import com.mmp.wanandroid.ui.base.BaseRepository
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.flow.Flow

//object HomeRepository {
//
//
//    private val articleCache = mutableListOf<Article>()
//
//    private val searchCache = mutableListOf<Article>()
//
//    private val historyKeyDao = MyRoomDatabase.getDatabase().historyKeyDao()
//
//    private var searchPage = 0
//
//    private var page = 0
//
//
//    private val wanAndroidService = WanAndroidService.create()
//
//    fun getHomeArticle(): LiveData<Result<MutableList<Article>>> {
//        page = 0
//        return requestData()
//    }
//
//    fun getMoreArticle(): LiveData<Result<MutableList<Article>>> {
//        page++
//        return requestData()
//    }
//
//    private fun requestData() = fire {
//        if (page == 0){
//            coroutineScope {
//                val deferredArticle = async {
//                    wanAndroidService.getHomeArticle(page)
//                }
//                val deferredTopArticle = async {
//                    wanAndroidService.getTopArticle()
//                }
//                val articleResponse = deferredArticle.await()
//                val topArticleResponse = deferredTopArticle.await()
//                if (articleResponse.errorCode == 0 && topArticleResponse.errorCode == 0){
//                    articleCache.addAll(topArticleResponse.data)
//                    articleCache.addAll(articleResponse.data.datas)
//                    Result.success(articleCache)
//                }else{
//                     Result.failure(RuntimeException(articleResponse.errorMsg))
//                }
//            }
//        }else{
//            val response = wanAndroidService.getHomeArticle(page)
//            if (response.errorCode == 0){
//                articleCache.addAll(response.data.datas)
//                Result.success(articleCache)
//            }else{
//                Result.failure(RuntimeException(response.errorMsg))
//            }
//        }
//    }
//
//    fun getBanner(): LiveData<Result<List<Banner>>> = fire {
//        val response = wanAndroidService.getBanner()
//        if (response.errorCode == 0){
//            val data = response.data
//            Result.success(data)
//        }else{
//            Result.failure(RuntimeException(response.errorMsg))
//        }
//    }
//
//
//    fun getHotKey(): LiveData<Result<List<HotKey>>> = fire {
//        val response = wanAndroidService.getHotKey()
//        if (response.errorCode == 0){
//            Result.success(response.data)
//        }else{
//            Result.failure(RuntimeException(response.errorMsg))
//        }
//    }
//
//    fun getSearchArticle(k: String): LiveData<Result<List<Article>>> {
//        searchPage = 0
//        return requestSearch(k)
//    }
//
//    fun getMoreSearch(k: String):LiveData<Result<List<Article>>>{
//        searchPage++
//        return requestSearch(k)
//    }
//
//    private fun requestSearch(k: String) = fire {
//        val response = wanAndroidService.getSearchArticle(k, searchPage)
//        if (response.errorCode == 0){
//            searchCache.addAll(response.data.datas)
//            Result.success(searchByName(k))
//        }else{
//            Result.failure(RuntimeException(response.errorMsg))
//        }
//    }
//
//    private fun searchByName(k: String): List<Article>{
//        return searchCache.filter {
//            it.title.contains(k,true)
//        }
//    }
//
//    fun getKeyList() = historyKeyDao.getHistoryKeyList()
//
//    suspend fun addKey(historyKey: HistoryKey) =  historyKeyDao.addKey(historyKey)
//
//    suspend fun clean() = historyKeyDao.deleteAll()
//
//}

class HomeRepository : BaseRepository(){

    private var pager = 0

    private val wanAndroidService = WanAndroidService.create()

    private val historyKeyDao = MyRoomDatabase.getDatabase().historyKeyDao()

    fun getHomeArticle(): Flow<PagingData<Article>>{
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE,prefetchDistance = PREFETCH_DISTANCE)){
            ArticleDataSource()
        }.flow
    }

    suspend fun getBanner(bannerLiveData: StateLiveData<List<Banner>>) = executeResp(bannerLiveData){
        wanAndroidService.getBanner()
    }

    suspend fun getHotKey(hotKeyLiveData: StateLiveData<List<HotKey>>) = executeResp(hotKeyLiveData){
        wanAndroidService.getHotKey()
    }

    suspend fun getSearch(articleLiveData: StateLiveData<ArticleData>,k: String) = executeResp(articleLiveData){
        pager = 0
        wanAndroidService.getSearchArticle(pager,k)
    }

    suspend fun getSearchMore(articleLiveData: StateLiveData<ArticleData>, k: String) = executeResp(articleLiveData){
        wanAndroidService.getSearchArticle(pager++,k)
    }

    fun getKeyList() = historyKeyDao.getHistoryKeyList()

    suspend fun addKey(historyKey: HistoryKey) =  historyKeyDao.addKey(historyKey)

    suspend fun clean() = historyKeyDao.deleteAll()

    companion object{
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 10
    }
}