package com.mmp.wanandroid.ui.home.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mmp.wanandroid.R

/**
 * @author chen
 * @date 2021/8/26
 * des
 **/
class BtnBottomDialog : BottomSheetDialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity == null){
            return super.onCreateDialog(savedInstanceState)
        }
        val dialog = BottomSheetDialog(requireActivity())
        dialog.setContentView(R.layout.fragment_btn_dialog)
//        val decorView = dialog.window?.decorView
//        decorView?.background = ColorDrawable(Color.TRANSPARENT)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        return dialog
    }
}