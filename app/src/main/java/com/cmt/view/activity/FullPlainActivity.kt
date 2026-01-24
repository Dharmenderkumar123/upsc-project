package com.cmt.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment

import com.cmt.helper.IConstants
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.services.model.Courses
import com.cmt.services.model.SubjectsModel
import com.cmt.services.model.SubmitedAnswerModel
import com.cmt.view.fragment.*
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ActivityFullPlainBinding
import java.util.*

class FullPlainActivity : AppCompatActivity() {
    lateinit var binding: ActivityFullPlainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullPlainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(IConstants.IntentStrings.type)) {
            when (intent.getStringExtra(IConstants.IntentStrings.type)) {
                IConstants.FragmentType.AppGuidFragment -> {
                    loadFragment(AppGuidFragment())
                }
                IConstants.FragmentType.Login -> {
                    loadFragment(LoginFragment())
                }
                IConstants.FragmentType.Register -> {
                    loadFragment(RegisterFragment())
                }
                IConstants.FragmentType.Otp -> {
                    val userId: String =
                        intent.getStringExtra(IConstants.IntentStrings.payload).toString()
                    loadFragment(OtpFragment(userId))
                }
                IConstants.FragmentType.SubCourse -> {
                    val model =
                        intent.getSerializableExtra(IConstants.IntentStrings.payload) as Courses
                    val type: String = intent.getStringExtra(IConstants.IntentStrings.courseType)!!
                    val catType: String = intent.getStringExtra(IConstants.IntentStrings.catType)!!
                    loadFragment(SubCourseFragment(model, type,catType))
                }
                IConstants.FragmentType.CourseDescription -> {
                    val subjModel =
                        intent.getSerializableExtra(IConstants.IntentStrings.model) as AgricatCategoryModel
                    val subImage = intent.getStringExtra(IConstants.IntentStrings.image)
                    loadFragment(SpecializationDetailsFragment(subjModel,subImage))
                }
                IConstants.FragmentType.ForgotPassword -> {
                    loadFragment(ForgotPasswordFragment())
                }
                IConstants.FragmentType.ScoreBoard -> {
                    val model =
                        intent.getSerializableExtra(IConstants.IntentStrings.payload) as? SubmitedAnswerModel
                    val examId: String =
                        intent.getStringExtra(IConstants.IntentStrings.examId).toString()
                    val from: String =
                        intent.getStringExtra(IConstants.IntentStrings.result).toString()
                    if (from.equals("result")) {
                        loadFragment(ScoreBoardFragment(model, examId))
                    } else {
                        loadFragment(ScoreBoardFragment(model, model?.exam_id))
                    }
                }


            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fullPlainContainer, fragment)
            .commit()
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fullPlainContainer)
        if (fragment is AppGuidFragment) {
            finishAffinity()
        } else {

            super.onBackPressed()
            overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
        }
    }

//    fun activityLoader(isShow: Boolean) {
//        if (isShow) {
//            window?.setFlags(
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//            )
//            binding.fullPlainContainer.animate()?.alpha(0.2f)
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//            binding.fullPlainContainer.animate()?.alpha(1f)
//            binding.progressBar.visibility = View.GONE
//        }
//    }


    fun activityLoader(isShow: Boolean) {
        if (isShow) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//            binding.plainContainer.animate()?.alpha(0.2f)
//            binding.progressBar.visibility = View.VISIBLE
            val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse)
            binding.logoLoader.visibility = View.VISIBLE
            binding.logoLoader.startAnimation(pulseAnimation)
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
//            binding.plainContainer.animate()?.alpha(1f)

            binding.logoLoader.clearAnimation()
            binding.logoLoader.visibility = View.GONE
//            binding.progressBar.visibility = View.GONE
        }
    }
}
