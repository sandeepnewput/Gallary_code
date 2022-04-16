package com.example.gallaryapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.databinding.FragmentFlickrImagesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FlickrImagesFragment : BaseFragment<FragmentFlickrImagesBinding>() {

    private val viewModel : FlickrSharedViewModel by activityViewModels()

    private val listAdapter = FlickrImageListAdapter()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFlickrImagesBinding? {
       return FragmentFlickrImagesBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPhotosFromFlicker()

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = listAdapter
        }

        viewModel.flickrImage.observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }

    }//end of onviewCreated method


    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }

}