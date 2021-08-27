package com.mmp.wanandroid.ui.home.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmp.wanandroid.R
import com.mmp.wanandroid.utils.start

/**
 * @author chen
 * @date 2021/8/26
 * des
 **/
class BtnBottomDialog : DialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_btn_dialog,container,false)
        val window = dialog?.window
        window?.setBackgroundDrawableResource(R.color.transparent)
        window?.decorView?.setPadding(0,0,0,0)
        val params = window?.attributes
        params?.gravity = Gravity.BOTTOM
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = params
        val slide = TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
            Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0f)
        slide.apply {
            duration = 400
            isFillEnabled = true
            fillAfter = true
        }
        rootView.startAnimation(slide)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btCamera = view.findViewById<Button>(R.id.bt_camera)
        val btPhoto = view.findViewById<Button>(R.id.bt_photo)
        val btCancel = view.findViewById<Button>(R.id.bt_cancel)
        btCamera.setOnClickListener {
            it.context.start<CameraActivity>()
        }
        btCancel.setOnClickListener {
            dismiss()
        }
    }
}