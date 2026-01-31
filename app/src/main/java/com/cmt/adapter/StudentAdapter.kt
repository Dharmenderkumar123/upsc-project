package com.cmt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmt.model.Student
import com.cmt.services.model.UserRank
import com.the_pride_ias.databinding.ItemStudentBinding

class StudentAdapter(var list: MutableList<UserRank>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.binding.apply {
            val model= list[position]
            tvSrNo.text = "#${position+1}"
            tvStudentName.text = model.name
            tvMarks.text = model.marks_obtained
            tvRank.text = "Rank ${model.rank}"
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }


    class StudentViewHolder(val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root)


}