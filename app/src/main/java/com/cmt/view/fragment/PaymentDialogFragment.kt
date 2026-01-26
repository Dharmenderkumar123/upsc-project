package com.cmt.view.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.cmt.services.model.PaymentModel
import com.the_pride_ias.databinding.PaymentPopupBinding

class PaymentDialogFragment(var dataResponse: PaymentModel) : DialogFragment() {
    lateinit var binding: PaymentPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= PaymentPopupBinding.inflate(layoutInflater)
//        return inflater.inflate(com.the_pride_ias.R.layout.payment_popup, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val qrUrl = "https://api.theprideias.com/qr.jpeg"
        val qrUrl = dataResponse.qr_code
//        val titleText = "Collect Payment via UPI"
        val titleText = dataResponse.title
//        val descText = "After successful payment, please send us the payment screenshot along with your name and registered mobile number on our email theprideiasacademy1@gmail.com."
        val descText = dataResponse.description


        with(binding){
            tvPaymentInstruction.text = titleText
            tvDescription.text = descText

            // Configure WebView
            qrWebView.settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = true
            }
            qrWebView.webViewClient = WebViewClient()
            qrWebView.loadUrl(qrUrl)

            btnClose.setOnClickListener { dismiss() }
            btnDone.setOnClickListener { dismiss() }
        }

    }
}