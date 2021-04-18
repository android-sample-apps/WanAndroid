package com.mmp.wanandroid.data

import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mmp.wanandroid.api.WanAndroidService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    private val wanAndroidService = WanAndroidService.create()

    fun getHomeArticleData() : Flow<PagingData<Article>>{
        return Pager(config = PagingConfig(50),pagingSourceFactory = {HomeArticlePagingSource()}).flow
    }

    fun getArticleData(cid: Int) : Flow<PagingData<Article>>{
        return Pager(config = PagingConfig(50),pagingSourceFactory = {ArticlePagingSource(cid)}).flow
    }

    private fun <T> fire(block: suspend () -> Result<T>) = liveData<Result<T>>(Dispatchers.IO) {
        val result = try {
            block()
        }catch (e: Exception){
            Result.failure(e)
        }
        emit(result)
    }

    fun getTree() = fire {
        coroutineScope {
            val tree = wanAndroidService.getTree().data
            Result.success(tree)
        }
    }
}