package com.cmt.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.cmt.TestModule.PurchasedQuestionAdapter
import com.cmt.TestModule.TestCancelPopUpFragment
import com.cmt.adapter.PurchasedQuestionNumberAdapter
import com.cmt.helper.ICallback
import com.cmt.services.model.OptionsModel
import com.cmt.viewModel.fragment.PurchasedTestVM
import com.the_pride_ias.databinding.FragmentPurchasedTestModuleBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class PurchasedTestModuleFragment(
    var lang: String,
    var testId: String,
    var subjectId: String,
    var duration: String? = null
) :
    Fragment() {
    lateinit var binding: FragmentPurchasedTestModuleBinding
    private var questionsAdapter: PurchasedQuestionAdapter? = null
    private var questionsCountAdapter: PurchasedQuestionNumberAdapter? = null
    private var position: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentPurchasedTestModuleBinding.inflate(layoutInflater, container, false).apply {
                viewModel =
                    ViewModelProvider(this@PurchasedTestModuleFragment).get(PurchasedTestVM::class.java)
                viewModel?.binding = this
                lifecycleOwner = this@PurchasedTestModuleFragment

            }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testCountDown(duration?.toUInt())

        binding.ivHambler.setOnClickListener {
            binding.layoutQuestions.startAnimation(inFromRightAnimation())
            binding.layoutQuestions.isVisible = true
            binding.ivHambler.isVisible = false
            binding.ivClose.isVisible = true
            binding.viewModel?.testData?.observe(requireActivity()) {
                if (!it.isNullOrEmpty()) {
                    binding.rvQuestions.apply {
                        questionsCountAdapter =
                            PurchasedQuestionNumberAdapter(
                                binding.root.context,
                                it,
                                object : ICallback {
                                    @SuppressLint("NotifyDataSetChanged")
                                    override fun delegate(any: Any?) {
                                        if (any != null) {
                                            position = any as Int?
                                            binding.questionsView.setCurrentItem(
                                                position!!,
                                                true
                                            )
                                            binding.rvQuestions.scrollToPosition(position!!)
                                            questionsCountAdapter?.updatePosition(position!!)
                                            binding.layoutQuestions.isVisible = false
                                            binding.ivClose.isVisible = false
                                            binding.ivHambler.isVisible = true

                                        }
                                    }

                                })
                        adapter = questionsCountAdapter
                        adapter?.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "No questions available in this subject",
                        Toast.LENGTH_SHORT
                    ).show()
                    requireActivity().onBackPressed()
                }
            }
        }

        binding.ivClose.setOnClickListener {
            binding.layoutQuestions.startAnimation(outToRightAnimation())
            binding.layoutQuestions.isVisible = false
            binding.ivClose.isVisible = false
            binding.ivHambler.isVisible = true
        }

        binding.viewModel?.setQuestions(requireActivity(), lang, testId, subjectId)
        if (binding.viewModel?.noQuestions?.value == true) {
            requireActivity().onBackPressed()
            Toast.makeText(requireActivity(), "No Questions Available", Toast.LENGTH_SHORT).show()
        }

        binding.viewModel?.testData?.observe(requireActivity()) {

            if (!it.isNullOrEmpty()) {
                binding.rvQuestions.apply {
                    questionsCountAdapter =
                        PurchasedQuestionNumberAdapter(
                            binding.root.context,
                            it,
                            object : ICallback {
                                @SuppressLint("NotifyDataSetChanged")
                                override fun delegate(any: Any?) {
                                    if (any != null) {
                                        position = any as Int?
                                        binding.questionsView.setCurrentItem(
                                            position!!,
                                            true
                                        )
                                        binding.rvQuestions.scrollToPosition(position!!)
                                        questionsCountAdapter?.updatePosition(position!!)
                                        binding.layoutQuestions.isVisible = false
                                        binding.ivClose.isVisible = false
                                        binding.ivHambler.isVisible = true

                                    }
                                }

                            })
                    adapter = questionsCountAdapter
                    adapter?.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(
                    requireActivity(),
                    "No questions available in this subject",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().onBackPressed()
            }

            if (!it.isNullOrEmpty()) {
                binding.recyclerView.apply {
                    questionsCountAdapter =
                        PurchasedQuestionNumberAdapter(
                            binding.root.context,
                            it,
                            object : ICallback {
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
            } else {
                Toast.makeText(
                    requireActivity(),
                    "No questions available in this subject",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().onBackPressed()
            }


            binding.backNav.setOnClickListener {
                answersSubmitPOP()
            }

            binding.questionsView.apply {
                questionsAdapter = PurchasedQuestionAdapter(binding.root.context, it)
                adapter = questionsAdapter
                isUserInputEnabled = false
                questionsAdapter?.mCallBack =
                    object : PurchasedQuestionAdapter.OnSelectedAnswerListener {
                        override fun selectionAnswer(
                            position: Int,
                            model: MutableList<OptionsModel>
                        ) {
                            jsonConversion(model)
                        }

                        @SuppressLint("NotifyDataSetChanged")
                        override fun nextQuestions() {
                            binding.questionsView.setCurrentItem(
                                binding.questionsView.currentItem + 1,
                                true
                            )
                            binding.recyclerView.scrollToPosition(binding.questionsView.currentItem)
                            questionsCountAdapter?.updatePosition(binding.questionsView.currentItem)
                            questionsCountAdapter?.notifyDataSetChanged()
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

    private fun testCountDown(testTime: UInt?) {
        val countDownTimer = object : CountDownTimer((testTime?.times(60000u))?.toLong()!!, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                var millis = millisUntilFinished
                val hours = TimeUnit.MILLISECONDS.toHours(millis)
                millis -= TimeUnit.HOURS.toMillis(hours)

                val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
                millis -= TimeUnit.MINUTES.toMillis(minutes)

                val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)

                binding.tvTitle.text = "Time Left ${hours}:${minutes}:${seconds}"
            }

            override fun onFinish() {
                binding.tvTitle.text = "Time Up"
                answersSubmitPOP()
            }
        }
        countDownTimer.start()

    }


    //Json convert for answered questions
    var jsonAnswersArray = JSONArray()
    private fun jsonConversion(answers: MutableList<OptionsModel>) {
        val jsonArray = JSONArray()
        for (i in answers.indices) {
            val jsonObject = JSONObject()
            try {
                jsonObject.put("qid", answers[i].question_id)
                if (answers[i].isSelected) {
                    if (answers[i].selectedOption.equals("option1")) {
                        jsonObject.put("answer", "1")
                    } else if (answers[i].selectedOption.equals("option2")) {
                        jsonObject.put("answer", "2")
                    } else if (answers[i].selectedOption.equals("option3")) {
                        jsonObject.put("answer", "3")
                    } else if (answers[i].selectedOption.equals("option4")) {
                        jsonObject.put("answer", "4")
                    }
                } else {
                    jsonObject.put("answer", "")
                }
                jsonArray.put(jsonObject)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        jsonArray.toString()
        jsonAnswersArray = jsonArray
    }

    private fun answersSubmitPOP() {
        val testCancelPOP = TestCancelPopUpFragment()
        testCancelPOP.isCancelable = false
        testCancelPOP.show(requireActivity().supportFragmentManager, "dialog")
        testCancelPOP.mCallback = object : TestCancelPopUpFragment.OnTestCancelListener {
            override fun cancelTest(cancel: Boolean) {
                if (cancel) {
                    testCancelPOP.dismiss()
                    Log.d("selectedAnswers", "responseListener: $jsonAnswersArray")
                    submitAnswers(jsonAnswersArray)
                } else {
                    testCancelPOP.dismiss()
                }
            }

        }
    }

    private fun submitAnswers(jsonAnswersArray: JSONArray) {
        binding.viewModel?.submitAnswers(requireActivity(), jsonAnswersArray)
    }
}
