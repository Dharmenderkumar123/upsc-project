package com.cmt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cmt.services.model.MockTestModel
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.dialog.TestAttemptsDialog
import com.cmt.view.dialog.TestInstructionsDailog
import com.the_pride_ias.databinding.ItemsMockTestBinding

class MockTestItemsAdapter(val context: Context, val dataset: MutableList<MockTestModel>) :
    RecyclerView.Adapter<MockTestItemsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsMockTestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: MockTestModel) {
            binding.model = datamodel

            binding.testBtn.setOnClickListener {
                /*if (datamodel.attempts == datamodel.attempted) {
                    val activity = context as? FullPlainActivity
                    val dailog = datamodel.test_id?.let { it1 -> TestAttemptsDialog() }
                    activity?.supportFragmentManager?.let { dailog?.show(it, null) }
                } else {
                    val activity = context as? FullPlainActivity
                    val dailog = datamodel.test_id?.let { it1 -> TestInstructionsDailog(it1, "") }
                    activity?.supportFragmentManager?.let { dailog?.show(it, null) }
                }*/
                if (datamodel.attempted!!.toInt() < datamodel.attempts!!.toInt()) {
                    val activity = context as? FullPlainActivity
                    val dailog = datamodel.test_id?.let { it1 -> TestInstructionsDailog(it1, "") }
                    activity?.supportFragmentManager?.let { dailog?.show(it, null) }
                } else {
                    val activity = context as? FullPlainActivity
                    val dailog = datamodel.test_id?.let { it1 -> TestAttemptsDialog() }
                    activity?.supportFragmentManager?.let { dailog?.show(it, null) }
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemsMockTestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binder(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}