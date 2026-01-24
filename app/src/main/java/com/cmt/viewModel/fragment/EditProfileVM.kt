package com.cmt.viewModel.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide

import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.api.UserProfile
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentEditProfileBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class EditProfileVM : ViewModel() {
    lateinit var binding: FragmentEditProfileBinding
    var selectedDate: MutableLiveData<String> = MutableLiveData()
    val data: MutableLiveData<UserDetailsModel> = MutableLiveData()
    var uploadImageData: MutableLiveData<MutableList<String>> = MutableLiveData()

    var mainDFT: SimpleDateFormat? = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())


    private var imageFile: File ?= null

    fun setFile(context: Context, filePath: File) {
        val imageData = uploadImageData.value
        imageData?.add(filePath.toString())
        Glide.with(context)
            .load(filePath)
            .into(binding.profileImg)
        imageFile = filePath
        uploadImageData.value = imageData!!
    }

    fun profileUpdate(view: View) {
        updateProfile(view.context, imageFile)
    }

    fun clickCalendarEvent(view: View) {
        val calendar: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            view.context,
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val mobDate: String? = mainDFT?.format(calendar.time)
                selectedDate.value = mobDate!!
                binding.dateOfBirth.text = selectedDate.value
            },

            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = Calendar.getInstance().time.time
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(view.context, R.color.colorPrimary))

    }

    fun getProfile(view: View) {
        val activity = view.context as PlainActivity
        val params = HashMap<String, String>()
        activity.activityLoader(true)
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)
        UserProfile().profile(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                if (error != null) {
                    activity.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as? UserDetailsModel
                        data.value = dataResponse!!
                        Glide.with(activity)
                            .load(dataResponse?.profile_image)
                            .placeholder(R.drawable.pride_ias_logo)
                            .into(binding.profileImg)
                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }
                }
            }

        })
    }

  private  fun updateProfile(context: Context, filePath: File?) {
        val activity = context as? PlainActivity
        val params = HashMap<String, RequestBody>()
        activity?.activityLoader(true)
        params[IConstants.Params.user_id] = activity?.let {
            AppPreferences().getUserId(it)
                .toRequestBody("text/plain; charset=utf-8".toMediaType())
        }!!
        params[IConstants.Params.name] = binding.username.text.toString()
            .toRequestBody("text/plain; charset=utf-8".toMediaType())!!
        params[IConstants.Params.dob] = binding.dateOfBirth.text.toString()
            .toRequestBody("text/plain; charset=utf-8".toMediaType())!!
        UserProfile().updateProfile(params, filePath, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                if (error != null) {
                    activity.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        activity.setSnackBar(apiResponse.message)
                    } else {
                        activity.setSnackBar(apiResponse?.message)
                    }

                }
            }

        })
    }
}