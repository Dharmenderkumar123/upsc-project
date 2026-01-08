package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmt.viewModel.fragment.CategoriesVM
import com.the_pride_ias.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {
    lateinit var binding : FragmentCategoriesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       binding = FragmentCategoriesBinding.inflate(inflater,container,false).apply {
           viewModel = ViewModelProvider(this@CategoriesFragment).get(CategoriesVM::class.java)
           viewModel?.binding = this
           lifecycleOwner = this@CategoriesFragment

       }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setCourseData(view)
    }

}