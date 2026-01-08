package com.cmt.viewModel.fragment

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.cmt.adapter.ProfileMenuItemsAdapter
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.model.ProfileMenuModel
import com.cmt.services.api.UserProfile
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentProfileBinding

class ProfileVM : ViewModel() {
    lateinit var binding: FragmentProfileBinding
    val data: MutableLiveData<UserDetailsModel> = MutableLiveData()

    fun updateProfile(view: View) {
        val activity = view.context as MainActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.EditProfile)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun getProfile(context: Context) {
        val activity = context as MainActivity
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
                        data.value = dataResponse
                        Glide.with(activity)
                            .load(dataResponse?.profile_image)
                            .placeholder(R.drawable.pride_ias_logo)
                            .into(binding.profileImage)
                    } else {
                        apiResponse?.message?.let { activity.setSnackBar(it) }
                    }
                }
            }

        })
    }

    fun setMenuItems(context: Context, supportFragmentManager: FragmentManager) {
        val items = mutableListOf<ProfileMenuModel>()

        items.add(ProfileMenuModel(
                context.getString(R.string.tit_my_results),
                R.drawable.ic_terms_conditionss,
                IConstants.ProfileType.Results))

        items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_my_courses),
                R.drawable.ic_coursess,
                IConstants.ProfileType.My_Courses))


        items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_my_materials),
                R.drawable.ic_materialss,
                IConstants.ProfileType.My_Material))

        items.add(
            ProfileMenuModel(
                context.getString(R.string.menu_online_tests),
                R.drawable.ic_coursess,
                IConstants.FragmentType.OnlineTest))


        /*items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_current_affairs),
                R.drawable.ic_current_affairs,
                IConstants.ProfileType.Current_Affairs
            )
        )*/
        items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_my_orders),
                R.drawable.ic_my_orderss,
                IConstants.ProfileType.My_Orders
            )
        )

        items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_settings),
                R.drawable.ic_settingss,
                IConstants.ProfileType.Settings
            )
        )
        items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_terms_conditions),
                R.drawable.ic_terms_conditionss,
                IConstants.ProfileType.Terms_conditions
            )
        )
        items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_help_support),
                R.drawable.ic_help_supports,
                IConstants.ProfileType.Help_Support
            )
        )
        items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_faq),
                R.drawable.ic_faqss,
                IConstants.ProfileType.Faq
            )
        )
        items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_about_us),
                R.drawable.ic_about_uss,
                IConstants.ProfileType.About_us
            )
        )
        items.add(
            ProfileMenuModel(
                context.getString(R.string.tit_log_out),
                R.drawable.ic_logouts,
                IConstants.ProfileType.Logout
            )
        )

        binding.recycleView.apply {
            adapter = ProfileMenuItemsAdapter(binding.root.context, items,supportFragmentManager)

        }

    }



}