package com.cmt.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.ICallback
import com.cmt.model.QuestionNumberModel
import com.cmt.services.model.TestOptionsModel
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsTestQuestionNumbersBinding

class TestQuestionNumberAdapter(
    val context: Context,
    val dataset: MutableList<TestOptionsModel>,
    val callBack: ICallback? = null
) :
    RecyclerView.Adapter<TestQuestionNumberAdapter.ViewHolder>() {
    var selectedPos: Int = -1

    inner class ViewHolder(val binding: ItemsTestQuestionNumbersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: TestOptionsModel) {
            binding.model = datamodel
            binding.questionCount = (adapterPosition + 1).toString()

            binding.root.setOnClickListener {
                selectedPos = adapterPosition
                callBack?.delegate(adapterPosition)
            }

            if (selectedPos == adapterPosition) {
                binding.tvCount.setBackgroundResource(R.drawable.bg_question_selected)
            } else {
                binding.tvCount.setBackgroundResource(R.drawable.bg_question_number)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsTestQuestionNumbersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    var currentPos: Int = -1
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePosition(position: Int) {
        currentPos = position
        selectedPos = position
        if (position != 0) {
            dataset[position].isSelectedQues = true
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}