package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.SampleVideoListAdapter

import com.cmt.services.model.AgricatCategoryModel
import com.cmt.viewModel.fragment.SampleVideoVM
import com.the_pride_ias.databinding.FragmentSampleVideosBinding

class SampleVideosFragment(var subjModel: AgricatCategoryModel) : Fragment() {

    lateinit var binding: FragmentSampleVideosBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSampleVideosBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@SampleVideosFragment).get(SampleVideoVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@SampleVideosFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setdata(view, subjModel)

        binding.viewModel?.packageData?.observe(requireActivity()) {
            if (!it?.videos.isNullOrEmpty()) {
                it?.let {
                    binding.recycleView.apply {
                        binding.recycleView.apply {
                            adapter = it.videos?.let { it1 ->
                                SampleVideoListAdapter(
                                    binding.root.context,
                                    it1
                                )
                            }

                        }
                    }
                }
                binding.tvNoVideos.setVisibility(View.GONE)
            } else {
                binding.recycleView.setVisibility(View.GONE)
                binding.tvNoVideos.setVisibility(View.VISIBLE)
            }
        }
    }

}