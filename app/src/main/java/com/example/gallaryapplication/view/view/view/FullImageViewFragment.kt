package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.util.getProgressDrawable
import com.example.gallaryapplication.view.view.util.loadImage
import kotlinx.android.synthetic.main.fragment_full_image_view.*
import kotlinx.android.synthetic.main.fragment_play_video.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FullImageViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FullImageViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

//    var image: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_image_view, container, false)
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
            showimagelayout.setOnClickListener {
                if (isVisible) {
                    imageprev.visibility = View.VISIBLE
                    imagenext.visibility = View.VISIBLE
                } else {
                    imageprev.visibility = View.INVISIBLE
                    imagenext.visibility = View.INVISIBLE
                }
                isVisible = !isVisible
            }

            imageprev.setOnClickListener {
                if (indexpostion > 0) {
                    indexpostion--
                    showImage(arrayList[indexpostion])

                } else {
                    indexpostion = arrayList.size - 1
                    showImage(arrayList[indexpostion])
                }
            }//end of imageprev

            imagenext.setOnClickListener {
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

    fun showImage(url:String?){
        context?.let {
            userImage.loadImage(url , getProgressDrawable(it))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FullImageViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FullImageViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}