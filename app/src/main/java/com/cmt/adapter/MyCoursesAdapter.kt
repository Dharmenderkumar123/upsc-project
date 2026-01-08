package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.*
import com.cmt.services.api.PaymentAPI
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.MyCourseModel
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsMyCoursesBinding
import org.json.JSONObject

class MyCoursesAdapter(
    val context: Context,
    val dataset: MutableList<MyCourseModel>,
    val callBack: ICallback? = null
) :
    RecyclerView.Adapter<MyCoursesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemsMyCoursesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: MyCourseModel) {
            binding.model = datamodel

            //binding.btnRenewal.isVisible = datamodel.status == IConstants.ValidateStrings.expired

            binding.layout.setOnClickListener {
                if (datamodel.status == IConstants.ValidateStrings.expired) {
                    Toast.makeText(
                        context,
                        "Course Already expired, please renewal",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    binding.btnRenewal.isVisible = false
                    IConstants.ValueHolder.courseName = datamodel.title
                    val intent = Intent(context, PlainActivity::class.java)
                    intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.Subjects)
                    intent.putExtra(IConstants.IntentStrings.payload, datamodel)
                    context.startActivity(intent)
                    (context as FragmentActivity).overridePendingTransition(
                        R.anim.enter,
                        R.anim.exit
                    )
                }

            }

            binding.btnRenewal.setOnClickListener {
                callBack?.delegate(datamodel)
                /* val params = getGlobalParams(context)
                 params[IConstants.Params.grand_total] = datamodel.total.toString()
                 params[IConstants.Params.user_id] = AppPreferences().getUserId(context);
                 params[IConstants.Params.package_id] = datamodel.course_id.toString()
                 PaymentAPI().generateOrderId(params, object : RetrofitCallBack {
                     override fun responseListener(response: Any?, error: String?) {
                         if (error != null) {
                             Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                         } else {
                             val apiResponse = response as? JSONObject
                             if (apiResponse != null) {
                                 try {
                                     if (apiResponse.getString("error_code") == IConstants.Response.valid) {
                                         openPaymentActivity.value =
                                             apiResponse.getJSONObject("data")
                                         val jsonObject = apiResponse.getJSONObject("data")
                                         order_id = jsonObject.getString("order_id")
                                     } else {
                                         Toast.makeText(
                                             context,
                                             apiResponse.getString("message"),
                                             Toast.LENGTH_SHORT
                                         ).show()
                                     }
                                 } catch (e: Exception) {
                                     Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT)
                                         .show()
                                 }
                             } else {
                                 Toast.makeText(context, "Connect to developer", Toast.LENGTH_SHORT)
                                     .show()

                             }
                         }
                     }

                 })*/
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsMyCoursesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}