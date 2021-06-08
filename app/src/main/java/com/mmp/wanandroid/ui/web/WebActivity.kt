package com.mmp.wanandroid.ui.web

import android.os.Build
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.databinding.library.baseAdapters.BR
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivityWebBinding
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.BaseFragment

class WebActivity : BaseActivity<ActivityWebBinding,WebViewModel>() {



    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        binding.webTitle.text = intent?.extras?.getString("title") ?: "标题"
        binding.webBack.setOnClickListener{
            finish()
        }
        val url = intent?.extras?.getString("url")
        url?.let { binding.webView.loadUrl(it) }
        binding.webView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }

        binding.webView.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                viewModel.currentProcess(newProgress)
            }
        }


        binding.webView.settings.apply {
            //将图片调整到适合webview的大小
            useWideViewPort = true
            // 缩放至屏幕的大小
            loadWithOverviewMode = true
            setSupportZoom(true)
            builtInZoomControls = true
            useWideViewPort = true
            javaScriptEnabled = true
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()){
                    binding.webView.goBack()
                }else{
                    finish()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this,callback)
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_web
    }


}