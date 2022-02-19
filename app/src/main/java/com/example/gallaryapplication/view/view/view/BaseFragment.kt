package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.gallaryapplication.R


 abstract class BaseFragment<viewBinding : ViewBinding> : Fragment() {


     private var _binding: viewBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateViewBinding(inflater, container)
        return binding.root
    }
     abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): viewBinding?


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}//end of base fragment