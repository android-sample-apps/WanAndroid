package com.mmp.wanandroid.data

import com.blankj.utilcode.util.CacheMemoryUtils

object LocalSource {

    private const val SAVE_TIME = 86400

    private val cacheMemoryUtils = CacheMemoryUtils.getInstance()

    fun putCache(key: String,value: Any){
        cacheMemoryUtils.put(key, value, SAVE_TIME)
    }

    fun<T> getCache(key: String): T{
        return  cacheMemoryUtils.get(key)
    }
}