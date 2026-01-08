package com.cmt.TestModule

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.cmt.services.model.OptionsModel
import com.cmt.services.model.TestOptionsModel
import com.the_pride_ias.R
import com.the_pride_ias.databinding.QuestionAnswerItemViewBinding

class PurchasedQuestionAdapter(
    private val context: Context,
    private val dataSet: MutableList<OptionsModel>
) :
    RecyclerView.Adapter<PurchasedQuestionAdapter.ViewHolder>() {
    inner class ViewHolder constructor(val binder: QuestionAnswerItemViewBinding) :
        RecyclerView.ViewHolder(binder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            QuestionAnswerItemViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = dataSet[position]
        holder.setIsRecyclable(false)
        holder.binder.tvQuestionCount.text = "Question ${position + 1}"
        val quest: String = model.question.toString()

        if (model.selectedOption.isNotEmpty()) {
            checkSelectedAnswer(holder.binder, model.selectedOption)
        }

        holder.binder.tvQuestion.text =
            HtmlCompat.fromHtml(quest, HtmlCompat.FROM_HTML_MODE_COMPACT)

        holder.binder.option1.text =
            HtmlCompat.fromHtml(model.option1.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
        holder.binder.option2.text =
            HtmlCompat.fromHtml(model.option2.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
        holder.binder.option3.text =
            HtmlCompat.fromHtml(model.option3.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
        holder.binder.option4.text =
            HtmlCompat.fromHtml(model.option4.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)

        /**
         * Next and Previous question action events
         */
        holder.binder.btnNxt.setOnClickListener {
            mCallBack?.selectionAnswer(position, dataSet)
            if (dataSet.size == 1) {
                mCallBack?.submitAnswers()
            } else {
                if (holder.binder.btnNxt.text != context.getString(R.string.btn_submit)) {
                    mCallBack?.nextQuestions()
                } else {
                    mCallBack?.submitAnswers()
                }
            }
        }

        holder.binder.nxt.setOnClickListener {
            mCallBack?.selectionAnswer(position, dataSet)
            if (dataSet.size == 1) {
                mCallBack?.submitAnswers()
            } else {
                if (holder.binder.nxt.text == context.getString(R.string.txt_save_next)) {
                    mCallBack?.nextQuestions()
                } else {
                    mCallBack?.submitAnswers()
                }
            }

        }

        if (model.isMarkReview)
            holder.binder.previous.text = "UnMark as review"
        else
            holder.binder.previous.text = "Mark as review"


        holder.binder.previous.setOnClickListener {
            if (model.isMarkReview) {
                holder.binder.previous.text = "Mark as review"
                model.isMarkReview = false
            } else {
                holder.binder.previous.text = "UnMark as review"
                model.isMarkReview = true
            }
            /*mCallBack?.selectionAnswer(position, dataSet)
            mCallBack?.previousQuestion()
            checkSelectedAnswer(holder.binder, model.selectedOption)*/
        }


        when {
            position == 0 -> {
                /* holder.binder.nextLayout.visibility = View.GONE
                 holder.binder.btnNxt.visibility = View.VISIBLE*/
                holder.binder.nextLayout.visibility = View.VISIBLE
                holder.binder.btnNxt.visibility = View.GONE
            }
            position != 0 -> {
                holder.binder.nextLayout.visibility = View.VISIBLE
                holder.binder.btnNxt.visibility = View.GONE
            }
        }

        if (dataSet.size == (position + 1)) {
            holder.binder.nxt.text = context.getString(R.string.btn_submit)
        } else {
            holder.binder.btnNxt.text = context.getString(R.string.txt_save_next)
        }


        /**
         * Options click events
         */
        holder.binder.option1.setOnClickListener {
            model.isSelected = true
            model.selectedOption = "option1"
            changeSelectedView(holder.binder, "option1", model)
        }
        holder.binder.option2.setOnClickListener {
            model.isSelected = true
            model.selectedOption = "option2"
            changeSelectedView(holder.binder, "option2", model)
        }
        holder.binder.option3.setOnClickListener {
            model.isSelected = true
            model.selectedOption = "option3"
            changeSelectedView(holder.binder, "option3", model)
        }
        holder.binder.option4.setOnClickListener {
            model.isSelected = true
            model.selectedOption = "option4"
            changeSelectedView(holder.binder, "option4", model)
        }

    }

    private fun checkSelectedAnswer(binder: QuestionAnswerItemViewBinding, answer: String?) {
        when (answer) {
            "option1" -> {
                binder.option1Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )
            }
            "option2" -> {
                binder.option2Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )
            }
            "option3" -> {
                binder.option3Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )
            }
            "option4" -> {
                binder.option4Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )
            }
        }

    }

    interface OnSelectedAnswerListener {
        fun selectionAnswer(position: Int, model: MutableList<OptionsModel>)
        fun nextQuestions()
        fun previousQuestion()
        fun submitAnswers()
    }

    var mCallBack: OnSelectedAnswerListener? = null

    /**
     * Selection option highlighter
     */
    private fun changeSelectedView(
        binder: QuestionAnswerItemViewBinding,
        answerType: String,
        model: OptionsModel
    ) {
        when (answerType) {
            "option1" -> {
                binder.option1Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )
                binder.option2Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
                binder.option3Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
                binder.option4Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
            }
            "option2" -> {
                binder.option2Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )
                binder.option1Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
                binder.option3Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
                binder.option4Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
            }
            "option3" -> {
                binder.option3Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )
                binder.option1Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
                binder.option2Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
                binder.option4Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
            }
            "option4" -> {
                binder.option4Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )
                binder.option1Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
                binder.option2Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )
                binder.option3Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_edit_10dp
                )

            }

        }
        //checkCorrectAnswer(binder, model.answer)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}