package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.cmt.helper.IConstants
import com.cmt.viewModel.fragment.CmsViewModel
import com.the_pride_ias.databinding.FragmentCmsBinding

class CmsFragment : Fragment() {
    lateinit var binding: FragmentCmsBinding
    var type: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCmsBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@CmsFragment).get(CmsViewModel::class.java)
            lifecycleOwner = this@CmsFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (type == IConstants.ProfileType.About_us) {
            binding.viewModel?.aboutUsAPI(view)
        } else if (type == IConstants.ProfileType.Terms_conditions) {
            binding.viewModel?.termsAPI(view)
        }
    }
}