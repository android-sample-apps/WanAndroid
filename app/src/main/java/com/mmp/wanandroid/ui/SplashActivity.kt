package com.mmp.wanandroid.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivitySplashBinding
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.BaseActivityNoViewModel
import com.mmp.wanandroid.utils.start
import com.mmp.wanandroid.utils.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import permissions.dispatcher.*

@RuntimePermissions
class SplashActivity : BaseActivityNoViewModel<ActivitySplashBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
    fun startIntent(){
        lifecycleScope.launch {
            delay(2000)
            start<MainActivity>()
            finish()
        }
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onDenied(){
        toast("不开缓存会很消耗流量哦")
        startIntent()
    }

    override fun initView() {
        startIntentWithPermissionCheck()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}