package com.cmt.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cmt.services.model.ReviewAnswersModel
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ReviewAnswersItemlistBinding

class ReviewAnswersAdapter(var context: Context, val dataSet: MutableList<ReviewAnswersModel>) :
    RecyclerView.Adapter<ReviewAnswersAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ReviewAnswersItemlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun binder(dataModel: ReviewAnswersModel) {
            binding.model = dataModel
            binding.tvQuestionCount.text =
                context.getString(R.string.txt_questions) + (adapterPosition + 1).toString()

            binding.layoutSolution.isVisible =
                !(dataModel.solution.isNullOrEmpty() || dataModel.solution.equals("<br>"))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ReviewAnswersItemlistBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataSet[position])
        val model: ReviewAnswersModel = dataSet[position]
        if (model.answer_status.equals("Right")) {
            selectRightAnswer(holder.binding, model.answer)
        } else if (model.answer_status.equals("Wrong")) {
            wrongAnswered(holder.binding, model.answered)
            selectRightAnswer(holder.binding, model.answer)
        }
    }

    private fun wrongAnswered(
        binding: ReviewAnswersItemlistBinding,
        answered: String?,
    ) {
        when (answered) {
            "1" -> {
                binding.option1Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_wrong
                )
            }
            "2" -> {
                binding.option2Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_wrong
                )
            }
            "3" -> {
                binding.option3Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_wrong
                )

            }
            "4" -> {
                binding.option4Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_wrong
                )
            }
        }
    }

    private fun selectRightAnswer(
        binding: ReviewAnswersItemlistBinding,
        answer: String?,
    ) {
        when (answer) {
            "1" -> {
                binding.option1Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )

            }
            "2" -> {

                binding.option2Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )

            }
            "3" -> {
                binding.option3Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )

            }
            "4" -> {
                binding.option4Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )
            }
        }
    }


    override fun getItemCount(): Int {
        return dataSet.size
    }
}