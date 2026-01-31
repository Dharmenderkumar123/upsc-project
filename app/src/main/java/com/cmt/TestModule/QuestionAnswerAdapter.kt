package com.cmt.TestModule

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView

import com.cmt.services.model.TestOptionsModel
import com.the_pride_ias.R
import com.the_pride_ias.databinding.LayoutMockAnswerListBinding

class QuestionAnswerAdapter(
    private val context: Context,
    private val dataSet: MutableList<TestOptionsModel>?
) :
    RecyclerView.Adapter<QuestionAnswerAdapter.ViewHolder>() {
    inner class ViewHolder constructor(val binder: LayoutMockAnswerListBinding) :
        RecyclerView.ViewHolder(binder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutMockAnswerListBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = dataSet?.get(position)
        holder.setIsRecyclable(false)

        if (model?.selectedOption?.isNotEmpty() == true) {
            checkSelectedAnswer(holder.binder, model.selectedOption, model)
        }

        holder.binder.tvQuestionCount.text = "Question ${position + 1}"
        holder.binder.tvQuestion.text =
            HtmlCompat.fromHtml(model?.question.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)

        holder.binder.option1.text =
            HtmlCompat.fromHtml(model?.option1.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
        holder.binder.option2.text =
            HtmlCompat.fromHtml(model?.option2.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
        holder.binder.option3.text =
            HtmlCompat.fromHtml(model?.option3.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
        holder.binder.option4.text =
            HtmlCompat.fromHtml(model?.option4.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
        holder.binder.tvSolution.text=model?.solution


        if (dataSet?.size == (position + 1)) {
            holder.binder.nxt.text = context.getString(R.string.btn_submit)
        } else {
            holder.binder.btnNxt.text = context.getString(R.string.txt_save_next)
        }

        /**
         * Next and Previous question action events
         */
        holder.binder.btnNxt.setOnClickListener {
            mCallBack?.selectionAnswer(position, dataSet!!)
            if (dataSet?.size?.equals(1) == true) {
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
            mCallBack?.selectionAnswer(position, dataSet!!)
            if (holder.binder.nxt.text == context.getString(R.string.txt_save_next)) {
                mCallBack?.nextQuestions()
            } else {
                mCallBack?.submitAnswers()
            }
        }

        holder.binder.previous.setOnClickListener {
            mCallBack?.selectionAnswer(position, dataSet!!)
            mCallBack?.previousQuestion()
        }

        when {
            position == 0 -> {
                holder.binder.nextLayout.visibility = View.GONE
                holder.binder.btnNxt.visibility = View.VISIBLE
            }
            position != 0 -> {
                holder.binder.nextLayout.visibility = View.VISIBLE
                holder.binder.btnNxt.visibility = View.GONE
            }
        }

        /**
         * Options click events
         */

        holder.binder.option1.setOnClickListener {
            model?.isSelected = true
            model?.selectedOption = "option1"
            model?.selectedAns="1"
            changeSelectedView(holder.binder, "option1", model!!)
            holder.binder.tvSolution.visibility= View.VISIBLE
        }
        holder.binder.option2.setOnClickListener {
            model?.isSelected = true
            model?.selectedOption = "option2"
            model?.selectedAns="2"
            changeSelectedView(holder.binder, "option2", model!!)
            holder.binder.tvSolution.visibility= View.VISIBLE

        }
        holder.binder.option3.setOnClickListener {
            model?.isSelected = true
            model?.selectedOption = "option3"
            model?.selectedAns="3"
            changeSelectedView(holder.binder, "option3", model!!)
            holder.binder.tvSolution.visibility= View.VISIBLE

        }
        holder.binder.option4.setOnClickListener {
            model?.isSelected = true
            model?.selectedOption = "option4"
            model?.selectedAns="4"
            changeSelectedView(holder.binder, "option4", model!!)
            holder.binder.tvSolution.visibility= View.VISIBLE
        }

    }

    private fun checkSelectedAnswer(binder: LayoutMockAnswerListBinding, answer: String?, model: TestOptionsModel) {
        when (answer) {
            "option1" -> {
                if (model.answer=="1"){
                    binder.option1Layout.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_option
                    )
                }else{
                    binder.option1Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_selected_wrong)
                }
               /* binder.option1Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )*/
            }
            "option2" -> {
                if (model.answer=="2"){
                    binder.option2Layout.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_option
                    )
                }else{
                    binder.option2Layout.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_wrong
                    )
                }
               /* binder.option2Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )*/
            }
            "option3" -> {
                if (model.answer=="3"){
                    binder.option3Layout.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_option
                    )
                }else{
                    binder.option3Layout.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_wrong
                    )
                }
                /*binder.option3Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )*/
            }
            "option4" -> {
                if (model.answer=="4"){
                    binder.option4Layout.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_option
                    )
                }else{
                    binder.option4Layout.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_selected_wrong
                    )
                }
               /* binder.option4Layout.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.bg_selected_option
                )*/
            }
        }
        checkCorrectAnswer(binder, model.answer)
    }

    private fun checkCorrectAnswer(binder: LayoutMockAnswerListBinding, answer: String?) {
        when (answer) {
            "1" -> {
                binder.option1Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_selected_option)
            }
            "2" -> {
                binder.option2Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_selected_option)
            }
            "3" -> {
                binder.option3Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_selected_option)
            }
            "4" -> {
                binder.option4Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_selected_option)
            }
        }

    }

    interface OnSelectedAnswerListener {
        fun selectionAnswer(position: Int, model: MutableList<TestOptionsModel>)
        fun nextQuestions()
        fun previousQuestion()
        fun submitAnswers()
    }
    var mCallBack: OnSelectedAnswerListener? = null
    /**
     * Selection option highlighter
     */
    private fun changeSelectedView(binder: LayoutMockAnswerListBinding, answerType: String, model: TestOptionsModel) {
        when (answerType) {
            "option1" -> {
                binder.option1Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_selected_wrong)
                binder.option2Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
                binder.option3Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
                binder.option4Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
            }
            "option2" -> {
                binder.option2Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_selected_wrong)
                binder.option1Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
                binder.option3Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
                binder.option4Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
            }
            "option3" -> {
                binder.option3Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_selected_wrong)
                binder.option1Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
                binder.option2Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
                binder.option4Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
            }
            "option4" -> {
                binder.option4Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_selected_wrong)
                binder.option1Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
                binder.option2Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
                binder.option3Layout.background = ContextCompat.getDrawable(context, R.drawable.bg_edit_10dp)
            }
        }
        checkCorrectAnswer(binder, model.answer)
    }


    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }


}