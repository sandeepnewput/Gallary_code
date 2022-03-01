package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gallaryapplication.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding? {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val date1 = viewModel.currentDateTime
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToBottomNav())
            viewModel.saveLoggedinTime(date1)
        }


    }//end of onViewCreated

    override fun backPressed() {
        Log.d("backpress","backpressed of loginfragment")
    }


}