package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.services.model.Courses
import com.cmt.viewModel.fragment.SubcourseVM
import com.the_pride_ias.databinding.FragmentSubCourseBinding

class SubCourseFragment(
    var model: Courses,
    var type: String,
    var catType: String,
    var subCategoryId: String?,
    var type1: Int
) : Fragment() {
    lateinit var binding: FragmentSubCourseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSubCourseBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@SubCourseFragment)[SubcourseVM::class.java]
            viewModel?.binding = this
            lifecycleOwner = this@SubCourseFragment
        }
        binding.close.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.title.setText(model.category_name)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setdata(view, model, type,subCategoryId,type1)

        val noString = "$catType not available"
        binding.tvNoCourse.text = noString
    }
}