package com.cmt.TestModule

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.the_pride_ias.databinding.FragmentTestCancelPopUpBinding

class TestCancelPopUpFragment : DialogFragment() {
    private lateinit var binder: FragmentTestCancelPopUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binder = FragmentTestCancelPopUpBinding.inflate(layoutInflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binder.noBtn.setOnClickListener {
            mCallback?.cancelTest(false)
        }

        binder.yesBtn.setOnClickListener {
            mCallback?.cancelTest(true)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onDestroy() {
        try {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }


    interface OnTestCancelListener {
        fun cancelTest(cancel: Boolean)
    }

    var mCallback: OnTestCancelListener? = null

}