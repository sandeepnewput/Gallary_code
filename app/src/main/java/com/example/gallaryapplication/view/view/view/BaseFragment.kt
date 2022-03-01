package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.gallaryapplication.R
import kotlinx.android.synthetic.main.activity_main.*


abstract class BaseFragment<viewBinding : ViewBinding> : Fragment() {


    private var _binding: viewBinding? = null
    val binding get() = _binding!!


    abstract fun backPressed()
    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): viewBinding?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = inflateViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}//end of base fragment