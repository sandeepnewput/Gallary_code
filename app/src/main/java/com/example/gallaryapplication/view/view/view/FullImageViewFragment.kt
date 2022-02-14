package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.gallaryapplication.databinding.FragmentFullImageViewBinding
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage

class FullImageViewFragment : BaseFragment() {

    private var _binding: FragmentFullImageViewBinding? = null
    private val binding get() = _binding!!



    private lateinit var imageArray:Array<String>
    private var indexPosition:Int = 0



    private val fullImageViewModel by lazy {
        ViewModelProvider(this,FullImageViewModelFactory(imageArray,indexPosition))
            .get(FullImageViewModel::class.java) }
 //   private val fullImageViewModel: FullImageViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            imageArray = FullImageViewFragmentArgs.fromBundle(it).imageArray
            indexPosition = FullImageViewFragmentArgs.fromBundle(it).indexposition


            Log.d("imagearrayoncreate","$imageArray")
            Log.d("indexinoncreate","$indexPosition")

        }

    }//end of onCreate method



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFullImageViewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        showImage(imageArray[indexPosition])

        fullImageViewModel.currentIndexPosition.observe(viewLifecycleOwner){imageUri->
            Log.d("next","next index $imageUri ")
            showImage(imageUri)
        }

        fullImageViewModel.isVisibleInvisible.observe(viewLifecycleOwner){isVisible->
            if (isVisible) {
                    binding.imageprev.visibility = View.VISIBLE
                    binding.imagenext.visibility = View.VISIBLE
                } else {
                    binding.imageprev.visibility = View.INVISIBLE
                    binding.imagenext.visibility = View.INVISIBLE
                }
        }


            binding.showimagelayout.setOnClickListener {
                fullImageViewModel.visibleInvisible()
            }

            binding.imageprev.setOnClickListener {
                fullImageViewModel.previousImage()
            }//end of imageprev


            binding.imagenext.setOnClickListener {
                fullImageViewModel.nextImage()
            }

    }//end of onViewCreatedView


    fun showImage(url: String?) {
        context?.let {
            binding.userImage.loadImage(url, getProgressDrawable(it))
        }
    }


}