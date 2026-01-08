package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.services.model.CurrentAffairsListModel
import com.cmt.services.model.CurrentAffairsViewModel

import com.cmt.services.model.NotificationDataModel
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.databinding.CurrentAffairsViewListBinding

class CurrentAffairsViewListAdapter(
    val context: Context,
    val dataSet: MutableList<CurrentAffairsViewModel>
) :
    RecyclerView.Adapter<CurrentAffairsViewListAdapter.ViewHolder>() {
    inner class ViewHolder constructor(val binder: CurrentAffairsViewListBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun binding(model: CurrentAffairsViewModel) {
            binder.model = model
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CurrentAffairsViewListBinding.inflate(
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