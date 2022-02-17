package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.gallaryapplication.databinding.FragmentFullImageViewBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage


class FullImageViewFragment : BaseFragment<FragmentFullImageViewBinding>() {

    private lateinit var imageList:List<String>
    private  var indexPosition:Int = 0

    private val viewModel by lazy {
        ViewModelProvider(this,FullImageViewModelFactory(imageList,indexPosition))
            .get(FullImageViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            imageList = FullImageViewFragmentArgs.fromBundle(it).imageArray.toList()
            indexPosition = FullImageViewFragmentArgs.fromBundle(it).indexposition

        }

    }//end of onCreate method

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


        viewModel.currentUri.observe(viewLifecycleOwner){ imageUri->
            updateGalleryImage(imageUri)
        }

        viewModel.showControlButton.observe(viewLifecycleOwner){ isVisible->
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