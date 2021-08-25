package com.mmp.wanandroid.ui.mine.view

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bugrui.buslib.LiveDataBus
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.DataState
import com.mmp.wanandroid.databinding.ActivityLoginBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ui.ShareViewModel
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.MyApplication
import com.mmp.wanandroid.ui.mine.viewmodel.LoginViewModel
import com.mmp.wanandroid.utils.SPreference
import com.mmp.wanandroid.utils.start
import com.mmp.wanandroid.utils.toast

class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>() {

    private val shareViewModel by lazy {
        ViewModelProvider(application as MyApplication).get(ShareViewModel::class.java)
    }

    private var isLogin: Boolean by SPreference("login_state",false)

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {

        binding.btLogin.setOnClickListener {
            if (!viewModel.username.value.isNullOrEmpty() && !viewModel.password.value.isNullOrEmpty()){
                viewModel.getLogin(viewModel.username.value.toString(),viewModel.password.value.toString())
            }else{
                toast("账号或密码为空")
            }
        }
        binding.textVisitor.setOnClickListener {
            finish()
        }
        binding.textRegister.setOnClickListener {
            start<RegisterActivity>()
        }
    }

    override fun initViewObservable() {
        viewModel.loginLiveData.myObserver(this,error = {toast(it)}){ user ->
            shareViewModel.loginLiveData.value = user
            isLogin = true
            toast("登录成功")
            finish()
        }
    }

    companion object{
        const val TAG = "LoginActivity"
    }

}