package com.mmp.wanandroid.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Tree
import com.mmp.wanandroid.ui.base.BaseFragment

class HomeFragment(private val mTreeList: List<Tree>) : BaseFragment() {

    private lateinit var viewPager: ViewPager2

    private lateinit var tabLayout: TabLayout


    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        super.initView()
        viewPager = view?.findViewById(R.id.tagViewPager)!!
        tabLayout = view?.findViewById(R.id.tagTabLayout)!!
        val viewPagerFragmentStateAdapter = activity?.let { ViewPagerFragmentStateAdapter(it,mTreeList) }
        viewPager.adapter = viewPagerFragmentStateAdapter
        TabLayoutMediator(tabLayout,viewPager){ tab,position ->
            tab.text = mTreeList[position].name
        }.attach()
    }

    override fun initData() {
        super.initData()

    }





    companion object{
        fun getInstance(treeList: List<Tree>) : HomeFragment{
            return HomeFragment(treeList)
        }
    }
}

class ViewPagerFragmentStateAdapter(activity: FragmentActivity, private val treeList: List<Tree>) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return treeList.size
    }

    override fun createFragment(position: Int): Fragment {
        return ArticleFragment.getInstance(treeList[position].id)
    }
}