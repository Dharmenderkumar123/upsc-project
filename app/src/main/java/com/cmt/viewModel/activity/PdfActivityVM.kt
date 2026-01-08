package com.cmt.viewModel.activity

import android.app.Activity
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PdfActivityVM : ViewModel() {
    var activityTitle: MutableLiveData<String> = MutableLiveData()

    fun backEvent(view: View) {
        (view.context as Activity).onBackPressed()
    }
}