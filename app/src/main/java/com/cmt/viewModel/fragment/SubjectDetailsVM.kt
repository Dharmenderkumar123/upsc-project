package com.cmt.viewModel.fragment

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.adapter.CourseSpecializationAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.the_pride_ias.databinding.FragmentSubjectDetailsBinding

class SubjectDetailsVM : ViewModel() {
    lateinit var binding: FragmentSubjectDetailsBinding
    var pagerData: MutableLiveData<MutableList<String>> = MutableLiveData()

    fun setTabData(context: Context) {
        val activity = context as FragmentActivity
        val data = mutableListOf<String>()
        data.add("Description")
        data.add("Sample Videos")
        data.add("Mocktest")

        pagerData.value = data
    }
}