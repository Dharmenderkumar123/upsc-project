package com.cmt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.cmt.services.model.NotificationDataModel
import com.the_pride_ias.databinding.LayoutNotificationItemBinding

class NotificationAdapter(val context: Context, val dataSet: MutableList<NotificationDataModel>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    inner class ViewHolder constructor(val binder: LayoutNotificationItemBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun binding(model: NotificationDataModel) {
            binder.model = model
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutNotificationItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}