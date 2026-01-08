package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.services.model.SubmitedAnswerModel
import com.cmt.viewModel.fragment.ScoreBoardVM
import com.the_pride_ias.databinding.FragmentScoreBoardBinding


class ScoreBoardFragment(var model: SubmitedAnswerModel? = null, var exam_id: String? = null) :
    Fragment() {
    lateinit var binding: FragmentScoreBoardBinding
    var examId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoreBoardBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@ScoreBoardFragment).get(ScoreBoardVM::class.java)
            lifecycleOwner = this@ScoreBoardFragment
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        examId = exam_id
        model?.let {
            binding.viewModel?.setScoreData(requireActivity(), it)
        }

        if (!examId.isNullOrEmpty()) {
            examId?.let {
                binding.viewModel?.scoreCard(requireActivity(), it)
            }
        }


    }


}