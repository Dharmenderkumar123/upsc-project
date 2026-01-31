package com.cmt.view.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmt.adapter.StudentAdapter
import com.cmt.model.Student
import com.cmt.viewModel.fragment.ProfileVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.the_pride_ias.R
import com.the_pride_ias.databinding.LayoutStudentBottomSheetBinding

class StudentBottomSheet(var testId: String) : BottomSheetDialogFragment() {
    lateinit var binding: LayoutStudentBottomSheetBinding
    lateinit var viewmodel : ProfileVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = LayoutStudentBottomSheetBinding.inflate(layoutInflater)
        viewmodel= ViewModelProvider(this@StudentBottomSheet)[ProfileVM::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTestResult(testId)
    }

    private fun getTestResult(testId: String){
        viewmodel.getStudentResult(testId,requireContext())
        viewmodel.testResult.observe(viewLifecycleOwner, Observer{ it1->
            val studentAdapter = StudentAdapter(it1)
            binding.rvStudentSheet.layoutManager = LinearLayoutManager(context)
            binding.rvStudentSheet.adapter = studentAdapter
        })
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme


}