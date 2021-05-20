package com.mmp.wanandroid.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.liveData
import com.mmp.wanandroid.ui.base.MyApplication
import java.lang.Exception


inline fun <reified T> Context.start(bundle: Bundle){
    val intent = Intent(this,T::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}


fun<T> fire(block:suspend () -> Result<T>) = liveData {
    val result = try {
        block()
    }catch (e: Exception){
        Result.failure(e)
    }
    emit(result)
}


fun Context.toast(msg: String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}

fun toast(msg: String){
    MyApplication.getContext().toast(msg)
}

