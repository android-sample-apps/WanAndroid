package com.mmp.wanandroid.model.remote

import com.mmp.wanandroid.data.DataState

class BaseResponse<T>{
    var errorCode = -1
    var errorMsg: String? = null
    var data: T? = null
        private set
    var dataState: DataState? = null
    var error: Throwable? = null
    val isSuccess: Boolean
        get() = errorCode == 0
}