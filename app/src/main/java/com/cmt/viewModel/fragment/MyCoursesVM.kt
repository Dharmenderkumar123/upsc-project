package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.adapter.MyCoursesAdapter
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.MyCoursesAPI
import com.cmt.services.api.PaymentAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.MyCourseModel
import com.cmt.services.model.SubjectsListModel
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentMyCoursesBinding
import org.json.JSONObject

class MyCoursesVM : ViewModel() {
    lateinit var binding: FragmentMyCoursesBinding
    var courseData: MutableLiveData<MutableList<MyCourseModel>> = MutableLiveData()
    var openPaymentActivity: MutableLiveData<JSONObject> = MutableLiveData()
    var order_id: String? = null
    var paymentSuccessHashMap: HashMap<String, String> = HashMap()
    var courseId: String? = null
    var total: String? = null
    var noData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        noData.value = false
    }

    fun setCoursesList(context: Context) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)

        MyCoursesAPI().myPurchasedCourses(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse =
                            (apiResponse.data as MutableList<*>).filterIsInstance<MyCourseModel>()
                                .toMutableList()
                        courseData.value = dataResponse
                    } else {
                        noData.value = true
                        // apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })

    }

    fun createOrderId(context: Context, model: MyCourseModel) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        params[IConstants.Params.grand_total] = model.total.toString()
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context);
        params[IConstants.Params.package_id] = model.course_id.toString()
        courseId = model.course_id
        total = model.total
        PaymentAPI().generateOrderId(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? JSONObject
                    if (apiResponse != null) {
                        try {
                            if (apiResponse.getString("error_code") == IConstants.Response.valid) {
                                openPaymentActivity.value = apiResponse.getJSONObject("data")
                                val jsonObject = apiResponse.getJSONObject("data")
                                order_id = jsonObject.getString("order_id")
                            } else {
                                activity?.setSnackBar(apiResponse.getString("message"))
                            }
                        } catch (e: Exception) {
                            activity?.setSnackBar(e.localizedMessage)
                        }
                    } else {
                        activity?.setSnackBar("Connect to developer")
                    }
                }
            }

        })
    }

    fun confirmOnlinePaymentOrder(txn: String, view: View?) {
        paymentSuccessHashMap[IConstants.Params.txn_id] = txn
        Log.d("orderId", "confirmOnlinePaymentOrder: $paymentSuccessHashMap")
        confirmOrder(txn, view)
    }

    private fun confirmOrder(txn: String, view: View?) {
        val activity = view?.context as? PlainActivity
        activity?.activityLoader(true)
        val params: HashMap<String, String> = HashMap()
        params[IConstants.Params.user_id] =
            activity?.let { AppPreferences().getUserId(it) }.toString()
        params[IConstants.Params.tracking_id] = txn
        params[IConstants.Params.order_id] = order_id.toString()
        params[IConstants.Params.package_id] = courseId.toString()
        params[IConstants.Params.amount] = total.toString()
        params[IConstants.Params.status] = "success"

        PaymentAPI().paySuccess(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {

                        val intent = Intent(activity, PlainActivity::class.java)
                        intent.putExtra(
                            IConstants.IntentStrings.type,
                            IConstants.ProfileType.My_Courses
                        )
                        intent.putExtra(
                            IConstants.IntentStrings.payload,
                            activity?.getString(R.string.tit_my_courses)
                        )
                        activity?.startActivity(intent)
                        (activity as FragmentActivity).overridePendingTransition(
                            R.anim.enter,
                            R.anim.exit
                        )
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })
    }
}