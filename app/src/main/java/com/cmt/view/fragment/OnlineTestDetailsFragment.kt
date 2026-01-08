package com.cmt.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.the_pride_ias.databinding.FragmentOnlineTestDetailsBinding

class OnlineTestDetailsFragment : Fragment() {
   lateinit var binding : FragmentOnlineTestDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentOnlineTestDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}