package com.mmp.wanandroid.ui.mine.view

import android.view.View
import com.bugrui.buslib.LiveDataBus
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.DataState
import com.mmp.wanandroid.model.data.User
import com.mmp.wanandroid.databinding.FragmentMineBinding
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.mine.viewmodel.MineViewModel
import com.mmp.wanandroid.utils.SPreference
import com.mmp.wanandroid.utils.start


class MineFragment : BaseFragment<FragmentMineBinding,MineViewModel>() {




    private var isLogin: Boolean by SPreference("login_state",false)

    private var mCookie: String by SPreference("cookie","")

    private var mUsername: String by SPreference("username","")

    private var mIntegral: String by SPreference("integral","0")

    override fun initView() {
        if (isLogin){
            startUser()
            binding.userTip.visibility = View.GONE
            binding.userId.text = mUsername
            binding.tvIntegral.text = mIntegral
        }else{
            startLogin()
            binding.userTip.visibility = View.VISIBLE
        }
        binding.userTip.setOnClickListener {
            activity?.start<LoginActivity>()
        }
        binding.userSetting.setOnClickListener {
            activity?.start<SettingActivity>()
        }
    }

    override fun initData() {
        viewModel.getUserRank()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initViewObservable() {
        LiveDataBus.with("user").observe(this){
            viewModel.id.set((it as User).username)
            viewModel.integral.set((it as User).coinCount.toString())
            mUsername = (it as User).username
            mIntegral = it.coinCount.toString()
            startUser()
            binding.userTip.visibility = View.GONE
            binding.executePendingBindings()
        }

        LiveDataBus.with("logout").observe(this){
            viewModel.id.set("--")
            viewModel.integral.set("0")
            startLogin()
            mUsername = "--"
            mIntegral = "0"
            isLogin = false
            binding.userTip.visibility = View.VISIBLE
            mCookie = ""
            binding.executePendingBindings()
        }

        viewModel.integralLiveData.observe(this){
            if (it.dataState == DataState.STATE_SUCCESS){
                viewModel.integral.set(it.data?.coinCount.toString())
            }
        }
    }

    private fun startLogin(){
        binding.userIntegral.setOnClickListener{
            activity?.start<LoginActivity>()
        }
        binding.userCollect.setOnClickListener{
            activity?.start<LoginActivity>()
        }
        binding.userTodo.setOnClickListener {
            activity?.start<LoginActivity>()
        }
    }

    private fun startUser(){
        binding.userIntegral.setOnClickListener{
            activity?.start<IntegralActivity>()
        }
        binding.userCollect.setOnClickListener{
            activity?.start<CollectActivity>()
        }
        binding.userTodo.setOnClickListener {
            activity?.start<TodoActivity>()
        }
    }

    companion object {
        fun instance(): MineFragment = MineFragment()

        const val TAG = "MineFragment"
    }

}