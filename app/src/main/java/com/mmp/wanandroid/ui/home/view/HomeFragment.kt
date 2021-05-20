package com.mmp.wanandroid.ui.home.view

import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.databinding.FragmentHomeBinding
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.home.adapter.ArticleAdapter
import com.mmp.wanandroid.ui.home.adapter.ArticleLoadStateAdapter
import com.mmp.wanandroid.ui.home.adapter.ImageAdapter
import com.mmp.wanandroid.ui.home.viewmodel.HomeViewModel
import com.mmp.wanandroid.utils.toast
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment() : BaseFragment<FragmentHomeBinding,HomeViewModel>() {

    private val bannerList = mutableListOf<Banner>()

    private val bannerAdapter by lazy { context?.let { ImageAdapter(bannerList,it) } }

    private val articleAdapter by lazy { context?.let { ArticleAdapter(it) } }

    private val mLayoutManager by lazy { activity?.let { LinearLayoutManager(it) } }



    override fun getLayoutId(): Int = R.layout.fragment_home

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.home_menu,menu)
//    }

    override fun initViewObservable() {


        viewModel.bannerLiveData.observe(this,object : IStateObserver<List<Banner>>(binding.banner){
            override fun onReload(v: View?) {
                v?.setOnClickListener{
                  viewModel.getBanner()
                }
            }

            override fun onDataChange(data: List<Banner>?) {
                super.onDataChange(data)
                if (data != null) {
                    if (bannerList.isEmpty()) {
                        bannerList.addAll(data)
                        bannerAdapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                context?.toast(e.message.toString())
            }
        })
    }

    override fun initView() {
        initBanner()
        initRv()
        initSmartFresh()
        initBar()
    }

    private fun initBar(){
        binding.search.setOnClickListener{
            val intent = Intent(this.context,SearchActivity::class.java)
            this.context?.startActivity(intent)
        }
    }

    override fun initData() {
//        viewModel.requestBanner()
//        viewModel.requestArticle()
        viewModel.getBanner()
        lifecycleScope.launch {
            viewModel.getHomeArticle().collect { articleList ->
                articleAdapter?.submitData(articleList)
            }
        }
    }

//    override fun initStatusBar() {
//        ImmersionBar.with(this).statusBarView(binding.statusBarView).init()
//    }

    private fun initRv(){
        articleAdapter?.addLoadStateListener {
            when(it.refresh){
                is LoadState.Loading -> {
                    binding.rvArticle.showShimmerAdapter()
                }
                is LoadState.NotLoading -> {
                    binding.rvArticle.hideShimmerAdapter()
                }
                is LoadState.Error -> {
                    binding.rvArticle.hideShimmerAdapter()
                }
            }
        }
        binding.rvArticle.apply {
            layoutManager = mLayoutManager
            adapter = articleAdapter!!.withLoadStateHeaderAndFooter(
                    header = ArticleLoadStateAdapter(articleAdapter!!),
                    footer = ArticleLoadStateAdapter(articleAdapter!!)
            )
//            showShimmerAdapter()
        }
    }


    private fun initSmartFresh(){
        binding.smartRefresh.setEnableLoadMore(false)
        binding.smartRefresh.setOnRefreshListener {
            initData()
            binding.smartRefresh.finishRefresh()
        }
        binding.smartRefresh.setEnableOverScrollDrag(true)
    }

    private fun initBanner(){
        binding.banner.setAdapter(bannerAdapter)
            .addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(activity)
    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.scan -> TODO()
//        }
//        return true
//    }




    companion object{
        fun instance(): HomeFragment {
            return HomeFragment()
        }

        val TAG = "HomeFragment"
    }

}
