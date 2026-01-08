package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.ForgotPasswordVM
import com.the_pride_ias.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {
    lateinit var binding: FragmentForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@ForgotPasswordFragment).get(ForgotPasswordVM::class.java)
            lifecycleOwner = this@ForgotPasswordFragment
        }
        return binding.root
    }


}