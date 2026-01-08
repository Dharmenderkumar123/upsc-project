package com.cmt.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestSubmitConfirmationVM : ViewModel() {
    val isLogOutScreen : MutableLiveData<Boolean> = MutableLiveData()

    init {
        isLogOutScreen.value = false
    }
 }