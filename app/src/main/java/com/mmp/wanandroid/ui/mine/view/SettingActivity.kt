package com.mmp.wanandroid.ui.mine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivitySettingBinding
import com.mmp.wanandroid.ui.SharedViewModel
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.MyApplication
import com.mmp.wanandroid.ui.mine.viewmodel.SettingViewModel
import com.mmp.wanandroid.utils.Event

class SettingActivity : BaseActivity<ActivitySettingBinding,SettingViewModel>() {

    private val sharedViewModel by lazy {  ViewModelProvider(this.applicationContext as MyApplication,
        this.application.let { ViewModelProvider.AndroidViewModelFactory.getInstance(it) }).get(
        SharedViewModel::class.java)}

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
            sharedViewModel.logout.value = Event(true)
        }
    }
}