package com.mmp.wanandroid.ext

import android.content.res.Resources

/**
 * @author chen
 * @date 2021/8/27
 * des
 **/
val Float.dp2px
 get() = Resources.getSystem().displayMetrics.density * this + 0.5f