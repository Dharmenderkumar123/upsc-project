package com.cmt.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmt.adapter.CurrentAffairsViewListAdapter
import com.cmt.services.model.CurrentAffairsViewModel
import com.cmt.viewModel.fragment.CurrentAffairsViewVM
import com.the_pride_ias.databinding.FragmentCurrentAffairsViewBinding


class CurrentAffairsViewFragment(val id: String? = null, val name: String? = null) : Fragment() {
    lateinit var binding: FragmentCurrentAffairsViewBinding
    var pageListCont: Int = 1
    var dataList: MutableList<CurrentAffairsViewModel> = mutableListOf()
    var currentViewAdapter: CurrentAffairsViewListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentAffairsViewBinding.inflate(inflater, container, false).apply {
            viewModel =
                ViewModelProvider(this@CurrentAffairsViewFragment).get(CurrentAffairsViewVM::class.java)
            lifecycleOwner = this@CurrentAffairsViewFragment
        }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCategory.text = name
        setData()
        binding.viewModel?.currentAffairsList?.observe(requireActivity()) {
            if (it != null) {
                dataList.clear()
                totalPagesFound = it.total_pages ?: 0
                val listData =
                    (it.results as MutableList<*>).filterIsInstance<CurrentAffairsViewModel>()
                        .toMutableList()
                dataList.addAll(listData)
                if (pageListCont == 1) {
                    currentViewAdapter?.notifyDataSetChanged()
                } else {
                    val endPosition: Int = dataList.size
                    val additionalItemSize: Int = listData.size
                    val startPosition = endPosition - additionalItemSize
                    currentViewAdapter?.notifyDataSetChanged()
                }
                binding.tvError.isVisible = false
            } else {

                binding.tvError.isVisible = true
                dataList.clear()
                currentViewAdapter?.notifyDataSetChanged()
            }
        }

    }

    private fun setData() {
        currentViewAdapter = CurrentAffairsViewListAdapter(requireActivity(), dataList)
        binding.recyclerView.apply {
            adapter = currentViewAdapter
            addOnScrollListener(listScrollListener)
        }
    }

    override fun onResume() {
        dataList.clear()
        binding.viewModel?.setData(requireActivity(), id, pageListCont.toString())
        super.onResume()
    }

    //Pagination
    var isLoading = false
    var isScrolling = false
    var isLast = false
    var totalPagesFound = 0


    private val listScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager =
                binding.recyclerView.layoutManager as LinearLayoutManager
            val firstViewItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLast
            val isAtLastItem = firstViewItemPosition + visibleItemCount >= totalItemCount
            val isNotBeginning = firstViewItemPosition >= 0
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotBeginning && isScrolling

            if (shouldPaginate) {
                if (pageListCont < totalPagesFound) {
                    pageListCont += 1
                    binding.viewModel?.setData(
                        requireActivity(), id,
                        pageListCont.toString()
                    )
                }
                isLoading = false
                isScrolling = false
                isLast = false
            }
        }
    }

}