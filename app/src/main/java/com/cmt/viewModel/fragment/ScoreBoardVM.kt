package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.ScoreCardAPI
import com.cmt.services.api.TestModuleAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.SubmitedAnswerModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R

class ScoreBoardVM : ViewModel() {
    var scoreData: MutableLiveData<SubmitedAnswerModel> = MutableLiveData()

    fun setScoreData(context: Context, model: SubmitedAnswerModel) {
        scoreData.value = model
    }

    fun clickOnReview(view: View) {
        val intent = Intent(view.context, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.ReviewAnswer)
        intent.putExtra(IConstants.IntentStrings.examId, scoreData.value?.exam_id ?: "")
        view.context.startActivity(intent)
        (view.context as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)
        (view.context as FragmentActivity).finish()

    }

    fun scoreCard(context: Context, examId: String) {
        val activity = context as? FullPlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)
        params[IConstants.Params.exam_id] = examId
        ScoreCardAPI().score(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as? SubmitedAnswerModel
                        if (dataResponse != null) {
                            scoreData.value = dataResponse!!
                        }
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })
    }

}