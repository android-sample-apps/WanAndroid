package com.mmp.wanandroid.ui.base

import android.view.View
import androidx.lifecycle.Observer
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.mmp.wanandroid.model.remote.api.BaseResponse
import com.mmp.wanandroid.model.data.DataState
import com.mmp.wanandroid.ui.base.callback.EmptyCallback
import com.mmp.wanandroid.ui.base.callback.ErrorCallback
import com.mmp.wanandroid.ui.base.callback.LoadingCallback

abstract class IStateObserver<T>(view: View?) : Observer<BaseResponse<T>>, Callback.OnReloadListener {

    private var mLoadService: LoadService<Any>? = null

    init {
        if (view != null){
            mLoadService = LoadSir.getDefault().register(view,this,
                Convertor<BaseResponse<T>>{ t ->
                    var resultCode: Class<out Callback> = SuccessCallback::class.java
                    when(t?.dataState){
                        DataState.STATE_LOADING,DataState.STATE_CREATE -> resultCode = LoadingCallback::class.java
                        DataState.STATE_SUCCESS -> resultCode = SuccessCallback::class.java
                        DataState.STATE_EMPTY -> resultCode = EmptyCallback::class.java
                        DataState.STATE_ERROR,DataState.STATE_FAILED -> resultCode = ErrorCallback::class.java
                        DataState.STATE_COMPLETED,DataState.STATE_UNKNOWN -> {}
                        else -> {}
                    }
                    resultCode
                })
        }
    }

    override fun onChanged(t: BaseResponse<T>?) {
        when(t?.dataState){
            DataState.STATE_SUCCESS ->{
                onDataChange(t.data)
            }

            DataState.STATE_EMPTY -> {
                onDataEmpty()

            }

            DataState.STATE_FAILED,DataState.STATE_ERROR -> {
                t.error?.let { onError(it) }
            }

            else -> {}
        }

        mLoadService?.showWithConvertor(t)
    }

    open fun onDataChange(data: T?){

    }

    open fun onDataEmpty(){}

    open fun onError(e: Throwable){}
}