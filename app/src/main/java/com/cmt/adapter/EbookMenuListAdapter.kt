package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.services.model.Courses
import com.cmt.view.activity.FullPlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsTotalListEbookBinding

class EbookMenuListAdapter(
    val context: Context,
    val dataset: MutableList<Courses>,
    val courseType: String
) :
    RecyclerView.Adapter<EbookMenuListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsTotalListEbookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(dataModel: Courses) {
            binding.model = dataModel
            binding.itemLayout.setOnClickListener {
                val intent = Intent(context, FullPlainActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.SubCourse)
                intent.putExtra(IConstants.IntentStrings.courseType, IConstants.IntentStrings.ebook)
                intent.putExtra(IConstants.IntentStrings.catType, "Notes")
                intent.putExtra(IConstants.IntentStrings.payload, dataModel)
                intent.putExtra(IConstants.IntentStrings.courseType, courseType)
                context.startActivity(intent)
                (context as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsTotalListEbookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}