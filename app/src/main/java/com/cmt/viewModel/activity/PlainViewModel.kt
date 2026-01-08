package com.cmt.viewModel.activity

import android.app.Activity
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlainViewModel: ViewModel() {

    fun backEvent(view: View) {
        (view.context as Activity).onBackPressed()
    }
}