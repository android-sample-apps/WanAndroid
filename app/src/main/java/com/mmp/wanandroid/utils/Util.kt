package com.mmp.wanandroid.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mmp.wanandroid.ui.base.MyApplication
import kotlinx.coroutines.launch
import java.lang.Exception


inline fun <reified T> Context.start(bundle: Bundle){
    val intent = Intent(this,T::class.java)
    intent.putExtras(bundle)
    startActivity(intent)
}

inline fun <reified T> Context.start(){
    val intent = Intent(this,T::class.java)
    startActivity(intent)
}


fun Context.toast(msg: String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}

fun toast(msg: String){
    MyApplication.getContext().toast(msg)
}
//
//fun ViewModel.listScrolled(visibleItemCount: Int,lastVisibleItemPosition: Int,totalItemCount: Int,block: suspend () -> Unit){
//    if (visibleItemCount + lastVisibleItemPosition + Const.VISIBLE_THRESHOLD >= totalItemCount){
//        viewModelScope.launch {
//            block()
//        }
//    }
//}

