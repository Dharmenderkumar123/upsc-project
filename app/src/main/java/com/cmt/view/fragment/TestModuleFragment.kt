package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cmt.TestModule.QuestionAnswerAdapter
import com.cmt.TestModule.TestCancelPopUpFragment
import com.cmt.adapter.TestQuestionNumberAdapter
import com.cmt.helper.ICallback
import com.cmt.model.UserAnswer
import com.cmt.services.model.TestOptionsModel
import com.cmt.viewModel.fragment.TestScreenVM
import com.google.gson.Gson
import com.the_pride_ias.databinding.FragmentTestModuleBinding

class TestModuleFragment(var lang: String, var testId: String) : Fragment() {
    lateinit var binding: FragmentTestModuleBinding
    private var questionsAdapter: QuestionAnswerAdapter? = null
    private var questionsCountAdapter: TestQuestionNumberAdapter? = null
    private var listQuestionAns= ArrayList<UserAnswer>()
    private var position: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestModuleBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@TestModuleFragment).get(TestScreenVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@TestModuleFragment

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.setQuestions(requireActivity(), lang, testId)

        binding.ivHambler.setOnClickListener {
            binding.layoutQuestions.startAnimation(inFromRightAnimation())
            binding.layoutQuestions.isVisible = true
            binding.ivHambler.isVisible = false
            binding.ivClose.isVisible = true
        }

        binding.ivClose.setOnClickListener {
            binding.layoutQuestions.startAnimation(outToRightAnimation())
            binding.layoutQuestions.isVisible = false
            binding.ivClose.isVisible = false
            binding.ivHambler.isVisible = true
        }

        binding.viewModel?.testData?.observe(requireActivity()) {
            testId= it?.exam_id.toString()
            binding.rvQuestions.apply {
                questionsCountAdapter = TestQuestionNumberAdapter(requireActivity(), it.data, object : ICallback {
                        override fun delegate(any: Any?) {
                            if (any != null) {
                                position = any as Int?
                                binding.questionsView.setCurrentItem(position!!, true)
                                binding.rvQuestions.scrollToPosition(position!!)
                                questionsCountAdapter?.updatePosition(position!!)
                                binding.layoutQuestions.startAnimation(outToRightAnimation())
                                binding.layoutQuestions.isVisible = false
                                binding.ivClose.isVisible = false
                                binding.ivHambler.isVisible = true
                            }
                        }

                    })
                adapter = questionsCountAdapter
            }


            binding.recyclerView.apply {
                questionsCountAdapter =
                    TestQuestionNumberAdapter(requireActivity(), it.data, object : ICallback {
                        override fun delegate(any: Any?) {
                            if (any != null) {
                                position = any as Int?
                                binding.questionsView.setCurrentItem(
                                    position!!,
                                    true
                                )
                                binding.recyclerView.scrollToPosition(position!!)
                                questionsCountAdapter?.updatePosition(position!!)
                            }
                        }

                    })
                adapter = questionsCountAdapter
            }


            binding.questionsView.apply {
                questionsAdapter = QuestionAnswerAdapter(binding.root.context, it.data)
                adapter = questionsAdapter
                isUserInputEnabled = false
                questionsAdapter?.mCallBack =
                    object : QuestionAnswerAdapter.OnSelectedAnswerListener {
                        override fun selectionAnswer(
                            position: Int,
                            model: MutableList<TestOptionsModel>
                        ) {

                        }

                        override fun nextQuestions() {

                            val currModel = it.data?.get(binding.questionsView.currentItem)
                            val newQid = (currModel?.question_id ?: "0").toInt()
                            val alreadyExists = listQuestionAns.any { it.qid == newQid }
                            if (!alreadyExists) {
                                listQuestionAns.add(UserAnswer(qid = newQid, currModel?.selectedAns))
                            } else {
                                val index = listQuestionAns.indexOfFirst { it.qid == newQid }
                                listQuestionAns[index] = UserAnswer(qid = newQid, currModel?.selectedAns)
                            }

                            binding.questionsView.setCurrentItem(
                                binding.questionsView.currentItem + 1,
                                true)
                            binding.recyclerView.scrollToPosition(binding.questionsView.currentItem)
                            questionsCountAdapter?.updatePosition(binding.questionsView.currentItem)
                        }

                        override fun previousQuestion() {
                            binding.questionsView.setCurrentItem(
                                binding.questionsView.currentItem - 1,
                                true
                            )
                            binding.recyclerView.scrollToPosition(binding.questionsView.currentItem)
                            questionsCountAdapter?.updatePosition(binding.questionsView.currentItem)
                        }

                        override fun submitAnswers() {
                            answersSubmitPOP()
                        }

                    }


            }

            binding.viewModel?.testDataResult?.observe(viewLifecycleOwner, Observer { it1->
//                    val bottomSheet = StudentBottomSheet(it1.exam_id)
//                    bottomSheet.show(requireActivity().supportFragmentManager, "StudentBottomSheetTag")

                val bottomSheet = SeeMyResultBottomSheet(it1)
                    bottomSheet.show(requireActivity().supportFragmentManager, "StudentBottomSheetTag")
            })

        }
    }

    private fun inFromRightAnimation(): Animation? {
        val inFromRight: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, +1.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
        )
        inFromRight.setDuration(500)
        inFromRight.setInterpolator(AccelerateInterpolator())
        return inFromRight
    }

    private fun outToRightAnimation(): Animation? {
        val outtoRight: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, +1.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
        )
        outtoRight.duration = 500
        outtoRight.interpolator = AccelerateInterpolator()
        return outtoRight
    }

    private fun answersSubmitPOP() {
        val testCancelPOP = TestCancelPopUpFragment()
        testCancelPOP.isCancelable = false
        testCancelPOP.show(childFragmentManager, "dialog")
        testCancelPOP.mCallback = object : TestCancelPopUpFragment.OnTestCancelListener {
            override fun cancelTest(cancel: Boolean) {
                if (cancel) {
                    testCancelPOP.dismiss()
                    val jsonArrayString: String = Gson().toJson(listQuestionAns)
                    binding.viewModel?.submitResult(requireContext(),testId,jsonArrayString)

//                    requireActivity().onBackPressed()
//                    requireActivity().finish()
                    /*val intent = Intent(requireActivity(), MainActivity::class.java)
                    requireActivity().startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
                    requireActivity().finishAffinity()*/
                } else {
                    testCancelPOP.dismiss()
                }
            }

        }
    }


}