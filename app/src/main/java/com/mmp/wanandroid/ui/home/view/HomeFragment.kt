package com.mmp.wanandroid.ui.home.view

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.*
import com.mmp.wanandroid.databinding.FragmentHomeBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.model.remote.DataStatus
import com.mmp.wanandroid.ui.ShareViewModel
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.base.MyApplication
import com.mmp.wanandroid.ui.home.adapter.ImageAdapter
import com.mmp.wanandroid.ui.home.adapter.SearchArticleAdapter
import com.mmp.wanandroid.ui.home.viewmodel.HomeViewModel
import com.mmp.wanandroid.utils.start
import com.mmp.wanandroid.utils.toast
import com.youth.banner.indicator.CircleIndicator
import kotlinx.coroutines.launch
import permissions.dispatcher.*
import timber.log.Timber

@RuntimePermissions
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(),SearchArticleAdapter.OnCollectListener{


    private val bannerAdapter by lazy { context?.let { ImageAdapter(viewModel.bannerList,it) } }

    private val shareViewModel by lazy {
        ViewModelProvider(requireActivity().application as MyApplication).get(ShareViewModel::class.java)
    }

    private val articleAdapter by lazy {
        activity?.let { SearchArticleAdapter(it) }
    }

    private var mArticle: Article? = null

    override fun onCollect(article: Article) {
        mArticle = article
        viewModel.collect(article.id)
    }

    override fun unCollect(article: Article) {
        mArticle = article
        viewModel.unCollect(article.id)
    }


    override fun getLayoutId(): Int = R.layout.fragment_home


    override fun initViewObservable() {

        val loadService = binding.smartRefresh.registerLoad {
            viewModel.getBanner()
            viewModel.getRefresh()
        }

        viewModel.bannerLiveData.myObserver(this,loadService){
            viewModel.bannerList.addAll(it)
            bannerAdapter?.apply {
                setDatas(viewModel.bannerList)
                notifyDataSetChanged()
            }
        }

        viewModel.articleLiveData.myObserver(this,loadService){
            binding.smartRefresh.finishLoadMore()
            binding.smartRefresh.finishRefresh()
            viewModel.articleList.addAll(it.datas)
            articleAdapter?.submitList(viewModel.articleList)
        }

        viewModel.collectLiveData.myObserver(this){
            if (mArticle?.collect == true){
                toast("????????????")
            }else{
                toast("????????????")
            }
        }

        shareViewModel.loginLiveData.observe(this){
            if (it != null){
                viewModel.articleList.forEach { article ->
                    it.collectIds.forEach { id ->
                        if (id == article.id){
                            article.collect = true
                        }
                    }
                }
            }
        }

        shareViewModel.collectLiveData.observe(this){
            if (it != null){
                viewModel.articleList.forEach { article ->
                    if (it.id == article.id){
                        article.collect = it.collect
                    }
                }
            }
        }
    }

    override fun initView() {
        initBanner()
        initRv()
        initBar()
        binding.ivCamera.setOnClickListener {
//            showCameraWithPermissionCheck()
            childFragmentManager.beginTransaction().add(BtnBottomDialog(),BtnBottomDialog::class.java.simpleName)
                .commit()
        }
    }


    private fun initBar(){
        binding.search.setOnClickListener{
            val intent = Intent(context,SearchActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun initData() {
    }


    private fun initRv(){
        binding.rvArticle.apply {
            adapter = articleAdapter
        }

    }


    private fun initBanner(){
        binding.banner.setAdapter(bannerAdapter)
            .addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(activity)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun showCamera(){
        activity?.apply {
            start<CameraActivity>()
        }
    }


    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied(){
        AlertDialog.Builder(requireActivity())
            .setMessage("??????????????????????????????????????????")
            .setPositiveButton("??????") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode,grantResults)
    }

    companion object{
        fun instance(): HomeFragment {
            return HomeFragment()
        }

        val TAG = "HomeFragment"
    }

}
