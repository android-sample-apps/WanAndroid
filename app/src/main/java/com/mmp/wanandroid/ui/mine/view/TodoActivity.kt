package com.mmp.wanandroid.ui.mine.view

import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.ActivityTodoBinding
import com.mmp.wanandroid.ui.base.BaseActivity
import com.mmp.wanandroid.ui.mine.viewmodel.TodoViewModel
import com.mmp.wanandroid.utils.start

class TodoActivity : BaseActivity<ActivityTodoBinding,TodoViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_todo
    }


    override fun initView() {
        binding.todoAdd.setOnClickListener {
            start<TodoCreateActivity>()
        }
    }
}