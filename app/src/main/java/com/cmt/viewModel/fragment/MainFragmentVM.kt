package com.cmt.viewModel.fragment

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cmt.adapter.AdvBannerAdapter
import com.cmt.adapter.CoursesAdaptor
import com.cmt.adapter.EbooksAdapter
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.services.model.EbookDataModel
import com.cmt.services.model.HomeAdvBannerModel
import com.cmt.services.api.HomeContentAPI
import com.cmt.services.api.UserProfile
import com.cmt.services.helper.RetrofitCallBack
import com.cmt.services.model.APIResponse
import com.cmt.services.model.Courses
import com.cmt.services.model.UserDetailsModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentMainBinding
import java.lang.Math.abs

class MainFragmentVM : ViewModel() {
    lateinit var binding: FragmentMainBinding
    val data: MutableLiveData<UserDetailsModel> = MutableLiveData()
    val nameVis: MutableLiveData<Boolean> = MutableLiveData()
    var swipeRefersh: MutableLiveData<Boolean> = MutableLiveData()


    init {
        swipeRefersh.value = false
    }

    private var bannerHandler: Handler? = null
    private var bannerRunnable: Runnable? = null

    private fun ViewPager2.autoScroll(interval: Long) {

        bannerRunnable?.let { runnable ->
            bannerHandler?.removeCallbacks(runnable)
            bannerHandler = null
        }

        bannerHandler = Handler(Looper.getMainLooper())
        var scrollPosition: Int
        bannerRunnable = object : Runnable {
            override fun run() {
                val count = binding.bannerViewPager.adapter?.itemCount ?: 0
                scrollPosition = binding.bannerViewPager.currentItem
                if (scrollPosition < count - 1) {
                    scrollPosition += 1
                    setCurrentItem(scrollPosition, true)
                } else {
                    scrollPosition = 0
                    setCurrentItem(scrollPosition, true)
                }
                postDelayed(this, interval)
            }
        }
        bannerRunnable?.let {
            bannerHandler?.post(it)
        }

    }


    fun setAdvData(view: View) {
        val activity = view.context as? MainActivity
        activity?.activityLoader(true)
        HomeContentAPI().banners(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                nameVis.value = true
                activity?.activityLoader(false)
                swipeRefersh.value = false
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    val dataResponse =
                        (apiResponse?.data as MutableList<*>).filterIsInstance<HomeAdvBannerModel>()
                            .toMutableList()

                    binding.bannerViewPager.apply {
                        adapter = AdvBannerAdapter(context = context, dataResponse)

                        if (bannerHandler == null) {
                            autoScroll(10000)
                        }
                        isUserInputEnabled = true

                        clipToPadding = false
                        clipChildren = false
                        offscreenPageLimit = 3
                        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS

                        val comPosPageTarn = CompositePageTransformer()
                        comPosPageTarn.addTransformer(MarginPageTransformer(20))
                        comPosPageTarn.addTransformer { page, position ->
                            val r: Float = 1 - abs(position)
                            page.scaleY = 0.85f + r * 0.15f
                        }
                        setPageTransformer(comPosPageTarn)
                    }
                }
            }

        })
    }

    fun getProfile(view: View) {
        val activity = view.context as MainActivity
        val params = HashMap<String, String>()
        activity.activityLoader(true)
        params[IConstants.Params.user_id] = AppPreferences().getUserId(activity)
        UserProfile().profile(params, object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity.activityLoader(false)
                swipeRefersh.value = false
                if (error != null) {
                    activity.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = apiResponse.data as? UserDetailsModel
                        data.value = dataResponse!!
                    } else {
                        apiResponse?.message?.let { activity.setSnackBar(it) }
                    }
                }
            }

        })
    }

    fun setCourseData(view: View) {
        swipeRefersh.value = true
        val activity = view.context as? MainActivity
        activity?.activityLoader(true)
        HomeContentAPI().courseCategories(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                swipeRefersh.value = false
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    swipeRefersh.value = false
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = (apiResponse.data as MutableList<*>).filterIsInstance<Courses>().toMutableList()
                        binding.courseRecycler.apply {
                            val firstChunk= dataResponse.chunked(4)[0]
                            adapter = CoursesAdaptor(binding.root.context,
                                firstChunk as MutableList<Courses>
                            )
                        }

                    } else {
                        swipeRefersh.value = false
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }

                }
            }

        })
    }

    fun setEBooksData(view: View) {
        val activity = view.context as? MainActivity
        activity?.activityLoader(true)
        HomeContentAPI().eBookCourses(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                activity?.activityLoader(false)
                swipeRefersh.value = false
                if (error != null) {
                    activity?.setSnackBar(error)
                } else {
                    val apiResponse = response as? APIResponse<*>
                    if (apiResponse?.error_code == IConstants.Response.valid) {
                        val dataResponse = (apiResponse.data as MutableList<*>).filterIsInstance<EbookDataModel>().toMutableList()
                        binding.ebookRecycler.apply {
                            val firstChunk=dataResponse.chunked(4)[0]
                            adapter = EbooksAdapter(binding.root.context,
                                firstChunk as MutableList<EbookDataModel>
                            )
                        }

                    } else {
                        apiResponse?.message?.let { activity?.setSnackBar(it) }
                    }

                }
            }

        })
    }

    fun categoriesViewAll(view: View) {
        val activity = view.context as MainActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.Categories)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun ebookViewAll(view: View) {
        val activity = view.context as MainActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.EbookSubjectsType)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun search(view: View) {
        val activity = view.context as MainActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.Search)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun notificationClick(view: View) {
        val activity = view.context as MainActivity
        val intent = Intent(activity, PlainActivity::class.java)
        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.Notifications)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

}