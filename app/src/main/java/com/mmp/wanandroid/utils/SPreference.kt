package com.mmp.wanandroid.utils

import android.content.Context
import android.content.SharedPreferences
import com.mmp.wanandroid.ui.base.MyApplication
import kotlin.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SPreference<T>(private val name: String,private val default: T) : ReadWriteProperty<Any?,T>{

    companion object{
        private val sPreference = MyApplication.getContext().getSharedPreferences(
                MyApplication.getContext().packageName,
                Context.MODE_PRIVATE)

    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setSpValue(name,value)
    }

    private fun setSpValue(name: String,value: T) = sPreference.edit().apply {
        when(value){
            is String -> putString(name,value)
            is Long -> putLong(name,value)
            is Boolean -> putBoolean(name,value)
            is Int -> putInt(name,value)
            is Float -> putFloat(name,value)
            else -> throw IllegalArgumentException("not this type")
        }
    }.apply()

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSpValue(name,default)
    }

    private fun getSpValue(name: String,default: T) = sPreference.run {
        when(default){
            is String -> getString(name,default)
            is Long -> getLong(name,default)
            is Boolean -> getBoolean(name,default)
            is Int -> getInt(name,default)
            is Float -> getFloat(name,default)
            else -> throw IllegalArgumentException("not this type")
        } as T
    }

}