package com.example.gallaryapplication.view.view.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentPhotoBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*


@AndroidEntryPoint
class PhotoFragment : BaseFragment<FragmentPhotoBinding>() {

    private val listAdapter = PhotoListAdapter(this::onClickMedia)

    private val viewModel: SharedViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPhotoBinding? {
        return FragmentPhotoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickRequestPermission(binding.photoFragment)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
            scrollToPosition(viewModel.currentImageIndexPosition)
        }

        viewModel.userImages.observe(viewLifecycleOwner) {
            it?.let {
                listAdapter.submitList(it)
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }//end of onViewCreatedView Method

    private fun getMediaFiles() {
        viewModel.getAllUserImage()
        viewModel.getAllUserVideo()
        viewModel.getAllUserMusic()
    }

    private fun onClickRequestPermission(view: View) {
        when {
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } == PackageManager.PERMISSION_GRANTED -> {
                //put code for Snackbar
                getMediaFiles()
            }
            activity?.let {

                ActivityCompat.shouldShowRequestPermissionRationale(
                    it,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } == true -> {
                Snackbar.make(
                    view,
                    getString(R.string.permission_required),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(
                    getString(R.string.ok)
                ) {
                    requestPermissionLauncher.launch(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                }.show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }//end of function

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMediaFiles()
            }
        }

    private fun onClickMedia(uri: String) {
        viewModel.setCurrentImageUri(uri)
        findNavController().navigate(R.id.action_global_fullImageFragmentView)
    }

    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }

}//end of PhotoFragment