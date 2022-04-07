package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentVideoBinding
import com.example.gallaryapplication.view.view.model.MediaModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    private val viewModel: SharedViewModel by activityViewModels()

    private val listAdapter = VideoListAdapter(this::onClickMedia)

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVideoBinding? {
        return FragmentVideoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
            scrollToPosition(viewModel.currentVideoIndexPosition)
        }

        viewModel.userVideos.observe(viewLifecycleOwner) {
            it?.let {
                listAdapter.submitList(it)
            }
        }
    }//end of onViewCreated method

    private fun onClickMedia(mediaModel: MediaModel) {
        viewModel.setCurrentVideoUri(mediaModel)
        findNavController().navigate(R.id.action_global_playVideoFragmentView)
    }

    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }
}