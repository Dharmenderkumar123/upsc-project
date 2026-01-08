package com.cmt.view.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.cmt.helper.IConstants
import com.cmt.viewModel.activity.PdfActivityVM
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.rajat.pdfviewer.PdfRendererView
import com.rajat.pdfviewer.PdfViewerActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ActivityPdfBinding
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class PdfActivity : AppCompatActivity() {
    lateinit var binding: ActivityPdfBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@PdfActivity).get(PdfActivityVM::class.java)
            lifecycleOwner = this@PdfActivity
        }
        setContentView(binding.root)

        binding.tvTitle.text = getString(R.string.title_material)
        val url = intent.getStringExtra(IConstants.IntentStrings.payload)

        val pdfUrl = "https://docs.google.com/gview?embedded=true&url=$url"
        val finalUrl=getDirectPdfUrl(pdfUrl)
        Log.d("qdwqwd", "onCreate: ${getDirectPdfUrl(pdfUrl)}")
        binding.pdfView.initWithUrl(
            url = finalUrl,
            lifecycleCoroutineScope = lifecycleScope,
            lifecycle = lifecycle
        )


        binding.pdfView.statusListener = object : PdfRendererView.StatusCallBack {
            override fun onPdfLoadStart() {
                Log.i("PDF Status", "Loading started")
            }

            override fun onPdfLoadProgress(progress: Int, downloadedBytes: Long, totalBytes: Long?) {
                Log.i("PDF Status", "Download progress: $progress%")
            }

            override fun onPdfLoadSuccess(absolutePath: String) {
                Log.i("PDF Status", "Load successful: $absolutePath")
            }

            override fun onError(error: Throwable) {
                Log.e("PDF Status", "Error loading PDF: ${error.message}")
            }

            override fun onPageChanged(currentPage: Int, totalPage: Int) {
                Log.i("PDF Status", "Page changed: $currentPage / $totalPage")
            }

            override fun onPdfRenderStart() {
                Log.i("PDF Status", "Render started")
            }

            override fun onPdfRenderSuccess() {
                Log.i("PDF Status", "Render successful")
//                binding.pdfView.jumpToPage($number)  // Recommend to use `jumpToPage` inside `onPdfRenderSuccess`
            }
        }

//        binding.webView.webViewClient = MyWebViewClient()
//        binding.webView.settings.javaScriptEnabled = true
//        binding.webView.loadUrl(pdfUrl)
//
//        binding.webView.webChromeClient = object : WebChromeClient() {
//            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
//                Log.d("WebView", consoleMessage.message())
//                return true
//            }
//        }

        /* startActivity(
              PdfViewerActivity.launchPdfFromUrl(
                  this,
                  "https://colormoon.in//sthree//uploads//4deafb675e91cc44ff6b822ab2101b4a.pdf",
                  getString(R.string.title_material),
                  "",
                  enableDownload = false
              )
          )
         finish()*/

    }

    fun getDirectPdfUrl(rawUrl: String): String {
        return if (rawUrl.contains("url=")) {
            rawUrl.substringAfter("url=")
        } else {
            rawUrl
        }
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.d("pdfUrl", url)
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            binding.pbr.isVisible = false
            Log.d("pageFinish", "onPageFinished: $url")
            view.loadUrl("javascript:(function() {document.querySelector('[class=\"ndfHFb-c4YZDc-Wrql6b\"]').remove();})()")
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            binding.pbr.isVisible = true
            Log.d("start", "onPageStarted: $url")
            super.onPageStarted(view, url, favicon)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }
}