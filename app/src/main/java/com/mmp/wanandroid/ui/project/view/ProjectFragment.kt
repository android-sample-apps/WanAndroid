package com.mmp.wanandroid.ui.project.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.ProjectTree
import com.mmp.wanandroid.databinding.FragmentProjectBinding
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.project.viewmodel.ProjectViewModel
import com.mmp.wanandroid.utils.toast


class ProjectFragment : BaseFragment<FragmentProjectBinding,ProjectViewModel>() {


    override fun initViewObservable() {
        viewModel.treeLiveData.observe(this){
            if (it.isEmpty()){
                viewModel.getProjectTree()
            }else{
                initViewPage(it)
            }
        }

        viewModel.projectTreeLiveData.observe(this){
            if (it.isSuccess){
                it.data?.let { it1 -> viewModel.addProjectTree(it1) }
            }else{
                activity?.toast(it.errorMsg.toString())
            }
        }
    }

    private fun initViewPage(list: List<ProjectTree>){
        val fragments = mutableListOf<Fragment>()
        list.forEach{
            val fragment = ProjectContentFragment(it.id)
            fragments.add(fragment)
        }
        binding.viewPager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

        }

        TabLayoutMediator(binding.tabs,binding.viewPager,true){ tab,position ->
            tab.text = list[position].name
        }.attach()
    }

    companion object {
        fun instance(): ProjectFragment = ProjectFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }
}