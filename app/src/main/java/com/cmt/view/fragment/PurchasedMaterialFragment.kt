package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.MyMaterialAdapater
import com.cmt.viewModel.fragment.PurchasedMaterialsVM
import com.the_pride_ias.databinding.FragmentPurchasedMaterialBinding


class PurchasedMaterialFragment(val subjectId: String?) : Fragment() {
    lateinit var binding: FragmentPurchasedMaterialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPurchasedMaterialBinding.inflate(layoutInflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@PurchasedMaterialFragment).get(PurchasedMaterialsVM::class.java)
            lifecycleOwner = this@PurchasedMaterialFragment
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.setData(requireActivity(), subjectId)

        binding.viewModel?.materialData?.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                binding.recycleView.apply {
                    adapter = MyMaterialAdapater(binding.root.context, it)
                }
            }
        }
    }
}