package com.mmp.wanandroid.ui.mine.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.DataState
import com.mmp.wanandroid.model.data.Integral
import com.mmp.wanandroid.model.data.IntegralData
import com.mmp.wanandroid.databinding.ActivityIntegralBinding
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.mine.adapter.CoinAdapter
import com.mmp.wanandroid.ui.mine.viewmodel.IntegralViewModel
import com.mmp.wanandroid.utils.start

class IntegralActivity : BaseActivity<ActivityIntegralBinding,IntegralViewModel>() {

    private val coinAdapter = CoinAdapter()

    private val coinList = mutableListOf<Integral>()


    private val linearLayoutManager = LinearLayoutManager(this)

    override fun getLayoutId(): Int {
        return R.layout.activity_integral
    }



    override fun initView() {
        initSf()
        initRv()
        initTitleBar()
    }



    override fun initViewObservable() {
        viewModel.integralLiveData.observe(this){
            if (it.dataState == DataState.STATE_SUCCESS){
                viewModel.integral.value = it.data?.coinCount
            }
        }

        viewModel.coinLiveData.observe(this, object : IStateObserver<IntegralData>(binding.integralRv){
            override fun onReload(v: View?) {
                v?.setOnClickListener {
                   initData()
                }
            }

            override fun onDataChange(data: IntegralData?) {
                coinList.reverse()
                data?.let { coinList.addAll(it.datas) }
                coinList.reverse()
                coinAdapter.submitList(mutableListOf<Integral>().apply {
                    addAll(coinList)
                })
            }

        })
    }

    override fun initData() {
        viewModel.getUserRank()
        viewModel.getCoinList()
    }

    private fun initSf(){
        binding.smartFresh.setEnableLoadMore(false)
        binding.smartFresh.setOnRefreshListener {
            initData()
        }
    }

    private fun initRv(){
        binding.integralRv.apply {
            layoutManager = linearLayoutManager
            adapter = coinAdapter
        }


        binding.integralRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                viewModel.listScrolled(visibleItemCount,lastVisibleItemPosition,totalItemCount)
            }
        })
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