package com.mmp.wanandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gyf.barlibrary.ImmersionBar
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.mmp.wanandroid.model.remote.*
import com.mmp.wanandroid.ui.base.callback.EmptyCallback
import com.mmp.wanandroid.ui.base.callback.ErrorCallback
import com.mmp.wanandroid.ui.base.callback.LoadingCallback
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<DB: ViewDataBinding,VM: ViewModel> : Fragment(),Callback.OnReloadListener {

     lateinit var binding: DB
     lateinit var viewModel: VM

     private var isLoaded = false

     lateinit var loadService: LoadService<Any>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)

        loadService = LoadSir.getDefault().register(binding.root,this,
            Convertor<DataStatus<*>> {
                val resultCode = when(it){
                    is DataStatus.Success<*> -> SuccessCallback::class.java
                    is DataStatus.Empty -> EmptyCallback::class.java
                    is DataStatus.Failure -> ErrorCallback::class.java
                    is DataStatus.Loading -> LoadingCallback::class.java
                    is DataStatus.None -> EmptyCallback::class.java
                }
                resultCode
            })
        return loadService.loadLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = initViewModel()
        initDataBinding()
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded){
            lazyInit()
            isLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    private fun lazyInit(){
        initView()
        initData()
        initViewObservable()
    }

    abstract fun getLayoutId() : Int

    open fun initData(){}

    private fun initDataBinding(){
        binding.setVariable(BR.viewModel,viewModel)
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }

    open fun initView(){}

    open fun initViewModel(): VM{
        val type = javaClass.genericSuperclass
        val modelClass = (type as ParameterizedType).actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this).get(modelClass)

    }

    open fun initViewObservable(){}


    override fun onReload(v: View?) {

    }
}