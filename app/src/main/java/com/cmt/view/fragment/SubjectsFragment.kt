package com.cmt.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.SubjectItemsAdapter
import com.cmt.services.model.MyCourseModel
import com.cmt.viewModel.fragment.SubjectsVM
import com.the_pride_ias.databinding.FragmentSubjectsBinding

class SubjectsFragment(var model: MyCourseModel) : Fragment() {
    lateinit var binding: FragmentSubjectsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectsBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@SubjectsFragment).get(SubjectsVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@SubjectsFragment

        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.subjectsData?.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                binding.recycleView.apply {
                    adapter = SubjectItemsAdapter(binding.root.context, it, model)
                }
            } else {
                binding.recycleView.isVisible = false
                binding.tvNoData.isVisible = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.setData(model, requireActivity())

    }


}