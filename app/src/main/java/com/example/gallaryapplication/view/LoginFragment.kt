package com.example.gallaryapplication.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
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

        binding.loginButton.setOnClickListener {
            viewModel.login("sandeep", "1234")
        }

        viewModel.isLogin.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(activity, "Welcome back ", Toast.LENGTH_LONG).show()
                findNavController().navigate(
                    R.id.action_login_to_bottomNav,
                    null,
                    navOptions {
                        Builder().setPopUpTo(R.id.loginFragment, true)
                    }
                )
            } else {
                Toast.makeText(activity, "username password incorrect", Toast.LENGTH_LONG).show()
            }
        }

    }//end of onViewCreated

    override fun handleBackPressed() {
        activity?.let {
            it.moveTaskToBack(true)
        }
    }
}