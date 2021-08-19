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
import com.mmp.wanandroid.network.DataStatus
import com.mmp.wanandroid.network.Success
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.base.MyApplication
import com.mmp.wanandroid.ui.home.adapter.ArticleAdapter
import com.mmp.wanandroid.ui.home.adapter.ImageAdapter
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.home.viewmodel.HomeViewModel
import com.mmp.wanandroid.utils.toast
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(),SearchArticleAdapter.OnCollectListener{

    private val bannerAdapter by lazy { context?.let { ImageAdapter(viewModel.bannerList,it) } }

    private val articleAdapter by lazy { ArticleAdapter(viewModel.articleList) }

    override fun onCollect(article: Article) {
        viewModel.collect(article.id)
    }

    override fun unCollect(article: Article) {
        viewModel.unCollect(article.id)
    }


    override fun getLayoutId(): Int = R.layout.fragment_home


    override fun initViewObservable() {


        myObserve(viewModel.articleLiveData){
            if (viewModel.page == 0){
                articleAdapter.addData(convert<MutableList<Article>>(it).toList())
            }else{
                articleAdapter.addData(convert<ArticleData>(it).datas.toList())
            }
            binding.smartRefresh.finishLoadMore()
            binding.smartRefresh.finishRefresh()
        }

        myObserve(viewModel.bannerLiveData){
            bannerAdapter?.setDatas(convert<List<Banner>>(it))
            bannerAdapter?.notifyDataSetChanged()
        }


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

    }


    private fun initRv(){

        binding.rvArticle.apply {
            adapter = articleAdapter
        }

    }


    private fun initSmartFresh(){
        binding.smartRefresh.setOnRefreshListener {
            viewModel.page = 0
            viewModel.articleList.clear()
            viewModel.getArticle()
        }

        binding.smartRefresh.setOnLoadMoreListener {
            viewModel.getArticle()
        }
        binding.smartRefresh.setEnableLoadMore(true)
    }

    private fun initBanner(){
        binding.banner.setAdapter(bannerAdapter)
            .addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(activity)
    }


    override fun onReload(v: View?) {
        v?.setOnClickListener {
            viewModel.getBanner()
            viewModel.getArticle()
        }
    }

    companion object{
        fun instance(): HomeFragment {
            return HomeFragment()
        }

        val TAG = "HomeFragment"
    }

}
