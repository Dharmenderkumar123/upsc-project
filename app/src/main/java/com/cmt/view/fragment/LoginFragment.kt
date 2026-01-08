package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.LoginVM
import com.the_pride_ias.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
   lateinit var binding : FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentLoginBinding.inflate(layoutInflater,container,false).apply {
            viewModel = ViewModelProvider(this@LoginFragment).get(LoginVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@LoginFragment

        }
        return binding.root
    }
}