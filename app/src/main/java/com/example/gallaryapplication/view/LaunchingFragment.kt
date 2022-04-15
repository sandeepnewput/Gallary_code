package com.example.gallaryapplication.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.gallaryapplication.R
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

        mainActivityViewModel.isLogIn.observe(viewLifecycleOwner) { isLogin ->
            if (isLogin) {
                onSessionIn()
            } else {
                onSessionLogout()
            }
        }
    }

    private fun onSessionIn() {
        findNavController().navigate(
            R.id.action_launch_to_bottomNav,
            null,
            navOptions {
                Builder().setPopUpTo(R.id.launchingFragment, true)
            }
        )
    }

    private fun onSessionLogout() {
        findNavController().navigate(LaunchingFragmentDirections.actionLaunchFragmentToLoginFragment())
    }
}