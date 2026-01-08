package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.PaymentAPI
import com.cmt.services.api.SubCategoriesAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.*
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentAgricetCategoryBinding
import org.json.JSONObject

class AgricetVM : ViewModel() {
    lateinit var binding: FragmentAgricetCategoryBinding
    var subjectsData: MutableLiveData<SubjectsListModel> = MutableLiveData()
    var openPaymentActivity: MutableLiveData<JSONObject> = MutableLiveData()
    var paymentSuccessHashMap: HashMap<String, String> = HashMap()
    var order_id: String? = null
    var package_id: String? = null
    var amount: String? = null

    fun setData(context: Context, model: SubCourseModel) {
        val activity = context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(context)
        model.sub_category_id.toString().let {
            params[IConstants.Params.sub_category_id] = it
        }
        params[IConstants.Params.user_id] = AppPreferences().getUserId(context)

        SubCategoriesAPI().subjectsList(params, object : RetrofitCallBack {
            @SuppressLint("SetTextI18n")
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as SubjectsListModel
                        subjectsData.value = dataResponse
                        if (dataResponse.paid_status.equals("yes")) {
                            binding.layoutPrices.isVisible = false
                        } else {
                            binding.layoutPrices.isVisible = true
                            binding.tvActual.setText("₹ " + dataResponse.actual_price)
                            binding.amount.setText("₹ " + dataResponse.price)
                        }
                        package_id = dataResponse.package_id
                        amount = dataResponse.price
                        binding.tvNoSubj.isVisible = false
                    } else {
                        binding.layoutPrices.isVisible = false
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                        binding.tvNoSubj.isVisible = true
                        binding.tvNoSubj.setText(apiResponse?.message)
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
        params[IConstants.Params.package_id] = package_id.toString()
        params[IConstants.Params.amount] = amount.toString()
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

    fun createOrderId(view: View) {
        val activity = view.context as? PlainActivity
        activity?.activityLoader(true)
        val params = getGlobalParams(view.context)
        subjectsData.value?.price.toString().let {
            params[IConstants.Params.grand_total] = it
        }
        AppPreferences().getUserId(view.context).let {
            params[IConstants.Params.user_id] = it
        }
        params[IConstants.Params.package_id] = package_id.toString()

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
}