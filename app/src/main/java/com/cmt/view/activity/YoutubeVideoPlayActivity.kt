package com.cmt.view.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cmt.helper.IConstants
import com.cmt.viewModel.activity.YoutubeVideoVM
import com.the_pride_ias.databinding.ActivityYoutubeVideoPlayBinding
import kotlin.system.exitProcess


class YoutubeVideoPlayActivity : AppCompatActivity() {
    lateinit var binding: ActivityYoutubeVideoPlayBinding
    var videoKey: String? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeVideoPlayBinding.inflate(layoutInflater).apply {
            viewModel =
                ViewModelProvider(this@YoutubeVideoPlayActivity).get(YoutubeVideoVM::class.java)
            lifecycleOwner = this@YoutubeVideoPlayActivity
        }
        setContentView(binding.root)
        //configVimeoClient();

        if (android.os.Debug.isDebuggerConnected() || android.os.Debug.waitingForDebugger()) {
            exitProcess(0)
        }

        binding.webView.settings.pluginState = WebSettings.PluginState.ON
        binding.webView.settings.pluginState = WebSettings.PluginState.ON_DEMAND
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.allowFileAccess = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.mediaPlaybackRequiresUserGesture = false
        binding.webView.settings.mediaPlaybackRequiresUserGesture = false

        if (intent.hasExtra(IConstants.IntentStrings.youtubeId)) {
            videoKey = intent.getStringExtra(IConstants.IntentStrings.youtubeId)
            val videoUrl = videoKey?.replace("\\", "")
//            val videoUrl = "https://player.vimeo.com/video/" + videoKey
//            val videoUrl = "https://player.vimeo.com/video/${videoKey}?h=e5565b56ac"
            // val videoUrl = "https://player.vimeo.com/video/" + "758953781"
            //"https://player.vimeo.com/video/"+"137805268"
            Log.d("sdhfjbs", "onCreate: ${videoUrl}")

            playVideo(videoUrl ?: "")
        }
    }


    private fun playVideo(id: String) {
        binding.webView.settings.apply {
            javaScriptEnabled = true // Required for iframe players
            domStorageEnabled = true // Often required by video players
            mediaPlaybackRequiresUserGesture = false // Allows autoplay=1 to work
        }
        binding.webView.webViewClient = WebViewClient()

//
//        val data_html =
//            "<!DOCTYPE html><html> <head> <meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"target-densitydpi=high-dpi\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <link rel=\"stylesheet\"  href=\"hdpi.css\" /></head> <body style=\"background:black;margin:0 0 0 0; padding:0 0 0 0;\"><iframe src=\"$id?autoplay=1\" \n\" +\n" +
//                    "        \"        width=\"100%\" \n\" +\n" +
//                    "        \"        height=\"100%\" \n\" +\n" +
//                    "        \"        frameborder=\"0\" \n\" +\n" +
//                    "        \"        webkitallowfullscreen mozallowfullscreen allowfullscreen style=\"position:absolute;top:0;left:0;width:100%;height:100%;\" ></iframe> </body> </html> "
//
//
//        binding.webView.loadData(
//            data_html,
//            "text/html",
//            "UTF-8"
//        )

//                    val data_html = """
//                <!DOCTYPE html>
//                <html>
//                <head>
//                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
//                    <style>
//                        body, html { margin: 0; padding: 0; height: 100%; width: 100%; background: black; }
//                        iframe { border: none; }
//                    </style>
//                </head>
//                <body>
//                    <iframe
//                        src="$id?autoplay=1"
//                        width="100%"
//                        height="100%"
//                        allow="autoplay; fullscreen"
//                        style="position:absolute;top:0;left:0;width:100%;height:100%;">
//                    </iframe>
//                </body>
//                </html>
//            """.trimIndent()
//
//        binding.webView.loadDataWithBaseURL(null, data_html, "text/html", "UTF-8", null)



        binding.webView.apply {
            // 1. Essential Settings
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            // 2. Allow the video to play without the user clicking it
            settings.mediaPlaybackRequiresUserGesture = false

            // 3. Optional: Handle navigation inside the view
            webViewClient = WebViewClient()

            // 4. Load the URL directly
            loadUrl(id)
        }
//        loadWebView(id)
    }

    private fun loadWebView(url: String) {
        binding.webView.loadData(url, "text/html", "utf-8")
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }
        }
    }

    /* private fun callVimeoAPIRequest() {
  val params = HashMap<String, String>()
  params["video_id"] = VIMDEO_ID
  VimeoExtractAPI().getVimeoVideoUrl(params, object : RetrofitCallBack {
      override fun responseListener(response: Any?, error: String?) {
          if (error != null) {
          } else {

              val apiResponse = response as JSONObject
              Log.d("vimeoUrl", "responseListener: $apiResponse")
          }
      }

  })
}*/

    /* private fun createMediaItem(s: String) {
  val mediaItem: MediaItem = MediaItem.fromUri(s)
  player!!.setMediaItem(mediaItem)
}*/

}