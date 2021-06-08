package com.mmp.wanandroid.ui.mine.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.Integral
import com.mmp.wanandroid.data.Web
import com.mmp.wanandroid.databinding.WebRvItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder
import com.mmp.wanandroid.ui.mine.view.CollectWebFragment
import com.mmp.wanandroid.ui.web.WebActivity
import com.mmp.wanandroid.utils.start

class WebAdapter(private val mFragment: CollectWebFragment) : ListAdapter<Web,BindingViewHolder>(COMPARATOR) {


    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<Web>(){
            override fun areItemsTheSame(oldItem: Web, newItem: Web): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Web, newItem: Web): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil.inflate<WebRvItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.web_rv_item,
                parent,
                false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val mWeb = getItem(position)
        val binding = holder.binding as WebRvItemBinding

        binding.web = mWeb
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title",mWeb.name)
            bundle.putString("url",mWeb.link)
            mFragment.activity?.start<WebActivity>(bundle)
        }

        binding?.executePendingBindings()
    }

}