package com.cmt.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmt.services.model.AgricatCategoryModel
import com.cmt.view.fragment.CourseDescriptionFragment
import com.cmt.view.fragment.MockTestFragment
import com.cmt.view.fragment.SampleVideosFragment

class CourseSpecializationAdapter(fragmentActivity: FragmentActivity, val page: MutableList<String>,var subjModel: AgricatCategoryModel
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return page.size
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return CourseDescriptionFragment(subjModel)
        } else if (position == 1) {
            return SampleVideosFragment(subjModel)
        } else {
            return MockTestFragment(subjModel)
        }
    }
}