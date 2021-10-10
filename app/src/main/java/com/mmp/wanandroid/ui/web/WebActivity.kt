package com.mmp.wanandroid.ui.web

import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.databinding.library.baseAdapters.BR
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivityWebBinding
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.utils.start

class WebActivity : BaseActivity<ActivityWebBinding,WebViewModel>() {



    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        binding.webTitle.text = intent?.extras?.getString("title") ?: "标题"
        binding.webBack.setOnClickListener{
            finish()
        }
        val url = intent?.extras?.getString("url")
        binding.webView.webViewClient = object : WebViewClient(){

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val tempUrl = request.url.toString()
                if (tempUrl.startsWith("intent://") || tempUrl.endsWith(".apk")
                ) {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    return true
                }

                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                if (url.startsWith("intent://") || url.endsWith(".apk")) {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    return true
                }
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                //默认为false
                //忽略证书，存在安全风险
                handler?.proceed()
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
            //支持缩放
            setSupportZoom(true)
            builtInZoomControls = true
            javaScriptEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
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
        url?.let { binding.webView.loadUrl(it) }
        onBackPressedDispatcher.addCallback(this,callback)
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_web
    }


}