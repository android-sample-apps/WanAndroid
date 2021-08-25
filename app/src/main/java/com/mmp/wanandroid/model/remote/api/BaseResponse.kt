package com.mmp.wanandroid.model.remote.api

import com.mmp.wanandroid.model.data.DataState

class BaseResponse<T>{
    var errorCode = -1
    var errorMsg: String = ""
    var data: T? = null
        private set
    var dataState: DataState? = null
    var error: Throwable? = null
    val isSuccess: Boolean
        get() = errorCode == 0
}