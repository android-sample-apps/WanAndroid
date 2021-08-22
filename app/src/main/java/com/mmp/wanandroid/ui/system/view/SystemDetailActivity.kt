package com.mmp.wanandroid.ui.system.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.databinding.ActivitySystemDetailBinding
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.system.viewmodel.SystemDetailViewModel
import kotlin.properties.Delegates

class SystemDetailActivity : BaseActivity<ActivitySystemDetailBinding,SystemDetailViewModel>() {


    private val articleAdapter = SearchArticleAdapter(this)

    private val articleList = mutableListOf<Article>()

    override fun getLayoutId(): Int {
        return R.layout.activity_system_detail
    }

    private var cid by Delegates.notNull<Int>()

    private lateinit var name: String

    override fun initView() {
        initTitleBar()
        initFresh()
        initRv()
    }

    override fun initData() {
        cid = intent.extras!!.getInt("cid")
        viewModel.getSystemArticle(cid)
    }

    override fun initViewObservable() {
        viewModel.articlesLiveData.observe(this,object : IStateObserver<ArticleData>(binding.articleRv){
            override fun onReload(v: View?) {
                v?.setOnClickListener {
                    initData()
                }
            }

            override fun onDataChange(data: ArticleData?) {
                binding.smartFresh.finishRefresh()
                binding.smartFresh.finishLoadMore()
                if (data != null){
                    articleList.addAll(data.datas)
                    articleAdapter.submitList(mutableListOf<Article>().apply {
                        addAll(articleList)
                    })
                }
            }
        })
    }

    private fun initFresh(){
        binding.smartFresh.autoRefresh()
        binding.smartFresh.setOnLoadMoreListener{
            viewModel.getMoreArticle(cid)
        }
    }

    private fun initRv(){
        binding.articleRv.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(this@SystemDetailActivity)
        }
    }

    private fun initTitleBar(){
        name = intent.extras!!.getString("name").toString()
        binding.titleBar.setTitle(name)
        binding.titleBar.setLeftIconOnClickListener{
            finish()
        }
    }
}