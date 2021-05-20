package com.mmp.wanandroid.ui.base

import androidx.lifecycle.MutableLiveData
import com.mmp.wanandroid.api.BaseResponse
import com.mmp.wanandroid.data.DataState
import com.mmp.wanandroid.utils.StateLiveData

open class BaseRepository {
    suspend fun <T : Any> executeResp(
        stateLiveData: StateLiveData<T>,
        block: suspend () -> BaseResponse<T>
    ){
        var baseResponse = BaseResponse<T>()
        try {
            baseResponse.dataState = DataState.STATE_LOADING
            val invoke = block.invoke()
            baseResponse = invoke
            if (baseResponse.errorCode == 0){
                if (baseResponse.data == null || (baseResponse.data is List<*> && (baseResponse.data as List<*>).size == 0)){
                    baseResponse.dataState = DataState.STATE_EMPTY
                }else{
                    baseResponse.dataState = DataState.STATE_SUCCESS
                }
            }else{
                baseResponse.dataState = DataState.STATE_FAILED
            }
        }catch (e: Exception){
            baseResponse.dataState = DataState.STATE_ERROR
            baseResponse.error = e
        }finally {
            stateLiveData.postValue(baseResponse)
        }
    }
}