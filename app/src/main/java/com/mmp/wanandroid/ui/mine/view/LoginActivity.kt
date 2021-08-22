package com.mmp.wanandroid.ui.mine.view

import android.util.Log
import com.bugrui.buslib.LiveDataBus
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.DataState
import com.mmp.wanandroid.databinding.ActivityLoginBinding
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.mine.viewmodel.LoginViewModel
import com.mmp.wanandroid.utils.SPreference
import com.mmp.wanandroid.utils.start
import com.mmp.wanandroid.utils.toast

class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>() {

    private var isLogin: Boolean by SPreference("login_state",false)




    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {

        binding.btLogin.setOnClickListener {
            viewModel.username.value = binding.loginEUsername.text.toString()
            viewModel.password.value = binding.loginEPassword.text.toString()
            viewModel.getLogin()
        }
        binding.textVisitor.setOnClickListener {
            finish()
        }
        binding.textRegister.setOnClickListener {
            start<RegisterActivity>()
        }
    }

    override fun initViewObservable() {
        viewModel.loginLiveDaa.observe(this){
            if (it.dataState == DataState.STATE_SUCCESS){
                LiveDataBus.send("user",it.data)
                Log.d(TAG,it.data.toString())
                isLogin = true
                toast("登录成功")
                finish()
            }else{
                toast(it.errorMsg.toString())
            }
        }
    }

    companion object{
        const val TAG = "LoginActivity"
    }

}