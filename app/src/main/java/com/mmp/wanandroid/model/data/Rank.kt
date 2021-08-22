package com.mmp.wanandroid.model.data

data class Rank(val coinCount: Int,
                val rank: String,
                val userId: Int,
                val username: String)

data class RankData(val curPage: Int, val datas: List<Rank>,val pageCount: Int)