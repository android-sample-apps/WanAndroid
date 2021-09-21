package com.mmp.wanandroid.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.NavItemRvItemBinding
import com.mmp.wanandroid.model.data.HotKey
import com.mmp.wanandroid.model.loacl.room.HistoryKey
import com.mmp.wanandroid.ui.base.BindingViewHolder

class HistoryKeyAdapter(private val context: Context) : ListAdapter<HistoryKey, BindingViewHolder>(COMPARATOR) {

    private var onLabelClickListener: ((String) -> Unit)? = null

    fun setOnLabelClickListener(listener: (String) -> Unit){
        onLabelClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil.inflate<NavItemRvItemBinding>(LayoutInflater.from(context), R.layout.nav_item_rv_item,parent,false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val binding = holder.binding as NavItemRvItemBinding
        val historyKey = getItem(position)
        binding.article = historyKey.name
        binding.root.setOnClickListener {
            onLabelClickListener?.invoke(historyKey.name)
        }
    }

    companion object{
        val COMPARATOR =object : DiffUtil.ItemCallback<HistoryKey>(){
            override fun areItemsTheSame(oldItem: HistoryKey, newItem: HistoryKey): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HistoryKey, newItem: HistoryKey): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

}