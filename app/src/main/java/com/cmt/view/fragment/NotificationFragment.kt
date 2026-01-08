package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cmt.adapter.NotificationAdapter
import com.cmt.viewModel.fragment.NotificationViewModel
import com.the_pride_ias.R
import com.the_pride_ias.databinding.FragmentNotificationBinding

class NotificationFragment : DialogFragment() {
    private lateinit var binder: FragmentNotificationBinding

    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(R.style.AppTheme_Dialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        return binder.apply {
            viewModel =
                ViewModelProvider(this@NotificationFragment).get(NotificationViewModel::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@NotificationFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder.viewModel?.setNotificationData()

        binder.viewModel?.getNotificationDataObserver()?.observe(requireActivity()) {
            if (it.isEmpty()) {
                binder.viewModel?.noData?.value = true
                binder.viewModel?.noDataMsg?.value = getString(R.string.no_notification)
            } else {
                binder.notificationListView.apply {
                    adapter = NotificationAdapter(requireContext(), it)
                }
            }

        }

    }
}
