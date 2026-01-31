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
import androidx.lifecycle.ViewModelProvider
import com.cmt.helper.IConstants
import com.cmt.helper.IConstants.IntentStrings.type
import com.cmt.helper.hideKeyboard
import com.cmt.view.activity.PaymentActivity
import com.cmt.viewModel.fragment.BuyPlanVM
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentBuyPlanBinding

class BuyPlanFragment(val id1: String?,val type: Int) : Fragment() {
    lateinit var binding : FragmentBuyPlanBinding

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBuyPlanBinding.inflate(layoutInflater,container,false).apply {
            viewModel = ViewModelProvider(this@BuyPlanFragment)[BuyPlanVM::class.java]
            viewModel?.binding = this
            lifecycleOwner = this@BuyPlanFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.getData(view,requireActivity(),id1,type)
        binding.viewModel?.openPaymentActivity?.observe(requireActivity()) {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra(IConstants.IntentStrings.payload, it.toString())
            activityResultLauncher.launch(intent)
            requireActivity().overridePendingTransition(R.anim.enter, R.anim.exit)
        }

    }


}