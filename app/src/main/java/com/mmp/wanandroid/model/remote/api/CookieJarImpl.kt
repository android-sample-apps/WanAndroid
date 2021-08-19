package com.mmp.wanandroid.model.remote.api

import com.mmp.wanandroid.utils.SPreference
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @author chen
 * @date 2021/8/19
 * des cookie持久化
 **/
class CookieJarImpl : CookieJar {

    var result by SPreference("cookie","")

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return result.split(";").map { str ->
            val build = Cookie.Builder()
            str.split("=").also {
                when(it[0]){
                    "name" -> build.name(it[1])
                    "value" -> build.value(it[1])
                    "expiresAt" -> build.expiresAt(it[1].toLong())
                    "domain" -> build.domain(it[1])
                    "path" -> build.path(it[1])
                    "hostOnlyDomain" -> build.hostOnlyDomain(it[1])
                    "secure" -> build.secure()
                    "httpOnly" -> build.httpOnly()
                }
            }
            build.build()
        }

    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {

        if (url.toString().contains("user/login") || url.toString().contains("user/register")){
            val str = StringBuilder()
            for (cookie in cookies){
                str.append(cookie).append(";")
            }
            //减去最后一个“;”
            str.deleteCharAt(str.length - 1)
            result = str.toString()
        }
    }
}