package com.cmt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.IConstants
import com.cmt.model.CourseDataModel
import com.cmt.services.model.Courses
import com.cmt.view.activity.FullPlainActivity
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsCategoryListBinding

class CategoryListAdapter(val context: Context, private val dataSet: MutableList<Courses>) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsCategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(dataModel: Courses) {
            binding.model = dataModel

            binding.cardView.setOnClickListener {
                val intent = Intent(context, FullPlainActivity::class.java)
                intent.putExtra(IConstants.IntentStrings.type, IConstants.FragmentType.SubCourse)
                intent.putExtra(IConstants.IntentStrings.courseType, IConstants.IntentStrings.course)
                intent.putExtra(IConstants.IntentStrings.catType,"Subjects")
                intent.putExtra(IConstants.IntentStrings.payload, dataModel)
                context.startActivity(intent)
                (context as FragmentActivity).overridePendingTransition(R.anim.enter, R.anim.exit)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsCategoryListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataSet[position])
//        holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(context, getColor()))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    private var c = 0
    private fun getColor(): Int {
        val colorList2 = intArrayOf(
            R.color.colorPrimary,
            R.color.colorCat1,
            R.color.colorCat2,
            R.color.colorCat3,
            R.color.colorCat4,
            R.color.colorCat5,
        )
        if (c >= 6) {
            c = 0
        }
        val color = colorList2[c]
        c++
        return color
    }
}