package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.services.model.CurrentAffairsListModel

import com.cmt.services.model.NotificationDataModel
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.CurrentAffairsListBinding

class CurrentAffairsListAdapter(
    val context: Context,
    val dataSet: MutableList<CurrentAffairsListModel>
) :
    RecyclerView.Adapter<CurrentAffairsListAdapter.ViewHolder>() {
    inner class ViewHolder constructor(val binder: CurrentAffairsListBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun binding(model: CurrentAffairsListModel) {
            binder.model = model

            binder.root.setOnClickListener {
                val intent = Intent(context, PlainActivity::class.java)
                intent.putExtra(
                    IConstants.IntentStrings.type,
                    IConstants.FragmentType.CurrentAffairsView
                )
                intent.putExtra(IConstants.IntentStrings.payload, model.category_id)
                intent.putExtra(IConstants.IntentStrings.title,model.title)
                context.startActivity(intent)
                (context as FragmentActivity).overridePendingTransition(
                    R.anim.enter,
                    R.anim.exit
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CurrentAffairsListBinding.inflate(
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