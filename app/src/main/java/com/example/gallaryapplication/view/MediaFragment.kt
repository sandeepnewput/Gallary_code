package com.example.gallaryapplication.view


import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentMediaBinding
import com.example.gallaryapplication.model.MediaModel
import com.example.gallaryapplication.model.MediaType
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MediaFragment : BaseFragment<FragmentMediaBinding>() {

    private val listAdapter = MediaListAdapter(this::onClickMedia)

    private val sharedViewModel: MediaSharedViewModel by activityViewModels()

    private val viewModel: MediaViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentMediaBinding? {
        return FragmentMediaBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            PermissionLauncher.requestPermission(
                this,
                it,
                listener
            ).launch(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
            if (arguments?.getString(MEDIA_TYPE) == MediaType.IMAGE.name) {
                scrollToPosition(sharedViewModel.currentImageIndexPosition)
            } else {
                scrollToPosition(sharedViewModel.currentVideoIndexPosition)
            }
        }

        viewModel.userImages.observe(viewLifecycleOwner){
            sharedViewModel.updateMediaList(MediaType.IMAGE,it)
        }
        viewModel.userVideos.observe(viewLifecycleOwner){
            sharedViewModel.updateMediaList(MediaType.VIDEO,it)
        }

        if (arguments?.getString(MEDIA_TYPE) == MediaType.IMAGE.name) {
            sharedViewModel.userImages.observe(viewLifecycleOwner) {
                it?.let {
                    listAdapter.submitList(it)
                }
            }
        } else {
            sharedViewModel.userVideos.observe(viewLifecycleOwner) {
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
            sharedViewModel.setCurrentImageUri(mediaModel)
            findNavController().navigate(R.id.action_global_fullImageFragmentView)
        } else {
            sharedViewModel.setCurrentVideoUri(mediaModel)
            findNavController().navigate(R.id.action_global_playVideoFragmentView)
        }

    }

    private val listener = object : Listener {
        override fun permissionAllowed() {
            arguments?.getString(MEDIA_TYPE)?.let { viewModel.getMediaFiles(it) }
        }

        override fun permissionDenied() {
            TODO("Not yet implemented")
        }

        override fun showRationalForPermission(rational: Boolean) {
         if(rational){
             TODO("Not yet implemented")
         }else{
             activity?.let {
                 val builder = AlertDialog.Builder(it)
                 builder.apply {
                     setTitle(getString(R.string.permission))
                     setMessage(
                         getString(R.string.permission_required)
                     )
                     setPositiveButton(R.string.setting,
                         DialogInterface.OnClickListener { dialog, id ->
                             Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                 addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                 data = Uri.fromParts(
                                     "package",
                                     "com.example.gallaryapplication",
                                     "MediaFragment"
                                 )
                                 ContextCompat.startActivity(context, this, null)
                             }
                         })
                     setNegativeButton(R.string.cancel,
                         DialogInterface.OnClickListener { dialog, id ->
                             dialog.dismiss()
                         })
                     show()
                 }
                 builder.create()
             }//end of dialog box
         }

        }
    }


    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }


}//end of mediafragment