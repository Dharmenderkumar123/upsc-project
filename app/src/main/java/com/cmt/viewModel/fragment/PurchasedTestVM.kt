package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.TestModuleAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.OptionsModel
import com.cmt.services.model.PurchasedTestOptionsModel
import com.cmt.services.model.SubmitedAnswerModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.PlainActivity
import com.cmt.view.dialog.TestSubmitConfirmationDialog
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentPurchasedTestModuleBinding
import org.json.JSONArray

class PurchasedTestVM : ViewModel() {
    lateinit var binding: FragmentPurchasedTestModuleBinding
    var testData: MutableLiveData<MutableList<OptionsModel>> = MutableLiveData()
    var examId: String? = null
    var noQuestions: MutableLiveData<Boolean> = MutableLiveData()

    init {
        noQuestions.value = false
    }

    fun setQuestions(context: Context, lang: String, testId: String, subjectId: String) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        testId.let {
            params[IConstants.Params.course_id] = it
        }
        lang.let {
            params[IConstants.Params.language] = it
        }
        subjectId.let {
            params[IConstants.Params.subject_id] = it
        }
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)

        TestModuleAPI().purchasedQuestionsList(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as? PurchasedTestOptionsModel
                        examId = dataResponse?.exam_id
                        if (dataResponse?.questions != null) {
                            testData.value = dataResponse.questions
                        } else if (dataResponse?.questions == null) {
                            noQuestions.value = true
                            Toast.makeText(context, "No Questions Available", Toast.LENGTH_SHORT)
                                .show()
                            binding.tvNoQuestion.isVisible = true
                        }
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })
    }

    fun saveAndSubmit(view: View) {
        val activity = view.context as PlainActivity
        val dialog = TestSubmitConfirmationDialog()
        activity.supportFragmentManager.let { dialog.show(it, null) }
    }

    fun submitAnswers(context: Context, jsonAnswersArray: JSONArray) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)
        params[IConstants.Params.exam_id] = examId.toString()
        params[IConstants.Params.answers] = jsonAnswersArray.toString()
        Log.d("AnswerParams", "submitAnswers: $params")

        TestModuleAPI().submitAnswerList(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as? SubmitedAnswerModel
                        val intent = Intent(activity, FullPlainActivity::class.java)
                        intent.putExtra(
                            IConstants.IntentStrings.type,
                            IConstants.FragmentType.ScoreBoard
                        )
                        intent.putExtra(IConstants.IntentStrings.payload, dataResponse)
                        activity?.startActivity(intent)
                        activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
                        activity?.finish()
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })


    }

}