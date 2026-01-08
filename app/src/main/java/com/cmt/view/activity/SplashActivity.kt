package com.cmt.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cmt.helper.internetActive
import com.cmt.internet.ConnectionStateMonitor
import com.cmt.viewModel.activity.SplashVm
import com.google.android.material.snackbar.Snackbar
import com.the_pride_ias.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binder: ActivitySplashBinding
    private var internetSnackBar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivitySplashBinding.inflate(layoutInflater).apply {
            viewModel = ViewModelProvider(this@SplashActivity).get(SplashVm::class.java)
            lifecycleOwner = this@SplashActivity
        }
        setContentView(binder.root)

        versionCheck()

        binder.viewModel?.setZoomForImage(binder.splashImage)

        binder.swipe.setOnRefreshListener {
            versionCheck()
        }

        val connectionStateMonitor = ConnectionStateMonitor()
        connectionStateMonitor.enable(this)
        connectionStateMonitor.observer.observe(this) {
            binder.viewModel?.noInternet?.value = !it
        }

    }

    private fun versionCheck() {
        binder.swipe.isRefreshing = false
        binder.viewModel?.setZoomForImage(binder.splashImage)
    }
}