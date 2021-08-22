package com.mmp.wanandroid.ui.project.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.ProjectBean
import com.mmp.wanandroid.databinding.LayoutProjectRvItemBinding
import com.mmp.wanandroid.ui.base.BindingViewHolder
import com.mmp.wanandroid.ui.web.WebActivity
import com.mmp.wanandroid.utils.start

class ProjectAdapter(private val context: Context) : ListAdapter<ProjectBean.Project,BindingViewHolder>(
    COMPARATOR
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = DataBindingUtil.inflate<LayoutProjectRvItemBinding>(LayoutInflater.from(parent.context),
            R.layout.layout_project_rv_item,parent,false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val mProject = getItem(position)
        val binding = holder.binding as LayoutProjectRvItemBinding
        binding.project = mProject
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            if (mProject != null) {
                bundle.putString("url",mProject.link)
            }
            if (mProject != null) {
                bundle.putString("title",mProject.title)
            }
            context.start<WebActivity>(bundle)
        }
        binding.executePendingBindings()
    }

    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<ProjectBean.Project>(){
            override fun areItemsTheSame(
                oldItem: ProjectBean.Project,
                newItem: ProjectBean.Project
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProjectBean.Project,
                newItem: ProjectBean.Project
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}