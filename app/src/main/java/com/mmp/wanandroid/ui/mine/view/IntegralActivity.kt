package com.mmp.wanandroid.ui.mine.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.DataState
import com.mmp.wanandroid.model.data.Integral
import com.mmp.wanandroid.model.data.IntegralData
import com.mmp.wanandroid.databinding.ActivityIntegralBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.mine.adapter.CoinAdapter
import com.mmp.wanandroid.ui.mine.viewmodel.IntegralViewModel
import com.mmp.wanandroid.utils.start

class IntegralActivity : BaseActivity<ActivityIntegralBinding,IntegralViewModel>() {

    private val coinAdapter = CoinAdapter()

    private val linearLayoutManager = LinearLayoutManager(this)

    override fun getLayoutId(): Int {
        return R.layout.activity_integral
    }

    override fun initView() {
        initRv()
        initTitleBar()
    }

    override fun initViewObservable() {
        val loadService = binding.smartFresh.registerLoad {
            viewModel.getRefresh()
        }
        viewModel.integralLiveData.myObserver(this){
            binding.tvIntegral.text = it.coinCount.toString()
        }

        viewModel.coinLiveData.myObserver(this,loadService){
            viewModel.coinList.addAll(it.datas.reversed())
            coinAdapter.submitList(viewModel.coinList)
        }
    }

    override fun initData() {
    }

    private fun initRv(){
        binding.integralRv.apply {
            layoutManager = linearLayoutManager
            adapter = coinAdapter
        }
    }

    private fun initTitleBar(){
        binding.back.setOnClickListener {
            finish()
        }
        binding.rankIcon.setOnClickListener {
            start<RankActivity>()
        }
    }
}