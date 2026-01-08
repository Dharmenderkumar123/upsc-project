package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.cmt.viewModel.fragment.SettingsVM
import com.the_pride_ias.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@SettingsFragment).get(SettingsVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@SettingsFragment
        }
        return binding.root
    }


}