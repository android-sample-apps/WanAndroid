package com.mmp.wanandroid.ui.mine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.bugrui.buslib.LiveDataBus
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivitySettingBinding
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.MyApplication
import com.mmp.wanandroid.ui.mine.viewmodel.SettingViewModel
import com.mmp.wanandroid.utils.Event

class SettingActivity : BaseActivity<ActivitySettingBinding,SettingViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        binding.switchMode.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        binding.switchMode.setOnCheckedChangeListener{ _,isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

        binding.logout.setOnClickListener {
            viewModel.logout()
            LiveDataBus.send("logout")
        }
    }
}