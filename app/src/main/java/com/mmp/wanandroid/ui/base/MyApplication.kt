package com.mmp.wanandroid.ui.base

import android.R
import android.app.Application
import android.graphics.Color.red
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout


class MyApplication : Application() {

    companion object{
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator{ context,layout ->
                layout.setPrimaryColorsId(R.color.holo_red_light,R.color.white)
                ClassicsHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator{ context,_ ->
                ClassicsFooter(context).setDrawableSize(20.0F)
            }
        }
    }

}