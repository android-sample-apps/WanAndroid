package com.mmp.wanandroid.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.kingja.loadsir.core.LoadService
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.utils.toast
import timber.log.Timber

fun <T> LiveData<DataStatus<T>>.myObserver(
    owner: LifecycleOwner, loadService: LoadService<Any>? = null, failure: (Throwable) -> Unit = {
        Timber.e(it)
        toast("$it")
    },error: (String) -> Unit = {}, success: (T) -> Unit
) {
    observe(owner) {
        loadService?.showWithConvertor(it)
        when (it) {
            is DataStatus.Success<T> -> success(it.data)
            is DataStatus.Failure -> failure(it.e)
            is DataStatus.Error -> error(it.errorMsg)
            else -> {
            }
        }
    }
}
