package com.cmt.view.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.cmt.view.activity.PlainActivity
import com.cmt.viewModel.fragment.EditProfileVM
import com.github.dhaval2404.imagepicker.ImagePicker
import com.the_pride_ias.databinding.FragmentEditProfileBinding
import java.io.File

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false).apply {
            viewModel = ViewModelProvider(this@EditProfileFragment).get(EditProfileVM::class.java)
            viewModel?.binding = this
            lifecycleOwner = this@EditProfileFragment
        }
        return binding.root
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.data?.path?.let {
                        val file = File(it)
                        binding.viewModel?.setFile(requireActivity(), file)
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.getProfile(view)

        binding.imagePicker.setOnClickListener {
            val activity = requireActivity() as? PlainActivity
            activity?.let {
                if (it.checkPermissions(allow = true) == true) {
                    ImagePicker.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .createIntent { intent -> startForProfileImageResult.launch(intent) }
                } else {
                    //it.openPermissionWarning()
                }
            }
        }
    }


}