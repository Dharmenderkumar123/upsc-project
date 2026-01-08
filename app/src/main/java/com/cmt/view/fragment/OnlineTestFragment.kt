package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.OnlineTestVM
import com.the_pride_ias.databinding.FragmentOnlineTestBinding

class OnlineTestFragment : Fragment() {
    lateinit var binding : FragmentOnlineTestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentOnlineTestBinding.inflate(layoutInflater,container,false).apply {
            viewModel = ViewModelProvider(this@OnlineTestFragment).get(OnlineTestVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@OnlineTestFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setDataItems(view)
    }

}