package com.cmt.view.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.cmt.helper.IConstants
import com.cmt.services.model.MyCourseModel
import com.cmt.services.model.SubCourseModel
import com.cmt.view.fragment.*
import com.cmt.viewModel.activity.PlainViewModel
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ActivityPlainBinding
import kotlin.system.exitProcess


class PlainActivity : BaseActivity() {
    lateinit var binding: ActivityPlainBinding
    var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlainBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@PlainActivity).get(PlainViewModel::class.java)
            lifecycleOwner = this@PlainActivity

        }

        setContentView(binding.root)

        if (android.os.Debug.isDebuggerConnected() || android.os.Debug.waitingForDebugger()) {
            exitProcess(0)
        }

        if (intent.hasExtra(IConstants.IntentStrings.type)) {
            when (intent.getStringExtra(IConstants.IntentStrings.type)) {
                IConstants.FragmentType.AgricetCategory -> {
                    binding.tvTitle.visibility = View.VISIBLE
                    val model = intent.getSerializableExtra(IConstants.IntentStrings.payload) as SubCourseModel
                    binding.tvTitle.text = model.sub_category_name
                    loadFragment(AgricetCategoryFragment(model))
                }
                IConstants.ProfileType.My_Courses -> {
                    binding.tvTitle.text = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(MyCoursesFragment())
                }
                IConstants.FragmentType.BuyPlan -> {
                    binding.tvTitle.text = intent.getStringExtra(IConstants.IntentStrings.payload)
                    val id = intent.getStringExtra(IConstants.IntentStrings.id)
                    val cat_type = intent.getStringExtra(IConstants.IntentStrings.cat_type)
                    loadFragment(BuyPlanFragment(id,(cat_type ?: "0").toInt()))
                }
                IConstants.ProfileType.My_Material -> {
                    binding.tvTitle.text = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(MyMaterialsFragment())
                }
                IConstants.ProfileType.My_Orders -> {
                    binding.tvTitle.text = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(MyOrdersFragment())
                }
                IConstants.ProfileType.Settings -> {
                    binding.tvTitle.text = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(SettingsFragment())
                }
                IConstants.ProfileType.Terms_conditions -> {
                    binding.tvTitle.text = getString(R.string.tit_terms_conditions)
                    val fragment = CmsFragment()
                    fragment.type = IConstants.ProfileType.Terms_conditions
                    loadFragment(fragment)
                }

                IConstants.ProfileType.Help_Support -> {
                    binding.tvTitle.text = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(HelpAndSupportFragment())
                }
                IConstants.ProfileType.About_us -> {
                    binding.tvTitle.text = getString(R.string.privacy_policy)
                    val fragment = CmsFragment()
                    fragment.type = IConstants.ProfileType.About_us
                    loadFragment(fragment)
                }

                IConstants.ProfileType.Refund_policy -> {
                    binding.tvTitle.text = getString(R.string.refund_policy)
                    val fragment = CmsFragment()
                    fragment.type = IConstants.ProfileType.Refund_policy
                    loadFragment(fragment)
                }

                IConstants.ProfileType.Current_Affairs -> {
                    binding.tvTitle.text = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(CurrentAffairsListFragment())
                }

                IConstants.ProfileType.Faq -> {
                    binding.tvTitle.text = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(FaqFragment())
                }
                IConstants.FragmentType.Subjects -> {
                    val model =
                        intent.getSerializableExtra(IConstants.IntentStrings.payload) as? MyCourseModel
                    binding.tvTitle.text = model?.title

                    model?.let { SubjectsFragment(it) }?.let { loadFragment(it) }
                }
                IConstants.FragmentType.SubjectsDetails -> {
                    //val model = intent.getSerializableExtra(IConstants.IntentStrings.payload) as? SubjectsModel
                    binding.tvTitle.text = IConstants.ValueHolder.courseName ?: ""
                    loadFragment(SubjectDetailsFragment())
                }
                IConstants.FragmentType.OlineTestCostDetails -> {
                    binding.tvTitle.text = intent.getStringExtra(IConstants.IntentStrings.payload)
                    loadFragment(OnlineTestDetailsFragment())
                }
                IConstants.FragmentType.EbookSubjects -> {
                    val model =
                        intent.getSerializableExtra(IConstants.IntentStrings.payload) as SubCourseModel
                    binding.tvTitle.text = model.sub_category_name
                    loadFragment(EbookCourseSubjectsFragment(model))
                }
                IConstants.FragmentType.EditProfile -> {
                    binding.tvTitle.text = getString(R.string.tit_edit_profile)
                    loadFragment(EditProfileFragment())
                }
                IConstants.FragmentType.Categories -> {
                    binding.tvTitle.text = getString(R.string.tit_categories)
                    loadFragment(CategoriesFragment())
                }
                IConstants.FragmentType.Ebook -> {
                    binding.tvTitle.text = getString(R.string.tit_ebook)
                    loadFragment(EBookFragment())
                }
                IConstants.FragmentType.EbookSubjectsType -> {
                    binding.tvTitle.text = getString(R.string.tit_ebook)
                    loadFragment(EBookFragment())
                }
                IConstants.FragmentType.TestScreen -> {
                    binding.tvTitle.text = getString(R.string.txt_sample_test)
                    val language: String =
                        intent.getStringExtra(IConstants.IntentStrings.language).toString()
                    val id: String =
                        intent.getStringExtra(IConstants.IntentStrings.testId).toString()
                    loadFragment(TestModuleFragment(language, id))
                }

                IConstants.FragmentType.CurrentAffairsView -> {
                    binding.tvTitle.text = getString(R.string.txt_current_affairs)
                    val id: String = intent.getStringExtra(IConstants.IntentStrings.payload)!!
                    val name: String = intent.getStringExtra(IConstants.IntentStrings.title)!!
                    loadFragment(CurrentAffairsViewFragment(id,name))
                }

                IConstants.FragmentType.PurchasedTestScreen -> {
                    binding.layoutTool.visibility = View.GONE
                    val language: String =
                        intent.getStringExtra(IConstants.IntentStrings.language).toString()
                    val id: String =
                        intent.getStringExtra(IConstants.IntentStrings.testId).toString()
                    val subjectId: String =
                        intent.getStringExtra(IConstants.IntentStrings.subjectId).toString()
                    val duration: String =
                        intent.getStringExtra(IConstants.IntentStrings.duration).toString()

                    loadFragment(PurchasedTestModuleFragment(language, id, subjectId, duration))
                }
                IConstants.FragmentType.Notifications -> {
                    binding.tvTitle.text = getString(R.string.tit_notifications)
                    loadFragment(NotificationFragment())
                }
                IConstants.FragmentType.OnlineTest -> {
                    binding.tvTitle.text = getString(R.string.menu_online_tests)
                    loadFragment(OnlineTestFragment())
                }
                IConstants.FragmentType.ReviewAnswer -> {
                    binding.tvTitle.text = getString(R.string.tit_review)
                    val examId: String =
                        intent.getStringExtra(IConstants.IntentStrings.examId).toString()
                    loadFragment(ReviewFragment(examId))
                }
                IConstants.FragmentType.Search -> {
                    binding.tvTitle.text = getString(R.string.tit_search)
                    loadFragment(SearchFragment())
                }
                IConstants.FragmentType.PurchasedMaterials -> {
                    binding.tvTitle.text = getString(R.string.tit_materials)
                    val subjectId =
                        intent.getStringExtra(IConstants.IntentStrings.subjectId)
                    loadFragment(PurchasedMaterialFragment(subjectId))
                }

            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        binding.plainContainer.id.let {
            supportFragmentManager.beginTransaction().replace(it, fragment).commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

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