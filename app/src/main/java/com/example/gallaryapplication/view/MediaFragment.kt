package com.example.gallaryapplication.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentMediaBinding
import com.example.gallaryapplication.model.MediaModel
import com.example.gallaryapplication.model.MediaType
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MediaFragment : BaseFragment<FragmentMediaBinding>() {

    private val listAdapter = MediaListAdapter(this::onClickMedia)

    private val viewModel: MediaSharedViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMediaBinding? {
        return FragmentMediaBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { context ->
            activity?.let { activity ->
                GetPermission.requestPermission(
                    this,
                    context,
                    activity,
                    this::isPermissionGranted,
                    this::alertUserAboutFeature
                ).launch(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
            if (arguments?.getString(MEDIA_TYPE) == MediaType.IMAGE.name) {
                scrollToPosition(viewModel.currentImageIndexPosition)
            } else {
                scrollToPosition(viewModel.currentVideoIndexPosition)
            }
        }

        if (arguments?.getString(MEDIA_TYPE) == MediaType.IMAGE.name) {
            viewModel.userImages.observe(viewLifecycleOwner) {
                it?.let {
                    listAdapter.submitList(it)
                }
            }
        } else {
            viewModel.userVideos.observe(viewLifecycleOwner) {
                it?.let {
                    listAdapter.submitList(it)
                }
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }//end of onViewCreatedView Method

    private fun onClickMedia(mediaModel: MediaModel) {

        if (mediaModel.mediaType == MediaType.IMAGE) {
            viewModel.setCurrentImageUri(mediaModel)
            findNavController().navigate(R.id.action_global_fullImageFragmentView)
        } else {
            viewModel.setCurrentVideoUri(mediaModel)
            findNavController().navigate(R.id.action_global_playVideoFragmentView)
        }

    }

    private fun isPermissionGranted() {
        arguments?.getString(MEDIA_TYPE)?.let { viewModel.getMediaFiles(it) }
    }

    private fun alertUserAboutFeature() {
        Snackbar.make(
            binding.mediaFragment,
            "Media access is required Permission to display images and videos",
            Snackbar.LENGTH_SHORT
        ).setAction(
            "OK"
        ) {
            context?.let { context ->
                activity?.let { activity ->
                    GetPermission.requestPermission(
                        this,
                        context,
                        activity,
                        this::isPermissionGranted,
                        this::alertUserAboutFeature
                    ).launch(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }
            }
        }.show()
    }

    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }


}//end of mediafragment