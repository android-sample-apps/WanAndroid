package com.mmp.wanandroid.ui.mine.view

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.databinding.FragmentCollectArticleBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.mine.viewmodel.CollectArticleViewModel

class CollectArticleFragment : BaseFragment<FragmentCollectArticleBinding,CollectArticleViewModel>(),SearchArticleAdapter.OnCollectListener {

    private val articleAdapter by lazy { activity?.let { SearchArticleAdapter(it) } }

    private var mArticle: Article? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_collect_article
    }

    override fun onCollect(article: Article) {

    }

    override fun unCollect(article: Article) {
        mArticle = article
        viewModel.unCollect(article.id)
    }

    override fun initView() {

        binding.articleRv.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    override fun initData() {
    }

    override fun initViewObservable() {
        val loadService = binding.smartFresh.registerLoad {
            viewModel.getRefresh()
        }
        viewModel.articleLiveData.myObserver(this,loadService){
            viewModel.articleList.addAll(it.datas)
            articleAdapter?.submitList(viewModel.articleList)
            binding.smartFresh.finishRefresh()
            binding.smartFresh.finishLoadMore()
        }
    }

    companion object{
        fun newInstance(): CollectArticleFragment{
            return CollectArticleFragment()
        }

        const val TAG = "CollectArticleFragment"
    }

}