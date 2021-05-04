package com.mmp.wanandroid.api

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(val data: T, val errorCode: Int, val errorMsg: String)