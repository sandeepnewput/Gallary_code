package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentFullImageViewBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage


class FullImageViewFragment : BaseFragment<FragmentFullImageViewBinding>() {


    private val viewModel: SharedViewModel by activityViewModels()



    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFullImageViewBinding? {

        return FragmentFullImageViewBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.showImageLayout.setOnClickListener {
            viewModel.toggleControlButton()
        }

        binding.imagePrev.setOnClickListener {
            viewModel.onPreviousImageClick()
        }//end of imageprev


        binding.imageNext.setOnClickListener {
            viewModel.onNextImageClick()
        }


        viewModel.currentUri.observe(viewLifecycleOwner) { imageUri ->
            updateGalleryImage(imageUri)
        }

        viewModel.showControlButton.observe(viewLifecycleOwner) { isVisible ->
            binding.imagePrev.isVisible = isVisible
            binding.imageNext.isVisible = isVisible
        }


    }//end of onViewCreatedView


    private fun updateGalleryImage(url: String?) {
        context?.let {
            binding.gallaryImage.loadImage(url, getProgressDrawable(it))
        }
    }


}