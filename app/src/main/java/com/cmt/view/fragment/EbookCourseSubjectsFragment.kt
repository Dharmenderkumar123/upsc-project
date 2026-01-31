package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.services.model.SubCourseModel
import com.cmt.viewModel.fragment.EbookCourseSubjectVM
import com.the_pride_ias.databinding.FragmentEbookCourseSubjectsBinding

class EbookCourseSubjectsFragment(var model: SubCourseModel) : Fragment() {
    lateinit var binding: FragmentEbookCourseSubjectsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentEbookCourseSubjectsBinding.inflate(layoutInflater, container, false).apply {
                viewModel = ViewModelProvider(this@EbookCourseSubjectsFragment).get(EbookCourseSubjectVM::class.java)
                viewModel?.binding = this
                lifecycleOwner = this@EbookCourseSubjectsFragment
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setData(view,model)
    }


}