package com.mmp.wanandroid.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mmp.wanandroid.api.WanAndroidService
import java.lang.Exception

class HomeArticlePagingSource : PagingSource<Int,Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: 0
            val response = WanAndroidService.create().getHomeArticle(position)
            val prevKey = if(position > 0) position -1 else null
            val articleList = response.data.datas
            val nextKey = if (articleList.isNotEmpty()) position +1 else null
            LoadResult.Page(articleList,prevKey,nextKey)
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}

class ArticlePagingSource(private val cid: Int) : PagingSource<Int,Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: 0
            val response = WanAndroidService.create().getArticle(position,cid)
            val prevKey = if(position > 0) position -1 else null
            val articleList = response.data.datas
            val nextKey = if (articleList.isNotEmpty()) position +1 else null
            LoadResult.Page(articleList,prevKey,nextKey)
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}