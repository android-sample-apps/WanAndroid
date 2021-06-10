package com.mmp.wanandroid.ui.home.view

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bugrui.buslib.LiveDataBus
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.*
import com.mmp.wanandroid.databinding.FragmentHomeBinding
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.base.MyApplication
import com.mmp.wanandroid.ui.home.adapter.ArticleAdapter
import com.mmp.wanandroid.ui.home.adapter.ArticleLoadStateAdapter
import com.mmp.wanandroid.ui.home.adapter.ImageAdapter
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.home.viewmodel.HomeViewModel
import com.mmp.wanandroid.utils.toast
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(),SearchArticleAdapter.OnCollectListener{

    private val bannerList = mutableListOf<Banner>()

    private val bannerAdapter by lazy { context?.let { ImageAdapter(bannerList,it) } }

    private val articleList = mutableListOf<Article>()

    private var isCache = false

    private val articleAdapter by lazy {
        activity?.let { SearchArticleAdapter(it) }
    }

    override fun onCollect(article: Article) {
        viewModel.collect(article.id)
    }

    override fun unCollect(article: Article) {
        viewModel.unCollect(article.id)
    }


    override fun getLayoutId(): Int = R.layout.fragment_home


    override fun initViewObservable() {

        viewModel.topArticleLiveData.observe(this){
            if (it.dataState == DataState.STATE_SUCCESS){
                it.data?.let { it1 -> articleList.addAll(it1) }
            }
        }

        viewModel.homeArticleLiveData.observe(this,object : IStateObserver<ArticleData>(binding.rvArticle){
            override fun onReload(v: View?) {
                v?.setOnClickListener {
                    viewModel.getTopArticle()
                    viewModel.getHomeArticle()
                }
            }

            override fun onDataChange(data: ArticleData?) {
                binding.smartRefresh.finishRefresh()
                binding.smartRefresh.finishLoadMore()
                if (data != null){
                    articleList.addAll(data.datas)
                    articleAdapter?.submitList(articleList)
                    if (!isCache){
                        viewModel.putCacheArticle(articleList)
                        isCache = true
                    }
                }
            }
        })


        viewModel.bannerLiveData.observe(this,object : IStateObserver<List<Banner>>(binding.banner){
            override fun onReload(v: View?) {
                v?.setOnClickListener{
                  viewModel.getBanner()
                }
            }

            override fun onDataChange(data: List<Banner>?) {
                super.onDataChange(data)
                binding.smartRefresh.finishRefresh()
                binding.smartRefresh.finishLoadMore()
                if (data != null) {
                    bannerList.addAll(data)
                    bannerAdapter?.notifyDataSetChanged()
                }
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                context?.toast(e.message.toString())
            }
        })

        viewModel.collectLiveData.observe(this){
            if (it.errorCode == 0){
                activity?.toast("操作成功")
            }else{
                activity?.toast("操作失败")
            }
        }
    }

    override fun initView() {
        initBanner()
        initRv()
        initSmartFresh()
        initBar()
//        binding.adapter = articleAdapter

    }


    private fun initBar(){
        binding.search.setOnClickListener{
            val intent = Intent(this.context,SearchActivity::class.java)
            this.context?.startActivity(intent)
        }
    }

    override fun initData() {
        viewModel.getBanner()
        lifecycleScope.launch{
            val list = viewModel.getCacheArticle()
            if (list.isEmpty()){
                viewModel.getTopArticle()
                viewModel.getHomeArticle()
            }else{
                articleList.addAll(list)
                articleAdapter?.submitList(articleList)
                isCache = true
                viewModel.page++
            }
        }
    }


    private fun initRv(){

        binding.rvArticle.apply {
            adapter = articleAdapter
//            layoutManager = linearLayoutManager
        }

    }


    private fun initSmartFresh(){
        binding.smartRefresh.setOnRefreshListener {
            binding.smartRefresh.finishRefresh()
        }

//        binding.smartRefresh.setOnLoadMoreListener {
//            viewModel.getArticleMore()
//        }
        binding.smartRefresh.setEnableOverScrollDrag(true)
        binding.smartRefresh.setEnableLoadMore(true)
    }

    private fun initBanner(){
        binding.banner.setAdapter(bannerAdapter)
            .addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(activity)
    }





    companion object{
        fun instance(): HomeFragment {
            return HomeFragment()
        }

        val TAG = "HomeFragment"
    }

}
