package com.mmp.wanandroid.ext

import android.app.Activity
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.base.callback.EmptyCallback
import com.mmp.wanandroid.ui.base.callback.ErrorCallback
import com.mmp.wanandroid.ui.base.callback.LoadingCallback

fun View.registerLoad(reload: () -> Unit): LoadService<Any> = LoadSir.getDefault().register(this,
    Callback.OnReloadListener { v -> v?.setOnClickListener { reload() } },
    Convertor<DataStatus<Any>> { t ->
        when(t){
            is DataStatus.Empty -> EmptyCallback::class.java
            is DataStatus.Failure -> ErrorCallback::class.java
            is DataStatus.Success<*> -> SuccessCallback::class.java
            is DataStatus.Loading -> LoadingCallback::class.java
            is DataStatus.Error -> SuccessCallback::class.java
            else -> ErrorCallback::class.java
        }
    })

fun<T> Activity.registerLoad(reload: () -> Unit): LoadService<Any> = LoadSir.getDefault().register(this,
    Callback.OnReloadListener { v -> v?.setOnClickListener { reload() } },
    Convertor<DataStatus<T>> { t ->
        when(t){
            is DataStatus.Empty -> EmptyCallback::class.java
            is DataStatus.Failure -> ErrorCallback::class.java
            is DataStatus.Success<*> -> SuccessCallback::class.java
            is DataStatus.Loading -> LoadingCallback::class.java
            is DataStatus.Error -> SuccessCallback::class.java
            else -> ErrorCallback::class.java
        }
    })