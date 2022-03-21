package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.gallaryapplication.R
import com.example.gallaryapplication.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()


    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding? {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("fragment", "login fragment is called")

        binding.loginButton.setOnClickListener {
            val date1 = viewModel.currentDateTime
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToBottomNav())
            viewModel.saveLoggedinTime(date1)
        }


    }//end of onViewCreated

    override fun handleBackPressed() {
        activity?.let { it ->
            it.supportFragmentManager?.popBackStack(
                R.id.launchingFragment,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            finishAffinity(it)
        };

    }
}