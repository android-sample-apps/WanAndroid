package com.mmp.wanandroid.ui.system.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder
import com.kunminx.linkage.bean.BaseGroupedItem
import com.kunminx.linkage.bean.DefaultGroupedItem
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig
import com.mmp.wanandroid.R
import com.mmp.wanandroid.model.data.SystemTree
import com.mmp.wanandroid.databinding.FragmentSystemBinding
import com.mmp.wanandroid.ext.myObserver
import com.mmp.wanandroid.ext.registerLoad
import com.mmp.wanandroid.ui.base.BaseFragment
import com.mmp.wanandroid.ui.base.IStateObserver
import com.mmp.wanandroid.ui.system.viewmodel.SystemViewModel
import com.mmp.wanandroid.utils.start

class SystemFragment : BaseFragment<FragmentSystemBinding,SystemViewModel>() {

    override fun initView() {
        binding.smartFresh.autoRefresh()
        binding.smartFresh.setEnableLoadMore(false)
    }

    override fun initData() {
    }

    override fun initViewObservable() {

        val loadService = binding.linkage.registerLoad {
            viewModel.getTree()
        }
        viewModel.treeLiveData.myObserver(this,loadService){
            val list = mutableListOf<DefaultGroupedItem>()
            it.forEach{ tree ->
                list.add(DefaultGroupedItem(true,tree.name))
                for (child in tree.children){
                    list.add(DefaultGroupedItem(DefaultGroupedItem.ItemInfo(child.name,tree.name,
                        child.id.toString()
                    )))
                }
            }
            binding.linkage.init(list,PrimaryAdapterConfig(),SecondAdapterConfig())
            binding.smartFresh.finishRefresh()
        }
    }


    companion object {
        fun instance(): SystemFragment = SystemFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }


    class PrimaryAdapterConfig : ILinkagePrimaryAdapterConfig{

        private lateinit var mContext: Context

        override fun setContext(context: Context?) {
            if (context != null) {
                mContext = context
            }
        }

        override fun getLayoutId(): Int {
            return R.layout.adapter_linkage_primary
        }

        override fun getGroupTitleViewId(): Int {
            return R.id.tv_group
        }

        override fun getRootViewId(): Int {
            return R.id.layout_group
        }

        override fun onBindViewHolder(
            holder: LinkagePrimaryViewHolder?,
            selected: Boolean,
            title: String?
        ) {
            val tvTitle: TextView = holder?.mGroupTitle as TextView

            tvTitle.setBackgroundColor(mContext.resources.getColor(if (selected)R.color.red else R.color.white ))
            tvTitle.apply {
                text = title
                setBackgroundColor(mContext.resources.getColor(if (selected)R.color.red else R.color.white ))
                ellipsize = if (selected) TextUtils.TruncateAt.MARQUEE else TextUtils.TruncateAt.END
                isFocusable = selected
                isFocusableInTouchMode = selected
                marqueeRepeatLimit = if (selected) -1 else 0
            }
        }

        override fun onItemClick(holder: LinkagePrimaryViewHolder?, view: View?, title: String?) {

        }

    }

    class SecondAdapterConfig : ILinkageSecondaryAdapterConfig<DefaultGroupedItem.ItemInfo>{

        private lateinit var mContext: Context

        override fun setContext(context: Context?) {
            if (context != null) {
                mContext = context
            }
        }

        override fun getGridLayoutId(): Int {
            return 0
        }

        override fun getLinearLayoutId(): Int {
            return R.layout.adapter_linkage_second
        }

        override fun getHeaderLayoutId(): Int {
            return R.layout.adapter_linkage_second_header
        }

        override fun getFooterLayoutId(): Int {
            return 0
        }

        override fun getHeaderTextViewId(): Int {
            return R.id.second_header
        }

        override fun getSpanCountOfGridMode(): Int {
            return 2
        }

        override fun onBindViewHolder(
            holder: LinkageSecondaryViewHolder?,
            item: BaseGroupedItem<DefaultGroupedItem.ItemInfo>?
        ) {
            val title = holder?.getView<TextView>(R.id.iv_title)
            title?.text = item?.info?.title
            holder?.itemView?.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("cid", item!!.info.content.toInt())
                bundle.putString("name",item.info.title)
                mContext.start<SystemDetailActivity>(bundle)
            }
        }

        override fun onBindHeaderViewHolder(
            holder: LinkageSecondaryHeaderViewHolder?,
            item: BaseGroupedItem<DefaultGroupedItem.ItemInfo>?
        ) {
            val title = holder?.getView<TextView>(R.id.second_header)
            title?.text = item?.header
        }

        override fun onBindFooterViewHolder(
            holder: LinkageSecondaryFooterViewHolder?,
            item: BaseGroupedItem<DefaultGroupedItem.ItemInfo>?
        ) {
        }

    }
}