package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider

import com.cmt.helper.IConstants
import com.cmt.viewModel.fragment.CmsViewModel
import com.the_pride_ias.databinding.FragmentCmsBinding

class CmsFragment : Fragment() {
    lateinit var binding: FragmentCmsBinding
    var type: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCmsBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@CmsFragment).get(CmsViewModel::class.java)
            lifecycleOwner = this@CmsFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (type == IConstants.ProfileType.About_us) {
//            binding.viewModel?.aboutUsAPI(view)
            loadUrl("https://theprideias.com/privacy-policy.php")
        } else if (type == IConstants.ProfileType.Terms_conditions) {
//            binding.viewModel?.termsAPI(view)
            loadUrl("https://theprideias.com/terms.php")
        }else if (type == IConstants.ProfileType.Refund_policy) {
//            binding.viewModel?.termsAPI(view)
            loadUrl("https://theprideias.com/refund.php")
        }
    }


    fun loadUrl(url: String){
        binding.webView.settings.apply {
        javaScriptEnabled = true           // Required for most sites & videos
        domStorageEnabled = true            // Allows sites to store data locally
        loadWithOverviewMode = true         // Fits content to screen width
        useWideViewPort = true
        builtInZoomControls = true
        displayZoomControls = false         // Hide the zoom buttons
        mediaPlaybackRequiresUserGesture = false // Allow autoplay
    }

    // Force links to open inside the app instead of a browser
    binding.webView.webViewClient = WebViewClient()

    // The URL you want to load
        binding.webView.loadUrl(url)
    }
}