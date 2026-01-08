package com.cmt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.services.model.HomeAdvBannerModel
import com.the_pride_ias.databinding.ItemAdvBannerBinding

class AdvBannerAdapter(val context: Context, val dataset: MutableList<HomeAdvBannerModel>) :
    RecyclerView.Adapter<AdvBannerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAdvBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: HomeAdvBannerModel) {
            binding.model = datamodel
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAdvBannerBinding.inflate(
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