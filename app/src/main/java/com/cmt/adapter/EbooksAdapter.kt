package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.services.model.EbookDataModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PdfActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsEBooksBinding

class EbooksAdapter(val context: Context, val dataset: MutableList<EbookDataModel>) :
    RecyclerView.Adapter<EbooksAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsEBooksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: EbookDataModel) {
            binding.model = datamodel
            binding.root.setOnClickListener {
                val activity = context as? MainActivity
                val intent = Intent(activity, PdfActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.payload, datamodel.pdf)
                intent.putExtra(IConstants.IntentStrings.sub_cat_id, datamodel.sub_cat_id.toString())
                intent.putExtra(IConstants.IntentStrings.cat_type, "1")
                intent.putExtra(IConstants.IntentStrings.courseType, "notes")
                intent.putExtra(IConstants.IntentStrings.is_purchased, datamodel.is_purchased.toString())
                activity?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsEBooksBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}