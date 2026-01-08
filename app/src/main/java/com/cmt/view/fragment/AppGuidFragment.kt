package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.AppGuideVM
import com.the_pride_ias.databinding.FragmentAppGuidBinding

class AppGuidFragment : Fragment() {
   lateinit var binding : FragmentAppGuidBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentAppGuidBinding.inflate(layoutInflater,container,false).apply {
           viewModel = ViewModelProvider(this@AppGuidFragment).get(AppGuideVM::class.java)
           lifecycleOwner = this@AppGuidFragment

       }
        return binding.root
    }


}