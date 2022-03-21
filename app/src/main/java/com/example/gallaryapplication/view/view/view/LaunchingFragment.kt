package com.example.gallaryapplication.view.view.view


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gallaryapplication.databinding.FragmentLaunchingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchingFragment : BaseFragment<FragmentLaunchingBinding>() {

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()




    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLaunchingBinding? {
        return FragmentLaunchingBinding.inflate(inflater, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("fragment", "lauching fragment is called")

        mainActivityViewModel.isLogIn.observe(viewLifecycleOwner) { isLogin ->
            if (isLogin) {
                onSessionIn()
            } else {
                onSessionLogout()
            }
        }


    }


    private fun onSessionIn() {
        findNavController().navigate(LaunchingFragmentDirections.actionLaunchFragmentToBottomNavFragment())

    }

    private fun onSessionLogout() {
        findNavController().navigate(LaunchingFragmentDirections.actionLaunchFragmentToLoginFragment())

    }

}