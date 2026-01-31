package com.cmt.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.cmt.helper.AppPreferences
import com.cmt.helper.IConstants
import com.cmt.helper.setSnackBar
import com.cmt.model.ProfileMenuModel
import com.cmt.model.Student
import com.cmt.view.activity.FullPlainActivity
import com.cmt.view.activity.PlainActivity
import com.cmt.view.dialog.TestSubmitConfirmationDialog
import com.cmt.view.fragment.ProfileFragment
import com.cmt.view.fragment.StudentBottomSheet
import com.the_pride_ias.R
import com.the_pride_ias.databinding.ItemsProfileMenuBinding

class ProfileMenuItemsAdapter(val context: Context, val dataset: MutableList<ProfileMenuModel>,private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ProfileMenuItemsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemsProfileMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binder(datamodel: ProfileMenuModel) {
            binding.model = datamodel

            binding.menuItemLayout.setOnClickListener {
                if (datamodel.type == IConstants.ProfileType.Results) {
//                    val bottomSheet = StudentBottomSheet()
//                    bottomSheet.show(fragmentManager, "StudentBottomSheetTag")
                } else if (datamodel.type != IConstants.ProfileType.Logout) {
                    val intent = Intent(context, PlainActivity::class.java)
                    intent.putExtra(IConstants.IntentStrings.type, datamodel.type)
                    intent.putExtra(IConstants.IntentStrings.payload, datamodel.name)
                    context.startActivity(intent)
                    (context as FragmentActivity).overridePendingTransition(
                        R.anim.enter,
                        R.anim.exit
                    )
                } else {
                    val dialog = TestSubmitConfirmationDialog(IConstants.Defaults.logout)
                    dialog.show((context as FragmentActivity).supportFragmentManager, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemsProfileMenuBinding.inflate(
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

    private fun getDummyStudents(): List<Student> {
        return listOf(
            Student(1, "Aman Sharma", 98, 1),
            Student(2, "Priya Patel", 95, 2),
            Student(3, "John Doe", 89, 3),
            Student(4, "Sneha Gupta", 85, 4),
            Student(5, "Rahul Verma", 82, 5),
            Student(6, "Jessica Smith", 78, 6),
            Student(7, "Vikram Singh", 75, 7),
            Student(8, "Anjali Rae", 70, 8),
            Student(9, "Kevin Hart", 65, 9),
            Student(10, "Pooja Hegde", 60, 10)
        )
    }
}