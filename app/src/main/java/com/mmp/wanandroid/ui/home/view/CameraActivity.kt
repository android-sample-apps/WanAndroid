package com.mmp.wanandroid.ui.home.view

import android.Manifest
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivityCameraBinding
import com.mmp.wanandroid.ui.base.BaseActivityNoViewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber

class CameraActivity : BaseActivityNoViewModel<ActivityCameraBinding>(),CameraFragment.OnFragmentListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_camera
    }

//    override fun onResume() {
//        super.onResume()
//        binding.fmContainer.postDelayed({
//            hideSystemUI()
//        },500L)
//    }

    override fun initView() {
        loadCameraFragment()
    }

    private fun loadCameraFragment(){
        val cameraFragment = CameraFragment.getInstance()
        cameraFragment.setOnFragmentListener(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fm_container,cameraFragment,CameraFragment::class.simpleName)
            .commit()
    }

    override fun loadToGallery(path: String) {
        val galleryFragment = GalleryFragment.newInstance(path)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fm_container,galleryFragment,GalleryFragment::class.simpleName)
            .addToBackStack(null)
            .commit()
    }


    override fun loadToScan() {
//        val captureFragment = ScanFragment.newInstance()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fm_container,captureFragment,CaptureFragment::class.simpleName)
//            .addToBackStack(null)
//            .commit()
    }

    override fun initStatusBar() {
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LOW_PROFILE
        }
    }

//    private fun hideSystemUI(){
//        WindowCompat.setDecorFitsSystemWindows(window,false)
//        WindowInsetsControllerCompat(window,binding.fmContainer).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE
//        }
//    }
}