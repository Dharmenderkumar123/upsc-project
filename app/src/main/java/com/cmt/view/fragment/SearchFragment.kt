package com.cmt.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.AgricetCategoryAdapter
import com.cmt.viewModel.fragment.SearchVM
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@SearchFragment).get(SearchVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@SearchFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etSearch.text.toString().length >= 2) {
                    binding.viewModel?.setData(requireActivity(), binding.etSearch.text.toString())
                    binding.recycleView.isVisible = true
                } else if (count == 0) {
                    binding.viewModel?.noDataMsg?.value = ""
                    binding.recycleView.isVisible = false
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        if (binding.viewModel?.noData?.value == true) {
            binding.recycleView.isVisible = false
        }

        binding.viewModel?.searchData?.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                binding.recycleView.apply {
                    adapter = it.let { it1 ->
                        AgricetCategoryAdapter(
                            binding.root.context,
                            it,
                            isPurchased = false,
                            subCategoryId = "1"
                        )
                    }
                }
            } else {
                binding.viewModel?.noData?.value = true
                binding.viewModel?.noDataMsg?.value = getString(R.string.no_subjects_available)
            }
        }

    }
}