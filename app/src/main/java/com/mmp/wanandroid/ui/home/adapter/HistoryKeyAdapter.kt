package com.mmp.wanandroid.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.mmp.wanandroid.R
import com.mmp.wanandroid.data.HotKey
import com.mmp.wanandroid.databinding.SearchHistoryKeyRvItemBinding
import com.mmp.wanandroid.databinding.SearchKeyRvItemBinding
import com.mmp.wanandroid.room.HistoryKey


class HistoryKeyAdapter : ListAdapter<HistoryKey, HistoryKeyAdapter.ViewHolder>(COMPARATOR){

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<HistoryKey>() {
            override fun areItemsTheSame(oldItem: HistoryKey, newItem: HistoryKey): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HistoryKey, newItem: HistoryKey): Boolean {
                return oldItem == newItem
            }
        }
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.history_key_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_history_key_rv_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyKey = getItem(position)
        holder.name.text = historyKey.name
    }
}