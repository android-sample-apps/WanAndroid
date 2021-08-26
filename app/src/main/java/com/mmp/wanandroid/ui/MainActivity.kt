package com.mmp.wanandroid.ui

import android.util.SparseArray
import androidx.fragment.app.Fragment
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivityMainBinding
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.home.view.HomeFragment
import com.mmp.wanandroid.ui.mine.view.MineFragment
import com.mmp.wanandroid.ui.navigation.view.NavigationFragment
import com.mmp.wanandroid.ui.project.view.ProjectContentFragment
import com.mmp.wanandroid.ui.project.view.ProjectFragment
import com.mmp.wanandroid.ui.system.view.SystemFragment

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>() {

    private var mLastIndex = -1
    private val mFragmentArray = SparseArray<Fragment>()
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        initNavigation()
        switchFragment(1)
    }


    private fun initNavigation(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.bottom_menu_home -> switchFragment(1)
                R.id.bottom_menu_navigation -> switchFragment(2)
                R.id.bottom_menu_system -> switchFragment(3)
                R.id.bottom_menu_project -> switchFragment(4)
                R.id.bottom_menu_mine -> switchFragment(5)
                else -> switchFragment(1)
            }
            true
        }
    }

    private fun switchFragment(index: Int){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        mCurrentFragment = fragmentManager.findFragmentByTag(index.toString())
        mLastFragment = fragmentManager.findFragmentByTag(mLastIndex.toString())
        if (index != mLastIndex){
            if (mLastFragment != null){
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null){
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.container,mCurrentFragment!!,index.toString())
            }else{
                transaction.show(mCurrentFragment!!)
            }
        }else{
            if (mCurrentFragment == null){
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.container,mCurrentFragment!!,index.toString())
            }
        }
        transaction.commitAllowingStateLoss()
        mLastIndex = index
    }

    private fun getFragment(index: Int): Fragment{
        var fragment: Fragment? = mFragmentArray.get(index)
        if (fragment == null){
            when(index){
                1 -> fragment = HomeFragment.instance()
                2 -> fragment = NavigationFragment.instance()
                3 -> fragment = SystemFragment.instance()
                4 -> fragment = ProjectFragment.instance()
                5 -> fragment = MineFragment.instance()
            }
            mFragmentArray.put(index,fragment)
        }
        return fragment!!
    }

}