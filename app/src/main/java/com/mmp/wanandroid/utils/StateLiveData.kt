package com.mmp.wanandroid.utils

import androidx.lifecycle.MutableLiveData
import com.mmp.wanandroid.model.remote.api.BaseResponse

class StateLiveData<T> : MutableLiveData<BaseResponse<T>>() {
}