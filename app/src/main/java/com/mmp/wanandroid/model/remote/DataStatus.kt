package com.mmp.wanandroid.model.remote

/**
 * @author chen
 * @date 2021/8/18
 * des
 **/
sealed class DataStatus<out R>{
    object Loading : DataStatus<Nothing>()
    class Success<out T>(val data: T): DataStatus<T>()
    class Failure(val t: Throwable): DataStatus<Nothing>()
    object Empty: DataStatus<Nothing>()
    object None: DataStatus<Nothing>()
}




