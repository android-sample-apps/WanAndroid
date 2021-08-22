package com.mmp.wanandroid.ui.home.view

import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.Article
import com.mmp.wanandroid.model.data.ArticleData
import com.mmp.wanandroid.model.data.HotKey
import com.mmp.wanandroid.databinding.ActivitySearchBinding
//import com.mmp.wanandroid.model.loacl.room.HistoryKey
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.home.viewmodel.SearchViewModel
import com.mmp.wanandroid.utils.KeyboardUtils
import com.mmp.wanandroid.utils.toast

class SearchActivity() : BaseActivity<ActivitySearchBinding,SearchViewModel>(),SearchArticleAdapter.OnCollectListener{


    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        initBar()
        initRv()
        initLabel()

    }

    override fun initData() {
        viewModel.getHotKey()
    }


    private fun initRv(){
        binding.searchArticleRv.apply {
            layoutManager = articleLayoutManager
            adapter = articleAdapter
        }
        articleAdapter.setOnCollectListener(this)
        binding.searchArticleRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = articleLayoutManager.itemCount
                val visibleItemCount = articleLayoutManager.childCount
                val lastVisibleItem = articleLayoutManager.findLastVisibleItemPosition()
                viewModel.listScrolled(visibleItemCount,lastVisibleItem,totalItemCount)
            }
        })
    }

    private fun initLabel(){
        binding.hotKtyLabels.setOnLabelClickListener{ _, data, _ ->
            viewModel.key.set(data.toString())
            viewModel.getArticle()
            binding.textClean.visibility = View.VISIBLE
            viewModel.addKey()
            KeyboardUtils.hideKeyboard(binding.search)
        }
        binding.historyLabels.setOnLabelClickListener { _, data, _ ->
            viewModel.key.set((data as HistoryKey).name)
            viewModel.getArticle()
            binding.textClean.visibility = View.VISIBLE
            viewModel.addKey()
            KeyboardUtils.hideKeyboard(binding.search)
        }
        binding.historyKeyClean.setOnClickListener {
            viewModel.clean()
        }
    }

    private fun initBar(){
        binding.search.requestFocus()
        binding.search.setOnEditorActionListener { view, actionId, _ ->
            if ( actionId == EditorInfo.IME_ACTION_SEARCH){
                if (!TextUtils.isEmpty(view.text.toString())){
                    viewModel.key.set(view.text.toString())
                    viewModel.getArticle()
                    viewModel.addKey()
                    binding.searchKey.visibility = View.GONE
                    binding.searchArticleRv.visibility = View.VISIBLE
                    binding.textClean.visibility = View.VISIBLE
                }else{
                    toast("搜索内容不能为空")
                }
            }
            true
        }
        binding.searchBack.setOnClickListener {
            finish()
        }
        binding.textClean.setOnClickListener {
            viewModel.key.set("")
            binding.searchArticleRv.visibility = View.GONE
            binding.searchKey.visibility = View.VISIBLE
            binding.textClean.visibility = View.GONE

            articleList.clear()
            articleAdapter.submitList(null)
        }
    }


    override fun initViewObservable() {
        viewModel.articleLiveData.observe(this,object : IStateObserver<ArticleData>(binding.searchArticleRv) {
            override fun onReload(v: View?) {
                v?.setOnClickListener{
                    viewModel.getArticle()
                }
            }

            override fun onDataChange(data: ArticleData?) {
                if (data != null) {
                    articleList.addAll(data.datas)
                    articleAdapter.submitList(mutableListOf<Article>().apply{
                        addAll(articleList)
                    })
                    binding.searchKey.visibility = View.INVISIBLE
                    binding.searchArticleRv.visibility = View.VISIBLE
                }
            }

            override fun onError(e: Throwable) {
                toast(e.message.toString())
            }
        })

        viewModel.hotKeyLiveData.observe(this,object : IStateObserver<List<HotKey>>(binding.hotKtyLabels){
            override fun onReload(v: View?) {
                v?.setOnClickListener {
                    viewModel.getHotKey()
                }
            }

            override fun onDataChange(data: List<HotKey>?) {
                data?.let {
                        for (i in 0..7){
                            hotKeyList.add(it[i].name)
                    }
                }
                binding.hotKtyLabels.setLabels(hotKeyList)
            }
        })

        viewModel.historyKeyList.observe(this){
            binding.historyLabels.setLabels(it){ _,_,data ->
                data.name
            }
        }

        viewModel.collectLiveData.observe(this){
            if (it.errorCode == 0){
                toast("操作成功")
            }else{
                toast("操作失败")
            }
        }
    }

    override fun onCollect(article: Article) {
        viewModel.collect(article.id)
    }

    override fun unCollect(article: Article) {
        viewModel.unCollect(article.id)
    }
}

