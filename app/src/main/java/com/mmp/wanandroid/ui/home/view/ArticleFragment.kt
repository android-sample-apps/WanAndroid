package com.mmp.wanandroid.ui.home.view
//
//import android.util.Log
//import android.view.View
//import android.widget.ProgressBar
//import android.widget.Toast
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.lifecycleScope
//import androidx.paging.LoadState
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.mmp.wanandroid.R
//import com.mmp.wanandroid.ui.base.BaseFragment
//import com.mmp.wanandroid.ui.home.adapter.ArticleAdapter
//import com.mmp.wanandroid.ui.home.viewmodel.ArticleViewModel
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.launch
//
//class ArticleFragment(private val cid: Int) : BaseFragment() {
//
//    private val TAG = "ArticleFragment"
//
//    private val viewModel by lazy { ViewModelProvider(this).get(ArticleViewModel::class.java) }
//
//    private val articleAdapter = ArticleAdapter()
//
//
//
//    override fun initView(view: View) {
//        super.initView(view)
//        val recyclerView: RecyclerView = view.findViewById(R.id.articleRv)
//        val progressBar: ProgressBar = view.findViewById(R.id.article_progress)
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = articleAdapter
//        lifecycleScope.launch {
//            if (cid == 0){
//                viewModel.getHomeArticleData().collect { pagingData ->
//                    articleAdapter.submitData(pagingData)
//                }
//            }else{
//                viewModel.getArticleData(cid).collect { pagingData ->
//                    articleAdapter.submitData(pagingData)
//                }
//            }
//        }
//        articleAdapter.addLoadStateListener {
//            when(it.refresh){
//                is LoadState.NotLoading -> {
//                    progressBar.visibility = View.INVISIBLE
//                    recyclerView.visibility = View.VISIBLE
//                }
//                is LoadState.Error -> {
//                    val state = it.refresh as LoadState.Error
//                    progressBar.visibility = View.INVISIBLE
//                    Toast.makeText(activity,state.error.message,Toast.LENGTH_LONG).show()
//                    Log.d(TAG,state.error.message.toString())
//                }
//                is LoadState.Loading -> {
//                    progressBar.visibility = View.VISIBLE
//                    recyclerView.visibility = View.INVISIBLE
//                }
//            }
//        }
//    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.fragment_article
//    }
//
//    companion object{
//        fun getInstance(cid: Int) : ArticleFragment {
//            return ArticleFragment(cid)
//        }
//    }
//
//}