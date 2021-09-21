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
import com.mmp.wanandroid.ui.base.BindingViewHolder

class HotKeyAdapter(private val context: Context) : ListAdapter<HotKey,BindingViewHolder>(COMPARATOR) {

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
        val hotkey = getItem(position)
        binding.article = hotkey.name
        binding.root.setOnClickListener {
            onLabelClickListener?.invoke(hotkey.name)
        }
    }

    companion object{
        val COMPARATOR =object : DiffUtil.ItemCallback<HotKey>(){
            override fun areItemsTheSame(oldItem: HotKey, newItem: HotKey): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HotKey, newItem: HotKey): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}