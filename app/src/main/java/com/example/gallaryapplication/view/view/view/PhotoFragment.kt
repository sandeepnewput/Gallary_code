package com.example.gallaryapplication.view.view.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentPhotoBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotoFragment : BaseFragment<FragmentPhotoBinding>() {


    private val listAdapter = PhotoListAdapter(arrayListOf())

    private val viewModel:SharedViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPhotoBinding? {
        return FragmentPhotoBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("photofragment","photfragment is created")
        onClickRequestPermission(binding.photoFragment)
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigation.setOnItemSelectedListener {
            if(it.itemId == R.id.Videos ) {
                    findNavController().navigate(PhotoFragmentDirections.actionphototovideofragment())
            }
            true
        }//end of setOnItemSelectedListener

        binding.imageList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }
        getImages()
        viewModel.userImage.observe(viewLifecycleOwner, Observer<List<String>> { contacts ->
            contacts?.let {
                listAdapter.updateImageList(it)
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE

        })



    }//end of onViewCreatedView Method

   private fun getImages() {
       viewModel.getUserImage()
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
                getImages()
            }
        }

}//end of PhotoFragment