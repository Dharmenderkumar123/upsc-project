package com.cmt.viewModel.fragment

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.cmt.adapter.BuyPackagesAdapter
import com.cmt.adapter.onPackage
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.BuyPlanApi
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.PackagesModel
import com.cmt.services.model.PaymentModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.fragment.PaymentDialogFragment
import com.the_pride_ias.databinding.FragmentBuyPlanBinding

class BuyPlanVM: ViewModel(), onPackage {

    lateinit var binding : FragmentBuyPlanBinding

    fun getData(view: View, requireActivity: FragmentActivity, id1: String?, type: Int){
        val activity = view.context as? FullPlainActivity

        val api=  BuyPlanApi()
        val hm= HashMap<String, String>()

        if(type==0){
            hm["category_id"]=id1.toString()
        }else{
            hm["sub_category_id"]=id1.toString()

        }
        api.getPlans( hm,object : RetrofitCallBack {
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

        api.getPayementInfo( object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = (apiResponse.data as PaymentModel)
                        binding.buyNowBtn.setOnClickListener {
                            val paymentDialog = PaymentDialogFragment(dataResponse)
                            paymentDialog.show(requireActivity.supportFragmentManager, "payment_dialog")
                        }
                    } else {
//                        binding.rvPackages.setVisibility(View.GONE)
//                        binding.tvNoCourse.setVisibility(View.VISIBLE)
                    }
                }
            }
        })


    }

    override fun onPackageClick(model: PackagesModel) {
        binding.layoutPrices.visibility= View.VISIBLE
    }

}