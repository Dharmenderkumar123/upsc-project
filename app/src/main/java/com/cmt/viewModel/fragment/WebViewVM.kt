package com.cmt.viewModel.fragment

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import com.the_pride_ias.databinding.FragmentWebViewBinding

class WebViewVM : ViewModel() {
    lateinit var binding: FragmentWebViewBinding
    @SuppressLint("SetJavaScriptEnabled")
    fun setUpWebView(url: String?) {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = MyWebViewClient()
        if (url != null) {
            binding.webView.loadUrl(url)
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
            return true
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return true
        }
    }
}
