package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.viewModel.fragment.CourseDescriptionVM
import com.the_pride_ias.databinding.FragmentCourseDescriptionBinding

class CourseDescriptionFragment(var subjModel: AgricatCategoryModel) : Fragment() {
    lateinit var binding: FragmentCourseDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseDescriptionBinding.inflate(layoutInflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@CourseDescriptionFragment).get(CourseDescriptionVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@CourseDescriptionFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = subjModel.title
        binding.tvDesc.text = subjModel.description
    }


}