package com.mmp.wanandroid.ui.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gyf.barlibrary.ImmersionBar
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.mmp.wanandroid.R
import com.mmp.wanandroid.ui.base.callback.ErrorCallback
import com.mmp.wanandroid.ui.base.callback.LoadingCallback
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<DB: ViewDataBinding,VM: ViewModel> : AppCompatActivity() {



    lateinit var binding: DB
    lateinit var viewModel: VM

    private var viewModelId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBar()
        viewModel = initViewModel()
        initDataBinding()
        initView()
        initData()
        initViewObservable()
        AppManager.instance.addActivity(this)
    }


    private fun initStatusBar(){
        ImmersionBar.with(this).transparentStatusBar().statusBarAlpha(0.3f).init()
    }

    private fun initViewModel(): VM{
        val type = javaClass.genericSuperclass
        val modelClass = (type as ParameterizedType).actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this).get(modelClass)

    }

    private fun initDataBinding(){
        viewModelId = getViewModelId()
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        binding.setVariable(viewModelId,viewModel)
        binding.lifecycleOwner = this
    }

    abstract fun getLayoutId() : Int

    abstract fun getViewModelId() : Int

    open fun initView(){}

    open fun initViewObservable(){}

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.removeActivity(this)
    }

    open fun initData(){}




}