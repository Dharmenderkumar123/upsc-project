package com.cmt.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.MyCoursesAdapter
import com.cmt.helper.ICallback
import com.cmt.helper.IConstants
import com.cmt.helper.hideKeyboard
import com.cmt.services.model.MyCourseModel
import com.cmt.view.activity.PaymentActivity
import com.cmt.viewModel.fragment.MyCoursesVM
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentMyCoursesBinding

class MyCoursesFragment : Fragment() {

    lateinit var binding: FragmentMyCoursesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyCoursesBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@MyCoursesFragment).get(MyCoursesVM::class.java)
            lifecycleOwner = this@MyCoursesFragment

        }
        return binding.root
    }

    private var activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            view?.hideKeyboard()
            val data = result.data
            val paymentID = data?.getStringExtra(IConstants.IntentStrings.payload)
            if (paymentID != null) {
                binding.viewModel?.confirmOnlinePaymentOrder(paymentID, view)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.setCoursesList(requireActivity())

        binding.viewModel?.openPaymentActivity?.observe(requireActivity()) {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.payload, it.toString())
            activityResultLauncher.launch(intent)
            requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
        }

        binding.viewModel?.courseData?.observe(requireActivity()) {
            if (!it.isNullOrEmpty()) {
                binding.recycleView.apply {
                    adapter = MyCoursesAdapter(binding.root.context, it, object : ICallback {
                        override fun delegate(any: Any?) {
                            if (any != null) {
                                any as MyCourseModel
                                binding.viewModel?.createOrderId(requireActivity(), any)
                            }
                        }

                    })
                    binding.recycleView.isVisible = true
                    binding.tvNoPurhase.isVisible = false
                }
            } else {
                binding.tvNoPurhase.isVisible = true
                binding.recycleView.isVisible = false
            }
        }
    }


}