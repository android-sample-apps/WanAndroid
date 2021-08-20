package com.mmp.wanandroid.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mmp.wanandroid.model.remote.api.WanAndroidService

class ArticleDataSource : PagingSource<Int,Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 0
//            if (page == 0){
//                val result = WanAndroidService.create().getTopArticle()
//                val result1 = WanAndroidService.create().getHomeArticle(page)
//                val list = mutableListOf<Article>()
//                list.addAll(result.data!!)
//                list.addAll(result1.data!!.datas)
//                LoadResult.Page(
//                        data = list,
//                        prevKey = null,
//                        nextKey = page + 1
//                )
//            }else{
//                val result = WanAndroidService.create().getHomeArticle(page)
//                LoadResult.Page(
//                        data = result.data!!.datas,
//                        prevKey = null,
//                        nextKey = if (result.data!!.curPage == result.data!!.pageCount) null else page + 1
//                )
//            }
            val result = WanAndroidService.create().getHomeArticle(page)
            LoadResult.Page(
                    data = result.data!!.datas,
                    prevKey = null,
                    nextKey = if (result.data!!.curPage == result.data!!.pageCount) null else page + 1
            )
        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }
}