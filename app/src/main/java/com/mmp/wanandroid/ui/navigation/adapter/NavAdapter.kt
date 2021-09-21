package com.mmp.wanandroid.ui.navigation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.google.android.flexbox.FlexboxLayoutManager
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.NavBean
import com.mmp.wanandroid.databinding.NavRvItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder

class NavAdapter(private val context: Context) : ListAdapter<NavBean,BindingViewHolder>(COMPARATOR) {
    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<NavBean>(){
            override fun areItemsTheSame(oldItem: NavBean, newItem: NavBean): Boolean {
                return oldItem.cid == newItem.cid
            }

            override fun areContentsTheSame(oldItem: NavBean, newItem: NavBean): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil.inflate<NavRvItemBinding>(LayoutInflater.from(parent.context),
            R.layout.nav_rv_item,
            parent,
            false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val mNavBean = getItem(position)
        (holder.binding as NavRvItemBinding).navBean = mNavBean
        val navItemAdapter = NavItemAdapter(context)
        holder.binding.itemRv.apply {
            adapter = navItemAdapter
            layoutManager = FlexboxLayoutManager(context)
        }
        navItemAdapter.submitList(mNavBean.articles)
    }
}