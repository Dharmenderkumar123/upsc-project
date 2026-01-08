package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.FaqViewModel
import com.the_pride_ias.databinding.FragmentFaqBinding

class FaqFragment : Fragment() {
   lateinit var binding : FragmentFaqBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFaqBinding.inflate(layoutInflater,container,false).apply {
            viewModel = ViewModelProvider(this@FaqFragment).get(FaqViewModel::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@FaqFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setData(view.context)
    }

}