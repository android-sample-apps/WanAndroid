package com.mmp.wanandroid.ui.project.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.ProjectBean
import com.mmp.wanandroid.databinding.FragmentProjectContentBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.project.adapter.ProjectAdapter
import com.mmp.wanandroid.ui.project.viewmodel.ProjectContentViewModel
import com.mmp.wanandroid.utils.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProjectContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProjectContentFragment : BaseFragment<FragmentProjectContentBinding,ProjectContentViewModel>() {
    // TODO: Rename and change types of parameters
    private var cid: Int? = null

    private val projectAdapter by lazy { activity?.let { ProjectAdapter(it) }}

    override fun initView() {
        cid = arguments?.getInt(ARG_PARAM1,1)
        initFresh()
        initRv()
    }

    override fun initData() {
        if (viewModel.projectList.isEmpty()){
            viewModel.getRefresh(cid!!)
        }
    }

    override fun initViewObservable() {

        val loadService = binding.smartFresh.registerLoad {
            viewModel.getRefresh(cid!!)
        }

        viewModel.projectLiveData.myObserver(this,loadService){
            viewModel.projectList.addAll(it.datas)
            projectAdapter?.submitList(viewModel.projectList)
            binding.smartFresh.finishLoadMore()
            binding.smartFresh.finishRefresh()
        }
    }

    private fun initFresh(){
        binding.smartFresh.setOnRefreshListener {
            viewModel.projectList.clear()
            viewModel.getRefresh(cid!!)
        }
        binding.smartFresh.setOnLoadMoreListener {
            viewModel.getRefresh(cid!!)
        }
    }

    private fun initRv(){
        binding.projectRv.apply {
            adapter = projectAdapter
            layoutManager = GridLayoutManager(activity,2,RecyclerView.VERTICAL,false)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProjectContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(cid: Int) =
            ProjectContentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, cid)
                }
            }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project_content
    }
}