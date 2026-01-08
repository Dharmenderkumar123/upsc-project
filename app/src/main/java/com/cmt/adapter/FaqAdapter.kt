package com.cmt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.services.model.FaqModel
import com.the_pride_ias.databinding.ItemsFaqBinding

class FaqAdapter(val context: Context, val dataset: MutableList<FaqModel>) :
    RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    private var lastSelectedPos = -1

    inner class ViewHolder(val binding: ItemsFaqBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: FaqModel) {
            binding.model = datamodel
            binding.root.setOnClickListener {

                val currentPos = adapterPosition
                if (currentPos == RecyclerView.NO_POSITION) return@setOnClickListener

                if (lastSelectedPos == currentPos) {
                    datamodel.isSelected = !datamodel.isSelected
                    notifyItemChanged(currentPos)
                    lastSelectedPos = if (datamodel.isSelected) currentPos else -1
                }
                else {
                    if (lastSelectedPos != -1) {
                        dataset[lastSelectedPos].isSelected = false
                        notifyItemChanged(lastSelectedPos)
                    }
                    datamodel.isSelected = true
                    notifyItemChanged(currentPos)
                    lastSelectedPos = currentPos
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsFaqBinding.inflate(
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