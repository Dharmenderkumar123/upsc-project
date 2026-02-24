package com.cmt.view.fragment

import android.app.Person
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.MainFragmentVM
import com.the_pride_ias.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MainFragment).get(MainFragmentVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@MainFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.getProfile(view)
        binding.viewModel?.setAdvData(view)
        binding.viewModel?.setCourseData(view)
        binding.viewModel?.setEBooksData(view)
    }
}
