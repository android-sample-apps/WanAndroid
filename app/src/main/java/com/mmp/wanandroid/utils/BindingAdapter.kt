package com.mmp.wanandroid.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.ui.base.BindingViewHolder
import com.mmp.wanandroid.ui.base.MyApplication
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

/********************* home_rv_item ******************************/
//@BindingAdapter("android:visibility")
//fun isTop(view: TextView,type: Int){
//    view.visibility = if (type == 1) View.VISIBLE else View.GONE
//}

@BindingAdapter("android:text")
fun isTop(view: TextView,type: Int){
    view.text = if (type ==1) "置顶" else ""
}


//@BindingAdapter("android:background")
//fun setRes(view: ImageView,collect: Boolean){
//    if (collect){
//        view.setImageResource(R.drawable.redheart)
//    }else{
//        view.setImageResource(R.drawable.heart)
//    }
//}
/********************** webFragment *******************************/
@BindingAdapter("hideIfMax")
fun hideIfMax(view: ProgressBar,number: Int){
    if (number < 100){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}

@BindingAdapter("progressScale")
fun progressScale(view: ProgressBar,number: Int){
    view.progress = number
}
/********************** project_tv_item *******************************/
@BindingAdapter("android:background")
fun getImage(view: ImageView,url: String){
    Glide.with(MyApplication.getContext()).load(url).into(view)
}

/********************** homeArticle recycleView *********************************/
//@BindingAdapter("adapter")
//fun setAdapter(view: RecyclerView,adapter: ListAdapter<*,out RecyclerView.ViewHolder>){
//    view.adapter = adapter
//}
//
//@BindingAdapter("submit")
//fun<T> setList(view: RecyclerView,list: MutableLiveData<MutableList<T>>) {
//    if (view.adapter != null){
//        val adapter = view.adapter as ListAdapter<T,BindingViewHolder>
//        adapter.submitList(list.value)
//    }
//}

/**********************smartFreshLayout***************************/
@BindingAdapter("loadMore")
fun setLoadMore(view: SmartRefreshLayout,block:() -> Unit){
    view.setOnLoadMoreListener {
        block()
    }
}




