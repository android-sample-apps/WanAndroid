package com.mmp.wanandroid.ui.base.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.blankj.utilcode.util.NetworkUtils

import com.mmp.wanandroid.utils.toast
import java.util.*

class NetworkStateReceive : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            if (Objects.equals(intent.action,ConnectivityManager.CONNECTIVITY_ACTION))
                if (!NetworkUtils.isConnected()){
                    context?.toast("网络不给力")
                }
        }
    }
}