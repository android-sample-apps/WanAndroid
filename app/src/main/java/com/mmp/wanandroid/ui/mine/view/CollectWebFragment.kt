package com.mmp.wanandroid.ui.mine.view

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.DataState
import com.mmp.wanandroid.data.Web
import com.mmp.wanandroid.databinding.FragmentCollectWebBinding
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.mine.adapter.WebAdapter
import com.mmp.wanandroid.ui.mine.viewmodel.CollectWebViewModel
import com.mmp.wanandroid.utils.toast
import kotlin.math.log

class CollectWebFragment : BaseFragment<FragmentCollectWebBinding,CollectWebViewModel>() {

    private val webAdapter by lazy { WebAdapter(this) }

    private val webList = mutableListOf<Web>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_collect_web
    }

    override fun initView() {

        Log.d(TAG,"initview")

        binding.webRv.apply {
            adapter = webAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        binding.smartFresh.setEnableLoadMore(false)
        binding.smartFresh.autoRefresh()
        binding.smartFresh.setOnRefreshListener {
            initData()
        }
    }

    override fun initData() {
        viewModel.getCollectTools()
    }

    override fun initViewObservable() {
        viewModel.websLiveData.observe(this,object : IStateObserver<List<Web>>(binding.webRv){
            override fun onReload(v: View?) {
                v?.setOnClickListener {
                    initData()
                }
            }

            override fun onDataChange(data: List<Web>?) {
                if (data != null) {
                    webList.addAll(data)
                    webAdapter.submitList(mutableListOf<Web>().apply {
                        addAll(webList)
                    })
                }
                binding.smartFresh.finishRefresh()
            }

            override fun onDataEmpty() {
                binding.smartFresh.finishRefresh()
            }

            override fun onError(e: Throwable) {
                binding.smartFresh.finishRefresh()
                activity?.toast(e.message.toString())
            }
        })

        viewModel.collectLiveData.observe(this){
            if (it.errorCode == 0){
                activity?.toast("取消收藏")
            }else{
                activity?.toast("发生错误，取消收藏失败")
            }
        }
    }

    companion object{
        fun newInstance(): CollectWebFragment{
            return CollectWebFragment()
        }

        const val TAG = "collectWebFragment"
    }


}