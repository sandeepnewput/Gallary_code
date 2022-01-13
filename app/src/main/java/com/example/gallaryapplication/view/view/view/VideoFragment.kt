package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentVideoBinding
import com.example.gallaryapplication.view.view.viewmodel.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_video.*

@AndroidEntryPoint
class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    private val videoViewModel: VideoViewModel by viewModels()

    private val listAdapter = VideoListAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomnavigationvideo.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Photos -> {
                    findNavController().navigate(VideoFragmentDirections.actionvideotophotofragment())
                }


            }
            true
        }

        videoViewModel.getVideo()

        videoViewModel.userVideo.observe(viewLifecycleOwner, Observer<List<String>> { contacts ->
            Log.d("videolist", "$contacts")
            contacts?.let {
                listAdapter.updateVideoList(it)
            }
            Log.d("list", "list of contact is $contacts")

        })
        binding.videolist.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

    }//end of onViewCreated method

}