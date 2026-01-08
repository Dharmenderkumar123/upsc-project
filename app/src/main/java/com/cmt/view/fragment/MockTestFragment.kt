package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.MockTestItemsAdapter
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.viewModel.fragment.MockTestVM
import com.the_pride_ias.databinding.FragmentMockTestBinding


class MockTestFragment(var subjModel: AgricatCategoryModel) : Fragment() {
    lateinit var binding : FragmentMockTestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding =FragmentMockTestBinding.inflate(layoutInflater,container,false).apply {
           viewModel = ViewModelProvider(this@MockTestFragment).get(MockTestVM::class.java)
           viewModel?.binding = this
           lifecycleOwner = this@MockTestFragment

       }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.packageData?.observe(requireActivity()) {
            if (!it?.mock_tests.isNullOrEmpty()) {
                it?.let {
                    binding.recycleView.apply {
                        binding.recycleView.apply {
                            adapter = it.mock_tests?.let { it1 ->
                                MockTestItemsAdapter(
                                    binding.root.context,
                                    it1
                                )
                            }
                        }
                    }
                }
                binding.tvNoTest.setVisibility(View.GONE)
            } else {
                binding.recycleView.setVisibility(View.GONE)
                binding.tvNoTest.setVisibility(View.VISIBLE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.setData(requireActivity(),subjModel)
    }

}