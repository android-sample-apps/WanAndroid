package com.mmp.wanandroid.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Banner
import com.youth.banner.adapter.BannerAdapter

class ImageAdapter(private val bannerList:List<Banner>) : BannerAdapter<Banner,ImageAdapter.ViewHolder>(bannerList) {

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
        holder?.image?.setOnClickListener{
            TODO()
        }
    }
}