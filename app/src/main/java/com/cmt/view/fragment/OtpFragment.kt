package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.OtpVM
import com.the_pride_ias.databinding.FragmentOtpBinding

class OtpFragment(var userId: String? = null) : Fragment() {
    lateinit var binding: FragmentOtpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@OtpFragment).get(OtpVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@OtpFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.userId?.value = userId

    }

}