package com.example.gallaryapplication.view.view.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.viewmodel.ImageVideoViewModel
import kotlinx.android.synthetic.main.fragment_photo.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "PhotoFragment"
private const val REQUEST_CODE_FETCH_IMAGE = 1
class PhotoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var permissionGranted = false


    private val viewModel by lazy { ViewModelProvider(this).get(ImageVideoViewModel::class.java) }
    private   val listAdapter  = ImageListAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("step1","onCreate running")
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

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
        Log.d("step2","onCreateView running")
        // Inflate the layout for this fragment
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.videoFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }//end of onCreateView Method

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//         var resolver = requireActivity().contentResolver
        super.onViewCreated(view, savedInstanceState)
        Log.d("step3","onViewCreated running")
        bottomnavigation.setOnItemSelectedListener{
            when(it.itemId){
                R.id.Videos ->{
                    val action = PhotoFragmentDirections.actionphototovideofragment()
                    findNavController().navigate(action)
                }


            }
            true
        }//end of setOnItemSelectedListener

        if(permissionGranted){
            viewModel.getImage()
        }

        viewModel.userImage.observe(viewLifecycleOwner, Observer<List<String>> {contacts ->
            Log.d("list of images","$contacts")

//            val listAdapter  = ImageListAdapter(contacts as ArrayList<String>)
//            imageList.apply {
//                layoutManager = GridLayoutManager(context,2)
//                adapter = listAdapter
//            }
            contacts?.let {
                listAdapter.updateImageList(it)
            }

            Log.d(TAG, "list of contact is $contacts")
            Log.d(TAG, "butoon onclick ends")
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {isLoading->
            loadingView.visibility = if(isLoading) View.VISIBLE else View.GONE

        })


        imageList.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = listAdapter
        }

    }//end of onViewCreatedView Method







    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }//end of companion object
}//end of PhotoFragment