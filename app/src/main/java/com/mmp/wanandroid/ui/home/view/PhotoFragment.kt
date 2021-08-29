package com.mmp.wanandroid.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mmp.wanandroid.R
import java.io.File

class PhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ImageView(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val path = arguments?.getString(PATH)
        val resource = path?.let { File(it) } ?: R.drawable.photo
        Glide.with(this).load(resource).into(view as ImageView)
    }

    companion object{

        private const val PATH = "FILE_PATH"

        fun newInstance(path: String) : PhotoFragment{
            return PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(PATH,path)
                }
            }
        }
    }
}