package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.CourseSpecializationAdapter
import com.cmt.helper.IConstants
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.viewModel.fragment.SubjectDetailsVM
import com.google.android.material.tabs.TabLayoutMediator
import com.the_pride_ias.databinding.FragmentSubjectDetailsBinding

class SubjectDetailsFragment : Fragment() {
    lateinit var binding: FragmentSubjectDetailsBinding
    lateinit var subjModel: AgricatCategoryModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectDetailsBinding.inflate(layoutInflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@SubjectDetailsFragment).get(SubjectDetailsVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@SubjectDetailsFragment

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setTabData(view.context)
        binding.courseName.text = IConstants.ValueHolder.courseName

        binding.viewModel?.pagerData?.observe(requireActivity(), {
            binding.viewpager1.apply {
                adapter = CourseSpecializationAdapter(requireActivity(), it, subjModel)
            }
            TabLayoutMediator(binding.tablayout, binding.viewpager1)
            { tab, position ->
                tab.text = it[position]
                for (i in 0 until binding.tablayout.tabCount) {
                    val tablayout =
                        (binding.tablayout.getChildAt(0) as ViewGroup).getChildAt(i)
                    val p = tablayout.layoutParams as ViewGroup.MarginLayoutParams
                    p.setMargins(20, 0, 20, 0)
                    tablayout.requestLayout()
                }
            }.attach()

        })

    }


}