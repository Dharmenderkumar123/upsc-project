package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.helper.IConstants.IntentStrings.type
import com.cmt.viewModel.fragment.BuyPlanVM
import com.the_pride_ias.databinding.FragmentBuyPlanBinding

class BuyPlanFragment(val id1: String?,val type: Int) : Fragment() {
    lateinit var binding : FragmentBuyPlanBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBuyPlanBinding.inflate(layoutInflater,container,false).apply {
            viewModel = ViewModelProvider(this@BuyPlanFragment)[BuyPlanVM::class.java]
            viewModel?.binding = this
            lifecycleOwner = this@BuyPlanFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.getData(view,requireActivity(),id1,type)
    }

}