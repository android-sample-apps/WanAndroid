package com.mmp.wanandroid.model.data

data class User(val admin: Boolean,val coinCount: Int,val collectIds: List<Int>,
                val email: String,val icon: String,val id: Int,val nickname: String,
                val password: String,val publicName: String,val token: String,
                val type: Int,val username: String)