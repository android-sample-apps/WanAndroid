package com.mmp.wanandroid.ui.base.manager

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mmp.wanandroid.utils.Utils

object NetworkStateManager : DefaultLifecycleObserver {

    private  var mNetworkStateReceive: NetworkStateReceive? = null

    override fun onResume(owner: LifecycleOwner) {
        mNetworkStateReceive = NetworkStateReceive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        Utils.getApp().applicationContext.registerReceiver(mNetworkStateReceive,filter)
    }

    override fun onPause(owner: LifecycleOwner) {
        Utils.getApp().applicationContext.unregisterReceiver(mNetworkStateReceive)
    }
}