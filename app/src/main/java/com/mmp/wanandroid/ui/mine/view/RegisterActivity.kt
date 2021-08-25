package com.mmp.wanandroid.ui.mine.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.mmp.wanandroid.BR
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.mine.viewmodel.RegisterViewModel
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivityRegisterBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.utils.toast

class RegisterActivity : BaseActivity<ActivityRegisterBinding,RegisterViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        binding.btRegister.setOnClickListener {
            val username = viewModel.username.value.toString()
            val password = viewModel.password.value.toString()
            val repassword = viewModel.repassword.value.toString()
            if (username.isNotEmpty() && password.isNotEmpty() && repassword.isNotEmpty()) {
                viewModel.getRegister(username,password,repassword)
            }else{
                toast("输入不能为空")
            }
        }
    }

    override fun initViewObservable() {
        viewModel.registerLiveData.myObserver(this,error = {toast(it)}){
            AlertDialog.Builder(this)
                .setMessage("注册成功")
                .setPositiveButton("ok") { _, _ ->
                    finish()
                }
                .create()
        }
    }
}