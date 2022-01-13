package com.example.gallaryapplication.view.view.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentPhotoBinding
import com.example.gallaryapplication.view.view.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photo.*


private const val TAG = "PhotoFragment"
private const val REQUEST_CODE_FETCH_IMAGE = 1

@AndroidEntryPoint
class PhotoFragment : BaseFragment() {


    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    private var permissionGranted = false


    private val imageViewModel: ImageViewModel by viewModels()
    private val listAdapter = ImageListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("step1", "onCreate running")
        super.onCreate(savedInstanceState)

        //take permission from user
        val hasReadImagePermission = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        Log.d(TAG, "onCreate : checkselfpermission return $hasReadImagePermission")

        if (hasReadImagePermission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCeate permission granted")
            permissionGranted = true
        } else {
            Log.d(TAG, "onCreate requesting permission")
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_FETCH_IMAGE
            )
        }


    }//end of onCreate Method

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("step2", "onCreateView running")
        // Inflate the layout for this fragment
        onBackpressed()
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }//end of onCreateView Method

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//         var resolver = requireActivity().contentResolver
        super.onViewCreated(view, savedInstanceState)
        Log.d("step3", "onViewCreated running")
        binding.bottomnavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Videos -> {
                    findNavController().navigate(PhotoFragmentDirections.actionphototovideofragment())
                }
            }
            true
        }//end of setOnItemSelectedListener

        if (permissionGranted) {
            imageViewModel.getImage()
        } else {
            Toast.makeText(context, "This app require permission", Toast.LENGTH_LONG).show()
        }

        imageViewModel.userImage.observe(viewLifecycleOwner, Observer<List<String>> { contacts ->
            Log.d("list of images", "$contacts")
            contacts?.let {
                listAdapter.updateImageList(it)
            }

            Log.d(TAG, "list of contact is $contacts")
            Log.d(TAG, "butoon onclick ends")
        })
        imageViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE

        })

        binding.imageList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

    }//end of onViewCreatedView Method

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_FETCH_IMAGE -> {
                // If request is cancelled, the result arrays are empty.
                permissionGranted =
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //permssion was granted
                        true
                    } else {
                        //pemission denied
                        Log.d(TAG, "onRequestPermissionsResult : permsssin denied")
                        false
                    }
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


}//end of PhotoFragment