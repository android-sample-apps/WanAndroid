package com.mmp.wanandroid.extension

import timber.log.Timber

/**
 * @author chen
 * @date 2021/8/20
 * des 安全的类型转换
 **/

fun <T> Any.safeAs(): T?{
    try {
        return this as T
    }catch (e: TypeCastException){
        Timber.e(e)
    }
    return null
}