package com.mmp.wanandroid.network

/**
 * @author chen
 * @date 2021/8/18
 * des
 **/
sealed class DataStatus

object Loading : DataStatus()
class Success<T>(val data: T?): DataStatus()
class Failure(val t: Throwable): DataStatus()
object Empty: DataStatus()
object None: DataStatus()


