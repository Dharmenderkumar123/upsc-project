package com.cmt.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.ICallback
import com.cmt.services.model.OptionsModel
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsPurchasedTestQuestionNumbersBinding

class PurchasedQuestionNumberAdapter(
    val context: Context,
    val dataset: MutableList<OptionsModel>,
    val callBack: ICallback? = null
) :
    RecyclerView.Adapter<PurchasedQuestionNumberAdapter.ViewHolder>() {

    private val selectedItem = -1

    inner class ViewHolder(val binding: ItemsPurchasedTestQuestionNumbersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun binder(datamodel: OptionsModel) {
            binding.model = datamodel
            binding.questionCount = (adapterPosition + 1).toString()

            selectedItem == adapterPosition
            binding.root.setOnClickListener {
                callBack?.delegate(adapterPosition)
                //binding.tvCount.setBackgroundResource(R.drawable.bg_selected_option)
            }

            if (datamodel.isSelected) {
                if (datamodel.isMarkReview){
                    binding.tvCount.setBackgroundResource(R.drawable.bg_mark_review)
                }else{
                    binding.tvCount.setBackgroundResource(R.drawable.bg_question_selected)
                }
            } else if (datamodel.isMarkReview) {
                binding.tvCount.setBackgroundResource(R.drawable.bg_mark_review)
            } else {
                binding.tvCount.setBackgroundResource(R.drawable.bg_question_number)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsPurchasedTestQuestionNumbersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    var currentPos: Int = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    fun updatePosition(position: Int) {
        currentPos = position
        if (position != 0) {
            dataset[position - 1].isSelectedQues = true
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return dataset.size
    }
}