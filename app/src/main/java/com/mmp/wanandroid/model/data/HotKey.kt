package com.mmp.wanandroid.model.data

import com.google.gson.annotations.SerializedName



data class HotKey(@SerializedName("id")val id: Int,
                  @SerializedName("name")val name: String)