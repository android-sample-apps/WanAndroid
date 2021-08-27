package com.mmp.wanandroid.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.barlibrary.ImmersionBar
import com.mmp.wanandroid.ui.base.manager.NetworkStateManager

abstract class BaseActivityNoViewModel<DB: ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(NetworkStateManager)
        initStatusBar()
        initDataBinding()
        initView()
        initData()
        initViewObservable()
        AppManager.instance.addActivity(this)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.removeActivity(this)
        ImmersionBar.with(this).destroy()
    }

    open fun init(){}
    open fun initView(){}

    open fun initData(){}

    open fun initViewObservable(){}

    abstract fun getLayoutId(): Int

    open fun initStatusBar(){
        ImmersionBar.with(this).transparentStatusBar().init()


    }

    private fun initDataBinding(){
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        binding.lifecycleOwner = this
    }
}