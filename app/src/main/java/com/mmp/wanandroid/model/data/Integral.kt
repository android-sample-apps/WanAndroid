package com.mmp.wanandroid.model.data

data class Integral(val coinCount: Int,val desc: String,val reason: String)


data class IntegralData(val curPage: Int,val datas: List<Integral>,val pageCount: Int)