package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cmt.adapter.CourseSpecializationAdapter
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.viewModel.fragment.CourseSpecializationDetailsVM
import com.google.android.material.tabs.TabLayoutMediator
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentSpecializationDetailsBinding

class SpecializationDetailsFragment(
    var subjModel: AgricatCategoryModel, var image: String?=null
) : Fragment() {
    private lateinit var binding: FragmentSpecializationDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentSpecializationDetailsBinding.inflate(layoutInflater, container, false).apply {
                viewModel = ViewModelProvider(this@SpecializationDetailsFragment).get(
                    CourseSpecializationDetailsVM::class.java
                )
                lifecycleOwner = this@SpecializationDetailsFragment

            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setTabData(view.context)

        Glide.with(requireActivity())
            .load(image)
            .placeholder(R.drawable.group_17129)
            .into(binding.courseThumbnail)

        binding.viewModel?.pagerData?.observe(requireActivity()) {
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

        }
    }

}