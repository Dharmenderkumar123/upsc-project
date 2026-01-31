package com.cmt.view.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.services.model.ExamCompleteModel
import com.cmt.viewModel.fragment.ProfileVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentSeeMyResultBinding

class SeeMyResultBottomSheet(val model: ExamCompleteModel) : BottomSheetDialogFragment() {
    lateinit var binding: FragmentSeeMyResultBinding
    lateinit var viewmodel : ProfileVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSeeMyResultBinding.inflate(layoutInflater)
        viewmodel= ViewModelProvider(this@SeeMyResultBottomSheet)[ProfileVM::class.java]
        setData()
        return binding.root
    }

    private fun setData(){
        with(model){
            with(binding){
                tvScore.text=score.toString()
                tvCorrectVal.text= "$correct_answers - ($correct_answers_percent)%"
                tvWrongVal.text= "$Wrong_answers -( $Wrong_answers_percent)%"
                tvNotAnsweredVal.text= "$not_answered - ($not_answered_percent)%"
                tvTotalMarks.text="Total Marks Possible: $total_marks"
            }
        }
    }
    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        requireActivity().finish()
    }

}