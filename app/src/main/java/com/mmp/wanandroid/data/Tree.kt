package com.mmp.wanandroid.data

import com.google.gson.annotations.SerializedName

data class Tree(
    @SerializedName("children")val childrenList: List<Tree>?,
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String)
