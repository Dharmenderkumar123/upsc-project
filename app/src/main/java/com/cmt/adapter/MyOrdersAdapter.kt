package com.cmt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.services.model.MyOrdersModel
import com.the_pride_ias.databinding.ItemsMyOrderBinding

class MyOrdersAdapter(val context: Context, val dataset: MutableList<MyOrdersModel>) :
    RecyclerView.Adapter<MyOrdersAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsMyOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: MyOrdersModel) {
            binding.model = datamodel

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsMyOrderBinding.inflate(
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