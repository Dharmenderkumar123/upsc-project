package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.CurrentAffairsListAdapter
import com.cmt.viewModel.fragment.CurrentAffairsVM
import com.the_pride_ias.databinding.FragmentCurrentAffairsListBinding


class CurrentAffairsListFragment : Fragment() {
    lateinit var binding: FragmentCurrentAffairsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentAffairsListBinding.inflate(inflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@CurrentAffairsListFragment).get(CurrentAffairsVM::class.java)
            lifecycleOwner = this@CurrentAffairsListFragment
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setCurrentAffairsData(requireActivity())

        binding.viewModel?.currentAffairsData?.observe(requireActivity()) {
            if (it.size != 0) {
                it?.let {
                    binding.rvCurrentAffairs.apply {
                        adapter = CurrentAffairsListAdapter(requireActivity(), it)
                    }
                }
            }
        }
    }

}