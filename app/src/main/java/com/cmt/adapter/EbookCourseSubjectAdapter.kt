package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.services.model.EbookCourseSubjectModel
import com.cmt.view.activity.MainActivity
import com.cmt.view.activity.PdfActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsEbookCourseSubjectsBinding

class EbookCourseSubjectAdapter(
    val context: Context,
    val dataset: MutableList<EbookCourseSubjectModel>
) :
    RecyclerView.Adapter<EbookCourseSubjectAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsEbookCourseSubjectsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(dataModel: EbookCourseSubjectModel) {
            binding.model = dataModel

            binding.cardImage.setOnClickListener {
                val activity = context as? PlainActivity
                val intent = Intent(activity, PdfActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.payload, dataModel.pdf)
                intent.putExtra(IConstants.IntentStrings.cat_type, "1")
                intent.putExtra(IConstants.IntentStrings.sub_cat_id, dataModel.sub_cat_id)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(R.anim.enter, R.anim.exit)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemsEbookCourseSubjectsBinding.inflate(
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