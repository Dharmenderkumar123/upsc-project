package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.ReviewAnswersAdapter
import com.cmt.viewModel.fragment.ReviewVM
import com.the_pride_ias.databinding.FragmentReviewBinding

class ReviewFragment(var examId: String) : Fragment() {
    lateinit var binding: FragmentReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@ReviewFragment).get(ReviewVM::class.java)
            lifecycleOwner = this@ReviewFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.setData(requireActivity(), examId)

        binding.viewModel?.reviewData?.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                binding.recyclerView.apply {
                    adapter = ReviewAnswersAdapter(requireContext(), it)
                }
            }
        }

    }

}