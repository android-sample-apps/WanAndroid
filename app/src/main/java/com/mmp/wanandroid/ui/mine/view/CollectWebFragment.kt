package com.mmp.wanandroid.ui.mine.view

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.Web
import com.mmp.wanandroid.databinding.FragmentCollectWebBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.mine.adapter.WebAdapter
import com.mmp.wanandroid.ui.mine.viewmodel.CollectWebViewModel
import com.mmp.wanandroid.utils.toast

class CollectWebFragment : BaseFragment<FragmentCollectWebBinding,CollectWebViewModel>(),WebAdapter.OnCollectListener {

    private val webAdapter by lazy { WebAdapter(this) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_collect_web
    }

    override fun initView() {
        binding.webRv.apply {
            adapter = webAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        webAdapter.setOnCollectListener(this)
    }

    override fun initData() {
        viewModel.getCollectTools()
    }

    override fun unCollect(id: Int,position: Int) {
        viewModel.unCollect(id)
        webAdapter.notifyItemRemoved(position)
    }

    override fun initViewObservable() {
        val loadService = binding.webRv.registerLoad {
            viewModel.getCollectTools()
        }
        viewModel.webLiveData.myObserver(this,loadService){
            viewModel.toolList.addAll(it)
            webAdapter.submitList(viewModel.toolList)
        }
    }

    companion object{
        fun newInstance(): CollectWebFragment{
            return CollectWebFragment()
        }

        const val TAG = "collectWebFragment"
    }


}