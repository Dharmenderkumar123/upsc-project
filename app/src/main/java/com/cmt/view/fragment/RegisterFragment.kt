package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.RegisterVM
import com.the_pride_ias.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@RegisterFragment).get(RegisterVM::class.java)
            viewModel?.binding= this
            lifecycleOwner = this@RegisterFragment

        }
        return binding.root
    }
}