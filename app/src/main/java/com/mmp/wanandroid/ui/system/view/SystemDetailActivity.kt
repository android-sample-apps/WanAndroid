package com.mmp.wanandroid.ui.system.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.databinding.ActivitySystemDetailBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.system.viewmodel.SystemDetailViewModel
import kotlin.properties.Delegates

class SystemDetailActivity : BaseActivity<ActivitySystemDetailBinding,SystemDetailViewModel>() {


    private val articleAdapter = SearchArticleAdapter(this)

    override fun getLayoutId(): Int {
        return R.layout.activity_system_detail
    }

    private var cid: Int? = null

    private var name: String = ""

    override fun initView() {
        initTitleBar()
        initFresh()
        initRv()
    }

    override fun initData() {
        cid = intent.extras?.getInt("cid",0)
        if (viewModel.articleList.isEmpty()){
            viewModel.getRefresh(cid!!)
        }
    }

    override fun initViewObservable() {
        val loadService = binding.smartFresh.registerLoad {
            viewModel.getRefresh(cid!!)
        }
        viewModel.articleData.myObserver(this,loadService){
            viewModel.articleList.addAll(it.datas)
            articleAdapter.submitList(viewModel.articleList)
            binding.smartFresh.finishRefresh()
            binding.smartFresh.finishLoadMore()
        }
    }

    private fun initFresh(){
        binding.smartFresh.setOnLoadMoreListener{
            viewModel.getLoadMore(cid!!)
        }
    }

    private fun initRv(){
        binding.articleRv.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(this@SystemDetailActivity)
        }
    }

    private fun initTitleBar(){
        name = intent.extras?.getString("name").toString()
        binding.titleBar.setTitle(name)
        binding.titleBar.setLeftIconOnClickListener{
            finish()
        }
    }
}