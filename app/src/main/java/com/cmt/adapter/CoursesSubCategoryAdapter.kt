package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.services.model.Courses
import com.cmt.services.model.SubCourseModel
import com.cmt.view.activity.PlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemSubcourseBinding

class CoursesSubCategoryAdapter(
    val context: Context,
    val dataset: MutableList<SubCourseModel>?,
    var type: String,
    val model: Courses,
    val subCategoryId: String?,
    val type1: Int
) :
    RecyclerView.Adapter<CoursesSubCategoryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemSubcourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: SubCourseModel?) {
            binding.model = datamodel
            binding.root.setOnClickListener {
//                if(!model.is_purchased){
//                 val intent = Intent(context, PlainActivity::class.java)
//                intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.BuyPlan)
//                intent.putExtra(IConstants.IntentStrings.payload, "Buy Plan")
//                context.startActivity(intent)
//                (context as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)
//                } else {
//                    if (type.equals(IConstants.IntentStrings.course)) {
//                        val intent = Intent(context, PlainActivity::class.java)
//                        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.AgricetCategory)
//                        intent.putExtra(IConstants.IntentStrings.payload, datamodel)
//                        context.startActivity(intent)
//                        (context as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)
//                    } else {
//                        val intent = Intent(context, PlainActivity::class.java)
//                        intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.EbookSubjects)
//                        intent.putExtra(IConstants.IntentStrings.payload, datamodel)
//                        context.startActivity(intent)
//                        (context as FragmentActivity).overridePendingTransition(
//                            R.anim.enter,
//                            R.anim.exit
//                        )
//                    }
//                }

                if (type.equals(IConstants.IntentStrings.course)) {
                    val intent = Intent(context, PlainActivity::class.java)
                    intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.AgricetCategory)
                    intent.putExtra(IConstants.IntentStrings.payload, datamodel)
                    context.startActivity(intent)
                    (context as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)
                } else {
                    val intent = Intent(context, PlainActivity::class.java)
                    intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.EbookSubjects)
                    intent.putExtra(IConstants.IntentStrings.payload, datamodel)
                    context.startActivity(intent)
                    (context as FragmentActivity).overridePendingTransition(
                        R.anim.enter,
                        R.anim.exit
                    )
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSubcourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset?.get(position))
    }

    override fun getItemCount(): Int {
        return dataset?.size ?: 0
    }
}