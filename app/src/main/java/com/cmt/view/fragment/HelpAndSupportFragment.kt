package com.cmt.view.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.HelpAndSupportVM
import com.the_pride_ias.databinding.FragmentHelpAndSupportBinding

class HelpAndSupportFragment : Fragment() {
    lateinit var binding: FragmentHelpAndSupportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpAndSupportBinding.inflate(layoutInflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@HelpAndSupportFragment).get(HelpAndSupportVM::class.java)
            lifecycleOwner = this@HelpAndSupportFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.support(view)

    }

}