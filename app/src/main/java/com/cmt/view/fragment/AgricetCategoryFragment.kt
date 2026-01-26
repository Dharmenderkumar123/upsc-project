package com.cmt.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.AgricetCategoryAdapter
import com.cmt.helper.IConstants
import com.cmt.helper.hideKeyboard
import com.cmt.services.model.SubCourseModel
import com.cmt.view.activity.PaymentActivity
import com.cmt.viewModel.fragment.AgricetVM
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentAgricetCategoryBinding


class AgricetCategoryFragment(var model: SubCourseModel) : Fragment() {
    lateinit var binding: FragmentAgricetCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgricetCategoryBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@AgricetCategoryFragment).get(AgricetVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@AgricetCategoryFragment
        }
        return binding.root
    }

    private var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
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

        binding.viewModel?.openPaymentActivity?.observe(requireActivity()) {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.payload, it.toString())
            activityResultLauncher.launch(intent)
            requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
        }


        binding.viewModel?.subjectsData?.observe(requireActivity()) {
            if (!it?.subjects.isNullOrEmpty()) {
                it?.let {
                    binding.recycleView.apply {
                        adapter = it.subjects?.let { it1 ->
                            it.image?.let { it2 ->
                                it.description?.let { it3 ->
                                    AgricetCategoryAdapter(binding.root.context, it1, it2, it3, it,model.is_purchased,model.sub_category_id)
                                }
                            }
                        }
                    }
                }
                binding.tvNoSubj.setVisibility(View.GONE)
            } else {
                binding.recycleView.setVisibility(View.GONE)
                binding.tvNoSubj.setVisibility(View.VISIBLE)
            }
        }
        binding.layoutPrices.visibility= if(!model.is_purchased) View.VISIBLE else View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.setData(requireActivity(), model)
    }


}