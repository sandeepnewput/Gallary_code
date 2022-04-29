package com.example.gallaryapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.gallaryapplication.databinding.FragmentFullImageViewBinding
import com.example.gallaryapplication.util.getProgressDrawable
import com.example.gallaryapplication.util.loadUri


class FullImageViewFragment : BaseFragment<FragmentFullImageViewBinding>() {

    private val sharedViewModel: MediaSharedViewModel by activityViewModels()



    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentFullImageViewBinding? {
        return FragmentFullImageViewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.showImageLayout.setOnClickListener {
            sharedViewModel.toggleControlButton()
        }

        binding.imagePrev.setOnClickListener {
            sharedViewModel.onPreviousImageClick()
        }//end of imageprev

        binding.imageNext.setOnClickListener {
            sharedViewModel.onNextImageClick()
        }

        sharedViewModel.currentUri.observe(viewLifecycleOwner) { imageUri ->
            updateGalleryImage(imageUri)
        }

        sharedViewModel.showControlButton.observe(viewLifecycleOwner) { isVisible ->
            binding.imagePrev.isVisible = isVisible
            binding.imageNext.isVisible = isVisible
        }

    }//end of onViewCreatedView

    private fun updateGalleryImage(url: String?) {
        context?.let {
            binding.gallaryImage.loadUri(url, getProgressDrawable(it))
        }
    }
}