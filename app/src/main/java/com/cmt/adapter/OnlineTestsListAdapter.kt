package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.model.OnlineTestModel
import com.cmt.services.model.Courses
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsOnlineTestBinding

class OnlineTestsListAdapter(val context: Context, val dataset: MutableList<Courses>) :
    RecyclerView.Adapter<OnlineTestsListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsOnlineTestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: Courses) {
            binding.model = datamodel

            binding.testItemLayout.setOnClickListener {
                val intent = Intent(context, FullPlainActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.SubCourse)
                intent.putExtra(
                    IConstants.IntentStrings.courseType,
                    IConstants.IntentStrings.course
                )
                intent.putExtra(IConstants.IntentStrings.catType, "OnlineTests")
                intent.putExtra(IConstants.IntentStrings.payload, datamodel)
                context.startActivity(intent)
                (context as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsOnlineTestBinding.inflate(
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