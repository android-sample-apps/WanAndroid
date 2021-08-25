package com.mmp.wanandroid.model.remote.api

import com.mmp.wanandroid.utils.SPreference
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieJarImpl : CookieJar {

    var result by SPreference("cookie","")


    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {

        val cookies = mutableListOf<Cookie>()
        if (result.isNotEmpty()){
            result.split(";").forEach {
                val builder = Cookie.Builder()
                val list = it.split("=")
                for (i in list.indices step 2){
                    when(list[i]){
                        "name" -> builder.name(list[i+1])
                        "value" -> builder.value(list[i+1])
                        "expiresAt" -> builder.expiresAt(list[i+1].toLong())
                        "domain" -> builder.domain(list[i+1])
                        "path" -> builder.path(list[i+1])
                        "secure" -> builder.secure()
                        "httpOnly" -> builder.httpOnly()
                    }
                }
                cookies.add(builder.build())
            }
        }
        return cookies
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val responseUrl = url.toString()
        if (responseUrl.contains("user/login") || responseUrl.contains("user/register")){
            val str = StringBuilder()
            for (cookie in cookies){
                str.append(cookie).append(";")
            }
            //删去最后一个“;”
            str.deleteCharAt(str.length-1)
            result = str.toString()
        }
    }
}