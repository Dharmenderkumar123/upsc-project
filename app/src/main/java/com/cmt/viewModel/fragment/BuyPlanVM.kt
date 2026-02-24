package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmt.adapter.BuyPackagesAdapter
import com.cmt.adapter.onPackage
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.getGlobalParams
import com.cmt.helper.setSnackBar
import com.cmt.services.api.BuyPlanApi
import com.cmt.services.api.PaymentAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.PackagesModel
import com.cmt.services.model.SubjectsListModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.PlainActivity
import com.google.android.material.snackbar.Snackbar
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentBuyPlanBinding
import org.json.JSONObject
import kotlin.toString

class BuyPlanVM: ViewModel(), onPackage {

    lateinit var binding : FragmentBuyPlanBinding
    var subjectsData: MutableLiveData<SubjectsListModel> = MutableLiveData()
    var openPaymentActivity: MutableLiveData<JSONObject> = MutableLiveData()
    var order_id: String? = null
    var paymentSuccessHashMap: HashMap<String, String> = HashMap()
    var amount: String = ""
    var package_idd: String=""
    var courseTypee: String=""
    fun getData(
        view: View,
        requireActivity: FragmentActivity,
        id1: String?,
        type: Int,
        courseType: String?
    ){
        courseTypee=courseType.toString()
        val activity = view.context as? FullPlainActivity
        binding.buyNowBtn.setOnClickListener {
            if (amount.isBlank() || amount.trim() == "0") {
                Snackbar.make(binding.root, "You don't have a valid amount.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            paymentApi(view, package_idd)
        }
//        val api=
        val hm= HashMap<String, String>()
        if(type==0){
            hm["category_id"]=id1.toString()
        }else{
            hm["sub_category_id"]=id1.toString()
        }
        hm["package_type"]= courseType.toString()

        BuyPlanApi().getPlans( hm,object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = (apiResponse.data as MutableList<*>).filterIsInstance<PackagesModel>().toMutableList()
                        binding.rvPackages.apply { adapter = BuyPackagesAdapter(binding.root.context, dataResponse,this@BuyPlanVM) }
                    } else {
                        binding.rvPackages.setVisibility(View.GONE)
                        binding.tvNoData.setVisibility(View.VISIBLE)
                    }
                }
            }
        })

//        BuyPlanApi().getPayementInfo( object : RetrofitCallBack {
//            override fun responseListener(response: Any?, error: String?) {
//                activity?.activityLoader(false)
//                if (error != null) {
//                    activity?.setSnackBar(error)
//                } else {
//                    val apiResponse = response as? APIResponse<*>
//                    if (apiResponse?.error_code == IConstants.Response.valid) {
//                        val dataResponse = (apiResponse.data as PaymentModel)
//                        binding.buyNowBtn.setOnClickListener {
//                            val paymentDialog = PaymentDialogFragment(dataResponse)
//                            paymentDialog.show(requireActivity.supportFragmentManager, "payment_dialog")
//                        }
//                    } else {
////                        binding.rvPackages.setVisibility(View.GONE)
////                        binding.tvNoCourse.setVisibility(View.VISIBLE)
//                    }
//                }
//            }
//        })

    }

    fun paymentApi(view: View,package_id: String){
        val activity = view.context as? FullPlainActivity

        activity?.activityLoader(true)
        val params = getGlobalParams(view.context)
        subjectsData.value?.price.toString().let {
            params[IConstants.Params.grand_total] = amount.toString()
        }
        AppPreferences().getUserId(view.context).let {
            params[IConstants.Params.user_id] = it
        }
        params[IConstants.Params.package_id] = package_id

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
        params[IConstants.Params.item_id] = package_idd.toString()
        params[IConstants.Params.Type] = if(courseTypee.trim() =="course") "package" else "notes"
        params[IConstants.Params.amount] = amount.toString()
        params[IConstants.Params.Status] = "success"
1
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
                        intent.putExtra(IConstants.IntentStrings.type, IConstants.ProfileType.My_Courses)
                        intent.putExtra(IConstants.IntentStrings.payload, activity?.getString(R.string.tit_my_courses))
                        activity?.startActivity(intent)
                        (activity as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)
                        (activity as FragmentActivity).finish()
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })
    }





    override fun onPackageClick(model: PackagesModel) {
        binding.layoutPrices.visibility= View.VISIBLE
        package_idd=model.package_id
        amount=model.price
    }

}