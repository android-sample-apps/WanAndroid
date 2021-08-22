package com.mmp.wanandroid.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.mmp.wanandroid.R

class CustomTitleBar constructor(context: Context,attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    private var barBack: ImageView

    private var barTitle: TextView

    private var barMore: ImageView


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_titlebar,this,true)
        barBack = view.findViewById(R.id.bar_back)
        barTitle = view.findViewById(R.id.bar_title)
        barMore = view.findViewById(R.id.bar_more)
        init(context, attrs)
    }

    private fun init(context: Context,attrs: AttributeSet?){
        val typeArray = context.obtainStyledAttributes(attrs,R.styleable.CustomTitleBar)
        val title = typeArray.getString(R.styleable.CustomTitleBar_title)
        val leftIcon = typeArray.getResourceId(R.styleable.CustomTitleBar_left_icon,R.drawable.back)
        val rightIcon = typeArray.getResourceId(R.styleable.CustomTitleBar_right_icon,0)
        barMore.setImageResource(leftIcon)
        barTitle.text = title
        if (rightIcon == 0){
            barMore.visibility = View.GONE
        }else{
            barMore.setImageResource(rightIcon)
        }
        typeArray.recycle()
    }

    fun setTitle(s: String){
        barTitle.text = s
    }

    fun setLeftIconOnClickListener(click: OnClickListener){
        barBack.setOnClickListener(click)
    }

    fun setRightOnClickListener(click: OnClickListener){
        barMore.setOnClickListener(click)
    }
}