package com.mmp.wanandroid.ui.base

import com.mmp.wanandroid.model.remote.api.BaseResponse
import com.mmp.wanandroid.model.data.DataState
import com.mmp.wanandroid.model.loacl.room.MyRoomDatabase
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.model.remote.api.WanAndroidService
import com.mmp.wanandroid.utils.StateLiveData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

open class BaseRepository {

    val wanAndroidService = WanAndroidService.create()

    val database = MyRoomDatabase.getDatabase()

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

    fun<T> execute(block: suspend () -> BaseResponse<T>) = flow<DataStatus<T>> {
        val response = block()
        if (response.errorCode == 0){
            if (response.data == null || (response.data is List<*> && (response.data as List<*>).size == 0)){
                emit(DataStatus.Empty)
            }else{
                emit(DataStatus.Success(response.data!!))
            }
        }else{
            emit(DataStatus.Error(response.errorMsg))
        }
    }.catch { e ->
        emit(DataStatus.Failure(e))
    }.conflate()
}