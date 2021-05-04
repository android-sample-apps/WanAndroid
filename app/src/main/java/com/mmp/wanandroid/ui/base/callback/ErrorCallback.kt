package com.mmp.wanandroid.ui.base.callback

import com.kingja.loadsir.callback.Callback
import com.mmp.wanandroid.R

class ErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.error_layout
    }
}