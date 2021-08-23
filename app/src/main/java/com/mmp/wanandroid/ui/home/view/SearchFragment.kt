package com.mmp.wanandroid.ui.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mmp.wanandroid.R
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.home.viewmodel.SearchFragmentViewModel

private const val ARG_PARAM1 = "param1"

class SearchFragment : BaseFragment<FragmentSearchBinding,SearchFragmentViewModel>() {
    private var param1: String? = null



    override fun initView() {
    }

    override fun initData() {
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}