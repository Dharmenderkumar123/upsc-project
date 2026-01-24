package com.cmt.view.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.view.activity.FullPlainActivity
import com.cmt.viewModel.TestSubmitConfirmationVM
import com.the_pride_ias.R
import com.the_pride_ias.databinding.TestSubmitConfirmationDialogBinding

class TestSubmitConfirmationDialog(val type : String ?=null) : DialogFragment() {
   private lateinit var binding : TestSubmitConfirmationDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TestSubmitConfirmationDialogBinding.inflate(layoutInflater,container,false).apply {
            viewModel = ViewModelProvider(this@TestSubmitConfirmationDialog).get(TestSubmitConfirmationVM::class.java)
            lifecycleOwner = this@TestSubmitConfirmationDialog
        }

        Log.d("sdfjvsadf", "onCreateView: ")
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if(type !=null){
            if(type ==IConstants.Defaults.logout){
                binding.viewModel?.isLogOutScreen?.value = true
            }
        }
       else{
            binding.viewModel?.isLogOutScreen?.value = false
        }
        binding.noBtn.setOnClickListener {
            dialog?.dismiss()
        }
        binding.yesBtn.setOnClickListener {
            if(type!= null){
                if(type == IConstants.Defaults.logout){
                    AppPreferences().logout(requireActivity())
                    val intent = Intent(requireActivity(),FullPlainActivity::class.java)
                    intent.putExtra(IConstants.IntentStrings.type,IConstants.FragmentType.Login)
                    requireActivity().startActivity(intent)
                    requireActivity().finishAffinity()
                    dialog?.dismiss()

                }
            }else{
                val intent = Intent(requireActivity(),FullPlainActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.type,IConstants.FragmentType.ScoreBoard)
                requireActivity().startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.enter,R.anim.exit)
                dialog?.dismiss()
            }
        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog_Alert)
    }

}