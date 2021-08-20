package com.mmp.wanandroid.model.remote

/**
 * @author chen
 * @date 2021/8/20
 * des 数据状态
 **/
sealed class DataStatus<out R>{

    class Success<T>(val data: T): DataStatus<T>()

    class Failure(val e: Throwable): DataStatus<Nothing>()

    object Loading: DataStatus<Nothing>()

    object Empty: DataStatus<Nothing>()

}
