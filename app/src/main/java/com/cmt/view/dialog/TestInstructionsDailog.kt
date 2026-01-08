package com.cmt.view.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.cmt.helper.IConstants
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.TestInstructionsDailogBinding

class TestInstructionsDailog(
    var testId: String,
    var subjectId: String,
    var duration: String? = null
) : DialogFragment() {
    private lateinit var binding: TestInstructionsDailogBinding
    var selectedlanguage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Dialog_Alert)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = TestInstructionsDailogBinding.inflate(layoutInflater, container, false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val languages = mutableListOf<String>()
        languages.add("English")
        languages.add("Telugu")

        binding.langType.apply {
            adapter =
                ArrayAdapter<String>(requireActivity(), R.layout.layout_spinner_item, languages)
        }

        binding.startTest.setOnClickListener {
            if (binding.langType.selectedItem.toString().equals("English")) {
                selectedlanguage = "english"
            } else if (binding.langType.selectedItem.toString().equals("Telugu")) {
                selectedlanguage = "telugu"
            }
            if (binding.checkBox.isChecked == true) {
                if (subjectId.isNullOrEmpty()) {
                    val intent = Intent(requireActivity(), PlainActivity::class.java)
                    intent.putExtra(
                        IConstants.IntentStrings.type,
                        IConstants.FragmentType.TestScreen
                    )
                    intent.putExtra(IConstants.IntentStrings.language, selectedlanguage)
                    intent.putExtra(IConstants.IntentStrings.testId, testId)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
                    dismiss()
                } else {
                    val intent = Intent(requireActivity(), PlainActivity::class.java)
                    intent.putExtra(
                        IConstants.IntentStrings.type,
                        IConstants.FragmentType.PurchasedTestScreen
                    )
                    intent.putExtra(IConstants.IntentStrings.language, selectedlanguage)
                    intent.putExtra(IConstants.IntentStrings.testId, testId)
                    intent.putExtra(IConstants.IntentStrings.subjectId, subjectId)
                    intent.putExtra(IConstants.IntentStrings.duration, duration)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
                    dismiss()
                }

            } else {
                Toast.makeText(
                    requireActivity(),
                    "Please accept the terms to start exam.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }


}