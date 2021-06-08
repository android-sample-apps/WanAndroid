package com.mmp.wanandroid.ui.mine.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.mmp.wanandroid.BR
import com.mmp.wanandroid.databinding.ActivityRegisterBindingImpl
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.mine.viewmodel.RegisterViewModel
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivityRegisterBinding
import com.mmp.wanandroid.utils.toast

class RegisterActivity : BaseActivity<ActivityRegisterBinding,RegisterViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        binding.btRegister.setOnClickListener {
            val username = binding.registerEUsername.text.toString()
            val password = binding.registerEPassword.text.toString()
            val repassword = binding.registerERePassword.text.toString()
            if (!TextUtils.isEmpty(username)
                    && !TextUtils.isEmpty(password)
                    && !TextUtils.isEmpty(repassword)){
                viewModel.getRegister(username,password,repassword)
            }else{
                toast("输入不能为空")
            }
        }
    }

    override fun initViewObservable() {
        viewModel.registerLiveData.observe(this){
            if (it.errorCode == 0){
                val alertDialog: AlertDialog? = AlertDialog.Builder(this)
                        .setMessage("注册成功")
                        .setPositiveButton("ok",DialogInterface.OnClickListener { _, _ ->
                            finish()
                        })
                        .create()
            }else{
                toast(it.errorMsg.toString())
            }
        }
    }
}