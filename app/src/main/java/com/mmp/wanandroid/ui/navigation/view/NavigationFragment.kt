package com.mmp.wanandroid.ui.navigation.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.NavBean
import com.mmp.wanandroid.databinding.FragmentNavigationBinding
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.navigation.adapter.NavAdapter
import com.mmp.wanandroid.ui.navigation.viewmodel.NavigationViewModel
import com.mmp.wanandroid.utils.toast

class NavigationFragment : BaseFragment<FragmentNavigationBinding,NavigationViewModel>() {

    private val navAdapter by lazy { activity?.let { NavAdapter(it) } }

    override fun initView() {
        binding.navigationRv.apply {
            adapter = navAdapter
            layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        }
        binding.smartFresh.setEnableLoadMore(false)
        binding.smartFresh.setOnRefreshListener {
            initData()
        }
    }

    override fun initData() {
        viewModel.getNavList()
    }

    override fun initViewObservable() {
        viewModel.navLiveData.observe(this,object : IStateObserver<List<NavBean>>(binding.navigationRv){
            override fun onReload(v: View?) {
                v?.setOnClickListener {
                    initData()
                }
            }

            override fun onDataChange(data: List<NavBean>?) {
                if (data != null){
                    navAdapter?.submitList(data)
                }
            }

            override fun onError(e: Throwable) {
                activity?.toast(e.message.toString())
            }
        })
    }

    companion object {

        fun instance(): NavigationFragment = NavigationFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navigation
    }
}