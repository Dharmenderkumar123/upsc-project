package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.services.model.MyCourseModel
import com.cmt.services.model.SubjectsModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.PlainActivity
import com.cmt.view.dialog.TestInstructionsDailog
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsSubjectsListBinding

class SubjectItemsAdapter(
    val context: Context,
    val dataset: MutableList<SubjectsModel>,
    var model: MyCourseModel
) :
    RecyclerView.Adapter<SubjectItemsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsSubjectsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: SubjectsModel) {
            binding.model = datamodel

            if (datamodel.exam_id == "0") {
                binding.btnResult.isVisible = false
            } else {
                binding.btnResult.isVisible = true
            }

            binding.btnMaterials.setOnClickListener {
                val activity = context as? PlainActivity
                val intent = Intent(activity, PlainActivity::class.java)
                intent.putExtra(
                    IConstants.IntentStrings.type,
                    IConstants.FragmentType.PurchasedMaterials
                )
                intent.putExtra(IConstants.IntentStrings.subjectId, datamodel.subject_id)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
            }

            binding.btnResult.setOnClickListener {
                val activity = context as? PlainActivity
                val intent = Intent(activity, FullPlainActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.ScoreBoard)
                intent.putExtra(IConstants.IntentStrings.examId, datamodel.exam_id)
                intent.putExtra(IConstants.IntentStrings.result, "result")
                activity?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
            }

            binding.btnWriteTest.setOnClickListener {
                val activity = context as? PlainActivity
                val dailog = datamodel.subject_id?.let { it2 ->
                    model.course_id?.let { it1 ->
                        TestInstructionsDailog(
                            it1,
                            it2,
                            datamodel.duration
                        )
                    }
                }
                activity?.supportFragmentManager?.let { dailog?.show(it, null) }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsSubjectsListBinding.inflate(
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