package com.cmt.viewModel.fragment

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CourseSpecializationDetailsVM : ViewModel() {
    var pagerData: MutableLiveData<MutableList<String>> = MutableLiveData()
    fun backEvent(view: View) {
        (view.context as Activity).onBackPressed()
    }

    fun setTabData(context: Context) {
        val activity = context as FragmentActivity
        val data = mutableListOf<String>()
        data.add("Description")
        data.add("Class Videos")
        data.add("Mocktest")

        pagerData.value = data

    }

}