package com.mmp.wanandroid.ui.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.*
import com.gyf.barlibrary.ImmersionBar
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.mmp.wanandroid.R
import com.mmp.wanandroid.network.*
import com.mmp.wanandroid.ui.base.callback.EmptyCallback
import com.mmp.wanandroid.ui.base.callback.ErrorCallback
import com.mmp.wanandroid.ui.base.callback.LoadingCallback
import com.mmp.wanandroid.ui.customview.CustomTitleBar
import java.lang.reflect.ParameterizedType
import java.util.jar.Attributes

abstract class BaseActivity<DB: ViewDataBinding,VM: ViewModel> : AppCompatActivity(),Callback.OnReloadListener {



    lateinit var binding: DB
    lateinit var viewModel: VM

    private lateinit var loadService: LoadService<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBar()
        viewModel = initViewModel()
        initDataBinding()
        loadService = LoadSir.getDefault().register(this,this,
            Convertor<DataStatus> {
                val resultCode = when(it){
                    is Success<*> -> SuccessCallback::class.java
                    is Empty -> EmptyCallback::class.java
                    is Failure -> ErrorCallback::class.java
                    is Loading -> LoadingCallback::class.java
                    is None -> EmptyCallback::class.java
                }
                resultCode
            })
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
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        binding.setVariable(BR.viewModel,viewModel)
        binding.lifecycleOwner = this


    }

    abstract fun getLayoutId() : Int


    open fun initView(){}

    open fun initViewObservable(){}

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.removeActivity(this)
        ImmersionBar.with(this).destroy()
    }

    open fun initData(){}

    override fun onReload(v: View?) {

    }

    fun <T: DataStatus> myObserve(liveData: LiveData<T>,success: (T) -> Unit){
        liveData.observe(this, Observer {
            loadService.showWithConvertor(it)
            when(it){
                is Success<*> -> success(it)
                is Failure -> Toast.makeText(this,"${it.t}",Toast.LENGTH_LONG).show()
            }

        })
    }


    fun<T> convert(status: DataStatus): T{
        return (status as Success<*>).data as T
    }





}