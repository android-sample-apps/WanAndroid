package com.mmp.wanandroid.ui.home.view

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.*
import com.mmp.wanandroid.databinding.FragmentHomeBinding
import com.mmp.wanandroid.extension.safeAs
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.home.adapter.ArticleAdapter
import com.mmp.wanandroid.ui.home.adapter.ImageAdapter
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.home.viewmodel.HomeViewModel
import com.mmp.wanandroid.utils.toast
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(),SearchArticleAdapter.OnCollectListener{

    @Inject lateinit var articleAdapter: ArticleAdapter

//    private val bannerAdapter by lazy { context?.let { ImageAdapter(viewModel.bannerList,it) } }

    @Inject lateinit var bannerAdapter: ImageAdapter


    var page = 0

    override fun onCollect(article: Article) {
        viewModel.collect(article.id)
    }

    override fun unCollect(article: Article) {
        viewModel.unCollect(article.id)
    }


    override fun getLayoutId(): Int = R.layout.fragment_home


    override fun initViewObservable() {

        viewModel.articleLiveData.observe(this){
            loadService.showWithConvertor(it)
            when(it){
                is DataStatus.Success -> articleAdapter.addData(it.data)
                is DataStatus.Failure -> toast("${it.t}")
                else -> null
            }
        }

        viewModel.bannerLiveData.observe(this){
            loadService.showWithConvertor(it)
            when(it){
                is DataStatus.Success -> {
                    bannerAdapter.setDatas(it.data)
                    bannerAdapter.notifyDataSetChanged()
                }
                is DataStatus.Failure -> toast("${it.t}")
                else -> null
            }
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
            page = 0
            viewModel.articleList.clear()
            viewModel.getArticle(page)
        }

        binding.smartRefresh.setOnLoadMoreListener {
            page++
            viewModel.getArticle(page)
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
            page = 0
            viewModel.getBanner()
            viewModel.getArticle(page)
        }
    }

    companion object{
        fun instance(): HomeFragment {
            return HomeFragment()
        }

        const val TAG = "HomeFragment"
    }

}
