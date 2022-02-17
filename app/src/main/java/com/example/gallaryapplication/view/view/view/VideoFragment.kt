package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentVideoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    private val viewModel: VideoViewModel by viewModels()

    private val listAdapter = VideoListAdapter(arrayListOf())



    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVideoBinding? {
        return FragmentVideoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationVideo.setOnItemSelectedListener {
            if(it.itemId == R.id.Photos ) {
                    findNavController().navigate(VideoFragmentDirections.actionvideotophotofragment())
            }
            true
        }
        binding.videolist.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        viewModel.getAllUserVideo()

        viewModel.userVideo.observe(viewLifecycleOwner, Observer<List<String>> { contacts ->
            contacts?.let {
                listAdapter.updateVideoList(it)
            }
        })


    }//end of onViewCreated method



}