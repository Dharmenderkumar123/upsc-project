package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

import com.cmt.helper.IConstants
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.services.model.SubjectsListModel
import com.cmt.view.activity.FullPlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsAgricetBinding

class AgricetCategoryAdapter(
    val context: Context,
    val dataset: MutableList<AgricatCategoryModel>,
    var image: String?=null,
    var description: String?=null,
    val modelSubjects: SubjectsListModel?=null
) :
    RecyclerView.Adapter<AgricetCategoryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsAgricetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: AgricatCategoryModel) {
            binding.model = datamodel

            binding.pdfView.visibility = View.GONE
            binding.iconsLayout.visibility = View.VISIBLE


            binding.layout.setOnClickListener {
                val intent = Intent(context, FullPlainActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.CourseDescription)
                intent.putExtra(IConstants.IntentStrings.model, datamodel)
                intent.putExtra(IConstants.IntentStrings.image,datamodel.image)
                context.startActivity(intent)
                (context as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsAgricetBinding.inflate(
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