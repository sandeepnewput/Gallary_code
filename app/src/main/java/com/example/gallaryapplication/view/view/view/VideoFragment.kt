package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentVideoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>(),OnClickListener {

    private val viewModel: SharedViewModel by activityViewModels()

    private val listAdapter = VideoListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter.setListener(this)
    }//end of onCreate method

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

    override fun onClick(uri: String, position: Int) {
       viewModel.setCurrentUriIndexPosition(uri,position)
        findNavController().navigate(VideoFragmentDirections.actionvideoFragmenttoPlayvideoFragment())
    }


}