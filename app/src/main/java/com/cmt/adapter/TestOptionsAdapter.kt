package com.cmt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.services.model.TestOptionsModel
import com.the_pride_ias.databinding.ItemsTestOptionsBinding

class TestOptionsAdapter(val context: Context, val dataset: MutableList<TestOptionsModel>) :
    RecyclerView.Adapter<TestOptionsAdapter.ViewHolder>() {
    var currentPosition = -1

    inner class ViewHolder(val binding: ItemsTestOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: TestOptionsModel) {
            binding.model = datamodel

            binding.root.setOnClickListener {
                currentPosition = adapterPosition

                notifyDataSetChanged()

            }

            datamodel.isSelected = currentPosition == adapterPosition


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsTestOptionsBinding.inflate(
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