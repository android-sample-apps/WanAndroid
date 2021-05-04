package com.mmp.wanandroid.ui.base.callback

import com.kingja.loadsir.callback.Callback
import com.mmp.wanandroid.R

class LoadingCallback : Callback(){
    override fun onCreateView(): Int {
        return R.layout.loading_layout
    }
}