package com.mmp.wanandroid.ui.home.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.ui.web.WebActivity
import com.mmp.wanandroid.utils.start
import com.youth.banner.adapter.BannerAdapter
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ImageAdapter @Inject constructor(private val bannerList:List<Banner>,@ActivityContext private val context: Context) : BannerAdapter<Banner,ImageAdapter.ViewHolder>(bannerList) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.banner_image)
        val title: TextView = view.findViewById(R.id.banner_title)
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.banner_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindView(holder: ViewHolder?, data: Banner?, position: Int, size: Int) {
        holder?.title?.text =  data?.title ?: "null"
        holder?.image?.let {
            Glide.with(it)
                .load(data?.imagePath)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(it)
        }
        holder?.image?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url",data?.url)
            bundle.putString("title",data?.title)
            context.start<WebActivity>(bundle)
        }
    }
}