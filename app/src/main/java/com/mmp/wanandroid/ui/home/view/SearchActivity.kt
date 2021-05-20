package com.mmp.wanandroid.ui.home.view

import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AbsListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.donkingliang.labels.LabelsView
import com.mmp.wanandroid.BR
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Article
import com.mmp.wanandroid.data.ArticleData
import com.mmp.wanandroid.data.HotKey
import com.mmp.wanandroid.databinding.ActivitySearchBinding
import com.mmp.wanandroid.room.HistoryKey
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.home.adapter.ArticleAdapter
import com.mmp.wanandroid.ui.home.adapter.HistoryKeyAdapter
import com.mmp.wanandroid.ui.home.adapter.HotKeyAdapter
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.home.viewmodel.SearchViewModel
import com.mmp.wanandroid.utils.KeyboardUtils
import com.mmp.wanandroid.utils.toast

class SearchActivity() : BaseActivity<ActivitySearchBinding,SearchViewModel>(){


    private val articleAdapter = SearchArticleAdapter(this)

    private val hotKeyList = mutableListOf<String>()

    private val articleLayoutManager = LinearLayoutManager(this)

    private val articleList = mutableListOf<Article>()

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        initBar()
        initRv()
        initLabel()
        binding.search.requestFocus()
        KeyboardUtils.showKeyboard(binding.search)
    }

    override fun initData() {
        viewModel.getHotKey()
    }

    private fun initRv(){
        binding.searchArticleRv.apply {
            layoutManager = articleLayoutManager
            adapter = articleAdapter
        }
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
            viewModel.key.value = data.toString()
            viewModel.getArticle()
            binding.textClean.visibility = View.VISIBLE
            KeyboardUtils.hideKeyboard(binding.search)
        }
        binding.historyLabels.setOnLabelClickListener { _, data, _ ->
            viewModel.key.value = (data as HistoryKey).name
            viewModel.getArticle()
            binding.textClean.visibility = View.VISIBLE
            KeyboardUtils.hideKeyboard(binding.search)
        }
        binding.historyKeyClean.setOnClickListener {
            viewModel.clean()
            articleList.clear()
//            articleAdapter.submitList(null)
        }
    }

    private fun initBar(){
        binding.search.setOnEditorActionListener { view, actionId, _ ->
            if ( actionId == EditorInfo.IME_ACTION_SEARCH){
                viewModel.key.value = view.text.toString()
                viewModel.getArticle()
                viewModel.addKey()
                binding.searchKey.visibility = View.GONE
                binding.searchArticleRv.visibility = View.VISIBLE
                binding.textClean.visibility = View.VISIBLE
            }
            true
        }
        binding.searchBack.setOnClickListener {
            finish()
        }
        binding.textClean.setOnClickListener {
            viewModel.key.value = ""
            binding.searchArticleRv.visibility = View.GONE
            binding.searchKey.visibility = View.VISIBLE
            binding.textClean.visibility = View.GONE

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
    }

    override fun getViewModelId(): Int {
        return BR.viewModel
    }

}

