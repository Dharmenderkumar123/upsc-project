package com.cmt.viewModel.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.cmt.helper.IConstants
import com.cmt.view.activity.FullPlainActivity
import com.the_pride_ias.R

class AppGuideVM : ViewModel() {

    fun startLearningBtn(view : View){
        val activity = view.context as FullPlainActivity
        val intent = Intent(activity,FullPlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type,IConstants.FragmentType.Login)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)

    }
}