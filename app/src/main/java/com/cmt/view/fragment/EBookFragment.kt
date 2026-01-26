package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.EbookMenuListAdapter
import com.cmt.viewModel.fragment.EBookVM
import com.the_pride_ias.databinding.FragmentEBookBinding

class EBookFragment : Fragment() {
    lateinit var binding: FragmentEBookBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEBookBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@EBookFragment).get(EBookVM::class.java)
            lifecycleOwner = this@EBookFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setdata(requireActivity())

        binding.viewModel?.ebookData?.observe(requireActivity()) {
            it?.let {
                binding.recycleView.apply {
                    adapter = EbookMenuListAdapter(binding.root.context, it)
                }

            }
        }
    }

}