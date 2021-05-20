package com.mmp.wanandroid.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Article

/********************* home_rv_item ******************************/
@BindingAdapter("android:visibility")
fun isTop(view: TextView,type: Int){
    view.visibility = if (type == 1) View.VISIBLE else View.GONE
}

@BindingAdapter("android:text")
fun showAuthor(view: TextView,article: Article){
    if (article.author == ""){
        view.text = article.shareUser
    }else{
        view.text = article.author
    }
}

@BindingAdapter("android:text")
fun showText(view: TextView,text: String){
    view.text = text
}

@BindingAdapter("android:background")
fun setRes(view: ImageView,collect: Boolean){
    if (collect){
        view.setImageResource(R.drawable.redheart)
    }else{
        view.setImageResource(R.drawable.heart)
    }
}
/********************** webFragment *******************************/
@BindingAdapter("app:hideIfMax")
fun hideIfMax(view: ProgressBar,number: Int){
    if (number < 100){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}

@BindingAdapter("app:progressScale")
fun progressScale(view: ProgressBar,number: Int){
    view.progress = number
}
