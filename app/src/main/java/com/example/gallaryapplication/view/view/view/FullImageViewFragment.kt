package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentFullImageViewBinding
import kotlinx.android.synthetic.main.fragment_full_image_view.*

class FullImageViewFragment : BaseFragment() {

    private var _binding: FragmentFullImageViewBinding? = null
    private val binding get() = _binding!!

    //    var image: String? = null
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
        var isVisible: Boolean = false
        arguments?.let { it ->
//            image = FullImageViewFragmentArgs.fromBundle(it).imageUrl
            var arrayList = FullImageViewFragmentArgs.fromBundle(it).imageArray
            var indexpostion = FullImageViewFragmentArgs.fromBundle(it).indexposition

//            context?.let {
//                userImage.loadImage(arrayList[indexpostion] , getProgressDrawable(it))
//            }
            showImage(arrayList[indexpostion])
            binding.showimagelayout.setOnClickListener {
                if (isVisible) {
                    binding.imageprev.visibility = View.VISIBLE
                    binding.imagenext.visibility = View.VISIBLE
                } else {
                    binding.imageprev.visibility = View.INVISIBLE
                    binding.imagenext.visibility = View.INVISIBLE
                }
                isVisible = !isVisible
            }

            binding.imageprev.setOnClickListener {
                if (indexpostion > 0) {
                    indexpostion--
                    showImage(arrayList[indexpostion])

                } else {
                    indexpostion = arrayList.size - 1
                    showImage(arrayList[indexpostion])
                }
            }//end of imageprev

            binding.imagenext.setOnClickListener {
                if (indexpostion < (arrayList.size - 1)) {
                    indexpostion++
                    showImage(arrayList[indexpostion])
                } else {
                    indexpostion = 0
                    showImage(arrayList[indexpostion])
                }
            }


        }//end of argumnets

    }//end of onViewCreatedView

}