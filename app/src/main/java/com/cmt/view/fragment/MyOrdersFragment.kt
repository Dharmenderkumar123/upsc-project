package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.MyOrdersAdapter
import com.cmt.viewModel.fragment.MyOrdersVM
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentMyOrdersBinding

class MyOrdersFragment : Fragment() {
    lateinit var binding: FragmentMyOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyOrdersBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MyOrdersFragment).get(MyOrdersVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@MyOrdersFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setData(requireActivity())

        binding.viewModel?.ordersData?.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                binding.recycleView.apply {
                    adapter = MyOrdersAdapter(binding.root.context, it)

                }
            } else {
                binding.viewModel?.noData?.value = true
                binding.viewModel?.noDataMsg?.value = getString(R.string.no_orders_found)
            }
        }
    }


}