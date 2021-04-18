package com.mmp.wanandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Tree
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.home.HomeFragment

class MainActivity : BaseActivity() {

    private var mLastIndex = -1
    private val mFragmentArray = SparseArray<Fragment>()
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    private val mTreeList = mutableListOf<Tree>()

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
        mTreeList.add(Tree(null,0,"热门博文"))
        mTreeList.add(Tree(null,152,"framework"))
        viewModel.setFresh(true)
        viewModel.treeLiveData.observe(this){
            val treeList = it.getOrNull()
            if (treeList != null){
                mTreeList.addAll(treeList)
            }
        }
        switchFragment(1)
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
                transaction.add(R.id.content,mCurrentFragment!!,index.toString())
            }else{
                transaction.show(mCurrentFragment!!)
            }
        }else{
            if (mCurrentFragment == null){
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content,mCurrentFragment!!,index.toString())
            }
        }
        transaction.commit()
        mLastIndex = index
    }

    private fun getFragment(index: Int): Fragment{
        var fragment: Fragment? = mFragmentArray.get(index)
        if (fragment == null){
            when(index){
                1 -> fragment = HomeFragment.getInstance(mTreeList)
            }
            mFragmentArray.put(index,fragment)
        }
        return fragment!!
    }
}