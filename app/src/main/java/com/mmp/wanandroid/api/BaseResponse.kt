package com.mmp.wanandroid.api

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")val data: T,
    @SerializedName("errorCode")val errorCode: Int,
    @SerializedName("errorMsg")val errorMsg: String)