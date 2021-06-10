package com.mmp.wanandroid.data

import com.blankj.utilcode.util.CacheDiskUtils
import com.blankj.utilcode.util.CacheMemoryUtils
import com.mmp.wanandroid.utils.Const
import java.io.Serializable

object LocalSource {

    private const val SAVE_TIME = 86400

    fun<T: Serializable> putCache(value: List<T>){
        when(value[0]){
            is Article -> {
                CacheDiskUtils.getInstance().put(Const.HOME_ARTICLE,HomeArticleCache(value as List<Article>),
                    SAVE_TIME)
            }
        }

    }

    fun<T: Serializable> getCache(key: String): List<T>?{
        return when(val serializable = CacheDiskUtils.getInstance().getSerializable(key)){
            is HomeArticleCache? ->{
                serializable?.articleList as List<T>?
            }
            else -> emptyList()
        }
    }
}