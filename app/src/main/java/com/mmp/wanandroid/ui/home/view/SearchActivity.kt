package com.mmp.wanandroid.ui.home.view

import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import com.google.android.flexbox.FlexboxLayoutManager
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivitySearchBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.home.adapter.HistoryKeyAdapter
import com.mmp.wanandroid.ui.home.adapter.HotKeyAdapter
import com.mmp.wanandroid.ui.home.viewmodel.SearchViewModel
import com.mmp.wanandroid.utils.KeyboardUtils
import com.mmp.wanandroid.utils.toast

class SearchActivity : BaseActivity<ActivitySearchBinding,SearchViewModel>(){

    private lateinit var searchFragment: SearchFragment

    private val hotKeyAdapter = HotKeyAdapter(this)
    private val historyKeyAdapter = HistoryKeyAdapter(this)

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        initBar()
        binding.hotkeyLabels.adapter = hotKeyAdapter
        binding.hotkeyLabels.layoutManager = FlexboxLayoutManager(this)
        binding.historyLabels.apply {
            adapter = historyKeyAdapter
            layoutManager = FlexboxLayoutManager(this@SearchActivity)
        }
        initLabel()
    }

    override fun initData() {
        searchFragment = SearchFragment.newInstance("")
        val transaction = supportFragmentManager.beginTransaction()
        transaction
            .add(R.id.container
                    ,searchFragment,SearchFragment::class.simpleName)
            .commit()
        hideFragment()


    }

    private fun initLabel(){
        hotKeyAdapter.setOnLabelClickListener{
            binding.textClean.visibility = View.VISIBLE
            binding.search.setText(it)
            searchFragment.setSearch(it)
            showFragment()
            viewModel.addKey(it)
            KeyboardUtils.hideKeyboard(binding.search)
        }
        historyKeyAdapter.setOnLabelClickListener {
            binding.textClean.visibility = View.VISIBLE
            binding.search.setText(it)
            viewModel.addKey(it)
            searchFragment.setSearch(it)
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
                viewModel.hotkeyList.add(it[i])
            }
            hotKeyAdapter.submitList(viewModel.hotkeyList)
        }

        viewModel.historyLiveData.observe(this){
            historyKeyAdapter.submitList(it)
        }

    }


    private fun hideFragment(){
        supportFragmentManager.beginTransaction()
            .hide(searchFragment)
            .commit()

    }

    private fun showFragment(){
        supportFragmentManager.beginTransaction()
            .show(searchFragment)
            .commit()
    }
}

