package com.mmp.wanandroid.ui.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.FragmentSearchBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.home.viewmodel.SearchFragmentViewModel
import com.mmp.wanandroid.utils.toast

private const val ARG_PARAM1 = "param1"

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchFragmentViewModel>(),SearchArticleAdapter.OnCollectListener{
    private var param1: String? = null

    private val articleAdapter by lazy { activity?.let { SearchArticleAdapter(it) } }

    private var mArticle: Article? = null

    override fun initView() {
        binding.rvArticle.adapter = articleAdapter
        binding.sfArticle.apply {
            setOnRefreshListener {
                param1?.let { it1 -> viewModel.getRefresh(it1) }
            }
            setOnLoadMoreListener {
                param1?.let { it1 -> viewModel.getLoadMore(it1) }
            }
        }
    }

    override fun initData() {
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        param1?.let { viewModel.getRefresh(it) }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    fun setSearch(k: String) {
        param1 = k
        viewModel.searchList.clear()
        viewModel.getRefresh(param1!!)

    }

    override fun initViewObservable() {
        val loadService = binding.sfArticle.registerLoad {
            param1?.let { viewModel.getRefresh(it) }
        }
        viewModel.searchLiveData.myObserver(this,loadService){
            viewModel.searchList.addAll(it.datas)
            articleAdapter?.submitList(viewModel.searchList)
            binding.sfArticle.finishRefresh()
            binding.sfArticle.finishLoadMore()
        }

        viewModel.collectLiveData.myObserver(this){
            if (mArticle?.collect == true){
                toast("收藏成功")
            }else{
                toast("取消收藏")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onCollect(article: Article) {
        mArticle = article
        viewModel.collect(article.id)
    }

    override fun unCollect(article: Article) {
        mArticle = article
        viewModel.unCollect(article.id)
    }
}