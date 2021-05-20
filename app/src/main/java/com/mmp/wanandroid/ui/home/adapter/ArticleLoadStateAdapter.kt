package com.mmp.wanandroid.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.LoadStateViewItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder

class ArticleLoadStateAdapter(private val adapter: ArticleAdapter) : LoadStateAdapter<BindingViewHolder>() {
    override fun onBindViewHolder(holder: BindingViewHolder, loadState: LoadState) {
        val binding = DataBindingUtil.getBinding<LoadStateViewItemBinding>(holder.itemView)

        binding?.progressBar?.isVisible = loadState is LoadState.Loading
        binding?.retryButton?.isVisible = loadState is LoadState.Error
        binding?.errorMsg?.isVisible = loadState is LoadState.Error
        binding?.progressBar?.setOnClickListener{
            adapter.retry()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BindingViewHolder {
        val binding = LoadStateViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BindingViewHolder(binding)
    }

}