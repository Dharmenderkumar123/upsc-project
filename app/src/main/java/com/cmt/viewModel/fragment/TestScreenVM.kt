package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.adapter.TestOptionsAdapter
import com.cmt.adapter.TestQuestionNumberAdapter
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.model.QuestionNumberModel
import com.cmt.services.api.SubCategoriesAPI
import com.cmt.services.api.TestModuleAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.SubjectsListModel
import com.cmt.services.model.TestOptionsModel
import com.cmt.view.activity.PlainActivity
import com.cmt.view.dialog.TestSubmitConfirmationDialog
import com.the_pride_ias.databinding.FragmentTestModuleBinding

class TestScreenVM : ViewModel() {
    lateinit var binding: FragmentTestModuleBinding
    var testData: MutableLiveData<MutableList<TestOptionsModel>> = MutableLiveData()

    fun setQuestions(context: Context, lang: String, testId: String) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        testId.let {
            params[IConstants.Params.test_id] = it
        }
        lang.let {
            params[IConstants.Params.language] = it
        }
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)
        TestModuleAPI().questionsList(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as? MutableList<*>)?.filterIsInstance<TestOptionsModel>()
                                ?.toMutableList()
                        testData.value = dataResponse!!
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

}