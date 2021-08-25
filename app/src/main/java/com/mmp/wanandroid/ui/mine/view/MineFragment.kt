package com.mmp.wanandroid.ui.mine.view

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bugrui.buslib.LiveDataBus
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.DataState
import com.mmp.wanandroid.model.data.User
import com.mmp.wanandroid.databinding.FragmentMineBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ui.ShareViewModel
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.MyApplication
import com.mmp.wanandroid.ui.mine.viewmodel.MineViewModel
import com.mmp.wanandroid.utils.SPreference
import com.mmp.wanandroid.utils.start


class MineFragment : BaseFragment<FragmentMineBinding,MineViewModel>() {

    private val shareViewModel by lazy { ViewModelProvider(requireActivity().application as MyApplication).get(ShareViewModel::class.java) }

    private var isLogin: Boolean by SPreference("login_state",false)

    private var mCookie: String by SPreference("cookie","")

    private var mUsername: String by SPreference("username","")


    override fun initView() {
        if (isLogin){
            startUser()
            binding.userTip.visibility = View.GONE
            binding.userId.text = mUsername
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

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initViewObservable() {

        shareViewModel.loginLiveData.observe(this){
            if (it != null){
                viewModel.id.set(it.username)
                viewModel.integral.set(it.coinCount.toString())
                mUsername = it.username
                startUser()
                binding.userTip.visibility = View.GONE
            }
        }

        shareViewModel.logoutLiveData.observe(this){
            viewModel.id.set("--")
            viewModel.integral.set("0")
            startLogin()
            mUsername = "--"
            isLogin = false
            binding.userTip.visibility = View.VISIBLE
            mCookie = ""
        }

        viewModel.integralLiveData.myObserver(this){
            viewModel.integral.set(it.coinCount.toString())
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