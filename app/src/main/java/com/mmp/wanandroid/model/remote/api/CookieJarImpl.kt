package com.mmp.wanandroid.model.remote.api

import com.mmp.wanandroid.utils.SPreference
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieJarImpl : CookieJar {

    private var result by SPreference("cookie","")


    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {

        val cookies = mutableListOf<Cookie>()
        if (result.isNotEmpty()){
            result.split(";").forEach {
                val cookie = Cookie.parse(url,it)
                if (cookie != null) {
                    cookies.add(cookie)
                }
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