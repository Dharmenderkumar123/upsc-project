package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.ProfileVM
import com.the_pride_ias.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@ProfileFragment).get(ProfileVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@ProfileFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setMenuItems(requireActivity(),requireActivity().supportFragmentManager)
        binding.viewModel?.getProfile(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.getProfile(requireActivity())
    }

}