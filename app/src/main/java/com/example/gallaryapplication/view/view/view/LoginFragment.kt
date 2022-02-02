package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentLoginBinding
import com.example.gallaryapplication.view.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*


@AndroidEntryPoint
class LoginFragment : BaseFragment() {


    private val loginViewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        onBackpressedinLoginFragment()
        onBackpressed(R.id.loginFragment)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }//end of onCreateView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginbutton.setOnClickListener {
            val date1 = loginViewModel.currentDateTime
            Log.d("date1", "date is in loginbutton is $date1")
            findNavController().navigate(LoginFragmentDirections.actionlogintophotofragment())
            loginViewModel.saveLoggedinTime(date1)
        }


    }//end of onViewCreated

    //to avoid memory leak
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}