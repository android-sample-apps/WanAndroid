package com.mmp.wanandroid.ui.mine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.Integral
import com.mmp.wanandroid.databinding.IntegralRvItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder

class CoinAdapter : ListAdapter<Integral,BindingViewHolder>(COMPARATOR) {


    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<Integral>(){
            override fun areItemsTheSame(oldItem: Integral, newItem: Integral): Boolean {
                return oldItem.desc == newItem.desc
            }

            override fun areContentsTheSame(oldItem: Integral, newItem: Integral): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil.inflate<IntegralRvItemBinding>(LayoutInflater.from(parent.context),
                R.layout.integral_rv_item,parent,false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val mIntegral = getItem(position)
        val binding = DataBindingUtil.getBinding<IntegralRvItemBinding>(holder.itemView)?.apply {
            integral = mIntegral
        }
        binding?.executePendingBindings()
    }
}