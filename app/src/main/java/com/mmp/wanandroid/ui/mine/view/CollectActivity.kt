package com.mmp.wanandroid.ui.mine.view

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivityCollectBinding
import com.mmp.wanandroid.ui.base.BaseActivityNoViewModel

class CollectActivity : BaseActivityNoViewModel<ActivityCollectBinding>() {


    private val tabList = mutableListOf<String>()

    private val fragmentList = mutableListOf<Fragment>()

    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun initView() {
        binding.toolBar.setLeftIconOnClickListener{
            finish()
        }
        tabList.add("文章收藏")
        tabList.add("网址收藏")
        fragmentList.add(CollectArticleFragment.newInstance())
        fragmentList.add(CollectWebFragment.newInstance())

        initViewPager()


    }

    private fun initViewPager(){
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }

        }
        TabLayoutMediator(binding.tabs,binding.viewPager,true){ tab,position ->
            tab.text = tabList[position]
        }.attach()
    }

    companion object{

        const val TAG = "CollectActivity"
    }
}