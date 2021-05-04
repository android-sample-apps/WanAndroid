package com.mmp.wanandroid.ui.home.view

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Banner
import com.mmp.wanandroid.databinding.FragmentHomeBinding
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.home.adapter.ImageAdapter

import com.mmp.wanandroid.ui.home.viewmodel.HomeViewModel
import com.youth.banner.indicator.CircleIndicator

class HomeFragment() : BaseFragment<FragmentHomeBinding,HomeViewModel>() {
//
//    private lateinit var viewPager: ViewPager2
//
//    private lateinit var tabLayout: TabLayout
//
//    private lateinit var homeSearchView: FrameLayout
//
//    private lateinit var homeSettingView: LinearLayout
//

    private var bannerList = listOf<Banner>()

    private val adapter by lazy {  ImageAdapter(bannerList) }

//
//    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
//
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu,menu)
    }

    override fun initViewObservable() {
        setLiveDataObserve(viewModel.bannerLiveData){
            bannerList = it
            initBanner()
        }

    }

    override fun initView() {
        viewModel.getBanner()
    }

    private fun initBanner(){
        binding.banner.setAdapter(adapter)
            .addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(activity)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.scan -> TODO()
        }
        return true
    }

    override fun reload() {

    }
    //
//    override fun initView(view: View) {
//        super.initView(view)
//        homeSearchView = view.findViewById(R.id.homeSearch)
//        viewPager = view.findViewById(R.id.tagViewPager)
//        tabLayout = view.findViewById(R.id.tagTabLayout)
//        homeSearchView = view.findViewById(R.id.homeSetting)
//        val viewPagerFragmentStateAdapter = activity?.let { ViewPagerFragmentStateAdapter(it,mTreeList) }
//        viewPager.adapter = viewPagerFragmentStateAdapter
//        TabLayoutMediator(tabLayout,viewPager){ tab,position ->
//            tab.text = mTreeList[position].name
//        }.attach()
//        homeSearchView.setOnClickListener {
//            val intent = Intent(activity, SearchActivity::class.java)
//            startActivity(intent)
//        }
//        homeSettingView.setOnClickListener {
//            val intent = Intent(activity,SettingActivity::class.java)
//
//        }
//    }
//
//    override fun initData() {
//        super.initData()
//        viewModel.treeLiveData.observe(this){
//            if ()
//        }
//    }

    companion object{
        fun instance(): HomeFragment {
            return HomeFragment()
        }

        val TAG = "HomeFragment"
    }

}

//class ViewPagerFragmentStateAdapter(activity: FragmentActivity, private val treeList: List<Tree>) : FragmentStateAdapter(activity) {
//
//    override fun getItemCount(): Int {
//        return treeList.size
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        return ArticleFragment.getInstance(treeList[position].id)
//    }
//}