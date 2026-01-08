package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.MyMaterialAdapater
import com.cmt.viewModel.fragment.MyMaterialsVM
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentMyMaterialsBinding

class MyMaterialsFragment : Fragment() {
    lateinit var binding: FragmentMyMaterialsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyMaterialsBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MyMaterialsFragment).get(MyMaterialsVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@MyMaterialsFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.setData(requireActivity())

        binding.viewModel?.materialData?.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                binding.recycleView.apply {
                    adapter = MyMaterialAdapater(binding.root.context, it)
                }
            } else {
                binding.viewModel?.noData?.value = true
                binding.viewModel?.noDataMsg?.value = getString(R.string.no_materilas_found)
            }
        }
    }

}
