package com.mmp.wanandroid.ui.base

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
import com.mmp.wanandroid.R
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<DB: ViewDataBinding,VM: ViewModel> : AppCompatActivity() {


    lateinit var binding: DB
    lateinit var viewModel: VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBar()
        viewModel = initViewModel()
        initDataBinding()
        initView()
    }


    private fun initStatusBar(){
        ImmersionBar.with(this).init()
    }

    private fun initViewModel(): VM{
        val type = javaClass.genericSuperclass
        val modelClass = (type as ParameterizedType).actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this).get(modelClass)

    }

    private fun initDataBinding(){
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        binding.setVariable(BR.viewModel,viewModel)
        binding.lifecycleOwner = this
    }

    abstract fun getLayoutId() : Int


    open fun initView(){}

}