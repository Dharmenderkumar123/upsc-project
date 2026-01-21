package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.AppGuideVM
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentAppGuidBinding
import com.the_pride_ias.databinding.FragmentBuyPlanBinding

class BuyPlanFragment : Fragment() {
    lateinit var binding : FragmentBuyPlanBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        return inflater.inflate(R.layout.fragment_buy_plan, container, false)
        binding = FragmentBuyPlanBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}