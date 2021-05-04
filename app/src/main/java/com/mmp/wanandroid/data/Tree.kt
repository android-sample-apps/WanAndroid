package com.mmp.wanandroid.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity
data class Tree(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String?) : Parcelable{


    var option = Option.UnFollow

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<Tree> {

        enum class Option{
            Followed,UnFollow
        }

        override fun createFromParcel(parcel: Parcel): Tree {
            return Tree(parcel)
        }

        override fun newArray(size: Int): Array<Tree?> {
            return arrayOfNulls(size)
        }
    }

}
