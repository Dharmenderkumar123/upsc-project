package com.cmt.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.helper.PdfPageLockDecorator
import com.cmt.helper.VerticalSeekBar
import com.cmt.viewModel.activity.PdfActivityVM
import com.rajat.pdfviewer.PdfRendererView
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ActivityPdfBinding

class PdfActivity : AppCompatActivity() {
    lateinit var binding: ActivityPdfBinding
    private val scrollHandler = Handler(Looper.getMainLooper())
    private var pendingScrollRunnable: Runnable? = null

    private var isUserDraggingScrollbar = false

    fun activityLoader(isShow: Boolean) {
        if (isShow) {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            val pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse)
            binding.logoLoader.visibility = View.VISIBLE
            binding.logoLoader.startAnimation(pulseAnimation)
        } else {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.logoLoader.clearAnimation()
            binding.logoLoader.visibility = View.GONE
        }
    }

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
        val finalUrl = getDirectPdfUrl(pdfUrl)
        Log.d("qdwqwd", "onCreate: ${url}")
        binding.pdfView.initWithUrl(
            url = finalUrl,
            lifecycleCoroutineScope = lifecycleScope,
            lifecycle = lifecycle
        )


        binding.pdfView.statusListener = object : PdfRendererView.StatusCallBack {
            override fun onPdfLoadStart() {
                Log.i("PDF Status", "Loading started")
                activityLoader(true)
            }

            override fun onPdfLoadProgress(
                progress: Int,
                downloadedBytes: Long,
                totalBytes: Long?) {
            }

            override fun onPdfLoadSuccess(absolutePath: String) {
                activityLoader(false)
                with(binding) {
                    val totalPages = pdfView.totalPageCount
                    customScrollbar.max =  totalPages - 1

                    val lockDecorator = PdfPageLockDecorator(this@PdfActivity, 50)
                    pdfView.recyclerView.addItemDecoration(lockDecorator)

                    binding.customScrollbar.setScrollStateListener(object : VerticalSeekBar.ScrollStateListener {

                        override fun onStartScrolling() {
                            isUserDraggingScrollbar = true
                            binding.pdfView.recyclerView.parent.requestDisallowInterceptTouchEvent(true)
                        }

                        override fun onStopScrolling() {
                            isUserDraggingScrollbar = false
                            binding.pdfView.recyclerView.parent.requestDisallowInterceptTouchEvent(false)
                        }

                        override fun onProgressChanged(progress: Int) {
                            val layoutManager = binding.pdfView.recyclerView.layoutManager as LinearLayoutManager
                            layoutManager.scrollToPositionWithOffset(progress, 0)
                        }
                    })

                    customScrollbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            if (isUserDraggingScrollbar) {
                                pendingScrollRunnable?.let { scrollHandler.removeCallbacks(it) }

                                pendingScrollRunnable = Runnable {
                                    val layoutManager = binding.pdfView.recyclerView.layoutManager as LinearLayoutManager
                                    layoutManager.scrollToPositionWithOffset(progress, 0)
                                }

                                scrollHandler.postDelayed(pendingScrollRunnable!!, 15)
                            }
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                            isUserDraggingScrollbar = true
                            binding.pdfView.recyclerView.parent.requestDisallowInterceptTouchEvent(true)
                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                            isUserDraggingScrollbar = false
                            binding.pdfView.recyclerView.parent.requestDisallowInterceptTouchEvent(false)

                            val layoutManager = binding.pdfView.recyclerView.layoutManager as LinearLayoutManager
                            layoutManager.scrollToPositionWithOffset(seekBar?.progress ?: 0, 0)
                        }
                    })

                    pdfView.recyclerView.addOnItemTouchListener(object : androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener() {
                        override fun onInterceptTouchEvent(rv: androidx.recyclerview.widget.RecyclerView, e: MotionEvent): Boolean {
                            if (e.action == MotionEvent.ACTION_UP) {
                                val child = rv.findChildViewUnder(e.x, e.y)
                                if (child != null) {
                                    val position = rv.getChildAdapterPosition(child)
                                    if (position >= 50) {
                                        val intent = Intent(this@PdfActivity, PlainActivity::class.java).apply {
                                            putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.BuyPlan)
                                            putExtra(IConstants.IntentStrings.payload, "Buy Plan")
                                        }
                                        startActivity(intent)
                                        return true
                                    }
                                }
                            }
                            return false
                        }
                    })
                }
            }



            override fun onPageChanged(currentPage: Int, totalPage: Int) {
                if (!isUserDraggingScrollbar) {
                    binding.customScrollbar.progress = currentPage
                }
            }

            override fun onError(error: Throwable) {
                Log.e("PDF Status", "Error: ${error.message}")
                activityLoader(false)
            }

            override fun onPdfRenderStart() {}

            override fun onPdfRenderSuccess() {
                activityLoader(false)

                Log.i("PDF Status", "Render successful")
            }
        }
    }





    fun redirectToBuyPlan() {
        val intent = Intent(this, PlainActivity::class.java).apply {
            putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.BuyPlan)
            putExtra(IConstants.IntentStrings.payload, "Buy Plan")
        }
        startActivity(intent)
    }



    private fun setScrollbarThick(recyclerView: RecyclerView, thicknessDp: Int) {
        try {
            recyclerView.isVerticalScrollBarEnabled = true
            val method = View::class.java.getDeclaredMethod("setScrollBarSize", Int::class.javaPrimitiveType)
            method.isAccessible = true

            val thicknessPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                thicknessDp.toFloat(),
                resources.displayMetrics
            ).toInt()

            method.invoke(recyclerView, thicknessPx)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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