package com.mmp.wanandroid.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mmp.wanandroid.R
import com.mmp.wanandroid.databinding.FragmentGalleryBinding
import java.io.File
import java.util.*

const val PATH = "FILE_PATH"
val EXTENSION_WHITELIST = arrayOf("JPG")

class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding

    private lateinit var mediaList: MutableList<File>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val path = arguments?.getString(PATH) ?: ""
        val rootDirectory = File(path)
        mediaList = rootDirectory.listFiles{ file ->
            EXTENSION_WHITELIST.contains(file.extension.uppercase(Locale.getDefault()))
        }?.sortedDescending()?.toMutableList() ?: mutableListOf()

        binding.viewPager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return mediaList.size
            }

            override fun createFragment(position: Int): Fragment {
                return PhotoFragment.newInstance(mediaList[position].absolutePath)
            }

        }

        binding.ivBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    companion object{
        fun newInstance(path: String) = GalleryFragment().apply {
            arguments = Bundle().apply {
                putString(PATH,path)
            }
        }
    }
}