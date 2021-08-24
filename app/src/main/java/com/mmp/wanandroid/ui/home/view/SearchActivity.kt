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
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.model.loacl.room.HistoryKey
//import com.mmp.wanandroid.model.loacl.room.HistoryKey
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.home.viewmodel.SearchViewModel
import com.mmp.wanandroid.utils.KeyboardUtils
import com.mmp.wanandroid.utils.toast

class SearchActivity() : BaseActivity<ActivitySearchBinding,SearchViewModel>(){

    private lateinit var searchFragment: SearchFragment


    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        initBar()
        initLabel()
    }

    override fun initData() {
        searchFragment = SearchFragment.newInstance("")
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(R.id.container
                    ,searchFragment,SearchFragment::class.simpleName)
            .commitAllowingStateLoss()


    }

    private fun initLabel(){
        binding.hotKtyLabels.setOnLabelClickListener{ _, data, _ ->
            binding.textClean.visibility = View.VISIBLE
            searchFragment.setSearch(data.toString())
            showFragment()
            viewModel.addKey(data.toString())
            KeyboardUtils.hideKeyboard(binding.search)
        }
        binding.historyLabels.setOnLabelClickListener { _, data, _ ->
            binding.textClean.visibility = View.VISIBLE
            viewModel.addKey((data as HistoryKey).name)
            searchFragment.setSearch((data as HistoryKey).name)
            showFragment()
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
                val str = view.text.toString()
                if (!TextUtils.isEmpty(str)){
                    searchFragment.setSearch(str)
                    viewModel.addKey(str)
                    showFragment()
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
            binding.search.setText("")
            binding.textClean.visibility = View.GONE
            hideFragment()
        }
    }


    override fun initViewObservable() {
        val loadService = binding.rlLayout.registerLoad {
            viewModel.getHotKey()
        }
        viewModel.hotKeyLiveData.myObserver(this,loadService){
            for (i in 0..7){
                viewModel.hotkeyList.add(it[i].name)
            }
            binding.hotKtyLabels.setLabels(viewModel.hotkeyList)
        }

        viewModel.historyLiveData.observe(this){
            binding.historyLabels.setLabels(it){ _,_,data ->
                data.name
            }
        }

    }


    private fun hideFragment(){
        supportFragmentManager.beginTransaction()
            .hide(searchFragment)
            .commitAllowingStateLoss()

    }

    private fun showFragment(){
        supportFragmentManager.beginTransaction()
            .show(searchFragment)
            .commitAllowingStateLoss()
    }
}

