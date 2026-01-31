package com.cmt.view.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cmt.helper.IConstants
import com.cmt.viewModel.activity.PaymentVM
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ActivityPaymentBinding
import org.json.JSONObject
import kotlin.system.exitProcess

class PaymentActivity : BaseActivity(), PaymentResultListener {
    private val classTag = "PAYMENT_ACT"
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var checkout: Checkout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@PaymentActivity)[PaymentVM::class.java]
            lifecycleOwner = this@PaymentActivity
        }
        setContentView(binding.root)
        if (android.os.Debug.isDebuggerConnected() || android.os.Debug.waitingForDebugger()) {
            exitProcess(0)
        }
        try {
            Checkout.preload(applicationContext)

            checkout = Checkout()

            val payload = intent.getStringExtra(IConstants.IntentStrings.payload)
            if (payload != null) {
                val jsonObject = JSONObject(payload)
                Log.d("sakdbaksd", "onCreate: ${jsonObject}")
//                checkout.setKeyID(jsonObject.getString("razorpay_key"))
                checkout.setKeyID("rzp_test_SAA4AZr56Q1yzc")
                checkout.open(this, jsonObject)
            }
        }catch (e: Exception){
            Log.d("Razorpay_exception", "Exceptions: ${e.message}")
        }

    }

    override fun onPaymentSuccess(p0: String?) {
        intent.putExtra(IConstants.IntentStrings.payload, p0)
        Log.d(classTag, "onPaymentSuccess: " + p0)
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        if (p1 != null) {
            try {
                val jsonObject = JSONObject(p1)
                val error = jsonObject.getJSONObject("error")
                Toast.makeText(this, error.getString("description"), Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        Log.d(classTag, "onPaymentError: $p1")
        setResult(Activity.RESULT_CANCELED, intent)
        onBackPressed()
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
    }
}