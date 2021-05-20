package com.mmp.wanandroid.utils

import androidx.lifecycle.MutableLiveData
import com.mmp.wanandroid.api.BaseResponse

class StateLiveData<T> : MutableLiveData<BaseResponse<T>>() {
}