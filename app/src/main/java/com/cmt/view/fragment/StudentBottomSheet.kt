package com.cmt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmt.adapter.StudentAdapter
import com.cmt.model.Student
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.the_pride_ias.R

class StudentBottomSheet(private val studentList: List<Student>) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_student_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvStudentSheet)

        val studentAdapter = StudentAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = studentAdapter

        studentAdapter.submitList(studentList)
    }

    // Optional: Make it rounded at the top
    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme
}