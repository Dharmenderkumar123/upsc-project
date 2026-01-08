package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.services.model.MyMaterialModel
import com.cmt.view.activity.PdfActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsMyMaterialsBinding

class MyMaterialAdapater(val context: Context, val dataset: MutableList<MyMaterialModel>) :
    RecyclerView.Adapter<MyMaterialAdapater.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsMyMaterialsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: MyMaterialModel) {
            binding.model = datamodel
            binding.iconsLayout.visibility = View.GONE
            binding.pdfView.visibility = View.VISIBLE
            binding.pdfView.setOnClickListener {
                val activity = context as? PlainActivity
                val intent = Intent(activity, PdfActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.payload, datamodel.pdf)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsMyMaterialsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}