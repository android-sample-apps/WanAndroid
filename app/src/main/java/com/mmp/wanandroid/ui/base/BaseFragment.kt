package com.mmp.wanandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gyf.barlibrary.ImmersionBar
import com.gyf.barlibrary.ImmersionFragment
import com.gyf.barlibrary.SimpleImmersionFragment
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.mmp.wanandroid.R
import com.mmp.wanandroid.api.BaseResponse
import com.mmp.wanandroid.ui.base.callback.ErrorCallback
import com.mmp.wanandroid.ui.base.callback.LoadingCallback
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<DB: ViewDataBinding,VM: ViewModel> : Fragment() {

     lateinit var binding: DB
     lateinit var viewModel: VM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        viewModel = initViewModel()
        initDataBinding()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        loadService.showCallback(LoadingCallback::class.java)
        initView()

//        initStatusBar()
        initViewObservable()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        ImmersionBar.with(this).destroy()
    }

    abstract fun getLayoutId() : Int

    open fun initData(){}

//    open fun initStatusBar(){
//        ImmersionBar.with(this).transparentStatusBar().init()
//    }
//
////    只有当immersionBarEnabled返回true时才执行
//    override fun initImmersionBar() {
//        ImmersionBar.with(this).init()
//    }
//
//    override fun immersionBarEnabled(): Boolean {
//        return true
//    }



    private fun initDataBinding(){
        binding.setVariable(BR.viewModel,viewModel)
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }

    open fun initView(){}

    private fun initViewModel(): VM{
        val type = javaClass.genericSuperclass
        val modelClass = (type as ParameterizedType).actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(this).get(modelClass)

    }

    open fun initViewObservable(){}

}