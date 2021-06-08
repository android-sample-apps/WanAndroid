package com.mmp.wanandroid.ui.mine.view

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.DataState
import com.mmp.wanandroid.databinding.FragmentCollectArticleBinding
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.home.adapter.ArticleAdapter
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.mine.viewmodel.CollectArticleViewModel
import com.mmp.wanandroid.utils.toast

class CollectArticleFragment : BaseFragment<FragmentCollectArticleBinding,CollectArticleViewModel>(),SearchArticleAdapter.OnCollectListener {

    private val articleAdapter by lazy { activity?.let { SearchArticleAdapter(it) } }

    private val articleList = mutableListOf<Article>()

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

        Log.d(TAG,"initView")

        binding.articleRv.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        binding.smartFresh.setOnRefreshListener {
            articleList.clear()
            initData()
        }

        binding.smartFresh.setOnLoadMoreListener {
            viewModel.gerArticleMore()
        }

        binding.smartFresh.autoRefresh()

    }

    override fun initData() {
        viewModel.getCollectArticle()
    }

    override fun initViewObservable() {
        viewModel.articlesLiveData.observe(this,object : IStateObserver<ArticleData>(binding.articleRv){
            override fun onReload(v: View?) {
                v?.setOnClickListener {
                    articleList.clear()
                    initData()
                }
            }

            override fun onDataChange(data: ArticleData?) {
                binding.smartFresh.finishRefresh()
                binding.smartFresh.finishLoadMore()
                data?.datas?.let { articleList.addAll(it) }
                articleAdapter?.submitList(mutableListOf<Article>().apply {
                    addAll(articleList)
                })
            }
        })

        viewModel.collectLiveData.observe(this){
            if (it.errorCode == 0){
                val position = articleList.indexOf(mArticle)
                articleList.removeAt(position)
                articleAdapter?.notifyItemRemoved(position)
                articleAdapter?.notifyItemRangeChanged(position,articleList.size - position)
            }
        }
    }

    companion object{
        fun newInstance(): CollectArticleFragment{
            return CollectArticleFragment()
        }

        const val TAG = "CollectArticleFragment"
    }

}